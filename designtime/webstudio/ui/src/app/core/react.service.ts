
import { Injectable } from '@angular/core';

import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { AlertService, AlertType } from './alert.service';
import { ArtifactService } from './artifact.service';
import { AuthStateService } from './auth-state.service';
import { LifecycleService } from './lifecycle.service';
import { Logger } from './logger.service';
import { SettingsService } from './settings.service';
import { Message, WebSocketService } from './websocket.service';

import { DashboardService } from '../dashboard/dashboard.service';
import { CandidateStatus } from '../models/commit-candidate';
import { Notification } from '../models/notification';
import { NotificationCenterService } from '../notification-center/notification-center.service';

// --- internal dto begins -----
type TopicKind = 'Artifact' | 'Commit' | 'Project' | 'DeploymentHistory';
interface IdStatusPair {
  id: string;
  status: CandidateStatus;
}
interface Topic {
  kind: TopicKind;
  id: string;
  idAndStatus?: IdStatusPair[];
}
interface InboundNotification {
  id: string;
  createdTime: string;
  read: boolean;
  content: string;
}

export type InboundTopicAction = 'CREATE' |
  /**
   * Worklist related actions
   */
  'APPROVE' | 'REJECT' | 'CANCEL' |
/**
 * Deployment related actions
 */ 'START' | 'FAIL' | 'SUCCESS' |
/**
 * SCM related actions
 */ 'EXTERNAL_UPDATE' |
 /**
 * Lock management
 */ 'LOCKED' | 'UNLOCKED';

interface InboundTopicMessage {
  topic: Topic;
  message: string;
  action: InboundTopicAction;
}
// --- internal dto ends -----

/**
 * Service to orchesture reactions to message received from server
 */
@Injectable()
export class ReactService {
  _deploymentStatusChange = new Subject<string>();
  constructor(
    protected log: Logger,
    protected authState: AuthStateService,
    protected websocket: WebSocketService,
    protected notification: NotificationCenterService,
    protected dashboard: DashboardService,
    protected artifact: ArtifactService,
    protected lifecycle: LifecycleService,
    protected alert: AlertService,
    protected settings: SettingsService
  ) { }

  init() {
    this.initImpl();
  }

  terminate() {
    return this.websocket.reset();
  }

  private initImpl() {
    this.websocket.init()
      .then(() => {
        this.notification.init();
        this.websocket.subscribe(msg => this.handleMessage(msg));
      });
  }

  private handleMessage(msg: Message) {
    switch (msg.kind) {
      case 'ERROR':
        this.log.err(msg.payload);
        break;
      case 'INFO':
        this.log.log(msg.payload);
        break;
      case 'NOTIFICATION':
        this.handleNotification(msg.payload, true);
        break;
      case 'INIT':
        this.handleNotification(msg.payload, false);
        break;
    }
  }

  private handleNotification(content: string, doSync?: boolean) {
    const inboundNotification: InboundNotification = JSON.parse(content);
    const inboundTopicMessage: InboundTopicMessage = JSON.parse(inboundNotification.content);

    const notification: Notification = <Notification>{
      id: inboundNotification.id,
      read: inboundNotification.read,
      content: inboundTopicMessage.message,
      link: ['/dashboard'],
      timestamp: inboundNotification.createdTime
    };

    if (inboundTopicMessage.topic.kind === 'DeploymentHistory') {
      notification.link = ['/deployment/history', inboundTopicMessage.topic.id];
      switch (inboundTopicMessage.action) {
        case 'START':
          notification.severity = 'warning';
          break;
        case 'FAIL':
          notification.severity = 'danger';
          break;
        case 'SUCCESS':
          notification.severity = 'success';
          break;
      }
    }

    if (inboundTopicMessage.topic.kind === 'Artifact'
      || inboundTopicMessage.topic.kind === 'Commit') {
      switch (inboundTopicMessage.action) {
        case 'CREATE':
          notification.icon = 'fa fa-plus-circle';
          notification.severity = 'info';
          break;
        case 'APPROVE':
          notification.icon = 'fa fa-thumbs-up';
          notification.severity = 'success';
          break;
        case 'CANCEL':
          notification.icon = 'fa fa-times-circle';
          notification.severity = 'info';
          break;
        case 'REJECT':
          notification.icon = 'fa fa-thumbs-down';
          notification.severity = 'warning';
          break;
        case 'LOCKED':
          notification.icon = 'fa fa-lock';
          notification.severity = 'warning';
          break;
        case 'UNLOCKED':
          notification.icon = 'fa fa-unlock';
          notification.severity = 'warning';
          break;
        case 'EXTERNAL_UPDATE':
          notification.icon = 'fa fa-level-down-alt';
          notification.severity = 'info';
          break;
        default:
          notification.icon = 'fa fa-info';
          notification.severity = 'info';
      }
    }

    this.notification.prepend(notification);
    let title = '';
    const alertHtml = [];
    alertHtml.push(`<div align="left">`);
    alertHtml.push(notification.content);
    alertHtml.push('<br>');
    if (doSync) {
      switch (inboundTopicMessage.topic.kind) {
        case 'Artifact':
          if (inboundTopicMessage.action === 'LOCKED' || inboundTopicMessage.action === 'UNLOCKED') {
            alertHtml.push('<br>Your local project has been updated to reflect these changes<br>');
            const id = inboundTopicMessage.topic.id;
            const currArt = this.getCheckedOutArtifact(id);
            if (!currArt) {
              this.artifact.getArtifactLatest(id)
                .then(lockArt => {
                  this.artifact.markAsRerender(id, lockArt.revisionNumber);
                });
            } else {
              if (currArt.isCheckedOutArtifact) {
                this.artifact.markCheckoutAsRerender(currArt);
              } else {
                // nothing needs to happen, artifact is not checked out?
                this.artifact.getArtifactLatest(id)
                  .then(lockArt => currArt.updateMeta(lockArt));
              }
            }
          }
          if (inboundTopicMessage.action === 'EXTERNAL_UPDATE') {
            title = 'External SCM Updates';
            //            alertHtml.push('Artifacts have been updated in an external SCM repository.<br>');
            alertHtml.push('<br>Your local project has been updated to reflect these changes<br>');
            if (inboundTopicMessage.topic.idAndStatus) {
              alertHtml.push('Details:<br>');
              let added = 0;
              let deleted = 0;
              let modified = 0;
              inboundTopicMessage.topic.idAndStatus.forEach(extart => {
                switch (extart.status) {
                  case 'ADDED':
                    added++;
                    break;
                  case 'DELETED':
                    deleted++;
                    break;
                  case 'MODIFIED':
                    modified++;
                    break;
                }
              });
              if (added > 0) {
                alertHtml.push(`${added} file(s) added<br>`);
              }
              if (modified > 0) {
                alertHtml.push(`${modified} file(s) modified<br>`);
              }
              if (deleted > 0) {
                alertHtml.push(`${deleted} file(s) deleted<br>`);
              }
              inboundTopicMessage.topic.idAndStatus.forEach(art => {
                const id = art.id;
                const status = art.status;
                switch (status) {
                  case 'ADDED':
                    const currArt = this.getCheckedOutArtifact(id);
                    if (!currArt) {
                      this.artifact.getArtifactLatest(id)
                        .then(toAdd => {
                          this.artifact.markAsAdded(toAdd);
                        });
                    } else {
                      if (currArt.isCheckedOutArtifact) {
                        this.artifact.markCheckoutAsRerender(currArt);
                      } else {
                        // nothing needs to happen, artifact is not checked out?
                        this.artifact.getArtifactLatest(id)
                          .then(toAdd => currArt.updateMeta(toAdd));
                      }
                    }
                    break;
                  case 'DELETED':
                    this.artifact.getArtifactRevisionWithContent(id, -1)
                      .then(toDelete => {
                        this.artifact.markAsDeleted(toDelete);
                      });
                    this.artifact.checkedOutArtifactCache.forEach((ca, _) => {
                      if (ca.parentId === id) {
                        this.artifact.markAsDeleted(ca);
                      }
                    });
                    break;
                  case 'MODIFIED':
                    let found = false;
                    this.artifact.checkedOutArtifactCache.forEach((ca, _) => {
                      if (ca.parentId === id) {
                        found = true;
                        this.artifact.markAsStale(ca.id);
                      }
                    });
                    if (!found) {
                      this.artifact.getArtifactLatest(id).then(latest => {
                        if (latest) {
                          if (latest.isCheckedOutArtifact) {
                            // artifact has not yet been cached, but it is checked out.
                            this.artifact.markIDAsUpdated(latest.parentId);
                          } else {
                            this.artifact.markIDAsUpdated(latest.id);
                          }
                        }
                      });
                    } else {
                      this.artifact.markAsRerender(id, -1);
                    }
                    break;
                }
              });
            }
          }
          alertHtml.push('</div>');
          if (notification.icon) {
            alertHtml.push(`<i class="${notification.icon} notification-overlay"/>`);
          }
          const str = alertHtml.join('');
          this.alert.flash(str, 'info', true, 5000, title);
          break;
        case 'Commit':
          let alertLevel = 'info' as AlertType;
          if (inboundTopicMessage.action === 'REJECT') {
            alertLevel = 'warning';
            title = 'Action Needed';
          }
          if ((inboundTopicMessage.action === 'APPROVE') || (inboundTopicMessage.action === 'REJECT')) {
            const commitId = inboundTopicMessage.topic.id;
            if (commitId) {
              this.lifecycle.getCommit(commitId).pipe(
                map(commit => commit.changeList))
                .toPromise()
                .then(candidates => candidates.forEach(candidate => {
                  if (candidate.status === 'DELETED') {
                    this.artifact.getArtifactRevisionWithContent(candidate.parentId, candidate.parentRevision)
                      .then(toDelete => this.artifact.markAsDeleted(toDelete));
                    this.artifact.checkedOutArtifactCache.forEach((ca, _) => {
                      if (ca.parentId === candidate.parentId) {
                        this.artifact.markAsDeleted(ca);
                      }
                    });
                  } else if (candidate.status === 'ADDED') {
                    if (candidate.parentId) {
                      //  need to loop through all checked out artifacts to see if the parentIds match -- the id on candidate DOES NOT MATCH
                      const currArt = this.getCheckedOutArtifact(candidate.parentId);
                      if (!currArt) {
                        if (candidate.parentRevision) {
                          this.artifact.getArtifactRevisionWithContent(candidate.parentId, candidate.parentRevision)
                            .then(toAdd => this.artifact.markAsAdded(toAdd));
                        } else {
                          // it is possible that this artifact already exists in the workspace, for instance
                          // in the Projects group in the content explorer.  Subscribers to this change need
                          // to check to ensure that the artifact is added when appropriate, and replaced
                          // when it already exists
                          this.artifact.getArtifactLatest(candidate.parentId)
                            .then(toAdd => this.artifact.markAsAdded(toAdd));
                        }
                      } else {
                        if (currArt.checkedOutFromRevision || inboundTopicMessage.action === 'REJECT') {
                          this.artifact.markCheckoutAsRerender(currArt);
                        } else {
                          // this can happen upon initial creation of a file -- no version exists yet
                          this.artifact.getArtifactLatest(candidate.parentId)
                            .then(toAdd => {
                              currArt.cacheStale = true;
                              this.artifact.markCheckoutAsRerender(currArt);
                            });
                        }
                      }
                    }
                  } else {
                    let found = false;
                    this.artifact.checkedOutArtifactCache.forEach((ca, _) => {
                      if (ca.parentId === candidate.parentId) {
                        found = true;
                        this.artifact.markAsStale(ca.id);
                      } else if (ca.path === candidate.path) {
                        found = true;
                        this.artifact.markAsStale(ca.id);
                      }
                    });
                    if (candidate.parentId && candidate.parentRevision) {
                      if (!found) {
                        // artifact has not yet been cached, but it is checked out.
                        this.artifact.markIDAsUpdated(candidate.parentId);
                      } else {
                        this.artifact.markAsRerender(candidate.parentId, candidate.parentRevision);
                      }
                    }
                  }
                  this.dashboard.updateCommits();
                }));
            }
          }
          alertHtml.push('</div>');
          if (this.settings.latestSettings.displayWorklistNotifications) {
            if (notification.icon) {
              alertHtml.push(`<i class="${notification.icon} notification-overlay"/>`);
            }
            this.alert.flash(alertHtml.join(''), alertLevel, true, 5000, title);
          }
          break;
        case 'DeploymentHistory':
          const recordId = inboundTopicMessage.topic.id;
          this._deploymentStatusChange.next(recordId);
          break;
      }
    }
  }

  private getCheckedOutArtifact(artId: string) {
    let found = null;
    this.artifact.checkedOutArtifactCache.forEach(art => {
      if (art.parentId === artId) {
        found = art;
        return;
      }
    });
    return found;
  }

  get deploymentStatusChange(): Observable<string> {
    return this._deploymentStatusChange;
  }
}
