import { Injectable } from '@angular/core';

import * as _ from 'lodash';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { environment } from '../../environments/environment';
import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { UserService } from '../core/user.service';
import { Artifact, ArtifactType } from '../models/artifact';
import { Result } from '../models/dto';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';

@Injectable()
export class ArtifactProblemsService {
  protected _artifact: Artifact;
  protected _results: Result[] = [];
  protected _errors: Result[] = [];
  protected _warnings: Result[] = [];
  protected _entryClicked = new Subject<any>();

  constructor(
    protected artifactService: ArtifactService,
    protected alert: AlertService,
    protected user: UserService,
    protected multiTabService: MultitabEditorService
  ) { }

  public refresh(artifact: Artifact, validationLevel?: string) {
    this._artifact = artifact;
    this._results = artifact.analyzeResult ? artifact.analyzeResult : [];
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
    return [];
  }

  get rowData(): Array<any> {
    return [];
  }

  set rowData(value: Array<any>) {
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

  onEntryClick(entry: Result) {
    const locations = this.toLocations(entry);
    if (locations) {
      this._entryClicked.next(locations);
    }
  }

  deleteProblem(entry: Result) {
  }

  deleteAllProblems() {
  }

  protected toLocations(entry: Result) {
    if (entry.locations && entry.locations.length !== 0) {
      const locations = entry.locations.map(location => {
        if (this._artifact.type === ArtifactType.BE_DECISION_TABLE) {
          const rowId = location[0];
          const colId = location[1];
          const entryJSON = JSON.parse(JSON.stringify(entry));
          return {
            rowId: rowId,
            colId: colId,
            artifactType: entryJSON.artifactType,
            artifactPath: entryJSON.artifactPath,
            projectName: entryJSON.projectName
          };
        } else if (environment.enableBEUI) {
          const rowId = Object.keys(location)[0];
          const entryJSON = JSON.parse(JSON.stringify(entry));
          return {
            rowId: rowId,
            colId: location[rowId],
            artifactType: entryJSON.artifactType,
            artifactPath: entryJSON.artifactPath,
            projectName: entryJSON.projectName
          };
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
}
