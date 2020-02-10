/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:05:31+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-28T20:59:52+05:30
 */

import { AfterViewInit, Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { BuilderSubClauseImpl } from './BuilderSubClauseImpl';
import { CommandOperator } from './CommandOperators';
import { FilterOperator } from './FilterOperators';
import { MultiFilterClient } from './MultiFilterClient';
import { MultiFilterImpl } from './MultiFilterImpl';
import { MatchType } from './MultiFilterImpl';
import { OperatorTypes } from './OperatorTypes';
import { SingleFilterClient } from './SingleFilterClient';
import { RuleTemplateInstanceBuilderEditorService } from './rule-template-instance-builder.service';

import { ArtifactPropertiesService } from '../../artifact-editor/artifact-properties.service';
import { BEOperatorService } from '../../core-be/be.operator.service';
import { AlertService } from '../../core/alert.service';
import { SettingsService } from '../../core/settings.service';
import { EditBuffer } from '../../editables/edit-buffer';
import { DiffHelper } from '../../editables/rule-template-instance/differ/DiffHelper';
import { ModificationEntry } from '../../editables/rule-template-instance/differ/ModificationEntry';
import { RuleTemplateInstance, RuleTemplateInstanceImpl } from '../../editables/rule-template-instance/rule-template-instance';
import { OperatorPreference } from '../../models-be/operator-preferences.modal';
import { BESettings } from '../../models-be/settings-be';
import { EditorParams } from '../editor-params';
import { EditorComponent } from '../editor.component';
import { ReviewPropertiesComponent } from '../review-properties.component';

@Component({
  selector: 'RTIBuilder',
  templateUrl: './RTIBuilder.html',
  styleUrls: ['rtiBuilder.css']
})

export class RuleTemplateInstanceBuilder extends EditorComponent<RuleTemplateInstanceImpl> implements OnInit, AfterViewInit {

  params: EditorParams<RuleTemplateInstanceImpl>;
  private editBuffer: EditBuffer<RuleTemplateInstanceImpl>;
  private _base: RuleTemplateInstanceImpl;
  private _multiFilterClientMap: Map<string, MultiFilterClient>;
  private _singleFilterClientMap: Map<string, SingleFilterClient>;
  private showDiff = false;
  private _oldMultiFilter?: MultiFilterImpl;
  private _rtiModifications?: Map<string, ModificationEntry>;
  private _filterOperators: Map<string, Array<OperatorTypes>>;
  private _commandOperators: Map<string, Array<OperatorTypes>>;
  private _defaultMatchType: MatchType = 'Match All';
  private _readOnly = false;
  /**
   * This map is for storing html elements of each filter against their filter id.
   * This map would be use for selection of filter when clicked on problem present
   * in problem selection.
   */
  private _filterClientProblemMap: Map<string, MultiFilterClient | SingleFilterClient>;
  private _previousSelectedProblematicFilter: MultiFilterClient | SingleFilterClient;

  constructor(private _editorService: RuleTemplateInstanceBuilderEditorService,
    private operator: BEOperatorService,
    private settings: SettingsService,
    private alert: AlertService,
    private propertiesService: ArtifactPropertiesService,
    public i18n: I18n) {
    super();
    this._multiFilterClientMap = new Map<string, MultiFilterClient>();
    this._filterOperators = new Map<string, Array<OperatorTypes>>();
    this._commandOperators = new Map<string, Array<OperatorTypes>>();
    this._filterClientProblemMap = new Map<string, MultiFilterClient | SingleFilterClient>();
  }

  ngOnInit() {
    this.filterOperator();
    this._defaultMatchType = <MatchType>(<BESettings>this.settings.latestSettings).defaultRTIFilterType;
    this.showDiff = this.params.editorMode === 'display'
      && this.params.showChangeSet === 'rhs';
    if (this.showDiff) {
      this.base = this.params.editBuffer.getBase();
      const ruleTemplateInstanceImpl = RuleTemplateInstance.deserialize(this.params.base);
      if (this.base.getSymbols().length === 0) {
        this.base.setSymbols(ruleTemplateInstanceImpl.getSymbols());
      }
      this._oldMultiFilter = ruleTemplateInstanceImpl.getConditions();
      this._rtiModifications = new Map<string, ModificationEntry>();
      DiffHelper.processMultiFilterDiff(this._oldMultiFilter,
        this.base.getConditions(),
        this._rtiModifications, true);
      this.diffActions(ruleTemplateInstanceImpl);
    } else {
      this.base = this.params.editBuffer.getBuffer();
      if (this.base == null) {
        this.base = this.params.editBuffer.getBase();
      }

    }
    if (this.params.editorMode === 'sync') {
      this.params.editBuffer.getBuffer().setisSyncMerge('true');
    }
    this.readOnly = this.params.editorMode !== 'edit';
  }

  ngAfterViewInit() {
    let priority = this.base.getRulepriortiy();
    if (priority == null || priority < 1) {
      priority = 1;
    } else if (priority > 10) {
      priority = 10;
    }
    let description = this.base.getDescription();
    if (description == null) {
      description = '';
    }
    this.propertiesService.setRTIBuilderProperties(this);
    this.base.setRulepriority(priority);
    this.base.setDescription(description);
  }

  /**
   * It will filter out operators available with all the data types.
   * The filtered operator list is generated from the current operator preference
   * settings.
   */
  filterOperator(): void {
    const operatorList: Array<OperatorPreference> = this.operator.latestOperatorPreference;
    if (operatorList == null || operatorList.length === 0) {
      this.defaultOperatorPreferences();
      return;
    }
    for (let i = 0; i < operatorList.length; i++) {
      const fieldType: string = operatorList[i].fieldType;
      const filterOperatorList: Array<OperatorTypes> = operatorList[i].filterOperators;
      const commandOperatorList: Array<OperatorTypes> = operatorList[i].commandOperators;
      this._filterOperators.set(fieldType, new Array<OperatorTypes>());
      this._commandOperators.set(fieldType, new Array<OperatorTypes>());
      /**
       * For filtering, Filter Opertors.
       */
      for (let j = 0; j < filterOperatorList.length; j++) {
        const operator: OperatorTypes = filterOperatorList[j];
        if (operator.available) {
          this._filterOperators.get(fieldType).push(operator);
        }
      }
      /**
       * For filtering, Command Opertors.
       */
      for (let j = 0; j < commandOperatorList.length; j++) {
        const operator: OperatorTypes = commandOperatorList[j];
        if (operator.available) {
          this._commandOperators.get(fieldType).push(operator);
        }
      }
    }
  }

  defaultOperatorPreferences() {
    const fieldType: string[] = ['String', 'integer', 'long', 'double', 'boolean', 'DateTime', 'Concept/Event'];
    for (let i = 0; i < fieldType.length; i++) {
      const operatorPreference = new OperatorPreference(fieldType[i]);
      operatorPreference.filterOperators = _.cloneDeep(FilterOperator.getOperators(operatorPreference.fieldType));
      operatorPreference.commandOperators = _.cloneDeep(CommandOperator.getOperators(operatorPreference.fieldType));
      for (let j = 0; j < operatorPreference.filterOperators.length; j++) {
        if (!this._filterOperators.has(fieldType[i])) {
          this._filterOperators.set(fieldType[i], new Array<OperatorTypes>());
        }
        this._filterOperators.get(fieldType[i]).push(operatorPreference.filterOperators[j]);
      }
      for (let j = 0; j < operatorPreference.commandOperators.length; j++) {
        if (!this._commandOperators.has(fieldType[i])) {
          this._commandOperators.set(fieldType[i], new Array<OperatorTypes>());
        }
        this._commandOperators.get(fieldType[i]).push(operatorPreference.commandOperators[j]);
      }
    }
  }

  get base(): RuleTemplateInstanceImpl {
    return this._base;
  }

  set base(value: RuleTemplateInstanceImpl) {
    this._base = value;
  }

  get readOnly(): boolean {
    return this._readOnly;
  }

  set readOnly(value: boolean) {
    this._readOnly = value;
  }

  get editorService(): RuleTemplateInstanceBuilderEditorService {
    return this._editorService;
  }

  get multiFilterclientMap(): Map<string, MultiFilterClient> {
    return this._multiFilterClientMap;
  }

  get rtiModifications(): Map<string, ModificationEntry> {
    return this._rtiModifications;
  }

  get filterOperators(): Map<string, Array<OperatorTypes>> {
    return this._filterOperators;
  }

  get commandOperators(): Map<string, Array<OperatorTypes>> {
    return this._commandOperators;
  }

  get defaultMatchType(): MatchType {
    return this._defaultMatchType;
  }

  public getShowDiff(): boolean {
    return this.showDiff;
  }

  focusOnLocations(locations: any[]) {
    if (this._previousSelectedProblematicFilter) {
      this._previousSelectedProblematicFilter.isProblematic = false;
    }
    const colIdSplit: Array<string> = locations[0].colId.split(':');
    const filterId: string = colIdSplit[colIdSplit.length - 1].trim();
    const client = this.filterClientProblemMap.get(filterId);
    if (client) {
      client.uiComponent.location.nativeElement.focus();
      client.isProblematic = true;
      this._previousSelectedProblematicFilter = client;
    } else {
      this.alert.flash(this.i18n('The filter related to this problem is now unavailable.'), 'warning');
    }
  }

  get filterClientProblemMap(): Map<string, MultiFilterClient | SingleFilterClient> {
    return this._filterClientProblemMap;
  }

  get showProperties(): boolean {
    if (this.params.editorMode === 'display') {
      return true;
    } else {
      return false;
    }
  }

  get initializationObject() {
    if (this.params.editBuffer.getBuffer()) {
      return this.params.editBuffer.getBuffer();
    } else if (this.params.editBuffer.getBase()) {
      return this.params.editBuffer.getBase();
    }
  }

  diffActions(ruleTemplateInstanceImpl: RuleTemplateInstanceImpl): void {
    const currentActions = this.base.getCommands().getActions();
    const previousActions = ruleTemplateInstanceImpl.getCommands().getActions();
    if (currentActions) {
      if (previousActions) {
        for (let i = 0; i < currentActions.length; i++) {
          for (let j = 0; j < previousActions.length; j++) {
            if (currentActions[i].getActionType() === previousActions[i].getActionType()
              && currentActions[i].getAlias() === previousActions[i].getAlias()) {
              const previousMultiFilterImplCommands: MultiFilterImpl = new MultiFilterImpl();
              const currentMultiFilterImplCommands: MultiFilterImpl = new MultiFilterImpl();
              if (currentActions[i].getFilter()) {
                currentMultiFilterImplCommands.setBuilderSubClause(currentActions[i].getFilter());
              } else {
                currentMultiFilterImplCommands.setBuilderSubClause(new BuilderSubClauseImpl());
              }
              if (previousActions[i].getFilter()) {
                previousMultiFilterImplCommands.setBuilderSubClause(previousActions[i].getFilter());
              } else {
                previousMultiFilterImplCommands.setBuilderSubClause(new BuilderSubClauseImpl());
              }
              DiffHelper.processMultiFilterDiff(previousMultiFilterImplCommands,
                currentMultiFilterImplCommands,
                this._rtiModifications);
            }
          }
        }
      }
    }
  }
}
