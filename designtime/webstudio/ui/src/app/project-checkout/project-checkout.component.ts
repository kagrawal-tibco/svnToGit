import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { IOption } from 'ng-select';

import { environment } from '../../environments/environment';
import { ArtifactService } from '../core/artifact.service';
import { ModalService } from '../core/modal.service';
import { ProjectService } from '../core/project.service';
import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { Artifact } from '../models/artifact';

export class ProjectCheckout {
  projectName: string;
  projectId: string;
  artifacts: Artifact[] = [];
}

type SelectItem = { value: string, label: string };

@Component({
  selector: 'project-checkout',
  templateUrl: './project-checkout.component.html',
  styleUrls: ['./project-checkout.component.css']
})
export class ProjectCheckoutComponent implements OnInit {
  @Input()
  input: ProjectCheckout;
  @Input()
  allowSwitchProject: boolean;
  @Output()
  inputChange = new EventEmitter<ProjectCheckout>();

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
    if (this.allowSwitchProject) {
      this.project.getAllProjects()
        .then(projects => {
          this.projectCandidates = projects.map(p => ({ value: p.projectId, label: p.projectName }));
        });
    } else {
      this.projectCandidates = [];
      const item = <SelectItem>{
        value: this.input.projectId,
        label: this.input.projectName
      };
      this.projectCandidates.push(item);
      this.onProjectSelect(item);
    }
  }

  onProjectSelect(item: SelectItem) {
    this.input.projectId = item.value;
    this.input.projectName = item.label;
    this.input.artifacts = [];
    this.artifactCandidates = null;
    this.project.getAllThenMerge(item.value)
      .then(artifacts => this.artifactCandidates = artifacts)
      .then(artifacts => {
        this.onArtifactsAllCheck();
        return artifacts;
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
      .filter(a => !a.isCheckedOutArtifact)
      .forEach(a => this.selectArtifact(a, true));
  }

  onArtifactsAllUncheck() {
    this.visibleArtifacts
      .filter(a => !a.isCheckedOutArtifact)
      .forEach(a => this.selectArtifact(a, false));
  }

  description(artifact: Artifact) {
    if (artifact.isCheckedOutArtifact) {
      return `${artifact.fullPath()} (already checked out)`;
    } else {
      return `${artifact.fullPath()}`;
    }
  }

  onViewContent(artifact: Artifact) {
    Promise.resolve()
      .then(() => {
        if (artifact.isCheckedOutArtifact) {
          return this.artifact.getCheckedOutArtifactWithContent(artifact.id);
        } else {
          return this.artifact.getArtifactLatest(artifact.id);
        }
      })
      .then(a => {
        const ctx = EditorLoaderModal.default(a);
        return this.modal.open(EditorLoaderModal, ctx);
      })
      .then(() => { }, () => { });
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
      .filter(a => !a.isCheckedOutArtifact)
      .reduce((val, e) => val && this.input.artifacts.indexOf(e) !== -1, true);
  }

  get visibleArtifacts() {
    if (this.artifactCandidates) {
      return this.artifactCandidates.filter(a => !this.artifactFilter || a.fullPath().includes(this.artifactFilter));
    } else {
      return [];
    }
  }

  selectArtifact(artifact: Artifact, val: boolean) {
    if (!artifact.isCheckedOutArtifact) {
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
  }

  showViewContent() {
    return !environment.enableBEUI;
  }
  getMessage(): String {
    return (this.artifactFilter ? this.i18n('Select All Matches: ') : this.i18n('Select All: '));
  }
}
