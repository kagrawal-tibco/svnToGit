import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { ArtifactProblemsService } from './artifact-problems.service';

import { environment } from '../../environments/environment';
import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { UserService } from '../core/user.service';
import { Artifact, ArtifactType } from '../models/artifact';
import { Result } from '../models/dto';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';
@Injectable()
export class BEArtifactProblemsService extends ArtifactProblemsService {

  get rowData(): Array<any> {
    return BEArtifactProblemsService._rowData;
  }

  set rowData(value: Array<any>) {
    BEArtifactProblemsService._rowData = value;
  }

  get artifact() {
    return this._artifact;
  }

  set artifact(artifact: Artifact) {
    this._artifact = artifact;
  }

  get errors() {
    return this._errors;
  }

  set errors(errors: Result[]) {
    this._errors = errors;
  }

  get warnings() {
    return this._warnings;
  }

  set warnings(warnings: Result[]) {
    this._warnings = warnings;
  }

  get hasWarnings() {
    return this._warnings.length > 0;
  }

  get hasProblems() {
    return this._results.length > 0;
  }

  get problems() {
    return this._results;
  }

  get entryClicked(): Observable<any> {
    return this._entryClicked;
  }

  get supportProblemsView() {
    if (environment.enableBEUI) {
      return true;
    } else {
      return this._artifact != null && this._artifact.type.supportValidate;
    }
  }
  static _rowData: Array<any> = new Array<any>();
  protected static _projectProblemsMap: Map<string, Map<string, Array<Result>>>
    = new Map<string, Map<string, Array<Result>>>();
  protected _artifact: Artifact;
  protected _results: Result[] = [];
  protected _errors: Result[] = [];
  protected _warnings: Result[] = [];
  protected _entryClicked = new Subject<any>();

  constructor(
    protected artifactService: ArtifactService,
    protected alert: AlertService,
    protected user: UserService,
    protected multiTabService: MultitabEditorService,
    public i18n: I18n
  ) {
    super(artifactService, alert, user, multiTabService);
  }

  public refresh(artifact: Artifact, validationLevel?: string) {
    this._artifact = artifact;
    this.maintainProblems(artifact, validationLevel);
    this._errors = [];
    this._warnings = [];
    this._results.forEach(r => {
      if (r.error) {
        this._errors.push(r);
      } else {
        this._warnings.push(r);
      }
    });
  }

  beProblems(): any[] {
    const results = this.problems;
    this.rowData = [];
    for (let i = 0; i < results.length; i++) {
      const locationObject = JSON.parse(JSON.stringify(results[i]));
      const details = results[i].message.split('@');
      const typeString = this.getTypeString(details[3]);
      const row = {
        description: details[1] ? details[1] : '',
        project: locationObject.projectName,
        path: locationObject.artifactPath,
        location: details[2] ? details[2].split('-')[1] : '',
        problemType: results[i].error ? 'Error' : 'Warning',
        type: typeString,
        result: results[i]
      };
      this.rowData.push(row);
    }
    return this.rowData;
  }

  getTypeString(type: String): String {

    if (type) {
      let tempString = type.split('-')[1];

      tempString = tempString.trim();

      if (tempString === '1') {
        return 'Syntax';
      } else {
        return tempString;
      }
    } else {
      return '';
    }
  }

  onEntryClick(entry: Result) {
    if (this._artifact == null) {
      this._artifact = this.multiTabService.activeTab ? this.multiTabService.activeTab.payload : null;
    }
    const locations = this.toLocations(entry);
    if (locations) {
      const activeArtifact = this.multiTabService.activeTab ? this.multiTabService.activeTab.payload : null;
      const locationsObject = JSON.parse(JSON.stringify(locations[0]));
      if (activeArtifact && activeArtifact != null && activeArtifact.path === locationsObject.artifactPath) {
        setTimeout(() => {
          this._entryClicked.next(locations);
        });
      } else {
        this.openArtifact(locationsObject, entry)
          .then((openedSuccesfully) => {
            if (openedSuccesfully) {
              setTimeout(() => {
                this._entryClicked.next(locations);
              });
            }
          });
      }
    }
  }

  deleteProblem(entry: Result) {
    const locationsObject = JSON.parse(JSON.stringify(this.toLocations(entry)));
    const artifactPath = locationsObject[0].artifactPath;
    const projectName = locationsObject[0].projectName;
    const artifactProblemMap = BEArtifactProblemsService._projectProblemsMap.get(projectName);
    const artifactProblems: Result[] = artifactProblemMap.get(artifactPath);
    for (let i = 0; i < artifactProblems.length; i++) {
      if (_.isEqual(artifactProblems[i], entry)) {
        artifactProblems.splice(i, 1);
        break;
      }
    }
    artifactProblemMap.set(artifactPath, artifactProblems);
    BEArtifactProblemsService._projectProblemsMap.set(projectName, artifactProblemMap);
    this._results = [];
    artifactProblemMap.forEach((problems: Array<Result>, artifactPath: string) => {
      BEArtifactProblemsService._projectProblemsMap.forEach((artifactMap: Map<string, Array<Result>>, projectId: string) => {
        for (let i = 0; i < problems.length; i++) {
          this._results.push(problems[i]);
        }
      });
    });
    this._errors = [];
    this._warnings = [];
    this._results.forEach(r => {
      if (r.error) {
        this._errors.push(r);
      } else {
        this._warnings.push(r);
      }
    });
    this.beProblems();
  }

  deleteAllProblems() {
    BEArtifactProblemsService._projectProblemsMap = new Map<string, Map<string, Result[]>>();
    this._results = [];
    this._errors = [];
    this._warnings = [];
    this.beProblems();
  }

  toLocations(entry: Result) {
    if (entry.locations && entry.locations.length !== 0) {
      const entryJSON = JSON.parse(JSON.stringify(entry));
      const locations = entry.locations.map(location => {
        if (environment.enableBEUI) {
          if (entryJSON.artifactType === 'rulefunctionimpl') {
            const rowId = location[0];
            const colId = location[1];
            return {
              rowId: rowId,
              colId: colId,
              artifactType: entryJSON.artifactType,
              artifactPath: entryJSON.artifactPath,
              projectName: entryJSON.projectName
            };
          } else {
            const rowId = Object.keys(location)[0];
            return {
              rowId: rowId,
              colId: location[rowId],
              artifactType: entryJSON.artifactType,
              artifactPath: entryJSON.artifactPath,
              projectName: entryJSON.projectName
            };
          }
        } else {
          const rowId = Object.keys(location)[0];
          return {
            rowId: rowId,
            colId: location[rowId]
          };
        }
      });
      return locations;
    } else {
      return null;
    }
  }

  protected maintainProblems(artifact: Artifact, validationLevel: string) {
    const projectId = artifact.projectId;
    let artifactMap: Map<string, Array<Result>>;
    let artifactPath: string = artifact.path;
    if (BEArtifactProblemsService._projectProblemsMap.has(projectId)) {
      artifactMap = BEArtifactProblemsService._projectProblemsMap.get(projectId);
    } else {
      artifactMap = new Map<string, Array<Result>>();
    }
    if (validationLevel === 'project') {
      artifactMap = new Map<string, Array<Result>>();
      const projectProblems: Result[] = artifact.analyzeResult;
      for (let i = 0; i < projectProblems.length; i++) {
        const location = this.toLocations(projectProblems[i]);
        const locationJsonObject = JSON.parse(JSON.stringify(location));
        artifactPath = locationJsonObject[0].artifactPath;
        let artifactProblems: Result[];
        if (artifactMap.has(artifactPath)) {
          artifactProblems = artifactMap.get(artifactPath);
        } else {
          artifactProblems = new Array<Result>();
        }
        artifactProblems.push(projectProblems[i]);
        artifactMap.set(artifactPath, artifactProblems);
      }
    } else if (validationLevel === 'artifact') {
      if (artifact.analyzeResult) {
        artifactMap.set(artifactPath, artifact.analyzeResult);
      } else {
        artifactMap.set(artifactPath, new Array<Result>());
      }
    }
    BEArtifactProblemsService._projectProblemsMap.set(projectId, artifactMap);
    this._results = [];
    BEArtifactProblemsService._projectProblemsMap.forEach((artifactMap: Map<string, Array<Result>>, projectId: string) => {
      artifactMap.forEach((problems: Array<Result>, artifactPath: string) => {
        for (let i = 0; i < problems.length; i++) {
          this._results.push(problems[i]);
        }
      });
    });

    if (validationLevel == null) {
      artifact.analyzeResult = this._results;
    }

    this._errors = [];
    this._warnings = [];
    this._results.forEach(r => {
      if (r.error) {
        this._errors.push(r);
      } else {
        this._warnings.push(r);
      }
    });
    this.beProblems();
  }

  protected openArtifact(locationsObject: any, entry: Result): Promise<boolean> {
    const id: string = this.user.currentUser().userName
      + '@' + locationsObject.projectName
      + '@' + locationsObject.artifactPath
      + '@' + locationsObject.artifactType;
    return this.artifactService.getCheckedOutArtifactWithContent(id)
      .then((artifact) => {
        if (artifact) {
          this._artifact = artifact;
          if (artifact.analyzeResult === undefined) {
            artifact.analyzeResult = new Array<Result>();
          }
          if (entry) {
            artifact.selectedProblem = entry;
          }
          this.multiTabService.activateTab(artifact)
            .then((rep) => { });
        } else {
          this.alert.flash(this.i18n('This artifact is not checked out'), 'warning');
        }
      }).then(() => {
        return true;
      });
  }
}
