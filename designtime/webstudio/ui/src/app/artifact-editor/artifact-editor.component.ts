
import { Component, ComponentFactoryResolver, DoCheck, ElementRef, EventEmitter, HostListener, Input, OnChanges, OnDestroy, OnInit, Output, ViewChild, ViewContainerRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { BehaviorSubject, Subscription } from 'rxjs';
import { filter, takeUntil } from 'rxjs/operators';

import { ArtifactEditorService } from './artifact-editor.service';
import { ArtifactPanelComponent } from './artifact-panel.component';
import { ArtifactProblemsService } from './artifact-problems.service';
import { ArtifactPropertiesService } from './artifact-properties.service';
import { ArtifactStatusBarComponent } from './artifact-status-bar.component';
import { ArtifactTestDataService } from './artifact-testdata.service';

import { environment } from '../../environments/environment';
import { CheckoutCommitComponent, CheckoutCommitContext } from '../checkout-lifecycle/checkout-commit.component';
import { CheckoutSyncComponent, CheckoutSyncContext } from '../checkout-lifecycle/checkout-sync.component';
import { AlertService } from '../core/alert.service';
import { ArtifactService, SynchronizeStrategy } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { ProjectService } from '../core/project.service';
import { ProviderService } from '../core/provider.service';
import { RecordService } from '../core/record.service';
import { SettingsService } from '../core/settings.service';
import { TakeUntilDestroy } from '../core/take.until.destroy';
import { EditorInterface } from '../editors/editor-interface';
import { EditorLoaderComponent } from '../editors/editor-loader.component';
import { EditorParams } from '../editors/editor-params';
import { MetadataEditorComponent } from '../editors/metadata/metadata-editor.component';
import { FOOTER_HEIGHT } from '../layout/properties';
import { Artifact, ArtifactStatus, ArtifactType } from '../models/artifact';
import { SynchronizeEditorContext, SynchronizeEditorModal } from '../synchronize-editor/synchronize-editor.modal';
import { VerifyConfigModal } from '../verify/verify-config.modal';
import { VerifyConfig } from '../verify/verify.service';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';
import { Context, ContextMenuService } from '../workspace/project-explorer/context-menu.service';
import { EditMenubuilderService } from '../workspace/project-explorer/edit-menubuilder-service';
import { WorkspaceService } from '../workspace/workspace.service';

const STATUS_BAR_HEIGHT = 50;

@Component({
  selector: 'artifact-editor',
  templateUrl: './artifact-editor.component.html',
  styleUrls: ['./artifact-editor.component.css'],
  providers: [EditMenubuilderService],
})
export class ArtifactEditorComponent extends TakeUntilDestroy implements OnChanges, OnInit, OnDestroy, DoCheck {
  @Input()
  input: Artifact;

  @Input()
  editorInterface: EditorInterface;

  @Output()
  dirty = new EventEmitter<boolean>();

  _commitCandidates: any;
  panelOpen: boolean;
  editorHeight = new BehaviorSubject<number>(0);
  editorParams: EditorParams<any>;
  currParams: EditorParams<any>;
  verifyConfig: VerifyConfig;
  showingMetadata: boolean;
  isshowAnalyzer = false;
  disableButtons = false;
  requiresSync = false;

  @ViewChild('editor', { static: false })
  private editorEle: ElementRef;

  private editorLoader: EditorLoaderComponent = new EditorLoaderComponent(
    Logger.prototype, ProviderService.prototype, ComponentFactoryResolver.prototype, ViewContainerRef.prototype, this.i18n
  );

  @ViewChild('artifactPanel', { static: false })
  private artifactPanel: ArtifactPanelComponent;

  private subscription: Subscription;
  // private listener: Keypress.Listener;
  private optionsCtxMenu: ContextMenuService;

  // These are for preventing values from changing during the wrong step of the angular lifecylce
  private editorIsLoaded = false;
  private editorSupportsUndoRedo = false;
  private editorCanUndo = false;
  private editorCanRedo = false;
  private editorCanSave = false;
  private editorCanSaveSub;

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    private projectService: ProjectService,
    private problemsService: ArtifactProblemsService,
    private testdataService: ArtifactTestDataService,
    private lifecycle: LifecycleService,
    private settings: SettingsService,
    private alert: AlertService,
    private workspace: WorkspaceService,
    private service: ArtifactEditorService,
    private record: RecordService,
    private router: Router,
    private activeRoute: ActivatedRoute,
    private modal: ModalService,
    private propertiesService: ArtifactPropertiesService,
    private editMenubuilder: EditMenubuilderService,
    private multiTab: MultitabEditorService,
    public i18n: I18n
  ) {
    super();
    this.showingMetadata = false;
  }

  @HostListener('window:keydown', ['$event'])
  onKeyDown(e) {
    if (platform.os.family === 'OS X') {
      if (e.metaKey && e.key === 's') {
        e.preventDefault();
        e.stopPropagation();
        this.onSave();
      }
    } else {
      if (e.ctrlKey && e.key === 's') {
        e.preventDefault();
        e.stopPropagation();
        this.onSave();
      }
    }
  }

  ngOnInit() {
    this.subscription = this.artifact.stateChanges().pipe(
      takeUntil(this.whenDestroyed),
      filter(change => change.artifact === null
        || (change.artifact && this.input && this.input.id === change.artifact.id)))
      .subscribe(change => {
        if (change.state === 'UPDATED') {
          const msg = this.i18n('{{name}}\'s base has been updated to catch up changes in the repository.', { name: this.input.name });
          this.refresh()
            .then((updated) => this.alert.flash(msg, 'info'));
        } else if (change.state === 'RERENDER') {
          this.refresh();
        }
      });

    this.problemsService.entryClicked.pipe(
      takeUntil(this.whenDestroyed))
      .subscribe(locations => {
        if (this.editorLoader.isLoaded) {
          if ((environment.enableBEUI && locations[0].artifactPath === this.editorLoader.params.artifact.path)
            || !environment.enableBEUI) {
            this.editorLoader.getEditor().focusOnLocations(locations);
          }
        }
      });

    // this.listener = new window.keypress.Listener();
    // let saveAct = (e: Event) => {
    //   e.preventDefault();
    //   e.stopPropagation();
    //   this.onSave();
    // };
    // if (platform.os.family === 'OS X') {
    //   this.listener.simple_combo('command s', saveAct);
    // } else {
    //   this.listener.simple_combo('ctrl s', saveAct);
    // }

    if (this.input.type !== ArtifactType.PROJECT_SUMMARY) {
      this.lifecycle.getCommitCandidates(this.input.projectId).toPromise().then(
        (cc) => this.commitCandidates = cc.filter(c => c.status !== 'CLEAN')
      );
    }

    this.optionsCtxMenu = new ContextMenuService(this.log, this.i18n);
    this.optionsCtxMenu.initWithComponent(this, this.editMenubuilder.getMenuBuilder(), '#options-context-menu');

    this.problemsService.refresh(this.input);
    let buffer = this.service.getBuffer(this.input);

    if (this.input.content != undefined) {
      if (!buffer || buffer.isEditable() !== this.canEdit()) {
        buffer = this.service.makeBuffer(this.input, this.editorInterface, this.canEdit());
        this.service.setBuffer(this.input, buffer);
      }
      this.currParams = <EditorParams<any>>{
        artifact: this.input,
        editorName: this.input.name,
        editorInterface: this.editorInterface,
        editBuffer: buffer,
        height: this.editorHeight,
        editorMode: this.canEdit() ? 'edit' : 'display',
        base: this.input.content ? this.input.content : null,
        showChangeSet: this.showDiff() ? 'rhs' : 'none',
      };
      this.editorParams = this.currParams;
    }
    // Disabling this out for TCE for now, will enable it once when collaboration is added
    if (environment.enableBEUI && !environment.enableTCEUI) {
      // BE doesn't track the current artifact version, need to check sync status
      const candidatesObs = this.lifecycle
        .getSyncCandidates(this.input.projectId, this.input.path, this.input.type.defaultExtension).
        toPromise().then(cc => {
          if (cc && cc.length > 0) {
            // TODO : Proper tracking of versions for BE
            // let latest = (<BECommitCandidate>cc[0]).revisionId;
            this.requiresSync = true;
            // this.input.checkedOutFromRevision = 122;
          } else {
            this.requiresSync = false;
          }
        });
      if (!this.input.locked) { this.input.locked = false; }
    }

  }

  ngOnDestroy() {
    if (this.subscription && !this.subscription.closed) {
      this.subscription.unsubscribe(); // not entirely sure why this doesn't get closed, should be via the 'takeUntil'
    }
    // if (this.listener) {
    //   // a hack because the typing does not have this API yet.
    //   (<any>this.listener).destroy();
    // }
  }

  ngDoCheck() {
    let val: number;
    if (this.editorEle) {
      const rect = this.editorEle.nativeElement.getBoundingClientRect();
      val = window.window.innerHeight - FOOTER_HEIGHT - STATUS_BAR_HEIGHT - rect.top - 5 + 24; // 24 is the negative margin of the container
    } else {
      val = window.window.innerHeight - FOOTER_HEIGHT - STATUS_BAR_HEIGHT - 5 + 24;
    }
    if (val !== this.editorHeight.value) {
      this.editorHeight.next(val);
    }
    if (this.editorIsLoaded !== this.editorLoader.isLoaded()) {
      this.editorIsLoaded = this.editorLoader.isLoaded();
    }
    if (this.editorIsLoaded) {
      if (this.editorSupportsUndoRedo !== this.editorLoader.getEditor().supportUndoRedo()) {
        this.editorSupportsUndoRedo = this.editorLoader.getEditor().supportUndoRedo();
      }
      const editor = this.editorLoader.getEditor();
      if (editor.params.editorInterface === EditorInterface.METADATA) {
        this.editorCanSaveSub = (editor as MetadataEditorComponent).valid.subscribe(
          validity => this.editorCanSave = validity && this.isDirty()
        );
      } else {
        if (this.editorCanSaveSub) {
          delete this.editorCanSaveSub;
        }
        this.editorCanSave = this.isDirty();
      }
    } else {
      this.editorSupportsUndoRedo = false;
    }
    if (this.editorSupportsUndoRedo) {
      this.editorCanUndo = this.editorLoader.getEditor().canUndo();
      this.editorCanRedo = this.editorLoader.getEditor().canRedo();
    } else {
      this.editorCanUndo = false;
      this.editorCanRedo = false;
    }
  }

  ngOnChanges() {
    this.refresh();
  }

  public statusIcon(): string {
    switch (this.input.status) {
      case <ArtifactStatus>'ADDED':
        return 'overlay-info fa fa-plus';
      case <ArtifactStatus>'MODIFIED':
        return 'overlay-warning fa fa-asterisk';
      case <ArtifactStatus>'DELETED':
        return 'overlay-danger fa fa-minus';
      case <ArtifactStatus>'CLEAN':
        return 'overlay-clean fa fa-check-circle';
    }
  }

  public breadcrumbs(): string {
    const projectMeta = this.projectService.getProjectMeta(this.input.projectId);
    const path = this.input.baseDir;
    const segments = path.split('/');
    const breadcrumbs = [];
    breadcrumbs.push(projectMeta.projectName);
    segments.forEach(seg => breadcrumbs.push(seg));
    breadcrumbs.push(' ');
    return breadcrumbs.filter(s => s !== '').join(' > ');
  }

  onDirty(dirty: boolean) {
    this.dirty.emit(dirty);
  }

  refresh(): Promise<any> {

    if (this.input.type === ArtifactType.PROJECT_SUMMARY) {
      let buffer = this.service.getBuffer(this.input);
      buffer = this.service.makeBuffer(this.input, this.editorInterface, this.canEdit());
      this.service.setBuffer(this.input, buffer);

      const editorBuffer = this.service.getBuffer(this.input);
      this.currParams = <EditorParams<any>>{
        artifact: this.input,
        editorName: this.input.name,
        editorInterface: editorBuffer.editorInterface,
        editBuffer: editorBuffer,
        height: this.editorHeight,
        editorMode: this.canEdit() ? 'edit' : 'display',
        base: null,
        showChangeSet: this.showDiff() ? 'rhs' : 'none',
      };
      this.editorParams = this.currParams;

    } else {
      return this.lifecycle.getCommitCandidates(this.input.projectId).toPromise()
        .then((cc) => {
          this.commitCandidates = cc.filter(c => c.status !== 'CLEAN');
          if (this.input.isCheckedOutArtifact) {
            return this.artifact.getCheckedOutArtifactWithContent(this.input.id);
          } else {
            return this.artifact.getArtifactLatest(this.input.id);
          }
        })
        .then(updated => this.input = updated)
        .then(() => {
          let buffer = this.service.getBuffer(this.input);
          if (!buffer || buffer.isEditable() !== this.canEdit()) {
            buffer = this.service.makeBuffer(this.input, this.editorInterface, this.canEdit());
            this.service.setBuffer(this.input, buffer);
          }
          this.showingMetadata = buffer.editorInterface === EditorInterface.METADATA;
        })
        .then(() => {
          if (this.input.checkedOutFromRevision) {
            return this.artifact.getArtifactRevisionWithContent(this.input.parentId, this.input.checkedOutFromRevision);
          } else {
            return null;
          }
        })
        .then(lhs => {
          this.problemsService.refresh(this.input);
          const editorBuffer = this.service.getBuffer(this.input);
          this.currParams = <EditorParams<any>>{
            artifact: this.input,
            editorName: this.input.name,
            editorInterface: editorBuffer.editorInterface,
            editBuffer: editorBuffer,
            height: this.editorHeight,
            editorMode: this.canEdit() ? 'edit' : 'display',
            base: lhs ? lhs.content : null,
            showChangeSet: this.showDiff() ? 'rhs' : 'none',
          };
          this.editorParams = this.currParams;
        }, reason => this.alert.flash(this.i18n('Fail to refresh editor because: {{reason}}', { reason: reason }), 'warning'));
    }
  }

  canCommit() {

    const activeTab = this.multiTab.getActive();
    const artifact = activeTab.payload;

    if (this.commitCandidates) {
      return !this.isDirty() && artifact.status !== 'CLEAN';
    }
    return false;
  }

  canSave() {
    return this.editorCanSave;
  }

  isDirty() {
    return this.currParams && this.currParams.editBuffer && this.currParams.editBuffer.isDirty();
  }

  onSave() {
    if (this.isDirty()) {
      this.disableButtons = true;
      if (this.currParams.editorInterface === EditorInterface.METADATA) {
        this.input.metadata = this.currParams.editBuffer.serialize();
      } else {
        this.input.content = this.currParams.editBuffer.serialize();
      }
      this.artifact.updateCheckoutArtifact(this.input)
        .then((res) => {
          if (res) {
            this.currParams.editBuffer.onSave();
            this.multiTab.getActive().payload.status = 'MODIFIED';
            this.problemsService.refresh(this.input);
            this.lifecycle.getCommitCandidates(this.input.projectId).toPromise().then(
              cc => {
                this.commitCandidates = cc;
                this.disableButtons = false;
              }
            );
          } else {
            this.problemsService.refresh(this.input);
            this.disableButtons = false;
          }
        },
          err => this.alert.flash(this.i18n('Failed to save because: {{err}}', { err: err }), 'warning'));
    }
  }

  get isContent() {
    return this.multiTab.getTabByKey(this.input.id).editorInterface !== EditorInterface.METADATA;
  }

  get handleToggle() {
    return this.editorInterface === EditorInterface.METADATA ? 'metadata' : 'content';
  }

  set handleToggle(type: 'content' | 'metadata') {
    this.editorParams = null;
    this.disableButtons = true;
    if (type === 'content') {
      switch (this.input.type) {
        case ArtifactType.DOMAIN_MODEL:
          this.multiTab.activateTab(this.input, EditorInterface.DOMAIN_MODEL)
            .then(() =>
              this.workspace.activateAll(this.input)
            )
            .then((done) => {
              this.disableButtons = !done;
            });
          break;
        case ArtifactType.BE_DECISION_TABLE:
          this.multiTab.activateTab(this.input, EditorInterface.BE_DECISION_TABLE)
            .then(() =>
              this.workspace.activateAll(this.input)
            ).then(
              (done) => {
                this.disableButtons = !done;
              }
            );
          break;
        case ArtifactType.SB_DECISION_TABLE:
          this.multiTab.activateTab(this.input, EditorInterface.SB_DECISION_TABLE)
            .then(() =>
              this.workspace.activateAll(this.input)
            ).then(
              (done) => {
                this.disableButtons = !done;
              }
            );
          break;
        default:
          this.multiTab.activateTab(this.input, EditorInterface.TEXT)
            .then(() =>
              this.workspace.activateAll(this.input)
            ).then(
              (done) => {
                this.disableButtons = !done;
              }
            );
      }
    } else {
      this.multiTab.activateTab(this.input, EditorInterface.METADATA)
        .then(() =>
          this.workspace.activateAll(this.input)
        ).then(
          (done) => {
            this.disableButtons = !done;
          }
        );
    }
  }

  onCommit() {
    const projectMeta = this.projectService.getProjectMeta(this.input.projectId);
    this.modal
      .open(CheckoutCommitComponent, new CheckoutCommitContext(projectMeta, this.input.path))
      .then((remainingCandidates) => {
        this.commitCandidates = remainingCandidates;
      }, () => { /* noop */ });
  }

  getOnSyncCheckoutHandler() {
    const project = this.projectService.getProjectMeta(this.input.projectId);
    this.modal
      .open(CheckoutSyncComponent, new CheckoutSyncContext(project, this.input.path, this.input.type.defaultExtension))
      .then(() => {
      }, () => {
      });
  }

  onSynchronize() {
    if (!this.input.isBinary) {
      return this.service.promptIfDirty(this.input)
        .then(( /* Artifact is clean */) =>
          this.artifact.getArtifactRevisionWithContent(this.input.parentId, this.input.checkedOutFromRevision))
        .then(base =>
          this.artifact.getArtifactRevisionWithContent(this.input.parentId, this.input.latestRevision)
            .then(rhs => this.modal.open(SynchronizeEditorModal, new SynchronizeEditorContext(base, this.input, rhs)))
            .then(() => { }, () => { }), err => { if (err) { throw err; } });
    } else {
      const artifact: Artifact = this.input;
      this.modal.confirm()
        .message(this.i18n('This artifact\'s type does not support synchronization. \
        Would you like to discard your local changes and replace the local artifact with the latest revision from the server?'))
        .okBtn(this.i18n('Replace'))
        .showClose(true)
        .open().result
        .then(
          (confirmed) => this.artifact.getArtifactLatest(artifact.parentId)
        ).then(
          (parent) => this.artifact.synchronize(parent, artifact, SynchronizeStrategy.LATEST, null)
        ).then(
          updated => {
            return updated;
          }
        ).catch(
          error => {
            if (error) {
              this.alert.flash(error.message, 'error', true);
            }
          }
        );
    }
  }

  onEditOptions(event?: Event) {
    this.optionsCtxMenu.initWithComponent(this, this.editMenubuilder.getMenuBuilder(), '#options-context-menu');
    if (event) {
      event.preventDefault();
      event.stopPropagation();
      const e: Event = event;
      const ctx: Context = {
        data: this.input,
        event: e,
      };
      this.optionsCtxMenu.showMenu(ctx);
    }
  }

  onVerify() {
    if (!this.artifactPanel || !this.artifactPanel.hasVerifyOnGoing) {
      this.modal.open(VerifyConfigModal, VerifyConfigModal.context())
        .then((config: VerifyConfig) => {
          if (this.input.isCheckedOutArtifact) {
            config.artifactKind = 'CHECKOUT';
            config.artifactId = this.input.id;
          } else {
            config.artifactKind = 'REVISION';
            config.artifactId = this.input.revisionId;
          }
          return config;
        })
        .then(config => {
          if (!this.artifactPanel) {
            this.verifyConfig = config;
            this.panelOpen = true;
          } else {
            this.artifactPanel.startVerifySession(config);
          }
        }, () => {/* noop when cancel*/ });
    } else {
      this.alert.flash(this.i18n('You already have an on-going Verify session with this artifact.'), 'info');
    }
  }

  onStatusBarClick() {
    if (this.panelOpen) {
      this.onClosePanel();
    } else {
      this.panelOpen = true;
    }
  }

  onTestDataCoverage() {
    if (this.panelOpen) {
      this.onClosePanel();
    } else {
      this.panelOpen = true;
    }
    ArtifactStatusBarComponent.selectedView = this.i18n('Test Data');
  }

  onClosePanel() {
    this.verifyConfig = null;
    this.panelOpen = false;
  }

  undo() {
    this.editorLoader.getEditor().undo();
  }

  redo() {
    this.editorLoader.getEditor().redo();
  }

  analyze() {
    this.isshowAnalyzer = !this.isshowAnalyzer;
  }

  canUndo() {
    return this.editorCanUndo;
  }

  canRedo() {
    return this.editorCanRedo;
  }

  onCheckout(artifact?: Artifact) {
    if (!artifact) {
      artifact = this.input;
    }
    if (artifact && !artifact.isCheckedOutArtifact) {
      this.artifact.checkoutArtifact(artifact.id)
        .then(a => {
          this.multiTab.activateTab(artifact, a.type.defaultInterface).then(
            (success) => {
              this.artifact.markAsModified(artifact, a);
              this.workspace.activateAll(a);
            }
          );
        }).catch(
          e => { /**
                  * TODO: Error catching
                  */
          }
        );
    }
  }

  onValidate() {
    if (this.input) {
      this.artifact.validateArtifact(this.input)
        .then(result => {
          if (result) {
            this.input.analyzeResult = this.record.recordToAnayzeResult(result.analyzeResult);
            if (this.input.analyzeResult) {
              if (this.input.analyzeResult.length === 0) {
                this.alert.flash(this.i18n('No errors/warning found.'), 'success');
              } else {
                let errorCount = 0;
                let warningCount = 0;
                this.input.analyzeResult.forEach(r => {
                  if (r.error) {
                    errorCount++;
                  } else {
                    warningCount++;
                  }
                });
                this.alert.flash(this.i18n('There are {{errorCount}}  error(s) and {{warningCount}}  warning(s) found.', { errorCount: errorCount, warningCount: warningCount }), 'error', true);
              }
              this.problemsService.refresh(this.input, 'artifact');
              this.refresh();
            }
          }
        });
    }
  }

  canEdit() {
    return this.input.isCheckedOutArtifact;
  }

  nonEditableSummary() {
    if (!this.input.isCheckedOutArtifact && this.input.id === 'Project@' + this.input.projectId + '@Summary') {
      return true;
    } else {
      return false;
    }
  }

  nonEditableOther() {
    if (!this.input.isCheckedOutArtifact && this.input.id !== 'Project@' + this.input.projectId + '@Summary') {
      return true;
    } else {
      return false;
    }
  }

  showDiff() {
    return this.input.isCheckedOutArtifact && this.input.status !== 'ADDED' && this.settings.latestSettings.showDiff;
  }

  get showValidate() {
    return !this.settings.latestSettings.autoArtifactValidation && this.input.type.supportValidate;
  }

  get supportEdit() {
    return !this.input.isBinary;
  }

  get supportVerify() {
    return this.input.type.supportVerify;
  }

  get supportUndoRedo() {
    return this.editorSupportsUndoRedo;
  }

  get supportMetadata() {
    return this.input.type.supportMetadata;
  }

  get showAnalyzer(): boolean {
    return (environment.enableBEUI && (this.input.type === ArtifactType.BE_DECISION_TABLE));
  }

  get commitCandidates() {
    return this._commitCandidates;
  }

  set commitCandidates(candidates) {
    this._commitCandidates = candidates;
  }

  get loaded() {
    if (this.hideContent()) {
      return false;
    } else {
      return this.editorIsLoaded;
    }
  }

  handleEditor(loader: EditorLoaderComponent) {
    this.editorLoader = loader;
  }

  hideContent() {
    return this.input.isBinary && this.editorInterface !== EditorInterface.METADATA;
  }

  showContentMetadataToggle() {
    environment.enableBEUI ? false : true;
  }

  get isBEUI() {
    return environment.enableBEUI;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
