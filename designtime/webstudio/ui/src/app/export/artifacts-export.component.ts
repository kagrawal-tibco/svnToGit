import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { IOption } from 'ng-select';

import { environment } from '../../environments/environment';
import { ArtifactService } from '../core/artifact.service';
import { ModalService } from '../core/modal.service';
import { ProjectService } from '../core/project.service';
import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { Artifact, ArtifactType } from '../models/artifact';

export class ArtifactsExport {
  projectName: string;
  projectId: string;
  artifacts: Artifact[] = [];
}

type SelectItem = { value: string, label: string };

@Component({
  selector: 'artifacts-export',
  templateUrl: './artifacts-export.component.html',
  styleUrls: ['./artifacts-export.component.css']
})
export class ArtifactsExportComponent implements OnInit {
  @Input()
  input: ArtifactsExport;
  @Output()
  inputChange = new EventEmitter<ArtifactsExport>();

  public projectCandidates: Array<SelectItem>;
  public artifactFilter: string;
  public artifactCandidates: Artifact[];

  constructor(
    private modal: ModalService,
    private project: ProjectService,
    private artifact: ArtifactService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.onProjectSelect({ value: this.input.projectId, label: this.input.projectName });
  }

  onProjectSelect(item: SelectItem) {
    this.input.projectId = item.value;
    this.input.projectName = item.label;
    this.input.artifacts = [];
    this.artifactCandidates = null;
    this.project.getCheckedOutArtifacts(item.value)
      .toPromise()
      .then((checkedOutArtifactList: Artifact[]) => {
        this.artifactCandidates = checkedOutArtifactList;
      });
  }

  onProjectUnselect(item: SelectItem) {
    this.input.projectId = null;
    this.input.projectName = null;
    this.input.artifacts = [];
    this.artifactCandidates = null;
  }

  onArtifactsAllCheck() {
    this.visibleArtifacts
      .forEach(a => this.selectArtifact(a, true));
  }

  onArtifactsAllUncheck() {
    this.visibleArtifacts
      .forEach(a => this.selectArtifact(a, false));
  }

  description(artifact: Artifact) {
    return `${artifact.fullPath()}`;
  }

  onToggleSelection(e: Event, artifact: Artifact) {
    e.stopPropagation();
    e.preventDefault();
    this.selectArtifact(artifact, !this.isSelected(artifact));
  }

  isSelected(artifact: Artifact) {
    return this.input.artifacts.indexOf(artifact) !== -1;
  }

  set selectAll(val: boolean) {
    if (val) {
      this.onArtifactsAllCheck();
    } else {
      this.onArtifactsAllUncheck();
    }
  }

  get selectAll() {
    return this.input.artifacts.length > 0 && this.visibleArtifacts
      .reduce((val, e) => val && this.input.artifacts.indexOf(e) !== -1, true);
  }

  get visibleArtifacts() {
    if (this.artifactCandidates) {
      return this.artifactCandidates.filter(a => (!this.artifactFilter || a.fullPath().includes(this.artifactFilter)) && (a.type === ArtifactType.BE_DECISION_TABLE || a.type === ArtifactType.RULE_TEMPLATE_INSTANCE));
    } else {
      return [];
    }
  }

  selectArtifact(artifact: Artifact, val: boolean) {
    if (val) {
      if (!this.isSelected(artifact)) {
        this.input.artifacts.push(artifact);
      }
    } else {
      const idx = this.input.artifacts.indexOf(artifact);
      if (idx !== -1) {
        this.input.artifacts.splice(idx, 1);
      }
    }
  }

  getMessage(): String {
    return (this.artifactFilter ? this.i18n('Select All Matches: ') : this.i18n('Select All: '));
  }
}
