import { Component, EventEmitter, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ArtifactProblemsService } from './artifact-problems.service';

import { environment } from '../../environments/environment';
import { ArtifactService } from '../core/artifact.service';
import { ProjectService } from '../core/project.service';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';

@Component({
  selector: 'artifact-status-bar',
  templateUrl: './artifact-status-bar.component.html',
  styleUrls: ['./artifact-status-bar.component.css'],
})
export class ArtifactStatusBarComponent {

  get warningsCount() {
    return this.problemsService.warnings.length;
  }

  get errorsCount() {
    return this.problemsService.errors.length;
  }

  get writeStatus() {
    if (this.multiTabService.activeTab != null) {
      const art = this.multiTabService.activeTab.payload;
      if (!art.locked) {
        art.locked = this.artifactService.getArtifactLockValueCache(art.id);
      }
      return art && !art.locked;
    }
    return this.problemsService.artifact && !this.problemsService.artifact.locked;
  }

  get fullPath() {
    if (environment.enableBEUI) {
      if (this.multiTabService.tabs.length > 0 && this.problemsService.artifact) {
        let art = this.problemsService.artifact;
        if (this.multiTabService.activeTab != null) {
          art = this.multiTabService.activeTab.payload;
        }
        let path = this.i18n('Location: ') + this.projectsService.getProjectMeta(art.projectId).projectName;
        if (art.path == null) {
          this.problemsService.artifact = this.multiTabService.activeTab.payload;
          art = this.problemsService.artifact;
        }
        path += art.fullPath();
        path += ' [' + art.type.displayString + ']';
        //      if (art.isCheckedOutArtifact) {
        //        path += ' [checked out revision ' + art.checkedOutFromRevision + ']';
        //      }
        return path;
      }
      return '';
    } else if (this.problemsService.artifact) {
      const art = this.problemsService.artifact;
      let path = this.i18n('Location: ') + this.projectsService.getProjectMeta(art.projectId).projectName;
      path += art.fullPath();
      path += ' [' + art.type.displayString + ']';
      //      if (art.isCheckedOutArtifact) {
      //        path += ' [checked out revision ' + art.checkedOutFromRevision + ']';
      //      }
      return path;
    }
    return '';
  }

  get supportProblemsView(): boolean {
    return this.problemsService.supportProblemsView;
  }

  get supportPropertiesView(): boolean {
    return environment.enableBEUI && this.multiTabService.tabs.length > 0;
  }

  get showLockStatus(): boolean {
    return this.multiTabService.tabs.length > 0;
  }
  get enableTCEUI() {
    return environment.enableTCEUI;
  }

  static selectedView;
  @Output()
  toggle = new EventEmitter<boolean>();

  constructor(
    private problemsService: ArtifactProblemsService,
    private projectsService: ProjectService,
    private multiTabService: MultitabEditorService,
    private artifactService: ArtifactService,
    public i18n: I18n
  ) { }

  onClick(e: Event) {
    e.preventDefault();
    e.stopPropagation();
    ArtifactStatusBarComponent.selectedView = this.i18n('Problems');
    this.toggle.emit(true);
  }

  onPropertiesClick(e: Event) {
    e.preventDefault();
    e.stopPropagation();
    ArtifactStatusBarComponent.selectedView = this.i18n('Properties');
    this.toggle.emit(true);
  }
  getMessage(): String {
    return (this.i18n('Artifact has {{errorsCount}} error(s) and {{warningsCount}} warning(s). Click for more information', { errorsCount: this.errorsCount, warningsCount: this.warningsCount }));
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
