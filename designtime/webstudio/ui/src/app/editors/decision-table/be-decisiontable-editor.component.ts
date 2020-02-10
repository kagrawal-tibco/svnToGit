
import { Component, Injector } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import {
  ColDef,
  ColGroupDef,
  GetContextMenuItemsParams,
  GetMainMenuItemsParams,
  GridOptions,
  MenuItemDef
} from 'ag-grid-community/main';
import { map, takeUntil } from 'rxjs/operators';

import { AddBEColumnContext, AddBEColumnModal } from './be-add-column.modal';
import { BEColumnSettingContext, BEColumnSettingModal } from './be-column-fields-setting.modal';
import { DecisionTableEditorComponent } from './decisiontable-editor.component';
import { DecisionTableEditorService } from './decisiontable-editor.service';
import { DateTimeModal, DateTimeModalContext } from './decorators/decisiontable-datetime-modal';
import { DecisionTableDiffDecorator } from './decorators/decisiontable-diff-decorator';
import { DomainCellEditorComponent } from './decorators/decisiontable-domain-cell-editor';
import { DecisionTableMergeDecorator } from './decorators/decisiontable-merge-decorator';
import { DecisionTableRegularDecorator } from './decorators/decisiontable-regular-decorator';

import { ArtifactPropertiesService } from '../../artifact-editor/artifact-properties.service';
import { AlertService } from '../../core/alert.service';
import { ArtifactService } from '../../core/artifact.service';
import { Logger } from '../../core/logger.service';
import { ModalService } from '../../core/modal.service';
import { RestService } from '../../core/rest.service';
import { SettingsService } from '../../core/settings.service';
import { BEDecisionTable } from '../../editables/decision-table/be-decision-table';
import { Column, ColumnType, PropertyType } from '../../editables/decision-table/column';
import { Differ } from '../../editables/decision-table/differ/differ';
import { Rule } from '../../editables/decision-table/rule';
import { EditBuffer } from '../../editables/edit-buffer';
import { BEDecisionTableUtil } from '../../models-be/decision-table-be/decision-table-util';
import { Property } from '../../models-be/decision-table-be/decisiontable-arguments-data';
import { BESettings } from '../../models-be/settings-be';
import { EditorParams } from '../editor-params';
import { ReviewPropertiesComponent } from '../review-properties.component';
import { ReviewPropertiesService } from '../review-properties.service';
export type CMenuItem = MenuItemDef | string;

@Component({
  selector: 'be-decisiontable-editor',
  templateUrl: './be-decisiontable-editor.component.html',
  styleUrls: ['./be-decisiontable-editor.component.css'],
})
export class BEDecisionTableEditorComponent extends DecisionTableEditorComponent {

  get showLegend(): boolean {
    if (this.params.editorMode === 'display') {
      return true;
    } else {
      return false;
    }
  }

  get showProperties(): boolean {
    if (this.params.editorMode === 'display') {
      return true;
    } else {
      return false;
    }
  }

  get initializationObject() {
    if (this.editBuffer.getBuffer()) {
      return this.editBuffer.getBuffer();
    } else if (this.editBuffer.getBase()) {
      return this.editBuffer.getBase();
    }
  }
  static implpath: string;

  params: EditorParams<BEDecisionTable>;
  isReady: boolean;
  etColumnDefs: (ColDef | ColGroupDef)[];
  editBuffer: EditBuffer<BEDecisionTable>;
  decorator: DecisionTableRegularDecorator;
  base: BEDecisionTable;

  gridOptionsNew: GridOptions = {
    getMainMenuItems: this.getETMainMenuItemsHandler(),
    getContextMenuItems: this.getETContextMenuItemsHandler(),
    enableRangeSelection: true,
    suppressCopyRowsToClipboard: true,
    localeText: {
      loadingOoo: this.i18n('Loading...'),
      selectAll: 'Select All',
      filterOoo: this.i18n('Filter...'),
      group: this.i18n('Group'),
      columns: this.i18n('Columns'),
      filters: this.i18n('Filters'),
      rowGroupColumnsEmptyMessage: this.i18n('Drag here to set new row groups'),
      pivotMode: this.i18n('Pivot Mode'),
      groups: this.i18n('Groups'),
      values: this.i18n('Values'),
      pivots: this.i18n('Pivots'),
      valueColumnsEmptyMessage: this.i18n('Drag columns to aggregate'),
      pivotColumnsEmptyMessage: this.i18n('Drag here'),
    }
  };

  constructor(public differ: Differ,
    public service: DecisionTableEditorService,
    public modal: ModalService,
    public log: Logger,
    public injector: Injector,
    public propertiesService: ArtifactPropertiesService,
    public reviewService: ReviewPropertiesService,
    public settings: SettingsService,
    public artifactService: ArtifactService,
    public artifact: RestService,
    public alert: AlertService,
    public i18n: I18n) {
    super(differ, service, modal, log, injector, propertiesService, reviewService, settings, artifactService, artifact, alert, i18n);
  }

  getETMainMenuItemsHandler() {
    return (params: GetMainMenuItemsParams): CMenuItem[] => {
      const columnMenu: CMenuItem[] = params.defaultItems.slice();
      const id = params.column.getColDef().colId;
      if (this.editable && this.editBuffer.getContent().hasETColumn(id)) {
        columnMenu.push({
          icon: `<i class='fa fa-trash'></i>`,
          name: this.i18n('Remove Column'),
          action: () => {
            this.onDeleteETColumn(params.column.getColDef());
          }
        });
        columnMenu.push({
          name: this.i18n('Field Settings'),
          action: () => {
            this.etColumnFieldsSetting(params.column.getColDef());
          }
        });
      }
      return columnMenu;
    };
  }

  getETContextMenuItemsHandler() {
    return (params: GetContextMenuItemsParams): CMenuItem[] => {
      let contextMenu: CMenuItem[];
      const row = params.node;
      // row can be null if right click on empty area
      if (row) {
        const rule: Rule = row.data;
        contextMenu = [
          {
            icon: `<i class='fa fa-clipboard'></i>`,
            name: `Copy (Ctrl + C)`,
            action: () => this.gridOptionsNew.api.copySelectedRangeToClipboard(false)
          },
          {
            icon: `<i class='fa fa-clipboard'></i>`,
            name: `Copy Row ${rule.getId()}`,
            action: () => this.gridOptionsNew.api.copySelectedRowsToClipboard(false)
          },
          {
            icon: `<i class='fa fa-clipboard'></i>`,
            name: `Copy Row ${rule.getId()} with header`,
            action: () => this.gridOptionsNew.api.copySelectedRowsToClipboard(true)
          },
          {
            icon: `<i class='fa fa-clipboard'></i>`,
            name: `Paste (Ctrl + V)`,
            action: () => this.modal.alert().message('Use Ctrl + V to do the pasting.').open()
          },
        ];
        if (rule && this.editBuffer.getContent().hasETRule(rule.getId())) {

          if (this.isEnabledRowET(rule)) {
            contextMenu.push({
              icon: `<i class='fa fa-trash'></i>`,
              name: this.i18n('Disable Row ') + rule.getId(),
              action: () => this.onDisableRuleET(rule),
            });
          } else {
            contextMenu.push({
              icon: `<i class='fa fa-trash'></i>`,
              name: this.i18n('Enable Row ') + rule.getId(),
              action: () => this.onEnableRuleET(rule),
            });
          }

          contextMenu.push({
            icon: `<i class='fa fa-trash'></i>`,
            name: this.i18n('Remove Row ') + rule.getId(),
            action: () => this.onDeleteETRule(rule),
          });

          contextMenu.push({
            icon: `<i class='fa fa-clipboard'></i>`,
            name: this.i18n('Duplicate Row ') + rule.getId(),
            action: () => this.onDuplicateETRule(rule),
          });
        }
        contextMenu.push('separator', 'toolPanel');
      } else {
        contextMenu = ['toolPanel'];
      }
      return contextMenu;
    };
  }

  ngOnInit() {
    this.update();
    // this.i18n = DecisionTableEditorComponent.i18n;
    this.updateExceptionTable();
    if (this.editBuffer.getBuffer()) {
      BEDecisionTableEditorComponent.implpath = this.editBuffer.getBuffer().implementsPath;
    } else if (this.editBuffer.getBase()) {
      BEDecisionTableEditorComponent.implpath = this.editBuffer.getBase().implementsPath;
    } else {
      BEDecisionTableEditorComponent.implpath = '';
    }
  }

  updateExceptionTable() {
    switch (this.params.editorMode) {
      case 'display':
      case 'edit':
        if (this.params.showChangeSet === 'rhs') {
          const diffResult = this.differ.liveDiff(this.base, this.editBuffer.getContent());
          this.decorator = new DecisionTableDiffDecorator(diffResult, this.editBuffer, false);
        } else {
          this.decorator = new DecisionTableRegularDecorator(this.editBuffer, true);
        }
        break;
      case 'sync':

        this.decorator = new DecisionTableMergeDecorator(this.params.mergeResult,
          this.editBuffer,
          this.params.showChangeSet,
          this.injector, this.i18n);

        this.editBuffer.onRefresh().pipe(takeUntil(this.whenDestroyed)).subscribe(() => {
          //            this.reset();
          this.updateAgGridET();
        });
        break;
    }
    // this.base = this.params.editBuffer.getBase();
    // this.decorator = new DecisionTableRegularDecorator(this.editBuffer, true);
    this.updateAgGridET();
    this.isReady = true;
  }

  updateAgGridET() {

    this.editBuffer.markForDirtyCheck();
    this.decorator.refresh();

    this.etColumnDefs = [
      <ColDef>{
        width: 36,
        headerName: this.i18n('ID'),
        pinned: this.getIDValue(),
        valueGetter: this.getValueGetter(),
        cellClassRules: this.decorator.getETCellClassRules(undefined),
        cellRenderer: this.decorator.getETCellRenderer(null),
        onCellDoubleClicked: this.decorator.getETCellDoubleClickHandler(),
        suppressMenu: true,
      },
      {
        headerName: this.i18n('CONDITION'),
        children: []
      },
      {
        headerName: this.i18n('ACTION'),
        children: []
      }
    ];
    this.gridOptions.localeText = this.gridOptionsNew.localeText;
    if (this.gridOptionsNew.api) {
      this.gridOptionsNew.api.setSideBarVisible(false);
    }

    this.gridOptionsNew.rowData = this.decorator.getETRows().sort((a, b) => {
      const idA = parseInt(a.getId(), 10);
      const idB = parseInt(b.getId(), 10);
      return idA - idB;
    });

    this.decorator.getETColumns()
      .sort((a, b) => {
        const idA = parseInt(a.getId(), 10);
        const idB = parseInt(b.getId(), 10);
        return idA - idB;
      })
      .concat([this.params.editBuffer.getContent().getPriorityColumn()])
      .forEach(col => {
        let headerName: string;
        if (col.columnType === ColumnType.PROPERTY) {
          headerName = col.name;
        } else {
          headerName = col.name + ` (${col.propertyType.name})` + (this.isStreamBaseExpressionColumn(col) ? ' - Expr' : '');
        }

        if ((<BESettings>this.settings.latestSettings).showColumnAliasIfPresent) {
          headerName = this.getColumnAliasName(col);
        }

        const editor = this.decorator.getCellEditor(col);

        let colDef: ColDef;
        if ((String(col.associatedDM) === 'true' || String(col.associatedDM) === String(true)) && this.params.editorMode !== 'display') {
          colDef = {
            headerName: headerName,
            valueGetter: this.getETValueGetter(col),
            newValueHandler: this.getNewETValueHandler(col),
            cellEditorFramework: DomainCellEditorComponent,
            cellRenderer: this.decorator.getETCellRenderer(col),
            editable: true,
            width: 250,
            cellEditorParams: this.getValuesNeeded(col)
          };
        } else if (col.propertyType.name === 'timestamp') {
          colDef = {
            colId: col.getId(),
            editable: this.decorator.getCellEditable(col),
            headerName: headerName,
            headerClass: this.decorator.getColHeaderClass(col.getId()),
            valueGetter: this.getETValueGetter(col),
            newValueHandler: this.getNewETValueHandler(col),
            cellClassRules: this.decorator.getETCellClassRules(col),
            cellRenderer: this.decorator.getETCellRenderer(col),
            cellEditor: editor.editor,
            cellEditorParams: editor.params,
            onCellDoubleClicked: this.editable ? this.getETCellDoubleClickHandler(col) : this.decorator.getETCellDoubleClickHandler(),
          };
        } else {
          colDef = {
            colId: col.getId(),
            editable: this.decorator.getCellEditable(col),
            headerName: headerName,
            headerClass: this.decorator.getColHeaderClass(col.getId()),
            valueGetter: this.getETValueGetter(col),
            newValueHandler: this.getNewETValueHandler(col),
            cellClassRules: this.decorator.getETCellClassRules(col),
            cellRenderer: this.decorator.getETCellRenderer(col),
            cellEditor: editor.editor,
            cellEditorParams: editor.params,
            onCellDoubleClicked: this.decorator.getETCellDoubleClickHandler(),
          };
        }

        // dispatch to CONDITION or ACTION
        switch (col.columnType) {
          case ColumnType.CONDITION:
          case ColumnType.EXPR_CONDITION:
            (<ColGroupDef>this.etColumnDefs[1]).children.push(colDef);
            break;
          case ColumnType.ACTION:
          case ColumnType.EXPR_ACTION:
            (<ColGroupDef>this.etColumnDefs[2]).children.push(colDef);
            break;
          case ColumnType.PROPERTY:
            colDef.pinned = this.getPropertyValue();
            colDef.width = 100;
            colDef.suppressMenu = true;
            this.etColumnDefs.push(colDef);
            break;
        }
      });
  }

  getETCellDoubleClickHandler(col: any) {
    return (params: any) => {
      this.modal
        .open(DateTimeModal, new DateTimeModalContext(params.value))
        .then(ref => ref.result)
        .then((colInfo: any) => {
          const rule = <Rule>params.data;
          if (rule) {
            if (col) {
              const action = this.service.cellEditActionET(rule.getId(), col.getId(), params.value, colInfo.cellDateValue);
              this.execute(action);
            }
          }
        },
          () => { });
      return null;
    };
  }

  getETValueGetter(col?: Column) {
    return this.decorator.getCellValueGetter(col);
  }

  getNewETValueHandler(col: Column) {
    return (params: any) => {
      if (params.oldValue !== params.newValue) {
        const rule: Rule = params.data;
        if (col.isSubstitution) {
          const substituted = BEDecisionTableUtil.getFormattedString(col.name, params.newValue);
          const action = this.service.cellEditActionET(rule.getId(), col.getId(), params.oldValue, substituted);
          this.execute(action);
        } else {
          const action = this.service.cellEditActionET(rule.getId(), col.getId(), params.oldValue, params.newValue);
          this.execute(action);
        }
      }
      return params.newValue;
    };
  }

  getValuesNeeded(col: Column) {
    let artifactType;
    for (const key in this.editBuffer.getBuffer().argumentObj) {
      const arg = this.editBuffer.getBuffer().argumentObj[key];
      if (arg.argumentAlias === col.name.substr(0, col.name.indexOf('.'))) {
        artifactType = arg.resourceType;
      }
    }

    return {
      editor: 'agRichSelectCellEditor',
      params: {
        pname: this.editBuffer.getBuffer().projectName,
        artifactpath: col.property.substr(0, col.property.lastIndexOf('/')),
        propertyname: col.property.substr(col.property.lastIndexOf('/') + 1),
        artifacttype: artifactType,
        isCondition: col.columnType === ColumnType.CONDITION ? true : false,
        coltype: col.propertyType.name
      }
    };
  }

  onAddRule() {
    let maxId: number;
    // let rhs = this.editBuffer.getBuffer().maxRuleId();
    const rhs = this.getMaxId(this.editBuffer.getBuffer());
    if (this.base) {
      // let lhs = this.base.maxRuleId();
      const lhs = this.getMaxId(this.base);
      maxId = lhs > rhs ? lhs : rhs;
    } else {
      maxId = rhs;
    }
    const id = (maxId + 1).toString();
    if (this.allowedRowAddition(this.editBuffer.getBuffer())) {
      const action = this.service.rowCreateActionBE(id);
      this.execute(action);
    } else {
      this.modal.alert()
        .message(this.i18n('Failed to add row: there should be atleast one condition.'))
        .open();
    }

    if (this.editBuffer.getBuffer().getRules().length > (<BESettings>this.settings.latestSettings).decisionTablePageSize) {
      this.artifactService.markDTStale(this.editBuffer.getBuffer().artifactPath);
      // this.artifactService.clear();
    }
  }

  onAddETRule() {
    let maxId: number;
    // let rhs = this.editBuffer.getBuffer().maxETRuleId();
    const rhs = this.getMaxId(this.editBuffer.getBuffer());
    if (this.base) {
      // let lhs = this.base.maxETRuleId();
      const lhs = this.getMaxId(this.base);
      maxId = lhs > rhs ? lhs : rhs;
    } else {
      maxId = rhs;
    }
    const id = (maxId + 1).toString();
    if (this.allowedRowAdditionET(this.editBuffer.getBuffer())) {
      const action = this.service.rowCreateActionET(id);
      this.execute(action);
    } else {
      this.modal.alert()
        .message(this.i18n('Failed to add row: there should be atleast one condition.'))
        .open();
    }
  }

  allowedRowAddition(dt: BEDecisionTable) {
    if (dt.getColumns().length > 0) {
      return true;
    } else {
      return false;
    }
  }

  allowedRowAdditionET(dt: BEDecisionTable) {
    if (dt.getETColumns().length > 0) {
      return true;
    } else {
      return false;
    }
  }

  getMaxId(dt: BEDecisionTable) {
    const dtValue: number = (dt.decisionTableLastRuleId && Number(dt.decisionTableLastRuleId) > dt.maxRuleId()) ? Number(dt.decisionTableLastRuleId) : dt.maxRuleId();
    const etValue: number = dt.maxETRuleId();
    return dtValue > etValue ? dtValue : etValue;
  }

  onDeleteETColumn(col: ColDef) {
    const id = col.colId;
    this.modal.confirm()
      .message(this.i18n('Please confirm you want to delete the column : ') + col.headerName)
      .open().result
      .then(ok => {
        if (ok) {
          try {
            if (this.onlyConditionRemoveET(id)) {
              this.modal.alert()
                .message(this.i18n('Failed to remove column: there should be atleast one condition.'))
                .open();
            } else {
              const action = this.service.columnDeleteActionET(id);
              this.execute(action);
            }
          } catch (e) {
            this.modal.alert()
              .okBtn('Close')
              .message(this.i18n('Failed to clear column.'))
              .open();
          }
        }
      }, err => { if (err) { throw err; } });
  }

  onlyConditionRemoveET(id: string) {
    const dt = this.editBuffer.getBuffer();
    let count = 0;
    for (const columnObject of dt.getETColumns()) {
      if (columnObject.columnType === ColumnType.CONDITION) {
        count = count + 1;
      }
    }
    const deletedColumn = dt.getETColumn(id);
    if (deletedColumn.columnType === ColumnType.CONDITION && count < 2 && dt.getETColumns().length !== 1) {
      return true;
    } else {
      return false;
    }
  }

  onlyConditionRemove(id: string) {
    const dt = this.editBuffer.getBuffer();
    let count = 0;
    for (const columnObject of dt.getColumns()) {
      if (columnObject.columnType === ColumnType.CONDITION) {
        count = count + 1;
      }
    }
    const deletedColumn = dt.getColumn(id);
    if (deletedColumn.columnType === ColumnType.CONDITION && count < 2 && dt.getColumns().length !== 1) {
      return true;
    } else {
      return false;
    }
  }

  onDeleteETRule(rule: Rule) {
    this.modal.confirm()
      .message(this.i18n('Please confirm you want to delete the rule with id : ') + rule.getId())
      .open().result
      .then(ok => {
        if (ok) {
          try {
            const action = this.service.rowDeleteActionET(rule.getId());
            this.execute(action);
          } catch (e) {
            this.modal.alert()
              .message(this.i18n('Fail to remove rule ') + rule.getId() + ': ' + e)
              .open();
          }
        }
      }, err => { if (err) { throw err; } });
  }

  onCellClicked(event: any, isET: boolean) {
    // console.log('cell', event);
    if (this.params.editorMode === 'display') {
      this.reviewService.setTableProperties(event, this.editBuffer, this, isET);
    } else {
      this.propertiesService.setTableProperties(event, this.editBuffer, this, isET);
    }

  }

  onDuplicateETRule(rule: Rule) {
    try {
      const action = this.service.rowDuplicateActionET(rule);
      this.execute(action);
    } catch (e) {
      this.modal.alert()
        .message(this.i18n('Fail to duplicate rule ') + rule.getId() + ': ' + e)
        .open();
    }
  }

  isEnabledRowET(rule: Rule): boolean {
    for (const cellObject of rule.getCells()) {
      if (!cellObject.isDisabled() && cellObject.getColId() !== 'PRIORITY') {
        return true;
      }
    }
    return false;
  }

  onDisableRuleET(rule: Rule) {
    for (const cellObject of rule.getCells()) {
      if (cellObject.getColId() !== 'PRIORITY') {
        cellObject.setDisabled(true);
      }
    }
    this.updateAgGridET();
  }

  onEnableRuleET(rule: Rule) {
    for (const cellObject of rule.getCells()) {
      if (cellObject.getColId() !== 'PRIORITY') {
        cellObject.setDisabled(false);
      }
    }
    this.updateAgGridET();
  }

  onAddBEColumn() {
    this.modal
      .open(AddBEColumnModal, new AddBEColumnContext(this.editBuffer.getContent(), this.editBuffer.getContent().projectName, this.editBuffer.getContent().artifactPath))
      .then((colInfo: any) => {
        this.editBuffer.markForDirtyCheck();
        if (colInfo.isVRF) {
          if (colInfo.colType === ColumnType.ACTION && this.editBuffer.getContent().getColumns().length === 0) {
            this.modal.alert()
              .message(this.i18n('Fail to add column: there should be atleast one condition.'))
              .open();
          } else {
            for (const col of colInfo.colArray) {
              if (this.isUniqueColumn(col, colInfo.colType)) {
                if (colInfo.colType === ColumnType.ACTION && col.resourceType && col.resourceType === 'PRIMITIVE') {
                  this.modal.alert()
                    .message(this.i18n('Fail to add column: The primitive field {{displayValue}} is immutable. The Action column can not be created with this name.', { displayValue: col.displayValue }))
                    .open();
                } else {
                  let maxId: number;
                  const rhs = this.editBuffer.getBuffer().maxColumnId();
                  if (this.base) {
                    const lhs = this.base.maxColumnId();
                    maxId = lhs > rhs ? lhs : rhs;
                  } else {
                    maxId = rhs;
                  }
                  const id = (maxId + 1).toString();
                  if (col instanceof Property) {
                    const columnDisplay = col.displayValue;
                    //               if(col.isArray == true){
                    //                  columnDisplay = columnDisplay + "[]";
                    //               }
                    const action = this.service.columnCreateActionBE(id, columnDisplay, this.getPropertyType(col.type), colInfo.colType, col.associatedDomain, col.ownerPath, col.name);
                    try {
                      this.execute(action);
                    } catch (e) {
                      this.log.debug(e);
                      this.modal.alert()
                        .message(this.i18n('Fail to add column: please make sure all fields are entered correctly.'))
                        .open();
                    }
                  } else {
                    const columnDisplay = col.displayValue;
                    //               if(col.isArray == true){
                    //                  columnDisplay = columnDisplay + "[]";
                    //               }
                    const action = this.service.columnCreateActionBE(id, columnDisplay, this.getPropertyType(col.path), colInfo.colType, col.associatedDomain, col.ownerPath, col.value);
                    try {
                      this.execute(action);
                    } catch (e) {
                      this.log.debug(e);
                      this.modal.alert()
                        .message(this.i18n('Fail to add column: please make sure all fields are entered correctly.'))
                        .open();
                    }
                  }

                }
              } else {
                this.modal.alert()
                  .message(this.i18n('Fail to add column: {{displayValue}}. This column already present', { displayValue: col.displayValue }))
                  .open();
              }
            }
          }
        } else {
          if (colInfo.columnType === ColumnType.EXPR_ACTION && this.editBuffer.getContent().getColumns().length === 0) {
            this.modal.alert()
              .message(this.i18n('Fail to add column: there should be atleast one condition.'))
              .open();
          } else {
            let maxId: number;
            const rhs = this.editBuffer.getBuffer().maxColumnId();
            if (this.base) {
              const lhs = this.base.maxColumnId();
              maxId = lhs > rhs ? lhs : rhs;
            } else {
              maxId = rhs;
            }
            const id = (maxId + 1).toString();
            const action = this.service.columnCreateActionBE(id, colInfo.columnName, colInfo.propertyType, colInfo.columnType, false, '', colInfo.columnName);
            try {
              this.execute(action);
            } catch (e) {
              this.log.debug(e);
              this.modal.alert()
                .message(this.i18n('Fail to add column: please make sure all fields are entered correctly.'))
                .open();
            }
          }
        }
      },
        () => { });
  }

  isUniqueColumn(col: any, colType: ColumnType): boolean {
    const dt = this.editBuffer.getBuffer();
    for (const column of dt.getColumns()) {
      if (column.name === col.displayValue && column.columnType === colType) {
        return false;
      }
    }
    return true;
  }

  isUniqueColumnET(col: any, colType: ColumnType): boolean {
    const dt = this.editBuffer.getBuffer();
    for (const column of dt.getETColumns()) {
      if (column.name === col.displayValue && column.columnType === colType) {
        return false;
      }
    }
    return true;
  }

  onAddBEETColumn() {
    this.modal
      .open(AddBEColumnModal, new AddBEColumnContext(this.editBuffer.getContent(), this.editBuffer.getContent().projectName, this.editBuffer.getContent().artifactPath))
      .then((colInfo: any) => {
        this.editBuffer.markForDirtyCheck();
        if (colInfo.isVRF) {
          if (colInfo.colType === ColumnType.ACTION && this.editBuffer.getContent().getETColumns().length === 0) {
            this.modal.alert()
              .message(this.i18n('Fail to add column: there should be atleast one condition.'))
              .open();
          } else {
            for (const col of colInfo.colArray) {
              if (this.isUniqueColumnET(col, colInfo.colType)) {
                if (colInfo.colType === ColumnType.ACTION && col.resourceType && col.resourceType === 'PRIMITIVE') {
                  this.modal.alert()
                    .message(this.i18n('Fail to add column: The primitive field {{displayValue}} is immutable. The Action column can not be created with this name.', { displayValue: col.displayValue }))
                    .open();
                } else {
                  let maxId: number;
                  const rhs = this.editBuffer.getBuffer().maxETColumnId();
                  if (this.base) {
                    const lhs = this.base.maxETColumnId();
                    maxId = lhs > rhs ? lhs : rhs;
                  } else {
                    maxId = rhs;
                  }
                  const id = (maxId + 1).toString();

                  if (col instanceof Property) {
                    const columnDisplay = col.displayValue;
                    //               if(col.isArray == true){
                    //                  columnDisplay = columnDisplay + "[]";
                    //               }
                    const action = this.service.columnCreateActionET(id, columnDisplay, this.getPropertyType(col.type), colInfo.colType, col.associatedDomain, col.ownerPath, col.name);
                    try {
                      this.execute(action);
                    } catch (e) {
                      this.log.debug(e);
                      this.modal.alert()
                        .message(this.i18n('Fail to add column: please make sure all fields are entered correctly.'))
                        .open();
                    }
                  } else {
                    const columnDisplay = col.displayValue;
                    //               if(col.isArray == true){
                    //                  columnDisplay = columnDisplay + "[]";
                    //               }
                    const action = this.service.columnCreateActionET(id, columnDisplay, this.getPropertyType(col.path), colInfo.colType, col.associatedDomain, col.ownerPath, col.value);
                    try {
                      this.execute(action);
                    } catch (e) {
                      this.log.debug(e);
                      this.modal.alert()
                        .message(this.i18n('Fail to add column: please make sure all fields are entered correctly.'))
                        .open();
                    }
                  }
                }
              } else {
                this.modal.alert()
                  .message(this.i18n('Fail to add column: {{displayValue}}. This column already present', { displayValue: col.displayValue }))
                  .open();
              }
            }
          }
        } else {
          if (colInfo.columnType === ColumnType.EXPR_ACTION && this.editBuffer.getContent().getETColumns().length === 0) {
            this.modal.alert()
              .message(this.i18n('Fail to add column: there should be atleast one condition.'))
              .open();
          } else {
            let maxId: number;
            const rhs = this.editBuffer.getBuffer().maxETColumnId();
            if (this.base) {
              const lhs = this.base.maxETColumnId();
              maxId = lhs > rhs ? lhs : rhs;
            } else {
              maxId = rhs;
            }
            const id = (maxId + 1).toString();
            const action = this.service.columnCreateActionET(id, colInfo.columnName, colInfo.propertyType, colInfo.columnType, false, '', colInfo.columnName);
            try {
              this.execute(action);
            } catch (e) {
              this.log.debug(e);
              this.modal.alert()
                .message(this.i18n('Fail to add column: please make sure all fields are entered correctly.'))
                .open();
            }
          }
        }
      },
        () => { });
  }

  getPropertyType(coltypeString: string): PropertyType {
    if (coltypeString === 'String') {
      return PropertyType.STRING;
    } else if (coltypeString === 'int') {
      return PropertyType.INT;
    } else if (coltypeString === 'long') {
      return PropertyType.LONG;
    } else if (coltypeString === 'double') {
      return PropertyType.DOUBLE;
    } else if (coltypeString === 'timestamp' || coltypeString === 'DateTime') {
      return PropertyType.TIMESTAMP;
    } else if (coltypeString === 'boolean') {
      return PropertyType.BOOLEAN;
    }

  }

  etColumnFieldsSetting(col: ColDef) {
    const id = col.colId;
    const columnToSet = this.editBuffer.getBuffer().getETColumn(id);

    this.modal
      .open(BEColumnSettingModal, new BEColumnSettingContext(columnToSet, this.editBuffer.getBuffer()))
      .then((colInfo: any) => {
        this.editBuffer.markForDirtyCheck();
        if (columnToSet.name !== colInfo.columnName) {
          columnToSet.name = colInfo.columnName;
        }
        if (columnToSet.alias !== colInfo.columnAlias) {
          columnToSet.alias = colInfo.columnAlias;
        }
        if (columnToSet.defaultCellText !== colInfo.columnDText) {
          columnToSet.defaultCellText = colInfo.columnDText;
        }
        if (colInfo.includeExistingRules) {
          this.updateETRulesWithDefaultValue(columnToSet);
        }

        this.updateExceptionTable();
      }, () => { });
  }

  updateETRulesWithDefaultValue(col: Column) {
    const dt = this.editBuffer.getBuffer();
    for (const rule of dt.getETRules()) {
      if (!rule.getCell(col.getId())) {
        if (col.columnType === ColumnType.CONDITION) {
          rule.setCell(col.getId(), col.defaultCellText, 'cond', false, '');

        } else if (col.columnType === ColumnType.ACTION) {
          rule.setCell(col.getId(), col.defaultCellText, 'act', false, '');
        }

      }
    }
  }

  getColumnAliasName(col: any) {
    return col.alias ? col.alias : col.name + ` (${col.propertyType.name})`;
  }

  focusOnLocations(locations: any[]) {
    if (locations && this.gridOptions.api && this.gridOptionsNew.api) {
      this.gridOptions.api.deselectAll();
      this.gridOptions.api.clearFocusedCell();
      this.gridOptions.api.clearRangeSelection();
      this.gridOptionsNew.api.deselectAll();
      this.gridOptionsNew.api.clearFocusedCell();
      this.gridOptionsNew.api.clearRangeSelection();
      locations.forEach(location => {
        const isDT: boolean = this.editBuffer.getContent().hasRule(location.rowId);
        const rowId = parseInt(location.rowId, 10);
        const colId = location.colId;
        if (rowId !== NaN && rowId !== -1) {
          const idx = rowId - 1;
          if (isDT) {
            this.gridOptions.api.ensureIndexVisible(idx);
            const column = this.editBuffer.getContent().getColumn(colId);
            if (column) {
              this.gridOptions.api.ensureColumnVisible(column.getId());
              let idTest = 0;
              while (idTest !== -1) {
                if (this.gridOptions.api.getModel().getRow(idTest) && this.gridOptions.api.getModel().getRow(idTest).data.getId() === String(location.rowId)) {
                  this.gridOptions.api.getModel().getRow(idTest).setSelected(true);
                  idTest = -1;
                } else {
                  idTest = idTest + 1;
                }
              }
              this.gridOptions.api.setFocusedCell(idx, column.getId());
            } else {
              let idTest = 0;
              while (idTest !== -1) {
                if (this.gridOptions.api.getModel().getRow(idTest) && this.gridOptions.api.getModel().getRow(idTest).data.getId() === String(location.rowId)) {
                  this.gridOptions.api.getModel().getRow(idTest).setSelected(true);
                  idTest = -1;
                } else {
                  idTest = idTest + 1;
                }
              }
            }
          } else {
            this.gridOptionsNew.api.ensureIndexVisible(idx);
            const column = this.editBuffer.getContent().getETColumn(colId);
            if (column) {
              this.gridOptionsNew.api.ensureColumnVisible(column.getId());
              let idTest = 0;
              while (idTest !== -1) {
                if (this.gridOptionsNew.api.getModel().getRow(idTest) && this.gridOptionsNew.api.getModel().getRow(idTest).data.getId() === String(location.rowId)) {
                  this.gridOptionsNew.api.getModel().getRow(idTest).setSelected(true);
                  idTest = -1;
                } else {
                  idTest = idTest + 1;
                }
              }
              this.gridOptionsNew.api.setFocusedCell(idx, column.getId());
            } else {
              let idTest = 0;
              while (idTest !== -1) {
                if (this.gridOptionsNew.api.getModel().getRow(idTest) && this.gridOptionsNew.api.getModel().getRow(idTest).data.getId() === String(location.rowId)) {
                  this.gridOptionsNew.api.getModel().getRow(idTest).setSelected(true);
                  idTest = -1;
                } else {
                  idTest = idTest + 1;
                }
              }
            }
          }
        }
      });
    }
  }

  updateGridWithNewPageConfirm(move: string, updatedpageNum?: string) {

    let pageNum = Number(this.editBuffer.getContent().decisionTableCurrentPage);
    const showAll = false;
    let queryPart = '';
    const totalPages = Number(this.editBuffer.getContent().decisionTableTotalPages);

    if (move === 'N' && (pageNum < totalPages)) {
      pageNum = pageNum + 1;
    } else if (move === 'P' && (pageNum > 1)) {
      pageNum = pageNum - 1;
    } else if (move === 'F') {
      pageNum = 1;
    } else if (move === 'L') {
      pageNum = totalPages;
    }

    if (move === 'SA') {
      queryPart = 'pageNum=1&tableType=DECISION_TABLE&showAll=true';
    } else if (move === 'SP') {
      queryPart = 'pageNum=1&tableType=DECISION_TABLE&showAll=false';
    } else if (move === 'GP' && updatedpageNum) {
      if (Number(updatedpageNum) <= totalPages) {
        queryPart = 'pageNum=' + String(updatedpageNum) + '&tableType=DECISION_TABLE';
      } else {
        return;
      }
    } else {
      queryPart = 'pageNum=' + String(pageNum) + '&tableType=DECISION_TABLE';
    }
    this.artifact.get('artifact.json?projectName=' + this.editBuffer.getContent().projectName + '&artifactPath=' + this.editBuffer.getContent().artifactPath + '&artifactExtension=rulefunctionimpl&' + queryPart).pipe(
      map(res => {
        if (res.ok()) {
          this.updateDecisionTableNextPage(res.record[0].artifactDetails);
          // console.log(res.record[0].artifactDetails);
        }
      }))
      .toPromise();
    console.log(this.i18n('Successfully Completed'));

  }

  updateGridWithNewPage(move: string, updatedpageNum?: string) {
    if (this.editBuffer.isDirty()) {
      this.modal.confirm()
        .message(this.i18n('Are you sure you want discard the changes on this page.'))
        .okBtn('Discard')
        .open().result
        .then(ok => {
          if (ok) {
            this.updateGridWithNewPageConfirm(move, updatedpageNum);
          }
        }, err => { if (err) { throw err; } });

    } else {
      this.updateGridWithNewPageConfirm(move, updatedpageNum);
    }

  }

  updateDecisionTableNextPage(artifactDetails: any) {
    this.editBuffer.getBase().updateDTNextPage(artifactDetails);
    if (this.editBuffer.getBuffer()) {
      this.editBuffer.getBuffer().updateDTNextPage(artifactDetails);
    }
    this.update();
  }

  isSinglePage() {
    if (this.editBuffer.getContent().decisionTableTotalPages === '1' || this.editBuffer.getContent().decisionTableTotalPages === '0') {
      return true;
    } else {
      return false;
    }
  }

  isShowPages() {
    if (this.editBuffer.getContent().decisionTableTotalPages === '1' && Boolean(this.editBuffer.getContent().decisionTableSinglePageView) && this.editBuffer.getContent().decisionTableLastRuleId !== '0') {
      return true;
    } else {
      return false;
    }
  }

  getCurrentPageCount() {
    return this.editBuffer.getBuffer() ? this.editBuffer.getBuffer().decisionTableCurrentPage : this.editBuffer.getBase().decisionTableCurrentPage;
  }

  getTotalPageCount() {
    return this.editBuffer.getBuffer() ? this.editBuffer.getBuffer().decisionTableTotalPages : this.editBuffer.getBase().decisionTableTotalPages;
  }

  getMessage(): String {
    return (this.i18n('Page: {{0}} of {{1}}', { 0: this.getCurrentPageCount(), 1: this.getTotalPageCount() }));
  }

  getLocale(): Boolean {
    const locale = navigator.language as string;
    if (locale.search('en') !== -1 || locale.search('zh') !== -1 || locale.search('ko') !== -1) {
      return true;
    } else {
      return false;
    }
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
