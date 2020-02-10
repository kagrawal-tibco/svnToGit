
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as moment from 'moment';
import { map } from 'rxjs/operators';

import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ProjectService } from '../core/project.service';
import { ReactService } from '../core/react.service';
import { RestService } from '../core/rest.service';
import { Artifact } from '../models/artifact';
import {
  DeploymentDescriptorItem,
  DeploymentDescriptorRecord,
  DeploymentHistoryRecord,
  UpdateDeploymentDescriptorRequest
} from '../models/dto';

const FORMAT = 'YYYY-MM-DD HH:mm:ssZ';

export type TargetType = 'STREAMBASE_URI' | 'STREAMBASE_SERVICE_NAME';

@Injectable()
export class DeploymentService {

  public deploymentHistoryCache = new Map<string, DeploymentHistoryRecord>();

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    private project: ProjectService,
    private react: ReactService,
    private rest: RestService,
    public i18n: I18n
  ) { }

  createNewDescriptor(projectName: string, artifact: Artifact): DeploymentDescriptorItem {
    return <DeploymentDescriptorItem>{
      type: 'STREAMBASE_SERVICE_NAME',
      amsProjectName: projectName,
      amsDecisionTablePath: artifact.path,
      amsDecisionTableVersion: -1,
    };
  }

  getAllDescriptors(projectName: string, artifactPath: string): Promise<DeploymentDescriptorRecord[]> {
    const url = `/deployment`;
    return this.rest.get(url)
      .toPromise()
      .then(res => {
        if (res.ok()) {
          return res.record
            .map(r => <DeploymentDescriptorRecord>r)
            .filter(descriptor => descriptor.amsProjectName === projectName && descriptor.amsDecisionTablePath === artifactPath);
        } else {
          return null;
        }
      });
  }

  saveDescriptor(descriptor: DeploymentDescriptorItem): Promise<DeploymentDescriptorRecord> {
    const url = `/deployment/create`;
    const payload: UpdateDeploymentDescriptorRequest = {
      deploymentDescriptor: <DeploymentDescriptorItem>{
        type: descriptor.type,
        amsProjectName: descriptor.amsProjectName,
        amsDecisionTablePath: descriptor.amsDecisionTablePath,
        amsDecisionTableVersion: descriptor.amsDecisionTableVersion,
        description: descriptor.description,
        streamBaseTarget: descriptor.streamBaseTarget,
        decisionTableOperatorName: descriptor.decisionTableOperatorName,
      }
    };
    return this.rest.post(url, payload)
      .toPromise()
      .then(res => {
        if (res.ok()) {
          return res.record[0];
        } else {
          return null;
        }
      });
  }

  cancelDeployment(id: string): Promise<DeploymentHistoryRecord> {
    return this.rest.put(`/deployment/${id}/cancel`, {})
      .toPromise()
      .then(res => {
        if (res.ok()) {
          return res.record[0];
        }
      });
  }

  getDeploymentHistoryById(id: string): Promise<DeploymentHistoryRecord> {
    return this.rest.get(`/deployment/history/${id}`).pipe(
      map(res => res.record[0]))
      .toPromise();
  }

  getDeploymentHistory(artifact: Artifact): Promise<DeploymentHistoryRecord[]> {
    const id = artifact.isCheckedOutArtifact ? artifact.parentId : artifact.id;
    const sorter = (a: DeploymentHistoryRecord, b: DeploymentHistoryRecord) => {
      if (b.deployTime === a.deployTime) {
        return b.createTime - a.createTime;
      } else {
        return b.deployTime - a.deployTime;
      }
    };
    return this.rest.get(`/deployment/history?action=DEPLOY&artifactId=${id}`).pipe(
      map(res => res.record.sort(sorter)))
      .toPromise();
  }

  getDeploymentHistoryFromArtifactId(id: string): Promise<DeploymentHistoryRecord[]> {
    const sorter = (a: DeploymentHistoryRecord, b: DeploymentHistoryRecord) => {
      if (b.deployTime === a.deployTime) {
        return b.createTime - a.createTime;
      } else {
        return b.deployTime - a.deployTime;
      }
    };
    return this.rest.get(`/deployment/history?action=DEPLOY&artifactId=${id}`).pipe(
      map(res => res.record.sort(sorter)))
      .toPromise();
  }

  deploy(id: string, deployTime: Date): Promise<boolean> {
    let url: string;
    if (deployTime) {
      url = `/deployment/${id}/deploy?deployTime=${deployTime.getTime()}`;
    } else {
      url = `/deployment/${id}/deploy`;
    }
    return this.rest.put(url, {})
      .toPromise()
      .then(res => res.ok());
  }

  remove(id: string): Promise<boolean> {
    return this.rest.delete(`/deployment/${id}/delete`)
      .toPromise()
      .then(res => res.ok());
  }

  getIndicator(entry: DeploymentHistoryRecord): string {
    if (entry.deployStatus === 'PENDING') {
      return 'fa fa-clock-o fa-spin fa-fw';
    } else if (entry.deployStatus === 'IN_PROGRESS') {
      return 'fa fa-circle-o-notch fa-spin fa-fw';
    } else if (entry.deployStatus === 'SUCCESS') {
      return 'fa fa-check';
    } else if (entry.deployStatus === 'FAILURE') {
      return 'fa fa-exclamation-circle';
    } else if (entry.deployStatus === 'CANCELLED') {
      return 'fa fa-ban';
    } else {
      throw Error(this.i18n('Unable to recognize status: {{err}}', { err: entry.deployStatus }));
    }
  }

  getLabelStyle(entry: DeploymentHistoryRecord): string {
    if (entry.deployStatus === 'PENDING') {
      return 'callout callout-info';
    } else if (entry.deployStatus === 'IN_PROGRESS') {
      return 'callout callout-warning';
    } else if (entry.deployStatus === 'SUCCESS') {
      return 'callout callout-success';
    } else if (entry.deployStatus === 'FAILURE') {
      return 'callout callout-danger';
    } else if (entry.deployStatus === 'CANCELLED') {
      return 'callout callout-default';
    } else {
      throw Error(this.i18n('Unable to recognize status: {{err}}', { err: entry.deployStatus }));
    }
  }

  getDuration(entry: DeploymentHistoryRecord): string {
    let interval: number;
    if (entry.deployStatus === 'IN_PROGRESS') {
      interval = new Date().getTime() - entry.lastUpdateTime;
    } else if (entry.deployStatus === 'PENDING') {
      interval = entry.deployTime - new Date().getTime();
    } else if (entry.deployStatus === 'CANCELLED') {
      interval = entry.lastUpdateTime - entry.createTime;
    } else {
      interval = entry.lastUpdateTime - entry.deployTime;
    }
    interval = interval > 0 ? interval : 0;
    const hours: number = interval / 1000 / 60 / 60;
    const minutes: number = interval / 1000 / 60 % 60;
    const seconds: number = interval / 1000 % 60;
    return `${this.leftpad(hours)}:${this.leftpad(minutes)}:${this.leftpad(seconds)}`;
  }

  getCreateTime(entry: DeploymentHistoryRecord): string {
    return moment(entry.createTime).format(FORMAT);
  }

  getDeployTime(entry: DeploymentHistoryRecord): string {
    return moment(entry.deployTime).format(FORMAT);
  }

  getLastUpdateTime(entry: DeploymentHistoryRecord): string {
    return moment(entry.lastUpdateTime).format(FORMAT);
  }

  getRevisionString(rev: number) {
    if (rev === -1) {
      return 'Latest';
    } else {
      return rev.toString();
    }
  }

  getStatusBrief(entry: DeploymentHistoryRecord): string {
    switch (entry.deployStatus) {
      case 'SUCCESS':
        return 'Success';
      case 'FAILURE':
        return 'Failure';
      case 'PENDING':
        return 'Pending';
      case 'IN_PROGRESS':
        return 'In progress';
      case 'CANCELLED':
        return 'Cancelled';
      default:
        throw Error(this.i18n('Unable to recognize status: {{err}}', { err: entry.deployStatus }));
    }
  }

  getEntityBrief(artifact: Artifact, descriptor: DeploymentDescriptorItem) {
    const revision = descriptor.amsDecisionTableVersion;
    return `${artifact.name} (${revision === -1 ? 'latest' : revision})`;
  }

  getTargetBrief(artifact: Artifact, descriptor: DeploymentDescriptorItem) {
    let ret = `${descriptor.streamBaseTarget}`;
    if (descriptor.type !== 'STREAMBASE_SERVICE_NAME') {
      ret += `/${descriptor.decisionTableOperatorName}`;
    }
    return ret;
  }

  get availableTargetTypes(): TargetType[] {
    return ['STREAMBASE_URI', 'STREAMBASE_SERVICE_NAME'];
  }

  getTargetTypeDisplay(type: TargetType): string {
    switch (type) {
      case 'STREAMBASE_URI':
        return 'StreamBase URI';
      case 'STREAMBASE_SERVICE_NAME':
        return 'StreamBase Service Name';
      default:
        throw Error(this.i18n('Unable to recognize target type: {{type}}', { type: type }));
    }
  }

  private leftpad(i: number): string {
    if (i < 10) {
      return `0${Math.floor(i)}`;
    } else {
      return Math.floor(i).toString();
    }
  }

}
