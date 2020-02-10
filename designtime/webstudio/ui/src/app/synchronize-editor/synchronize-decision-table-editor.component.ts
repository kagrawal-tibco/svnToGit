import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { MergeResult } from './../editables/decision-table/differ/merge-result';
import { ShowChangeSetOption } from './../editors/changeset-options';

import { environment } from '../../environments/environment';
import { ArtifactService, SynchronizeStrategy } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { BEDecisionTable } from '../editables/decision-table/be-decision-table';
import { DecisionTable } from '../editables/decision-table/decision-table';
import { Differ as DTDiffer } from '../editables/decision-table/differ/differ';
import { EditBuffer } from '../editables/edit-buffer';
import { MergedDiffHelper } from '../editables/rule-template-instance/differ/MergeDiffHelper';
import { MergedDiffModificationEntry } from '../editables/rule-template-instance/differ/MergedDiffModificationEntry';
import { RuleTemplateInstance, RuleTemplateInstanceImpl } from '../editables/rule-template-instance/rule-template-instance';
import { EditorInterface } from '../editors/editor-interface';
import { EditorParams } from '../editors/editor-params';
import { BuilderSubClauseImpl } from '../editors/rule-template-instance-builder/BuilderSubClauseImpl';
import { MultiFilterImpl } from '../editors/rule-template-instance-builder/MultiFilterImpl';
import { BindingInfo } from '../editors/rule-template-instance-view/BindingInfo';
import { Artifact, ArtifactType } from '../models/artifact';

@Component({
  selector: 'synchronize-decision-table-editor',
  templateUrl: './synchronize-decision-table-editor.component.html',
  styleUrls: ['./synchronize-decision-table-editor.component.css'],
})
export class SynchronizeDecisionTableEditorComponent implements OnInit {

  set showDiff(val: boolean) {
    this._showDiff = val;
    this.refreshOnLatest();
  }

  get showDiff() {
    return this._showDiff;
  }

  get diffOption(): ShowChangeSetOption {
    if (this._showLhsDiff && this._showRhsDiff) {
      return 'both';
    } else if (this._showLhsDiff) {
      return 'lhs';
    } else if (this._showRhsDiff) {
      return 'rhs';
    } else {
      return 'none';
    }
  }

  get showLhsDiff() {
    return this._showLhsDiff;
  }

  set showLhsDiff(val: boolean) {
    this._showLhsDiff = val;
    this.refresh();
  }

  get showRhsDiff() {
    return this._showRhsDiff;
  }

  set showRhsDiff(val: boolean) {
    this._showRhsDiff = val;
    this.refresh();
  }

  get showDiffOptionName() {
    switch (this.diffOption) {
      case 'lhs':
        return 'latest';
      case 'rhs':
        return 'working';
      case 'none':
        return 'none';
    }
  }

  get strategyExplanation() {
    switch (this.currentStrategy) {
      case SynchronizeStrategy.LATEST:
        return this.i18n('Discard your local changes, replacing your local working copy with the latest version');
      case SynchronizeStrategy.MERGE:
        return this.i18n('Merge the repository changes with your local changes, leaving the results in your working copy.\
        You may need to resolve conflicts.');
    }
  }

  get strategyName() {
    switch (this.currentStrategy) {
      case SynchronizeStrategy.LATEST:
        return 'latest';
      case SynchronizeStrategy.MERGE:
        return 'merge';
    }
  }

  @Input() // Latest revision from server
  mergeRhs: Artifact;

  @Input() // Clean, freshly, checked-out artifact
  mergeBase: Artifact;

  @Input() // Artifact from editor
  mergeLhs: Artifact;

  @Output()
  done: EventEmitter<Artifact> = new EventEmitter<Artifact>();

  mergeBuffer: EditBuffer<any>;
  currentStrategy: SynchronizeStrategy;
  editorParams: Promise<EditorParams<any>>;
  lastParams: EditorParams<any>;
  mergeResult: MergeResult;
  modification: Map<string, MergedDiffModificationEntry>;
  viewModification: Map<BindingInfo, MergedDiffModificationEntry>;
  canMerge = false;

  // access by setter/getter
  private _showDiff: boolean;
  private _showLhsDiff: boolean;
  private _showRhsDiff: boolean;

  constructor(
    private log: Logger,
    private differ: DTDiffer,
    private artifact: ArtifactService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    if (this.isDelete(this.mergeLhs) && this.isDelete(this.mergeRhs)) {
      // shall not see this
      this.log.warn(this.i18n('LHS and RHS are both deleted, choose whether keep the deleted checked-out artifact'));
    } else if (this.isDelete(this.mergeLhs)) {
      this.log.warn(this.i18n('LHS is deleted, choose whether keep the deleted checked-out artifact'));
    } else if (this.isDelete(this.mergeRhs)) {
      this.log.warn(this.i18n('RHS is deleted, choose whether keep the deleted checked-out artifact'));
    }

    this.canMerge = this.mergeRhs.status !== 'CLEAN';

    if (this.canMerge) {
      this.setInMerge();
    } else {
      this.setInLatest();
    }
  }

  inLatest(): boolean {
    return this.currentStrategy === SynchronizeStrategy.LATEST;
  }

  setInLatest() {
    this._showDiff = true;
    this.currentStrategy = SynchronizeStrategy.LATEST;
    switch (this.mergeRhs.type) {
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
        const rtiJson = JSON.parse(this.mergeRhs.content);
        const viewJson = rtiJson.view;
        const isRTIView: boolean = viewJson && viewJson.bindingInfo != null && viewJson.bindingInfo.length > 0;
        if (isRTIView) {
          this.lastParams = <EditorParams<RuleTemplateInstanceImpl>>{
            editorInterface: EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW,
            editBuffer: EditBuffer.ruleTemplateInstanceView().init(this.mergeLhs.content, false),
            base: this.mergeBase.content,
            editorMode: 'display',
            showChangeSet: this.showDiff ? 'rhs' : 'none',
            artifact: this.mergeLhs
          };
        } else {
          const mergeLhsJson = JSON.parse(this.mergeLhs.content);
          mergeLhsJson.symbols = rtiJson.symbols;
          this.mergeLhs.content = JSON.stringify(mergeLhsJson);
          this.lastParams = <EditorParams<RuleTemplateInstanceImpl>>{
            editorInterface: EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER,
            editBuffer: EditBuffer.ruleTemplateInstanceBuilder().init(this.mergeLhs.content, false),
            base: this.mergeBase.content,
            editorMode: 'display',
            showChangeSet: this.showDiff ? 'rhs' : 'none',
            artifact: this.mergeLhs
          };
        }
        break;
      case ArtifactType.BE_DECISION_TABLE:
        this.lastParams = <EditorParams<BEDecisionTable>>{
          editorInterface: EditorInterface.BE_DECISION_TABLE,
          editBuffer: EditBuffer.beDecisionTable().init(this.mergeLhs.content, false),
          base: this.mergeBase.content,
          editorMode: 'display',
          showChangeSet: this.showDiff ? 'rhs' : 'none',
        };
        break;
      case ArtifactType.SB_DECISION_TABLE:
        this.lastParams = <EditorParams<any>>{
          editorInterface: EditorInterface.SB_DECISION_TABLE,
          editBuffer: EditBuffer.decisionTable().init(this.mergeRhs.content, false),
          base: this.mergeBase.content,
          editorMode: 'display',
          showChangeSet: this.showDiff ? 'rhs' : 'none',
        };
        break;
      default:
        // This will never be called as all other cases will be handled in the wrapper.
        break;
    }

    this.editorParams = Promise.resolve(this.lastParams);
  }

  inMerge(): boolean {
    return this.currentStrategy === SynchronizeStrategy.MERGE;
  }

  setInMerge() {
    if (this.canMerge) {
      this.currentStrategy = SynchronizeStrategy.MERGE;

      // shall turn off showDiff because it's merge mode
      this._showDiff = false;
      // use these two flags instead
      this.showLhsDiff = true;
      this.showRhsDiff = true;
      switch (this.mergeRhs.type) {
        case ArtifactType.RULE_TEMPLATE_INSTANCE:
          const rtiJson = JSON.parse(this.mergeRhs.content);
          const viewJson = rtiJson.view;
          const isRTIView: boolean = viewJson && viewJson.bindingInfo != null && viewJson.bindingInfo.length > 0;
          if (isRTIView) {
            this.viewModification = new Map<BindingInfo, MergedDiffModificationEntry>();
            if (!this.mergeResult) {
              this.mergeBuffer = EditBuffer.ruleTemplateInstanceView().init(this.mergeRhs.content, true);
              const currentVersionRTI = this.mergeBuffer.getBuffer();
              const previousVersionRTI = RuleTemplateInstance.deserialize(this.mergeBase.content);
              const serverVersionRTI = RuleTemplateInstance.deserialize(this.mergeLhs.content);
              if (previousVersionRTI.getView().getBindingInfo().length > 0) {
                MergedDiffHelper.processRTIViewMergedDiff(previousVersionRTI.getView().getBindingInfo(),
                  currentVersionRTI.getView().getBindingInfo(),
                  serverVersionRTI.getView().getBindingInfo(),
                  this.viewModification);
              } else {
                MergedDiffHelper.processRTIViewMergedDiff(currentVersionRTI.getView().getBindingInfo(),
                  currentVersionRTI.getView().getBindingInfo(),
                  serverVersionRTI.getView().getBindingInfo(),
                  this.viewModification);
              }

              this.mergeBuffer.replaceBuffer(serverVersionRTI);
              if (!MergedDiffHelper.hasViewLocalChanges(this.viewModification)) {
                this.currentStrategy = SynchronizeStrategy.LATEST;
                this.showDiff = true;
                this.canMerge = false;
                this.mergeBase.content = RuleTemplateInstance.serialize(currentVersionRTI);
                this.setInLatest();
                break;
              }
            }
            this.lastParams = <EditorParams<RuleTemplateInstanceImpl>>{
              editorInterface: EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW,
              editBuffer: this.mergeBuffer,
              mergeResultRTIView: this.viewModification,
              base: this.mergeBase.content,
              lhs: this.mergeLhs.content,
              editorMode: 'sync',
              showChangeSet: this.diffOption,
              artifact: this.mergeLhs
            };
          } else {
            this.modification = new Map<string, MergedDiffModificationEntry>();
            if (!this.mergeResult) {
              this.mergeBuffer = EditBuffer.ruleTemplateInstanceBuilder().init(this.mergeRhs.content, true);
              const currentVersionRTI: RuleTemplateInstanceImpl = this.mergeBuffer.getBuffer();
              const previousVersionRTI: RuleTemplateInstanceImpl = RuleTemplateInstance.deserialize(this.mergeBase.content);
              const serverVersionRTI: RuleTemplateInstanceImpl = RuleTemplateInstance.deserialize(this.mergeLhs.content);
              const mergedRTI: RuleTemplateInstanceImpl = new RuleTemplateInstanceImpl();
              if (previousVersionRTI.getConditions().getBuilderSubClause().getFilters().length > 0) {
                mergedRTI.setConditions(MergedDiffHelper.processMultiFilterMergedDiff(previousVersionRTI.getConditions(),
                  currentVersionRTI.getConditions(),
                  serverVersionRTI.getConditions(),
                  this.modification));
              } else {
                mergedRTI.setConditions(MergedDiffHelper.processMultiFilterMergedDiff(currentVersionRTI.getConditions(),
                  currentVersionRTI.getConditions(),
                  serverVersionRTI.getConditions(),
                  this.modification));
              }
              mergedRTI.setSymbols(currentVersionRTI.getSymbols());
              const modifiedServerVersionRTI = this.processRTIBuilderCommandsDiff(previousVersionRTI, currentVersionRTI, serverVersionRTI);
              serverVersionRTI.setCommands(modifiedServerVersionRTI.getCommands());
              mergedRTI.setCommands(serverVersionRTI.getCommands());
              mergedRTI.setImplementsPath(serverVersionRTI.getImplementsPath());
              this.mergeBuffer.replaceBuffer(mergedRTI);
              if (!MergedDiffHelper.hasBuilderLocalChanges(this.modification)) {
                this.currentStrategy = SynchronizeStrategy.LATEST;
                this.showDiff = true;
                this.canMerge = false;
                this.mergeBase.content = RuleTemplateInstance.serialize(currentVersionRTI);
                this.setInLatest();
                break;
              }
            }
            this.lastParams = <EditorParams<RuleTemplateInstanceImpl>>{
              editorInterface: EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER,
              editBuffer: this.mergeBuffer,
              mergeResultRTI: this.modification,
              base: this.mergeBase.content,
              lhs: this.mergeLhs.content,
              editorMode: 'sync',
              showChangeSet: this.diffOption,
              artifact: this.mergeLhs
            };
          }
          break;
        case ArtifactType.BE_DECISION_TABLE:
          if (!this.mergeResult) {
            this.mergeBuffer = EditBuffer.beDecisionTable().init(this.mergeRhs.content, true);
            this.mergeResult = this.differ.merge(
              BEDecisionTable.fromXml(this.mergeBase.content),
              BEDecisionTable.fromXml(this.mergeLhs.content),
              this.mergeBuffer.getContent());
            this.mergeBuffer.replaceBuffer(this.mergeResult.getMerged());
            this.mergeBuffer.getBuffer().isSyncMerge = true;
            // Removing this code to fix BE-26008
            // if (this.isOnlyLatestChanges()) {
            //  this.setInLatest();
            //  this.canMerge = false;
            //  break;
            // }
          }
          this.lastParams = <EditorParams<BEDecisionTable>>{
            editorInterface: EditorInterface.BE_DECISION_TABLE,
            editBuffer: this.mergeBuffer,
            mergeResult: this.mergeResult,
            base: this.mergeBase.content,
            lhs: this.mergeLhs.content,
            editorMode: 'sync',
            showChangeSet: this.diffOption,
          };
          break;
        case ArtifactType.SB_DECISION_TABLE:
          if (!this.mergeResult) {
            this.mergeBuffer = EditBuffer.decisionTable().init(this.mergeRhs.content, true);
            this.mergeResult = this.differ.merge(
              DecisionTable.fromXml(this.mergeBase.content),
              DecisionTable.fromXml(this.mergeLhs.content),
              this.mergeBuffer.getContent());
            this.mergeBuffer.replaceBuffer(this.mergeResult.getMerged());
          }
          this.lastParams = <EditorParams<DecisionTable>>{
            editorInterface: EditorInterface.SB_DECISION_TABLE,
            editBuffer: this.mergeBuffer,
            mergeResult: this.mergeResult,
            base: this.mergeBase.content,
            lhs: this.mergeLhs.content,
            editorMode: 'sync',
            showChangeSet: this.diffOption,
          };
          break;
        default:
          // This should never be called.
          break;
      }
      this.editorParams = Promise.resolve(this.lastParams);
    }
  }

  isOnlyLatestChanges(): boolean {
    const cellDiffSize = this.mergeResult.getConflict().getRhsDiff().getCellItems().size;
    const etCellDiffSize = this.mergeResult.getConflict().getRhsDiff().getETCellItems().size;
    const ruleDiffSize = this.mergeResult.getConflict().getRhsDiff().getRuleItems().size;
    const etRuleDiffSize = this.mergeResult.getConflict().getRhsDiff().getETRuleItems().size;
    const colDiffSize = this.mergeResult.getConflict().getRhsDiff().getColumnItems().size;
    const etColDiffSize = this.mergeResult.getConflict().getRhsDiff().getETColumnItems().size;

    if ((cellDiffSize + etCellDiffSize + ruleDiffSize + etRuleDiffSize + colDiffSize + etColDiffSize) > 0) {
      return false;
    } else {
      return true;
    }
  }

  canConfirm() {
    return !this.inMerge()
      || this.mergeResult && !this.mergeResult.hasConflict()
      || this.modification && !MergedDiffHelper.hasConflict(this.modification)
      || this.viewModification && !MergedDiffHelper.hasViewConflict(this.viewModification);
  }

  onConfirm() {
    let content: string;
    if (this.inLatest()) {
      /* No op as we already have everything we need to inform the server about the resolution */
      content = this.mergeLhs.content;
    } else if (this.inMerge() && this.canConfirm()) {
      content = this.mergeBuffer.serialize();
    } else {
      throw Error(this.i18n('Unknown status: {{currentStrategy}}, something must be wrong', { currentStrategy: this.currentStrategy }));
    }
    this.artifact
      .synchronize(this.mergeRhs, this.mergeLhs, this.currentStrategy, content)
      .then(res => {
        if (res) {
          this.artifact.markAsStale(this.mergeRhs.id);
          this.done.emit(res);
        }
      });
  }

  onCancel() {
    this.done.emit(null);
  }

  refresh() {
    if (this.lastParams) {
      this.editorParams = Promise
        .resolve(this.lastParams)
        .then(params => {
          params.showChangeSet = this.diffOption;
          return params;
        });
    }
  }

  refreshOnLatest() {
    if (this.lastParams) {
      this.editorParams = Promise
        .resolve(this.lastParams)
        .then(params => {
          params.showChangeSet = this.showDiff ? 'rhs' : 'none';
          return params;
        });
    }
  }

  reset() {
    if (this.inMerge()) {
      this.mergeBuffer = null;
      this.mergeResult = null;
      this.setInMerge();
    } else {
      throw Error(this.i18n('This Shall not be invoked if not in merge mode'));
    }
  }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  processRTIBuilderCommandsDiff(
    previousVersionRTI: RuleTemplateInstanceImpl,
    currentVersionRTI: RuleTemplateInstanceImpl,
    serverVersionRTI: RuleTemplateInstanceImpl,
  ): RuleTemplateInstanceImpl {
    const previousActions = previousVersionRTI.getCommands().getActions();
    const currentActions = currentVersionRTI.getCommands().getActions();
    const serverActions = serverVersionRTI.getCommands().getActions();
    let index: number;
    for (let i = 0; i < currentActions.length; i++) {
      const currentActionAlias = currentActions[i].getActionType() + '_' + currentActions[i].getAlias();
      let currentBuilderSubClause = currentActions[i].getFilter();
      let previousBuilderSubClause;
      let serverBuilderSubClause;
      for (let j = 0; j < previousActions.length; j++) {
        const previousActionAlias = previousActions[j].getActionType() + '_' + previousActions[j].getAlias();
        if (currentActionAlias === previousActionAlias) {
          previousBuilderSubClause = previousActions[j].getFilter();
          break;
        }
      }
      if (serverActions) {
        for (let j = 0; j < serverActions.length; j++) {
          index = 0;
          const serverActionAlias = serverActions[j].getActionType() + '_' + serverActions[j].getAlias();
          if (currentActionAlias === serverActionAlias) {
            serverBuilderSubClause = serverActions[j].getFilter();
            index = j;
            break;
          }
        }
      }
      const currentVersionCommandMultiFilter = new MultiFilterImpl();
      const previousVersionCommandMultiFilter = new MultiFilterImpl();
      const serverVersionCommandMultiFilter = new MultiFilterImpl();
      if (currentBuilderSubClause == null) {
        currentBuilderSubClause = new BuilderSubClauseImpl();
      }
      if (serverBuilderSubClause == null) {
        serverBuilderSubClause = new BuilderSubClauseImpl();
      }

      currentVersionCommandMultiFilter.setBuilderSubClause(currentBuilderSubClause);
      previousVersionCommandMultiFilter.setBuilderSubClause(previousBuilderSubClause);
      serverVersionCommandMultiFilter.setBuilderSubClause(serverBuilderSubClause);
      if (previousBuilderSubClause != null) {
        serverVersionRTI.getCommands()
          .getActions()[index]
          .setFilter(
            MergedDiffHelper.processMultiFilterMergedDiff(
              previousVersionCommandMultiFilter,
              currentVersionCommandMultiFilter,
              serverVersionCommandMultiFilter,
              this.modification)
              .getBuilderSubClause()
          );
      } else {
        serverVersionRTI.getCommands()
          .getActions()[index]
          .setFilter(
            MergedDiffHelper.processMultiFilterMergedDiff(
              currentVersionCommandMultiFilter,
              currentVersionCommandMultiFilter,
              serverVersionCommandMultiFilter,
              this.modification)
              .getBuilderSubClause()
          );
      }
    }
    return serverVersionRTI;
  }

  getMessage(): string {
    const msg = this.i18n('Apply repository changes between revisions {{0}} and {{1}} to your working copy', { 0: this.mergeBase.revisionNumber, 1: this.mergeRhs.revisionNumber });
    return msg;
  }

  private isDelete(artifact: Artifact) {
    return artifact.status === 'DELETED';
  }

}
