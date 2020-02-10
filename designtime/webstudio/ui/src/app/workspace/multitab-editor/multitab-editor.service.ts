import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Subject } from 'rxjs';

import { environment } from '../../../environments/environment';
import { ArtifactEditorService } from '../../artifact-editor/artifact-editor.service';
import { ArtifactService } from '../../core/artifact.service';
import { EditorInterface } from '../../editors/editor-interface';
import { Artifact, ArtifactType } from '../../models/artifact';

export class Tab {
  key: string; // id of the artifact
  payload: Artifact;
  ignoreDirty: boolean;
  editorInterface?: EditorInterface;
}

@Injectable()
export class MultitabEditorService {

  get toRemove() {
    return this._toRemove.asObservable();
  }

  get tabs() {
    return this._tabs;
  }

  set tabs(values: Tab[]) {
    this._tabs = values;
  }
  public activeTabsSubject: Subject<Tab[]> = new Subject<Tab[]>();
  public closeTabSubject: Subject<Tab> = new Subject<Tab>();
  public closeAllSubject: Subject<boolean> = new Subject<boolean>();
  public activeTab: Tab;

  public activeTabSubject: Subject<Tab> = new Subject<Tab>();
  public _activeTab = new Subject<Tab>();
  public _toRemove = new Subject<Tab>();
  private _tabs: Tab[] = [];

  constructor(
    private artifactEditorService: ArtifactEditorService,
    private artifact: ArtifactService,
    private i18n: I18n
  ) {
    this.artifact.stateChanges().subscribe(change => {
      switch (change.state) {
        case 'DELETED':
          return this.markToRemove(tab => {
            const a = tab.payload;
            return a.id === change.artifact.id;
          });
        case 'DISPOSED':
          return this.artifact.getArtifactLatest(change.artifact.parentId)
            .then(latest => this.replaceTab(change.artifact, latest));
        case 'CHECKED-OUT':
          if (change.artifact && change.updated) {
            return this.replaceTab(change.artifact, change.updated);
          } else {
            return Promise.resolve(false);
          }
        //        case 'ADDED':
        //          if (change.artifact) {
        //            this.add(change.artifact);
        //            return Promise.resolve(true);
        //          }
        //          return Promise.resolve(true);
        case 'MODIFIED':
          if (change.artifact) {
            this.replaceTab(change.artifact, change.updated);
            return Promise.resolve(true);
          }
      }
    });
  }

  refresh() {
    if (!this.keepProjectSummary()) {
      this.initImpl();
    }
  }

  keepProjectSummary(): boolean {
    let keep = false;
    for (const tab of this.tabs) {
      if (tab.key.endsWith('@Summary')) {
        keep = true;
        break;
      }
    }
    return keep;
  }

  clear() {
    this.tabs = [];
    this.activeTab = null;
  }

  // tab section begins

  activateTab(artifact: Artifact, editorInterface?: EditorInterface): Promise<boolean> {
    if (!artifact) {
      this.setActive(null);
      this.setActiveObservable(null);
      return Promise.resolve(true);
    } else {
      const found = this.getTabByKey(artifact.id);
      return Promise.resolve()
        .then(() => {
          if (!found) {
            // if (artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE && artifact.content === undefined) {
            //   this.artifact.getCheckedOutArtifactWithContent(artifact.id)
            //   .then((artifactWithContent: Artifact) => {
            //     artifact.content = artifactWithContent.content;
            //     return this.addArtifactAsTab(artifact, editorInterface);
            //   });
            // } else {
            //     return this.addArtifactAsTab(artifact, editorInterface);
            // }
            return this.addArtifactAsTab(artifact, editorInterface);
          } else if (editorInterface && found.editorInterface !== editorInterface) {
            return this.artifactEditorService.promptIfDirty(artifact)
              .then(toProceed => {
                // we need to clear the old buffer
                this.artifactEditorService.clearBuffer(artifact);
                found.editorInterface = editorInterface;
                return found;
              });
          } else {
            if (environment) {
              this.artifactEditorService.clearBuffer(artifact);
            }
            return found;
          }
        })
        .then(tab => {
          this.setActive(tab);
          this.setActiveObservable(tab);
          this.setActiveTabs();
        })
        .then(() => true, () => false);
    }
  }

  insertTabToIdx(tab: Tab, idx: number) {
    return Promise.resolve().then(() => {
      this.tabs.splice(idx, 0, tab);
    });
  }

  addArtifactAsTab(artifact: Artifact, editorInterface: EditorInterface): Tab {
    const tab = new Tab();
    tab.key = artifact.id;
    if (artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE) {
      artifact.type.displayString = this.i18n('Rule Template Instance');
      if (this.isRTIView(artifact.content)) {
        tab.editorInterface = EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW;
      } else {
        tab.editorInterface = EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER;
      }
    } else {
      tab.editorInterface = editorInterface ? editorInterface : artifact.type.defaultInterface;
    }

    if (artifact.type.displayString.search('Decision Table') !== -1) {
      artifact.type.displayString = this.i18n('Decision Table');
    }
    tab.payload = artifact;
    this.pushTab(tab);
    return tab;
  }

  isRTIView(xml: string): boolean {
    if (JSON.parse(xml).view != null) {
      return true;
    } else {
      return false;
    }
  }

  pushTab(tab: Tab) {
    this.tabs = this.tabs.slice();
    this.tabs.push(tab);
  }

  getTabByKey(id: string) {
    return this.tabs.find(t => t.key === id);
  }

  replaceTab(lhs: Artifact, rhs: Artifact) {
    const l = this.getTabByKey(lhs.id);
    const r = this.getTabByKey(rhs.id);
    if (l && !r) {
      l.key = rhs.id;
      l.payload = rhs;
      l.editorInterface = rhs.type.defaultInterface;
    }
  }

  indexOf(tab: Tab) {
    return this.tabs.indexOf(tab);
  }

  removeFromService(tab: Tab) {
    const idx = this.tabs.indexOf(tab);
    return this.removeFromServiceByIdx(idx);
  }

  removeTabFromServiceIfExists(artifact: Artifact) {
    const found = this.getTabByKey(artifact.id);
    if (found) {
      if (found === this.activeTab) {
        this.setActive(null);
        this.setActiveObservable(null);
      }
      return this.removeFromService(found);
    }
  }

  removeFromServiceByIdx(idx: number) {
    this.tabs.splice(idx, 1);
    this.setActiveTabs();
    if (this.tabs.length === 0) {
      this.setActive(null);
      this.setActiveObservable(null);
    }
  }

  setActive(tab: Tab) {
    this.activeTab = tab;
    this._activeTab.next(tab);
  }

  setActiveObservable(tab: Tab) {
    this.activeTabSubject.next(tab);
  }

  getActiveObservable(): Subject<Tab> {
    return this.activeTabSubject;
  }

  setActiveTabs() {
    this.activeTabsSubject.next(this.tabs);
  }

  getActiveTabs(): Subject<Tab[]> {
    return this.activeTabsSubject;
  }

  setCloseTab(tab: Tab) {
    this.closeTabSubject.next(tab);
  }

  getCloseTab(): Subject<Tab> {
    return this.closeTabSubject;
  }

  getActive(): Tab {
    return this.activeTab;
  }

  isBEDTActive(): boolean {
    const tab = this.getActive();
    if (tab) {
      const artifact = tab.payload;
      if (artifact) {
        return artifact.type.defaultExtension === 'rulefunctionimpl';
      }
    }
    return false;
  }

  markToRemove(predicate: (tab: Tab) => boolean) {
    this.tabs.filter(predicate).forEach(t => {
      if (this._toRemove.observers.length === 0) {
        this.removeFromService(t);
      } else {
        this._toRemove.next(t);
      }
    });
  }

  private initImpl() {
    this.tabs = [];
    this.activeTab = null;
  }
}
