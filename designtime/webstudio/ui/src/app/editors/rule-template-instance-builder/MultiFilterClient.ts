/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:04:04+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-05-05T13:29:25+05:30
 */

import {
  Component,
  ComponentFactory,
  ComponentFactoryResolver,
  ComponentRef,
  Injector,
  Input,
  OnInit,
  ViewChild,
  ViewContainerRef
} from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { BuilderSubClauseImpl } from './BuilderSubClauseImpl';
import { CommandOperator } from './CommandOperators';
import { FilterClient } from './FilterClient';
import { FilterOperator } from './FilterOperators';
import { MatchType } from './MultiFilterImpl';
import { MultiFilterImpl } from './MultiFilterImpl';
import { RelatedFilterValueImpl } from './RelatedFilterValueImpl';
import { SimpleFilterValueImpl } from './SimpleFilterValueImpl';
import { SingleFilterClient } from './SingleFilterClient';
import { SingleFilterImpl } from './SingleFilterImpl';
import { SymbolInfo } from './SymbolInfo';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder.component';

import { ModalService } from '../../core/modal.service';
import { MergedDiffHelper } from '../../editables/rule-template-instance/differ/MergeDiffHelper';
import { ModificationEntry } from '../../editables/rule-template-instance/differ/ModificationEntry';
import { ModificationType } from '../../editables/rule-template-instance/differ/ModificationType';
import { RTIConfilctResolverModal, RTIConflictResolverContext } from '../../editables/rule-template-instance/differ/rti-conflict-resolver.modal';
import { RuleTemplateInstanceImpl } from '../../editables/rule-template-instance/rule-template-instance';
import { EditorParams } from '../editor-params';

@Component({
  selector: 'rtiWhen',
  templateUrl: './RTIBuilderWhenSection.html',
  styleUrls: ['rtiBuilder.css']
})

export class MultiFilterClient extends FilterClient implements OnInit {

  get parentClause(): BuilderSubClauseImpl {
    return this._parentClause;
  }

  set parentClause(clause: BuilderSubClauseImpl) {
    this._parentClause = clause;
  }

  get allowRemoveClause(): boolean {
    return this._allowRemoveClause;
  }

  set allowRemoveClause(value: boolean) {
    this._allowRemoveClause = value;
  }

  get allowNestedClause(): boolean {
    return this._allowNestedClause;
  }

  set allowNestedClause(value: boolean) {
    this._allowNestedClause = value;
  }

  get showMatchType(): boolean {
    return this._showMatchType;
  }

  set showMatchType(value: boolean) {
    this._showMatchType = value;
  }

  get multiFilter(): MultiFilterImpl {
    return this._multiFilter;
  }

  set multiFilter(value: MultiFilterImpl) {
    this._multiFilter = value;
  }

  set uiComponent(value: ComponentRef<MultiFilterClient>) {
    this._uiComponent = value;
  }

  get uiComponent(): ComponentRef<MultiFilterClient> {
    return this._uiComponent;
  }

  get isAdded(): boolean {
    return this._isAdded;
  }

  set isAdded(value: boolean) {
    this._isAdded = value;
  }

  get isDeleted(): boolean {
    return this._isDeleted;
  }

  set isDeleted(value: boolean) {
    this._isDeleted = value;
  }

  get isModified(): boolean {
    return this._isModified;
  }

  set isModified(value: boolean) {
    this._isModified = value;
  }

  get isProblematic(): boolean {
    return this._isProblem;
  }

  set isProblematic(value: boolean) {
    this._isProblem = value;
  }

  get isServerChange(): boolean {
    return this._isServerChange;
  }

  set isServerChange(value: boolean) {
    this._isServerChange = value;
  }

  get isConflict(): boolean {
    return this._isConflict;
  }

  set isConflict(value: boolean) {
    this._isConflict = value;
  }

  get previousValue(): string {
    return this._previousValue;
  }

  set previousValue(value: string) {
    this._previousValue = value;
  }

  get showDiff(): boolean {
    return this._showDiff;
  }

  set showDiff(value: boolean) {
    this._showDiff = value;
  }

  get readOnly(): boolean {
    return this._readOnly;
  }

  set readOnly(value: boolean) {
    this._readOnly = value;
  }

  get allowAddFilter(): boolean {
    return this._allowAddFilter;
  }

  set allowAddFilter(value: boolean) {
    this._allowAddFilter = value;
  }

  @Input()
  params: EditorParams<RuleTemplateInstanceImpl>;

  @Input()
  symbols: Array<SymbolInfo>;

  @Input()
  _multiFilter: MultiFilterImpl;

  @Input()
  parentRTIEditor: RuleTemplateInstanceBuilder;

  SINGLE_FILTER_FACTORY: ComponentFactory<SingleFilterClient>;
  MULTI_FILTER_FACTORY: ComponentFactory<MultiFilterClient>;
  public _matchTypeOptions: Array<String> = ['Match All', 'Match Any', 'Match None'];
  isMultiFilter = true;
  aliasConceptName?: string;
  actionConceptType: string;
  actionType: string;
  commandInfoSymbols: SymbolInfo;

  @ViewChild('child', { static: true, read: ViewContainerRef })
  private singleFilterView: ViewContainerRef;
  private _allowRemoveClause = true;
  private _allowNestedClause = true;
  private _allowAddFilter = true;
  private _showMatchType = true;
  private _uiComponent: ComponentRef<MultiFilterClient>;
  private _parentUiComponent: MultiFilterClient;
  private _parentClause: BuilderSubClauseImpl;
  private _showDiff = false;
  private _isAdded = false;
  private _isDeleted = false;
  private _isModified = false;
  private _isProblem = false;
  private _isServerChange = false;
  private _isConflict = false;
  private _previousValue = '';
  private modal: ModalService;
  private _readOnly = true;

  constructor(private viewContainerRef: ViewContainerRef,
    private _componentFactoryResolver: ComponentFactoryResolver,
    private injector: Injector,
    public i18n: I18n
  ) {
    super();
    this.SINGLE_FILTER_FACTORY = this._componentFactoryResolver.resolveComponentFactory(SingleFilterClient);
    this.MULTI_FILTER_FACTORY = this._componentFactoryResolver.resolveComponentFactory(MultiFilterClient);
    this.modal = injector.get(ModalService);
  }

  ngOnInit() {
    FilterOperator.intialize();
    CommandOperator.initialize();
    this.readOnly = this.parentRTIEditor.readOnly;
    if (this.multiFilter == null) {
      this._multiFilter = new MultiFilterImpl();
    } else if (this.isMultiFilter) {
      this.showDiff = this.params.editorMode === 'display' && this.params.showChangeSet === 'rhs';
      this.generateMultiFilterClient(this.multiFilter);
    }
  }

  public onAddSingleFilter(event?: any, filter?: SingleFilterImpl, position?: number): SingleFilterClient {

    let newSingleFilter: ComponentRef<SingleFilterClient>;
    if (position != null && position > -1) {
      newSingleFilter = this.singleFilterView.createComponent(this.SINGLE_FILTER_FACTORY, position);
    } else {
      newSingleFilter = this.singleFilterView.createComponent(this.SINGLE_FILTER_FACTORY);
    }

    const newSingleFilterInstance: SingleFilterClient = newSingleFilter.instance;
    /*
    * Don't combine above two lines.
    */
    newSingleFilterInstance.params = this.params;
    newSingleFilterInstance.uiComponent = newSingleFilter;
    newSingleFilterInstance.readOnly = this.readOnly;
    newSingleFilterInstance.parentClause = this.multiFilter.getBuilderSubClause();
    newSingleFilterInstance.currentSymbols = SingleFilterClient.removeExtraSymbols(this.symbols);
    newSingleFilterInstance.currentLHSSymbols = this.isMultiFilter
      ? newSingleFilterInstance.currentSymbols
      : this.getContainedSymbols();
    const currentPropertiesOfSelectedItem = newSingleFilter.instance.currentSymbols[0].getSymbolInfo();
    if (currentPropertiesOfSelectedItem != null && currentPropertiesOfSelectedItem.length > 0) {
      newSingleFilterInstance.currentProperties = currentPropertiesOfSelectedItem;
      newSingleFilterInstance.showProperties = true;
    }
    newSingleFilterInstance.isFilterOperator = this.isMultiFilter;
    newSingleFilterInstance.parentUiComponent = this;
    newSingleFilterInstance.parentRTIEditor = this.parentRTIEditor;
    newSingleFilterInstance.actionType = this.actionType;
    /*
    * When creating UI from exisiting model object, filter will not be null.
    * When creating single filter from user interaction, filter will be null.
    */
    if (filter != null) {
      newSingleFilterInstance.filter.setFilterId(filter.getFilterId());
      /*
      * Create lhs operand(filter links) of the current filter.
      */
      let previousLinkSymbolInfo: Array<SymbolInfo>;
      if (this.isMultiFilter) {
        previousLinkSymbolInfo = newSingleFilterInstance.fetchSelectedSymbol(filter.getLink(), newSingleFilterInstance.currentSymbols);
      } else {
        previousLinkSymbolInfo = newSingleFilterInstance.fetchSelectedSymbol(filter.getLink(), this.getContainedSymbols());
      }
      newSingleFilterInstance.updateRTIFilter(previousLinkSymbolInfo[0], 0, true, false);
      for (let i = 1; i < previousLinkSymbolInfo.length; i++) {
        newSingleFilterInstance.updateRTIFilter(previousLinkSymbolInfo[i], i, false, false);
      }
      const domainModel = previousLinkSymbolInfo[previousLinkSymbolInfo.length - 1].getDomainInfo();
      if (domainModel) {
        const values = domainModel.getValues();
        if (values && values.length > 0) {
          newSingleFilterInstance.domainValues = values;
          newSingleFilterInstance.showDomainModel = true;
        }
      }
      /*
      * Create and assign operator.
      */
      const operator = this.isMultiFilter ?
        FilterOperator.getOperator(filter.getOperator()) :
        CommandOperator.getOperator(filter.getOperator());
      newSingleFilterInstance.onOperatorSelection(operator, false);
      /*
      *Create rhs operand(filter value) of the current filter.
      */
      if (filter.getValue() instanceof RelatedFilterValueImpl) {
        const links = (<RelatedFilterValueImpl>filter.getValue()).getLinks();
        const previousLinkValueSymbolInfo: Array<SymbolInfo> = newSingleFilterInstance.fetchSelectedSymbol(links, newSingleFilterInstance.currentSymbols);
        newSingleFilterInstance.filter.setValue(new RelatedFilterValueImpl());
        for (let i = 0; i < previousLinkValueSymbolInfo.length; i++) {
          newSingleFilterInstance.updateLinkFilterValue(previousLinkValueSymbolInfo[i], i, true, false);
        }
      } else {
        newSingleFilterInstance.filter.setValue(filter.getValue());
        newSingleFilterInstance.oldValue = (<SimpleFilterValueImpl>filter.getValue()).value;
      }
      if (position != null && position > -1) {
        this.multiFilter.getBuilderSubClause().addFilter(newSingleFilterInstance.filter, position);
      }
      if (this.showDiff) {
        const modificationEntry: ModificationEntry = this.parentRTIEditor.rtiModifications.get(newSingleFilterInstance.filter.getFilterId());
        if (modificationEntry) {
          modificationEntry.getModificationType() === ModificationType.MODIFIED
            ? MergedDiffHelper.applyCss(modificationEntry, newSingleFilterInstance, modificationEntry.getPreviousValue())
            : MergedDiffHelper.applyCss(modificationEntry, newSingleFilterInstance);
        }
      }
      if (this.params.editorMode === 'sync' && this.params.mergeResultRTI.has(newSingleFilterInstance.filter.getFilterId())) {
        MergedDiffHelper.syncMerge(newSingleFilterInstance, this.params.showChangeSet, this.params.mergeResultRTI);
      }

    } else {
      this.multiFilter.getBuilderSubClause().addFilter(newSingleFilterInstance.filter);
      const symbolInfo: SymbolInfo = this.isMultiFilter
        ? newSingleFilterInstance.currentSymbols[0]
        : this.getContainedSymbols()[0];
      // Create lhs operand(filter links) of the new filter.
      newSingleFilterInstance.updateRTIFilter(symbolInfo, 0, true, false);
      // Create and assign operator.
      newSingleFilterInstance.onOperatorSelection(newSingleFilterInstance.currentOperators[0], false);
      // Create rhs operand(filter value) of the current filter.
      newSingleFilterInstance.filter.setValue(new SimpleFilterValueImpl());
      (<SimpleFilterValueImpl>newSingleFilterInstance.filter.getValue()).value = '';
      newSingleFilterInstance.oldValue = '';
      if (event) {
        const action = this.parentRTIEditor.editorService.addSingleFilter(newSingleFilterInstance, this, false, this.multiFilter.getBuilderSubClause().getFilters().length - 1);
        this.parentRTIEditor.execute(action);
      }
    }
    this.parentRTIEditor.filterClientProblemMap.set(newSingleFilterInstance.filter.getFilterId(), newSingleFilterInstance);
    return newSingleFilterInstance;
  }

  public onAddMultiFilter(event?: any, filter?: MultiFilterImpl, position?: number): MultiFilterClient {
    let newMultiFilter: ComponentRef<MultiFilterClient>;
    if (position != null && position > -1) {
      newMultiFilter = this.singleFilterView.createComponent(this.MULTI_FILTER_FACTORY, position);
    } else {
      newMultiFilter = this.singleFilterView.createComponent(this.MULTI_FILTER_FACTORY);
    }
    newMultiFilter.instance._allowRemoveClause = true;
    newMultiFilter.instance._allowNestedClause = true;
    newMultiFilter.instance.showMatchType = true;
    newMultiFilter.instance.readOnly = this.readOnly;
    newMultiFilter.instance._uiComponent = newMultiFilter;
    newMultiFilter.instance.parentClause = this.multiFilter.getBuilderSubClause();
    newMultiFilter.instance.symbols = this.symbols;
    newMultiFilter.instance.params = this.params;
    newMultiFilter.instance.parentRTIEditor = this.parentRTIEditor;
    newMultiFilter.instance._parentUiComponent = this;
    if (filter != null) {
      newMultiFilter.instance.multiFilter = filter;
    } else {
      newMultiFilter.instance.multiFilter = new MultiFilterImpl(this.parentRTIEditor.defaultMatchType);
    }
    if (position && position > -1) {
      this.multiFilter.getBuilderSubClause().addFilter(newMultiFilter.instance.multiFilter, position);
    } else {
      this.multiFilter.getBuilderSubClause().addFilter(newMultiFilter.instance.multiFilter);
    }

    if (event) {
      const action = this.parentRTIEditor.editorService.addMultiFilter(newMultiFilter.instance, this, false,
        this.multiFilter.getBuilderSubClause().getFilters().length - 1);
      this.parentRTIEditor.execute(action);
    }
    if (this.showDiff) {
      const modificationEntry: ModificationEntry = this.parentRTIEditor.rtiModifications.get(newMultiFilter.instance.multiFilter.getFilterId());
      if (modificationEntry) {
        modificationEntry.getModificationType() === ModificationType.MODIFIED
          ? MergedDiffHelper.applyCss(modificationEntry, newMultiFilter.instance, modificationEntry.getPreviousValue())
          : MergedDiffHelper.applyCss(modificationEntry, newMultiFilter.instance);
      }
    }
    if (this.params.editorMode === 'sync' && this.params.mergeResultRTI.has(newMultiFilter.instance.multiFilter.getFilterId())) {
      MergedDiffHelper.syncMerge(newMultiFilter.instance, this.params.showChangeSet, this.params.mergeResultRTI);
    }
    this.parentRTIEditor.filterClientProblemMap.set(newMultiFilter.instance.multiFilter.getFilterId(), newMultiFilter.instance);
    return newMultiFilter.instance;
  }

  public generateMultiFilterClient(multiFilterImpl: MultiFilterImpl): MultiFilterClient {
    if (multiFilterImpl != null) {
      const builderSubClauseImpl: BuilderSubClauseImpl = multiFilterImpl.getBuilderSubClause();
      if (builderSubClauseImpl != null) {
        const currentFilters = builderSubClauseImpl.getFilters();
        for (let i = 0; i < currentFilters.length; i++) {
          const filter = currentFilters[i];
          if (filter instanceof MultiFilterImpl) {
            this.onAddMultiFilter(null, filter);
          } else if (filter instanceof SingleFilterImpl) {
            this.onAddSingleFilter(null, filter);
          }
        }
      }
    }
    return this;
  }

  public generateActionFilter(multiFilterImpl: MultiFilterImpl): MultiFilterClient {
    if (multiFilterImpl != null) {
      const builderSubClauseImpl: BuilderSubClauseImpl = multiFilterImpl.getBuilderSubClause();
      if (builderSubClauseImpl != null) {
        const currentFilters = builderSubClauseImpl.getFilters();
        for (let i = 0; i < currentFilters.length; i++) {
          const filter = currentFilters[i];
          if (filter instanceof MultiFilterImpl) {
            this.onAddMultiFilter(null, filter);
            this.generateMultiFilterClient(filter);
          } else if (filter instanceof SingleFilterImpl) {
            this.onAddSingleFilter(null, filter);
          }
        }
      }
    }
    return this;
  }

  public onDeleteMultiFilter(filter: MultiFilterClient, event?: any): number {
    const clause = filter.parentClause;
    const bufferMultiFilter = this.params.editBuffer.getBuffer().getConditions();
    const parentClause = this.getParentMultiFilterFromEditBuffer(clause, bufferMultiFilter);
    const position = parentClause.removeFilter(filter.multiFilter);
    clause.removeFilter(filter.multiFilter);
    filter._uiComponent.destroy();
    if (event) {
      const action = this.parentRTIEditor.editorService.deleteMultiFilter(filter, this._parentUiComponent, false, position);
      this.parentRTIEditor.execute(action);
    }
    this.parentRTIEditor.filterClientProblemMap.delete(filter.multiFilter.getFilterId());
    return position;
  }

  public updateMatchType(matchType: MatchType, fromUI?: boolean): void {
    if (this.readOnly && fromUI) {
      return;
    }
    const action = this.parentRTIEditor.editorService.modifyMultiFilter(this.multiFilter.getMatchType(), matchType, this.multiFilter);
    this.parentRTIEditor.execute(action);
  }

  getParentMultiFilterFromEditBuffer(parentClause: BuilderSubClauseImpl, multiFilterImpl: MultiFilterImpl): BuilderSubClauseImpl {
    let parentMultiFilterSubClause: BuilderSubClauseImpl;
    if (multiFilterImpl.getBuilderSubClause() === parentClause) {
      parentMultiFilterSubClause = multiFilterImpl.getBuilderSubClause();
    } else {
      const currentFilters = multiFilterImpl.getBuilderSubClause().getFilters();
      for (let i = 0; i < currentFilters.length; i++) {
        const filter = currentFilters[i];
        if (filter instanceof MultiFilterImpl) {
          parentMultiFilterSubClause = this.getParentMultiFilterFromEditBuffer(parentClause, filter);
          if (parentMultiFilterSubClause != null) {
            break;
          }
        }
      }
    }
    return parentMultiFilterSubClause;
  }

  getContainedSymbols(): Array<SymbolInfo> {
    let symbolInfos: Array<SymbolInfo>;
    for (let i = 0; i < this.symbols.length; i++) {
      if ((this.symbols[i].getSymbolAlias() === this.aliasConceptName || this.symbols[i].getType() === this.actionConceptType)
        && this.symbols[i].getSymbolInfo() != null
        && this.symbols[i].getSymbolInfo().length > 0) {
        symbolInfos = this.symbols[i].getSymbolInfo();
        break;
      }
    }
    if (symbolInfos === undefined || symbolInfos.length === 0) {
      symbolInfos = this.commandInfoSymbols.getSymbolInfo();
    }
    return this.filterContainedSymbols(symbolInfos);
  }

  public toggled(event: Event): void {
    // console.log('Dropdown is now: ', open);
    event.preventDefault();
  }

  public resolveConflict(): void {
    if (this.params.editorMode === 'sync' && this.isConflict) {
      const modificationList = this.params.mergeResultRTI;
      if (modificationList.has(this.multiFilter.getFilterId())) {
        const modificationEntry = modificationList.get(this.multiFilter.getFilterId());
        if (!modificationEntry.isApplied()) {
          this.modal
            .open(RTIConfilctResolverModal, new RTIConflictResolverContext(this.multiFilter,
              this.params.editBuffer, modificationEntry, 'latest', 'working', false))
            .then((resolution: string) => {
              modificationEntry.setApplied(true);
              switch (modificationEntry.getModificationType()) {
                case ModificationType.ADDED:
                  if (resolution === 'rhs') {
                    this.onDeleteMultiFilter(this);
                  } else {
                    this.isAdded = true;
                  }
                  break;
                case ModificationType.DELETED:
                  if (resolution === 'rhs') {
                    this.onDeleteMultiFilter(this);
                  }
                  break;
                case ModificationType.MODIFIED:
                  if (resolution === 'rhs') {
                    const localFilter = <MultiFilterImpl>modificationEntry.localVersionObj;
                    this.multiFilter.setMatchType(localFilter.getMatchType());
                    this.multiFilter.setBuilderSubClause(localFilter.getBuilderSubClause());
                    this.isModified = true;
                  }
                  break;
              }
              // console.log('resolution: ' + resolution);
            });
        }
      }
    }
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

  private filterContainedSymbols(symbolInfos: Array<SymbolInfo>): Array<SymbolInfo> {
    switch (this.actionType) {
      case 'create':
        return symbolInfos.filter(symbolInfo => symbolInfo.getSymbolAlias().toUpperCase() !== 'ID'
          && symbolInfo.getSymbolAlias().toUpperCase() !== 'LENGTH');
      case 'call': return symbolInfos;
      case 'modify': return symbolInfos
        .filter(symbolInfo => symbolInfo.getSymbolAlias().toUpperCase() !== 'ID'
          && symbolInfo.getSymbolAlias().toUpperCase() !== 'LENGTH'
          && symbolInfo.getSymbolAlias().toUpperCase() !== 'EXTID');
    }
  }
  private show(filter: MultiFilterClient): void {
    // console.log(filter.multiFilter.getBuilderSubClause());
  }
}
