
import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import {
  ColDef,
  GridOptions,
  MenuItemDef
} from 'ag-grid-community/main';
import { map } from 'rxjs/operators';

import { DataType } from './data-types';
import { AddDomainEntryContext, AddDomainEntryModal } from './domain-add-edit-entry.modal';
import { RangeDomainEntry, SingleDomainEntry } from './domain-entry';
import { DomainModelContent } from './domain-model';
import { DomainModelEditorService } from './domainmodel-editor.service';

import { ModalService } from '../../core/modal.service';
import { RestService } from '../../core/rest.service';
import { EditBuffer } from '../../editables/edit-buffer';
import { EditorParams } from '../editor-params';
import { EditorComponent } from '../editor.component';

// ag-grid support
export type CMenuItem = MenuItemDef | string;

@Component({
  selector: 'domainModel',
  templateUrl: 'domainmodel-editor.component.html',
  styleUrls: ['domainmodel-editor.component.css']
})
export class DomainModelComponent extends EditorComponent<DomainModelContent> implements OnInit {

  get domainName(): string {
    return this.data.name;
  }

  set domainName(v: string) {
    this.data.name = v;
  }

  get domainFolder(): string {
    return this.data.folder;
  }

  set domainFolder(v: string) {
    this.data.folder = v;
  }

  get domainDescription(): string {
    return this.data.description;
  }

  set domainDescription(v: string) {
    this.data.description = v;
  }

  get domainOwnerProjectName(): string {
    return this.data.ownerProjectName;
  }

  set domainOwnerProjectName(v: string) {
    this.data.ownerProjectName = v;
  }

  get superDomainPath(): string {
    return this.data.superDomainPath;
  }

  set superDomainPath(v: string) {
    this.data.superDomainPath = v;
  }

  // ag-grid support

  gridOptions: GridOptions;
  public columnDefs: Array<ColDef> = new Array<ColDef>();
  public rowData: Array<any> = new Array<any>();

  params: EditorParams<DomainModelContent>;
  editBuffer: EditBuffer<DomainModelContent>;
  data = DomainModelContent.getDomainContent();
  selectedDataType: DataType = new DataType(1, this.data.dataType);
  domainsPath: Array<string> = new Array<string>();
  newSuperDomainValue = '';

  datatypes = [
    new DataType(1, 'String'),
    new DataType(2, 'int'),
    new DataType(3, 'long'),
    new DataType(4, 'double'),
    new DataType(5, 'boolean'),
    new DataType(6, 'DateTime')
  ];

  constructor(
    private service: DomainModelEditorService,
    public modal: ModalService,
    private rest: RestService,
    public i18n: I18n) {
    super();

    // ag-grid support
    this.gridOptions = <GridOptions>{
      pagination: true,
      domLayout: 'autoHeight'
    };
    this.createRowData();
    this.createColumnDefs();
    this.updateInheritDomainList();
    this.newSuperDomainValue = this.data.superDomainPath ? this.superDomainPath : '';
    this.rest.get('domains/values.json?projectName=' + this.data.ownerProjectName + '&dataType=' + this.data.dataType).pipe(
      map(res => {
        if (res.ok()) {
          this.creatSuperDomainsArray(res.record);
        }
      }))
      .toPromise();
  }

  ngOnInit() {

  }

  creatSuperDomainsArray(record: any) {
    this.domainsPath = new Array<string>();
    for (const rec of record) {
      if (rec.artifactDetails.artifactContent.name !== this.domainName) {
        this.domainsPath.push(rec.artifactDetails.artifactPath);
      }
    }
  }

  updateGrid(dm: DomainModelContent) {
    this.data = dm;
    this.createColumnDefs();
    this.createRowData();
  }

  updateInheritDomainList() {
  }

  newValueHandler(params: any): boolean {
    // console.log(params);
    const entryInfo = params.newValue;
    if (entryInfo.isValid) {
      const action = this.service.entryEditAction(params.data, entryInfo.isRange, entryInfo.value, entryInfo.lValue, entryInfo.uValue, entryInfo.eDescription, entryInfo.lIncluded, entryInfo.uIncluded);
      this.execute(action);
      return true;
    } else {
      return false;
    }
  }

  getCellDoubleClickHandler() {
    return (params) => {
      // console.log(params);
      this.modal
        .open(AddDomainEntryModal, new AddDomainEntryContext(params.data, 'Edit domain entry', this.data.dataType))
        .then((entryInfo: any) => {
          // console.log(entryInfo);
          const action = this.service.entryEditAction(params.data, entryInfo.isRange, entryInfo.value, entryInfo.lValue, entryInfo.uValue, entryInfo.eDescription, entryInfo.lIncluded, entryInfo.uIncluded);
          this.execute(action);

        }, () => { });

    };
  }

  getValueGetter(field: string) {
    return (params: any) => {
      if (field === '@id') {
        return params.data.id;
      } else if (field === '@value') {
        if (params.data instanceof SingleDomainEntry) {
          return params.data.value;
        } else {
          if (params.data.lowerInclusive && params.data.upperInclusive) {
            return '[' + params.data.lower + ',' + params.data.upper + ']';
          } else if (params.data.lowerInclusive) {
            return '[' + params.data.lower + ',' + params.data.upper + ')';
          } else if (params.data.upperInclusive) {
            return '(' + params.data.lower + ',' + params.data.upper + ']';
          } else {
            return '(' + params.data.lower + ',' + params.data.upper + ')';
          }
        }
      } else if (field === '@desc') {
        return params.data.description;
      } else {
        return null;
      }
    };
  }

  onInput($event) {
    $event.preventDefault();
    this.data.dataType = $event.target.value;
  }

  focusOutFunction(fieldName: string) {
    let oldValue = '';
    let newValue = '';
    if (fieldName === 'name') {
      oldValue = this.params.editBuffer.getBase().name;
      newValue = this.data.name;
    } else if (fieldName === 'folder') {
      oldValue = this.params.editBuffer.getBase().folder;
      newValue = this.data.folder;
    } else if (fieldName === 'description') {
      oldValue = this.params.editBuffer.getBase().description;
      newValue = this.data.description;
    } else if (fieldName === 'ownerProjectName') {
      oldValue = this.params.editBuffer.getBase().ownerProjectName;
      newValue = this.data.ownerProjectName;
    } else if (fieldName === 'superDomain') {
      oldValue = this.params.editBuffer.getBase().superDomainPath;
      newValue = this.data.superDomainPath;
    }

    if (oldValue !== newValue) {
      const action = this.service.propertyEditAction(fieldName, oldValue, newValue);
      this.execute(action);
    }
  }

  onKey() {
    this.params.editBuffer.markForDirtyCheck();
  }

  public addRow(model: DomainModelContent) {
    let entryID = 1;
    if (model.entries.length > 0) {
      const entry = model.entries[model.entries.length - 1];
      entryID = entry.id + 1;
    }

    let addNew: SingleDomainEntry;
    if (this.data.dataType === 'boolean') {
      if (this.data.entries.length === 0) {
        addNew = new SingleDomainEntry(false, entryID, 'true', '');
      } else {
        const btype = this.data.entries[0].value === 'true' ? 'false' : 'true';
        addNew = new SingleDomainEntry(false, entryID, btype, '');
      }
    } else {
      addNew = new SingleDomainEntry(false, entryID, '', '');
    }

    const action = this.service.rowCreateAction(new SingleDomainEntry(false, entryID, '', ''));
    this.execute(action);

    //  this.modal
    //   .open(AddDomainEntryModal, new AddDomainEntryContext(addNew, 'Add domain entry', this.data.dataType))
    //   .then((entryInfo: any) => {
    //     console.log(entryInfo);
    //     if (entryInfo.isRange){
    //         let action = this.service.rowCreateAction(new RangeDomainEntry(false, entryID, entryInfo.lValue, entryInfo.uValue, entryInfo.eDescription, entryInfo.lIncluded, entryInfo.uIncluded));
    //         this.execute(action);
    //     }else{
    //         let action = this.service.rowCreateAction(new SingleDomainEntry(false, entryID, entryInfo.value, entryInfo.eDescription));
    //         this.execute(action);
    //     }
    //   }, () => { });
  }

  public removeRow(model: DomainModelContent) {
    const row = this.gridOptions.api.getSelectedRows();
    const action = this.service.rowDeleteAction(row);
    this.execute(action);
  }

  public duplicateRow(model: DomainModelContent) {
    const row = this.gridOptions.api.getSelectedRows();
    let entryID = 1;
    if (model.entries.length > 0) {
      const entry = model.entries[model.entries.length - 1];
      entryID = entry.id + 1;
    }
    if (row[0] instanceof SingleDomainEntry) {
      const addNew: SingleDomainEntry = new SingleDomainEntry(false, entryID, row[0].value, row[0].description);
      const action = this.service.rowCreateAction(addNew);
      this.execute(action);
    } else {
      const addNew: RangeDomainEntry = new RangeDomainEntry(false, entryID, row[0].lower, row[0].upper, row[0].description, row[0].lowerInclusive, row[0].upperInclusive);
      const action = this.service.rowCreateAction(addNew);
      this.execute(action);
    }

  }

  canAdd() {
    if (this.data.dataType === 'boolean' && this.data.entries.length === 2) {
      return false;
    } else {
      return true;
    }
  }

  public canRemove() {
    if (this.gridOptions.api) {
      const row = this.gridOptions.api.getSelectedRows();
      if (row && row.length > 0) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public canDuplicate() {
    if (this.gridOptions.api && this.data.dataType !== 'boolean') {
      const row = this.gridOptions.api.getSelectedRows();
      if (row && row.length > 0) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  public selectSuperDomain() {
    //  this.modal
    //   .open(SuperDomainModal, new SuperDomainContext(this.data.name, this.data.ownerProjectName, this.data.dataType, this.data.superDomainPath))
    //   .then((entryInfo: any) => {
    //     let action = this.service.propertyEditAction('superDomainPath', this.data.superDomainPath, entryInfo.superDomain);
    //     this.execute(action);

    //   }, () => { });

    const action = this.service.propertyEditAction('superDomainPath', this.data.superDomainPath, this.newSuperDomainValue);
    this.execute(action);
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

  // ag-grid support
  private createRowData() {
    this.rowData = new Array<SingleDomainEntry>();
    for (const record of this.data.entries) {
      this.rowData.push(record);
    }
  }

  private createColumnDefs() {
    const colDef0: ColDef = {
      width: 50,
      headerName: this.i18n('ID'),
      pinned: 'left',
      valueGetter: this.getValueGetter('@id'),
      // onCellDoubleClicked: this.getCellDoubleClickHandler()
    };
    const colDef: ColDef = {
      width: 300,
      headerName: this.i18n('Value'),
      valueGetter: this.getValueGetter('@value'),
      editable: true,
      // onCellDoubleClicked: this.getCellDoubleClickHandler(),
      cellEditorFramework: AddDomainEntryModal,
      newValueHandler: (params) => {
        return this.newValueHandler(params);
      },
      cellEditorParams: (params) => {
        return { filter: params.data, addEditTitle: 'Edit', dataType: this.data.dataType };
      }
    };
    const colDef1: ColDef = {
      width: 600,
      headerName: this.i18n('Description'),
      valueGetter: this.getValueGetter('@desc'),
      editable: true,
      // onCellDoubleClicked: this.getCellDoubleClickHandler(),
      cellEditorFramework: AddDomainEntryModal,
      newValueHandler: (params) => {
        return this.newValueHandler(params);
      },
      cellEditorParams: (params) => {
        return { filter: params.data, addEditTitle: 'Edit', dataType: this.data.dataType };
      }
    };
    this.columnDefs.push(colDef0);
    this.columnDefs.push(colDef);
    this.columnDefs.push(colDef1);

  }
}
