
import { Injectable } from '@angular/core';

import { filter } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { ArtifactService } from '../../core/artifact.service';
import { RestService } from '../../core/rest.service';
import { UserService } from '../../core/user.service';
import { Artifact } from '../../models/artifact';

export class VisitEntry {
  artifact: Artifact;
  key: string;
}

const MAX_ENTRY_NUMBER = 20;
@Injectable()
export class VisitHistoryService {
  history: VisitEntry[] = [];
  selected: VisitEntry;
  artifactPositionMap: Map<string, Artifact>;
  constructor(
    private artifact: ArtifactService,
    private rest: RestService,
    private user: UserService
  ) {
    this.artifactPositionMap = new Map<string, Artifact>();
    this.artifact.stateChanges().pipe(
      filter(change => {
        return change.artifact != null &&
          (change.state === 'DELETED' || change.state === 'DISPOSED' || change.state === 'MODIFIED'
            || change.state === 'CHECKED-OUT' || change.state === 'ADDED');
      })).subscribe(change => {
        switch (change.state) {
          case 'DELETED':
            this.remove(change.artifact);
            return;
          case 'DISPOSED':
            return this.artifact.getArtifactLatest(change.artifact.parentId)
              .then(latest => this.replace(change.artifact, latest));
          case 'MODIFIED':
            if (change.updated) {
              return this.replace(change.artifact, change.updated);
            }
            return;
          case 'CHECKED-OUT':
            if (change.artifact && change.updated) {
              this.replace(change.artifact, change.updated);
            }
        }
      });
  }

  refresh() {
    this.initImpl();
  }

  clear() {
    // this.history = [];
    this.selected = null;
  }

  activate(artifact: Artifact, addOnTail?: boolean): Promise<boolean> {
    return Promise.resolve().then(() => {
      if (artifact) {
        const idx = this.getEntryIdxByKey(artifact.id);
        if (idx !== -1) {
          const found = this.history[idx];
          this.history.splice(idx, 1);
          this.history.unshift(found);
          this.selected = found;
        } else if (addOnTail) {
          const entry: VisitEntry = { artifact: artifact, key: artifact.id };
          this.history.push(entry);
          this.selected = entry;
          if (this.history.length > MAX_ENTRY_NUMBER) {
            this.history.pop();
          }
        } else {
          const entry: VisitEntry = { artifact: artifact, key: artifact.id };
          this.history.unshift(entry);
          this.selected = entry;
          if (this.history.length > MAX_ENTRY_NUMBER) {
            this.history.pop();
          }
        }
      } else {
        this.selected = null;
      }
      return true;
    });
  }

  remove(artifact: Artifact) {
    const idx = this.getEntryIdxByKey(artifact.id);
    if (idx !== -1) {
      this.history.splice(idx, 1);
    }
    return true;
  }

  removeIf(filter: (entry: VisitEntry) => boolean) {
    this.history = this.history.filter(filter);
  }

  replace(lhs: Artifact, rhs: Artifact) {
    this.remove(lhs);
    this.activate(rhs);
  }

  getEntryIdxByKey(id: string) {
    return this.history.findIndex(e => e.key === id);
  }

  private initImpl() {
    if (this.history.length === 0) {
      this.selected = null;
      this.rest.get(`recentlyOpened.json`, undefined, false)
        .toPromise()
        .then((response) => {
          if (response.ok()) {
            const record = response.record;
            if (record) {
              const userName = this.user.currentUser().userName;
              for (let i = 0; i < record.length; i++) {
                const artifactPathArray = record[i].artifactPath.split('.');
                const artifactId = userName
                  + '@' + record[i].projectName
                  + '@' + artifactPathArray[0]
                  + '@' + artifactPathArray[1];
                this.artifact.getCheckedOutArtifactWithContent(artifactId)
                  .then((artifact) => {
                    this.artifactPositionMap.set(artifact.id, artifact);
                    // this.activate(artifact, true);
                    if (this.artifactPositionMap.size === record.length) {
                      for (let index = 0; index < record.length; index++) {
                        const artifactPathArray = record[index].artifactPath.split('.');
                        const artifactId = userName
                          + '@' + record[index].projectName
                          + '@' + artifactPathArray[0]
                          + '@' + artifactPathArray[1];
                        this.activate(this.artifactPositionMap.get(artifactId), true);
                        this.artifactPositionMap.delete(artifactId);
                      }
                    }
                  });
              }
            }
          }
        });
    }
  }
}
