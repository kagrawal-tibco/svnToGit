/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:07:23+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-05-05T13:47:45+05:30
 */

import { Component, ComponentRef, Injector } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { BuilderSubClauseImpl } from './BuilderSubClauseImpl';
import { EmptyFilterValueImpl } from './EmptyFilterValueImpl';
import { FilterClient } from './FilterClient';
import { MultiFilterClient } from './MultiFilterClient';
import { MultiFilterImpl } from './MultiFilterImpl';
import { OperatorTypes } from './OperatorTypes';
import { RelatedFilterValueImpl } from './RelatedFilterValueImpl';
import { RelatedLinkImpl } from './RelatedLinkImpl';
import { SimpleFilterValueImpl } from './SimpleFilterValueImpl';
import { SingleFilterImpl } from './SingleFilterImpl';
import { SymbolInfo } from './SymbolInfo';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder.component';

import { ModalService } from '../../core/modal.service';
import { ModificationType } from '../../editables/rule-template-instance/differ/ModificationType';
import { RTIConfilctResolverModal, RTIConflictResolverContext } from '../../editables/rule-template-instance/differ/rti-conflict-resolver.modal';
import { RuleTemplateInstanceImpl } from '../../editables/rule-template-instance/rule-template-instance';
import { DateTimePickerComponent } from '../../shared/be-date-time-picker/DateTimePickerComponent';
import { EditorParams } from '../editor-params';
import { SingleEntry } from '../rule-template-instance-view/SingleEntry';

@Component({
  selector: 'singleFilter',
  templateUrl: './SingleFilterClient.html',
  styleUrls: ['rtiBuilder.css'],
})
export class SingleFilterClient extends FilterClient {

  get filter(): SingleFilterImpl {
    return this._filter;
  }

  set filter(filter: SingleFilterImpl) {
    this._filter = filter;
  }

  get uiComponent(): ComponentRef<SingleFilterClient> {
    return this._uiComponent;
  }

  set uiComponent(singleFilterUI: ComponentRef<SingleFilterClient>) {
    this._uiComponent = singleFilterUI;
  }

  get parentClause(): BuilderSubClauseImpl {
    return this._parentClause;
  }

  set parentClause(clause: BuilderSubClauseImpl) {
    this._parentClause = clause;
  }

  get parentUiComponent(): MultiFilterClient {
    return this._parentUiComponent;
  }

  set parentUiComponent(value: MultiFilterClient) {
    this._parentUiComponent = value;
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

  get isProblematic(): boolean {
    return this._isProblem;
  }

  set isProblematic(value: boolean) {
    this._isProblem = value;
  }
  public static symbols: Array<SymbolInfo> = new Array<SymbolInfo>();

  params: EditorParams<RuleTemplateInstanceImpl>;
  showDomainModel = false;
  public rtiForm: FormGroup;
  selectedOperator: OperatorTypes;
  currentOperators: OperatorTypes[];
  currentProperties: SymbolInfo[];
  currentLHSMenuOperators: OperatorTypes[];
  currentLHSMenuProperties: SymbolInfo[];
  currentRHSMenuProperties: SymbolInfo[];
  currentRHSSubMenuProperties: SymbolInfo[];
  showValueField = 'input';
  showProperties = false;
  currentSymbols: Array<SymbolInfo> = new Array<SymbolInfo>();
  currentLHSSymbols: Array<SymbolInfo> = new Array<SymbolInfo>();
  domainValues: Array<SingleEntry> = new Array<SingleEntry>();
  dateValue: Date = new Date();
  timeValue: Date = new Date();
  dateTimeValue: Date = new Date();
  isFilterOperator = true;
  parentRTIEditor: RuleTemplateInstanceBuilder;
  oldValue = '';
  readOnly = true;
  isDropup: boolean;
  actionType: string;

  private _uiComponent: ComponentRef<SingleFilterClient>;
  private _parentClause: BuilderSubClauseImpl;
  private _filter: SingleFilterImpl;
  private showDateTimePicker = false;
  private _parentUiComponent: MultiFilterClient;
  private _isAdded = false;
  private _isDeleted = false;
  private _isModified = false;
  private _isProblem = false;
  private _isServerChange = false;
  private _isConflict = false;
  private _previousValue = '';
  private modal: ModalService;

  constructor(private _formBuilder: FormBuilder,
    private injector: Injector,
    public i18n: I18n
  ) {
    super();
    this.rtiForm = this.addFilterUI();
    this._filter = new SingleFilterImpl();
    this.modal = injector.get(ModalService);
  }

  public static removeExtraSymbols(rawSymbolInfo: Array<SymbolInfo>): Array<SymbolInfo> {
    const symbols: Array<SymbolInfo> = new Array<SymbolInfo>();
    for (let i = 0; i < rawSymbolInfo.length; i++) {
      if (rawSymbolInfo[i] && rawSymbolInfo[i].getSymbolAlias() != null) {
        symbols.push(rawSymbolInfo[i]);
      }
    }
    return symbols;
  }

  /*
  * This method will update filter value and model as per the change of selection.
  * @param selectedItem : item which is selected.
  * @param i : Index of the selected link.
  * @param subMenu : If the newly selected item was part of menu (false) else true.
  * @return  : void
  */
  updateLinkFilterValue(selectedItem: SymbolInfo, i: number, subMenu: boolean, fromUI: boolean, event?: Event): void {
    if (this.readOnly && fromUI) {
      return;
    }
    const filterControl = <FormArray>this.rtiForm.controls['rhsFilter'];
    const filterControlLength = filterControl.length;
    const filterValue = <RelatedFilterValueImpl>this._filter.getValue();
    const length = filterValue.getLinks().length;
    const oldSingleFilter: SingleFilterImpl = _.cloneDeep(this.filter);
    if (subMenu) {
      filterValue.getLinks().splice(i + 1, length - i - 1);
      for (let filterIndex = filterControlLength - 1; filterIndex > i; filterIndex--) {
        filterControl.removeAt(filterIndex);
      }
      filterValue.addLink(new RelatedLinkImpl(selectedItem.getSymbolAlias(), selectedItem.getType()));
      filterControl.push(this._formBuilder.group({ rhsLink: [''] }));
    } else {
      filterValue.getLinks()[i] = new RelatedLinkImpl(selectedItem.getSymbolAlias(), selectedItem.getType());
      filterValue.getLinks().splice(i + 1, length - i - 1);
      for (let filterIndex = filterControlLength - 1; filterIndex > i; filterIndex--) {
        filterControl.removeAt(filterIndex);
      }
    }
    const selectedItemChildSymbolInfo = selectedItem.getSymbolInfo();
    if (i === 0 && selectedItemChildSymbolInfo != null && selectedItemChildSymbolInfo.length > 0) {
      this.currentRHSSubMenuProperties = selectedItemChildSymbolInfo;
    }
    if (fromUI) {
      const subClause = this.isFilterOperator ? this.params.editBuffer.getBuffer().getConditions().getBuilderSubClause()
        : this.params.editBuffer.getBuffer().getCommands().getActions()[this.fetchIndexOfParentAction()].getFilter();
      const action = this.parentRTIEditor.editorService.modifySingleFilter(oldSingleFilter, this.filter, this, subClause);
      this.parentRTIEditor.execute(action);
    }
    if (event) {
      // event.preventDefault();
    }
  }

  updateFilterValue(event: any): void {
    if (event) {
      const oldSingleFilter: SingleFilterImpl = _.cloneDeep(this.filter);
      const subClause = this.isFilterOperator ? this.params.editBuffer.getBuffer().getConditions().getBuilderSubClause()
        : this.params.editBuffer.getBuffer().getCommands().getActions()[this.fetchIndexOfParentAction()].getFilter();
      oldSingleFilter.setValue(new SimpleFilterValueImpl().value = this.oldValue);
      const action = this.parentRTIEditor.editorService.modifySingleFilter(oldSingleFilter, this.filter, this, subClause);
      this.parentRTIEditor.execute(action);
    }
  }

  updateDateValue(newDate: string): void {
    this.oldValue = (<SimpleFilterValueImpl>this.filter.getValue()).value;
    const filterValue = new SimpleFilterValueImpl();
    filterValue.value = newDate;
    this.filter.setValue(filterValue);
    this.updateFilterValue(newDate);
  }

  updateDomainValue(event: any): void {
    this.oldValue = (<SimpleFilterValueImpl>this.filter.getValue()).value;
    const filterValue = new SimpleFilterValueImpl();
    filterValue.value = event.target.options[event.target.selectedIndex].text;
    this.filter.setValue(filterValue);
    this.updateFilterValue(event);
  }
  /*
  * This method will be called when the operator of the filter is modified.
  * It will change lhs and rhs operands on the basis of the selected operator.
  * @param selectedOperator: the selected operator.
  * @param fromUI : True, if the function call was originated from the user activity.
  * @return : void.
  */
  onOperatorSelection(selectedOperator: OperatorTypes, fromUI: boolean, event?: Event): void {
    if (this.readOnly && fromUI) {
      return;
    }
    if (selectedOperator != null) {
      this.selectedOperator = selectedOperator;
      const oldSingleFilter: SingleFilterImpl = _.cloneDeep(this.filter);
      this.filter.setOperator(selectedOperator.operator);
      if (selectedOperator.operatorType === 'primitive') {
        if (this.showDateTimePicker) {
          this.showValueField = 'DateTime';
          this.filter.setValue(new SimpleFilterValueImpl());
        } else if (this.showDomainModel) {
          this.showValueField = 'DomainModel';
          const defaultValue: SimpleFilterValueImpl = new SimpleFilterValueImpl();
          defaultValue.value = this.domainValues[0].getValue();
          this.filter.setValue(defaultValue);
        } else {
          this.showValueField = 'input';
          this.filter.setValue(new SimpleFilterValueImpl());
        }
      } else if (selectedOperator.operatorType === 'non primitive') {
        this.showValueField = 'link';
        const filterValue = new RelatedFilterValueImpl();
        if (fromUI) {
          filterValue.addLink(new RelatedLinkImpl(this.currentSymbols[0].getSymbolAlias(),
            this.currentSymbols[0].getType()));
          this._filter.setValue(filterValue);
        }
        this.currentRHSMenuProperties = this.currentSymbols;
        this.currentRHSSubMenuProperties = this.currentSymbols[0].getSymbolInfo();
      } else {
        this.showValueField = 'NotApplicable';
        this._filter.setValue(new EmptyFilterValueImpl());
      }
      if (fromUI) {
        const subClause = this.isFilterOperator ? this.params.editBuffer.getBuffer().getConditions().getBuilderSubClause()
          : this.params.editBuffer.getBuffer().getCommands().getActions()[this.fetchIndexOfParentAction()].getFilter();
        const action = this.parentRTIEditor.editorService.modifySingleFilter(oldSingleFilter, this.filter, this, subClause);
        this.parentRTIEditor.execute(action);
      }
    }
    if (event) {
      //  event.preventDefault();
    }
  }

  /*
  * This method will update links and operator as per the change of selection.
  * @param selectedItem : item which is selected.
  * @param i : Index of the selected link.
  * @param lhs : If it is a lhs operand or operator.
  */
  updateRTIFilter(selectedItem: SymbolInfo, i: number, lhs: boolean, fromUI: boolean, event?: Event, rhs?: boolean): void {

    // console.log(selectedItem);
    if (this.readOnly && fromUI) {
      return;
    }
    const oldSingleFilter: SingleFilterImpl = _.cloneDeep(this.filter);
    const filterControl = <FormArray>this.rtiForm.controls['lhsFilter'];
    const filterControlLength = filterControl.length;
    /*
    * If property is selected from sub menu present with operator.
    * lhs would be false. Hence if section will be executed.
    */
    if (!lhs) {
      this._filter.getLink().push(new RelatedLinkImpl(selectedItem.getSymbolAlias(), selectedItem.getType()));
      filterControl.push(this._formBuilder.group({ lhsLink: [''] }));
    } else {
      const length = this._filter.getLink().length;
      this._filter.getLink()[i] = new RelatedLinkImpl(selectedItem.getSymbolAlias(), selectedItem.getType());
      this._filter.getLink().splice(i + 1, length - i - 1);
      for (let filterIndex = filterControlLength - 1; filterIndex > i; filterIndex--) {
        filterControl.removeAt(filterIndex);
      }
    }

    let dataType: string;
    const linkType = selectedItem.getType();
    if (linkType === 'NA'
      || linkType.search('concept') !== -1
      || linkType.search('event') !== -1
      || linkType.search('scorecard') !== -1
      || linkType.search('Concept') !== -1) {
      dataType = 'Concept/Event';
    } else if (linkType === 'int') {
      dataType = 'integer';
    } else {
      dataType = linkType;
    }
    if (this.isFilterOperator) {
      this.currentOperators = this.parentRTIEditor.filterOperators.get(dataType);
    } else {
      if ((dataType === 'long' || dataType === 'double' || dataType === 'float' || dataType === 'integer' || dataType === 'DateTime')
        && this.actionType
        && (this.actionType === 'call' || this.actionType === 'create')) {
        const allOperators = this.parentRTIEditor.commandOperators.get(dataType);
        this.currentOperators = new Array<OperatorTypes>();
        for (let i = 0; i < allOperators.length; i++) {
          if (allOperators[i].operator === 'set to' || allOperators[i].operator === 'set to field') {
            this.currentOperators.push(allOperators[i]);
          }
        }
      } else {
        this.currentOperators = this.parentRTIEditor.commandOperators.get(dataType);
      }

    }

    if (selectedItem.getType().search('concept') !== -1
      || selectedItem.getType().search('event') !== -1
      || selectedItem.getType().search('scorecard') !== -1) {
      this.showProperties = true;
      this.currentProperties = selectedItem.getSymbolInfo();
    } else {
      this.showProperties = false;
      this.currentProperties = null;
    }
    selectedItem.getType() === 'DateTime'
      ? this.showDateTimePicker = true
      : this.showDateTimePicker = false;
    if (selectedItem.getDomainInfo() != null && selectedItem.getDomainInfo().getValues().length !== 0) {
      this.domainValues = selectedItem.getDomainInfo().getValues();
      this.showDomainModel = true;
    } else {
      this.showDomainModel = false;
    }

    /*
    * When lhs operand is modified, the rhs operand should be reset to intial state.
    * The operator should be updated when the left hand side operand has been modified.
    */
    if (lhs) {
      this.selectedOperator = this.currentOperators[0];
      this.onOperatorSelection(this.selectedOperator, fromUI);
      if (this._filter.getValue() instanceof RelatedFilterValueImpl) {
        (<RelatedFilterValueImpl>this._filter.getValue())
          .getLinks().splice(1, (<RelatedFilterValueImpl>this._filter.getValue())
            .getLinks().length - 1);
      }
      const rhsFilterControl = <FormArray>this.rtiForm.controls['rhsFilter'];
      if (rhsFilterControl != null) {
        for (let indexRhsFilterControl = 1; indexRhsFilterControl < rhsFilterControl.length; indexRhsFilterControl++) {
          rhsFilterControl.removeAt(indexRhsFilterControl);
        }
      }
    } else if (!rhs) {
      this.selectedOperator = this.currentOperators[0];
      this.onOperatorSelection(this.selectedOperator, fromUI);
    }
    if (fromUI) {
      const subClause = this.isFilterOperator ? this.params.editBuffer.getBuffer().getConditions().getBuilderSubClause()
        : this.params.editBuffer.getBuffer().getCommands().getActions()[this.fetchIndexOfParentAction()].getFilter();
      const action = this.parentRTIEditor.editorService.modifySingleFilter(oldSingleFilter, this.filter, this, subClause);
      this.parentRTIEditor.execute(action);
    }
    if (event) {
      //  event.preventDefault();
    }
  }

  addFilterUI() {
    return this._formBuilder.group({
      lhsFilter: this._formBuilder.array([
        this._formBuilder.group({ lhsLink: [''] }),
      ]),
      operators: [''],
      inputValue: [''],
      rhsFilter: this._formBuilder.array([
        this._formBuilder.group({ rhsLink: [''] }),
      ]),
      datePicker: [''],
      timePicker: [''],
      dateTimeInputPicker: [''],
      domainModel: ['']
    });
  }

  public toggled(open: MouseEvent, i?: number): void {
    // console.log('Dropdown is now: ', open);
    this.isDropup = open.clientY > (window.innerHeight / 1.75);
    if (this.readOnly) {
      open.preventDefault();
    }
    if (i > 0) {
      const previousLink: RelatedLinkImpl = this._filter.getLink()[i - 1];
      let previousLinkType: string = previousLink.getType();
      if ((previousLinkType.indexOf('concept') > -1)
        || (previousLinkType.indexOf('event') > -1)
        || (previousLinkType.indexOf('scorecard') > -1)) {
        previousLinkType = 'Concept/Event';
      }
      this.currentLHSMenuOperators = this.isFilterOperator
        ? this.parentRTIEditor.filterOperators.get(previousLinkType)
        : this.parentRTIEditor.commandOperators.get(previousLinkType);
      this.currentLHSMenuProperties = this.isFilterOperator
        ? this.fetchSelectedSymbol(this._filter.getLink(), this.currentSymbols)[0].getSymbolInfo()
        : this.fetchSelectedSymbol(this._filter.getLink(), this.currentLHSSymbols)[0].getSymbolInfo();
    }
    open.preventDefault();
  }

  public toggledValue(open: MouseEvent, i: number): void {
    const filterValue: RelatedFilterValueImpl = <RelatedFilterValueImpl>this._filter.getValue();
    const symbols: Array<SymbolInfo> = this.fetchSelectedSymbol(filterValue.getLinks(), this.currentSymbols);
    if (i > 0) {
      this.currentRHSMenuProperties = symbols[i - 1].getSymbolInfo();
      const rhsSubMenuOptions = symbols[i].getSymbolInfo();
      if (rhsSubMenuOptions != null
        && rhsSubMenuOptions.length > 0) {
        this.currentRHSSubMenuProperties = rhsSubMenuOptions;
      } else {
        this.currentRHSSubMenuProperties = [];
      }
    } else {
      this.currentRHSMenuProperties = this.currentSymbols;
      this.currentRHSSubMenuProperties = symbols[0].getSymbolInfo();
    }
    open.preventDefault();
  }

  public onDeleteFilter(singleFilter: SingleFilterClient, event?: any): number {

    const clause = singleFilter.parentClause;
    let parentClause: BuilderSubClauseImpl;
    if (this.isFilterOperator) {
      const bufferMultiFilter = this.params.editBuffer.getBuffer().getConditions();
      parentClause = this.parentUiComponent.getParentMultiFilterFromEditBuffer(clause, bufferMultiFilter);
    } else {
      parentClause = this.params.editBuffer.getBuffer().getCommands().getActions()[this.fetchIndexOfParentAction()].getFilter();
    }
    const position: number = parentClause.removeFilter(singleFilter.filter);
    clause.removeFilter(singleFilter.filter);
    singleFilter.uiComponent.destroy();
    if (event) {
      const action = this.parentRTIEditor.editorService.deleteSingleFilter(this._uiComponent.instance, this._parentUiComponent, false, position);
      this.parentRTIEditor.execute(action);
    }
    this.parentRTIEditor.filterClientProblemMap.delete(singleFilter.filter.getFilterId());
    // console.log(singleFilter);
    return position;
  }

  fetchSelectedSymbol(links: RelatedLinkImpl[], symbolInfo: Array<SymbolInfo>): Array<SymbolInfo> {
    const resultantSelectedSymbols: Array<SymbolInfo> = new Array<SymbolInfo>();
    for (let i = 0; i < links.length; i++) {
      for (let j = 0; j < symbolInfo.length; j++) {
        if (links[i].getName() === symbolInfo[j].getSymbolAlias()) {
          resultantSelectedSymbols.push(symbolInfo[j]);
          if (symbolInfo[j].getSymbolInfo() != null
            && symbolInfo[j].getSymbolInfo().length > 0) {
            symbolInfo = symbolInfo[j].getSymbolInfo();
            j = 0;
          }
          break;
        }
      }
    }
    return resultantSelectedSymbols;
  }

  public updateSingleFilterInEditBuffer(filter: SingleFilterImpl, builderSubClauseImpl: BuilderSubClauseImpl) {
    const currentFilters = builderSubClauseImpl.getFilters();
    for (let i = 0; i < currentFilters.length; i++) {
      if (currentFilters[i] instanceof SingleFilterImpl
        && currentFilters[i].getFilterId() === filter.getFilterId()) {
        currentFilters[i] = filter;
        this.filter = filter;
        break;
      } else if (currentFilters[i] instanceof MultiFilterImpl) {
        this.updateSingleFilterInEditBuffer(filter, (<MultiFilterImpl>currentFilters[i]).getBuilderSubClause());
      }
    }
  }

  public resolveConflict(): void {
    if (this.params.editorMode === 'sync' && this.isConflict) {
      const modificationList = this.params.mergeResultRTI;
      if (modificationList.has(this._filter.getFilterId())) {
        const modificationEntry = modificationList.get(this._filter.getFilterId());
        if (!modificationEntry.isApplied()) {
          this.modal
            .open(RTIConfilctResolverModal, new RTIConflictResolverContext(this._filter,
              this.params.editBuffer, modificationEntry, 'latest', 'working', false))
            .then((resolution: string) => {
              modificationEntry.setApplied(true);
              switch (modificationEntry.getModificationType()) {
                case ModificationType.ADDED:
                  if (resolution === 'rhs') {
                    this.onDeleteFilter(this);
                  } else {
                    this.isAdded = true;
                    this.isServerChange = modificationEntry.isServerChange();
                  }
                  break;
                case ModificationType.DELETED:
                  if (resolution === 'rhs') {
                    this.onDeleteFilter(this);
                  } else {
                    if (modificationEntry.serverChangeType === ModificationType.MODIFIED) {
                      this.isModified = true;
                      this.isServerChange = true;
                    } else if (modificationEntry.localChangeType === ModificationType.MODIFIED) {
                      this.isModified = true;
                    }
                  }
                  this.isConflict = false;
                  break;
                case ModificationType.MODIFIED:
                  if (resolution === 'rhs') {
                    const localFilter = <SingleFilterImpl>modificationEntry.localVersionObj;
                    const subClause = this.isFilterOperator ? this.params.editBuffer.getBuffer().getConditions().getBuilderSubClause()
                      : this.params.editBuffer.getBuffer().getCommands().getActions()[this.fetchIndexOfParentAction()].getFilter();
                    // this._filter.setOperator(localFilter.getOperator());
                    // this.selectedOperator = this.isFilterOperator
                    //                         ? FilterOperator.getOperator(localFilter.getOperator())
                    //                         : CommandOperator.getOperator(localFilter.getOperator());
                    // this._filter.replaceFilterLinks(localFilter.getLink());
                    // this._filter.setValue(localFilter.getValue());
                    this.updateSingleFilterInEditBuffer(localFilter, subClause);
                  } else {
                    this.isServerChange = true;
                  }
                  this.isModified = true;
                  this.isConflict = false;
                  break;
              }
              // console.log('resolution: ' + resolution);
            }, () => { });
        }
      }
    }
  }

  getImageSrc(fieldType: string): string {
    if (fieldType.indexOf('concept') > -1) {
      fieldType = 'concept';
    }
    if (fieldType.indexOf('event') > -1) {
      fieldType = 'event';
    }
    if (fieldType.indexOf('scorecard') > -1) {
      fieldType = 'scorecard';
    }
    switch (fieldType) {
      case 'integer':
      case 'int': return 'assets/img/iconInteger16.gif';
      case 'String': return 'assets/img/iconString16.gif';
      case 'long': return 'assets/img/iconLong16.gif';
      case 'DateTime': return 'assets/img/iconDate16.gif';
      case 'boolean': return 'assets/img/iconBoolean16.gif';
      case 'double': return 'assets/img/iconReal16.gif';
      case 'concept': return 'assets/img/iconConcept16.gif';
      case 'event': return 'assets/img/event.gif';
      case 'scorecard': return 'assets/img/scorecard.gif';
    }
  }

  convertStringToDate(value: string): Date {
    return DateTimePickerComponent.convertStringToDate(value);
  }

  private fetchIndexOfParentAction(): number {
    let index = 0;
    const actions = this.params.editBuffer.getBuffer().getCommands().getActions();
    for (let i = 0; i < actions.length; i++) {
      if (actions[i].getFilter() && actions[i].getFilter().getFilters()) {
        const filters = actions[i].getFilter().getFilters();
        let j = 0;
        for (; j < filters.length; j++) {
          if (filters[j].getFilterId() === this.filter.getFilterId()) {
            index = i;
            break;
          }
        }
        if (j !== filters.length) {
          break;
        }
      }
    }
    return index;
  }
}
