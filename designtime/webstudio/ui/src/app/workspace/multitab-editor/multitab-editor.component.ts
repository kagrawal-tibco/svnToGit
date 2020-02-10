
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTabGroup } from '@angular/material/tabs';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { TabsetComponent } from 'ngx-bootstrap';
import { of as observableOf, Observable, Subject } from 'rxjs';
import { concatMap, delay, filter, take, takeUntil } from 'rxjs/operators';

import { MultitabEditorService, Tab } from './multitab-editor.service';

import { environment } from '../../../environments/environment';
import { ArtifactEditorService } from '../../artifact-editor/artifact-editor.service';
import { ArtifactService } from '../../core/artifact.service';
import { ModalService } from '../../core/modal.service';
import { TakeUntilDestroy } from '../../core/take.until.destroy';
import { EditorInterface } from '../../editors/editor-interface';
import { RTIViewClient } from '../../editors/rule-template-instance-view/RTIViewClient';
import { Artifact, ArtifactType } from '../../models/artifact';
import { ProjectCheckoutModal, ProjectCheckoutModalContext } from '../../project-checkout/project-checkout.modal';
import { WorkspaceService } from '../workspace.service';

@Component({
  selector: 'multitab-editor',
  templateUrl: './multitab-editor.component.html',
  styleUrls: ['../workspace.component.css', './multitab-editor.component.css'],
})
export class MultitabEditorComponent extends TakeUntilDestroy implements OnInit {

  get isBEUI() {
    return environment.enableBEUI;
  }

  public selectedIndex = -1;

  multiTabEditorHeight = 100;
  problemsPanelHeight = 0;

  panelOpen = false;

  @ViewChild('tabgroup', { static: false })
  private tabgroup: MatTabGroup;

  @ViewChild('tabset', { static: false })
  private tabset: TabsetComponent;
  private tabDirectiveRemoved = new Subject<Tab>();
  constructor(
    private artifactService: ArtifactService,
    private artifactEditorService: ArtifactEditorService,
    private service: MultitabEditorService,
    private workspace: WorkspaceService,
    private modal: ModalService,
    public i18n: I18n
  ) {
    super();
  }

  ngOnInit() {
    this.service._activeTab.asObservable().pipe(
      takeUntil(this.whenDestroyed))
      .subscribe(tab => {
        if (this.tabgroup) {
          const idx = this.service.indexOf(tab);
          if (idx !== -1 && idx !== this.tabgroup.selectedIndex) {
            this.tabgroup.selectedIndex = idx;
          }
        }
      });

    this.service.toRemove.pipe(
      takeUntil(this.whenDestroyed),
      // execute the async task one by one
      concatMap(tab => {
        tab.ignoreDirty = true;
        if (this.tabset) {
          const idx = this.getTabs().indexOf(tab);
          const tabDirective = this.tabset.tabs[idx];
          // we need to delay to make sure the possibly new tab is selected
          const done = this.tabDirectiveRemoved.pipe(
            filter(removed => removed.key === tab.key),
            take(1),
            delay(0))
            .toPromise();
          this.tabset.removeTab(tabDirective);
          return done;
        } else {
          this.service.removeFromService(tab);
          return observableOf(tab);
        }
      }))
      .subscribe(tab => {
      });

    this.service.closeTabSubject.takeUntil(this.whenDestroyed).subscribe((tab: Tab) => {
      this.onTabDirectiveRemoved(tab);
    });

    this.service.closeAllSubject.takeUntil(this.whenDestroyed).subscribe(() => {
      const tabs: Tab[] = this.getTabs();
      for (let i = 0; i < tabs.length; i++) {
        this.onTabDirectiveRemoved(tabs[i]);
      }
    });
  }

  getActive() {
    return this.service.activeTab;
  }

  getTabs() {
    return this.service.tabs;
  }

  onTabDirectiveRemoved(tab: Tab) {
    const idx = this.service.tabs.indexOf(tab);
    if (idx !== -1) {
      const artifact = tab.payload;
      const clearAct = () => {
        this.removeTabFromServiceByIdx(idx);
        if (artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE
          && artifact.type.defaultInterface === EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW) {
          RTIViewClient.removeExistingScriptElement(artifact.name);
        }
        this.artifactEditorService.clearBuffer(artifact);
        this.tabDirectiveRemoved.next(tab);
        return tab;
      };
      if (tab.ignoreDirty) {
        clearAct();
      } else {
        return this.artifactEditorService.promptIfDirty(artifact)
          .then(
            () => clearAct(),
            () => {
              this.removeTabFromServiceByIdx(idx);
              this.tabDirectiveRemoved.next(tab);
              this.service.activateTab(artifact);
              return tab;
            });
      }
    } else {
      this.tabDirectiveRemoved.next(tab);
    }
  }

  removeTabFromServiceByIdx(idx: number) {
    this.service.removeFromServiceByIdx(idx);
    if (this.service.tabs.length === 0) {
      this.workspace.activateAll(null);
    }
  }

  onTabSelected(event) {
    let tab: Tab;
    if (event.index > -1) {
      tab = this.service.tabs[event.index];
    }
    if (tab !== this.service.activeTab) {
      // protection code because tabs might be invoked when other tabs are removed.
      const artifact: Artifact = tab.payload;
      let deleted = true;
      if (artifact.isCheckedOutArtifact) {
        deleted = !this.artifactService.checkedOutArtifactCache.has(artifact.id);
      } else {
        const revisions = this.artifactService.artifactRevisionCache.get(artifact.id);
        deleted = !revisions || !revisions.has(artifact.revisionNumber);
      }
      if (!deleted) {
        this.workspace.activateAll(tab.payload);
      }
    }
  }

  displayName(tab: Tab) {
    let artifact: Artifact = tab.payload;
    if (artifact.isCheckedOutArtifact) {
      artifact = this.artifactService.checkedOutArtifactCache.get(artifact.id);
    } else if (artifact.type === ArtifactType.PROJECT_SUMMARY) {
      return artifact.projectId;
    } else {
      artifact = this.artifactService.artifactRevisionCache.get(artifact.id).get(artifact.revisionNumber);
    }
    if (artifact) {
      const buffer = this.artifactEditorService.getBuffer(artifact);
      const dirty = buffer && buffer.isDirty();
      if (dirty) {
        return '*' + artifact.displayName();
      }
      if (artifact.displayName().search('working') !== -1) {
        const name = artifact.displayName().split('(')[0];
        return (name + '(' + this.i18n('working') + ')');
      }
      return artifact.displayName();
    }
    return tab.payload.displayName();
  }

  iconOverlay(tab: Tab) {
    let artifact: Artifact = tab.payload;
    if (artifact.isCheckedOutArtifact) {
      artifact = this.artifactService.checkedOutArtifactCache.get(artifact.id);
    } else if (artifact.type === ArtifactType.PROJECT_SUMMARY) {
      return 'fa fa-eye';
    } else {
      artifact = this.artifactService.artifactRevisionCache.get(artifact.id).get(artifact.revisionNumber);
    }
    if (artifact) {
      const buffer = this.artifactEditorService.getBuffer(artifact);
      const dirty = buffer && buffer.isDirty();
      if (!dirty && !artifact.isCheckedOutArtifact) {
        return 'fa fa-eye';
      }
      if (tab.editorInterface === EditorInterface.METADATA) {
        return 'fa fa-code';
      }
    }
  }

  showStatusBar(): boolean {
    return environment.enableBEUI;
  }

  showProblems(): boolean {
    return environment.enableBEUI;
  }

  onStatusBarClick() {
    if (this.panelOpen) {
      this.onClosePanel();
      this.multiTabEditorHeight = 100;
      this.problemsPanelHeight = 0;
    } else {
      this.panelOpen = true;
      this.multiTabEditorHeight = 72.5;
      this.problemsPanelHeight = 27.5;
    }
  }

  onClosePanel() {
    this.panelOpen = false;
  }

  onCheckoutArtifacts() {
    this.modal.open(ProjectCheckoutModal, new ProjectCheckoutModalContext())
      .then(() => { }, err => { if (err) { throw err; } });
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
