
import { Component, Injector, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AgGridAngular } from 'ag-grid-angular';
import {
  ColDef,
  ColGroupDef,
  GetContextMenuItemsParams,
  GetMainMenuItemsParams,
  GridOptions,
  MenuItemDef
} from 'ag-grid-community/main';
import { ClipboardService, LicenseManager } from 'ag-grid-enterprise/main';
import { takeUntil } from 'rxjs/operators';

import { AddColumnContext, AddColumnModal } from './add-column.modal';
import { BEColumnSettingContext, BEColumnSettingModal } from './be-column-fields-setting.modal';
import { DecisionTableEditorService } from './decisiontable-editor.service';
import { DateTimeModal, DateTimeModalContext } from './decorators/decisiontable-datetime-modal';
import { DecisionTableDecorator, LabelClasses } from './decorators/decisiontable-decorator';
import { DecisionTableDiffDecorator } from './decorators/decisiontable-diff-decorator';
import { DomainCellEditorComponent } from './decorators/decisiontable-domain-cell-editor';
import { DecisionTableMergeDecorator } from './decorators/decisiontable-merge-decorator';
import { DecisionTableRegularDecorator } from './decorators/decisiontable-regular-decorator';
import { SchemaEditorContext, SchemaEditorModal, } from './schema-editor/schema-editor.modal';

import { environment } from '../../../environments/environment';
import { ArtifactPropertiesService } from '../../artifact-editor/artifact-properties.service';
import { SBSchema } from '../../artifact-importer/sb-schema-importer/sb-schema-importer.component';
import { AlertService } from '../../core/alert.service';
import { ArtifactService, StateChange } from '../../core/artifact.service';
import { Logger } from '../../core/logger.service';
import { ModalService } from '../../core/modal.service';
import { RestService } from '../../core/rest.service';
import { SettingsService } from '../../core/settings.service';
import { BEDecisionTable } from '../../editables/decision-table/be-decision-table';
import { Column, ColumnType, PropertyType } from '../../editables/decision-table/column';
import { DecisionTable } from '../../editables/decision-table/decision-table';
import { Differ } from '../../editables/decision-table/differ/differ';
import { Rule } from '../../editables/decision-table/rule';
import { EditBuffer } from '../../editables/edit-buffer';
import { BEDecisionTableUtil } from '../../models-be/decision-table-be/decision-table-util';
import { BESettings } from '../../models-be/settings-be';
import { EditorInterface } from '../editor-interface';
import { EditorParams } from '../editor-params';
import { EditorComponent } from '../editor.component';
import { ReviewPropertiesService } from '../review-properties.service';
/**
 * This is a hack unless
 * https://github.com/ceolter/ag-grid/issues/1122
 * got fixed.
 */
(<any>ClipboardService).prototype.executeOnTempElement = function (callbackNow, callbackAfter) {
  const eTempInput = document.createElement('textarea');
  eTempInput.style.width = '1px';
  eTempInput.style.height = '1px';
  eTempInput.style.top = '0px';
  eTempInput.style.left = '0px';
  eTempInput.style.position = 'absolute';
  eTempInput.style.opacity = '0.0';
  const guiRoot = this.gridCore.getRootGui();
  guiRoot.appendChild(eTempInput);
  try {
    const result = callbackNow(eTempInput);
    this.logger.log('Clipboard operation result: ' + result);
  } catch (err) {
    this.logger.log('Browser doesn\t support document.execComment(\'copy\') for clipboard operations');
  }
  if (callbackAfter) {
    setTimeout(function () {
      callbackAfter(eTempInput);
      guiRoot.removeChild(eTempInput);
    }, 100);
  } else {
    guiRoot.removeChild(eTempInput);
  }
};

// Set license key
LicenseManager.setLicenseKey('TIBCO_MultiApp_20Devs25_April_2020__MTU4Nzc2OTIwMDAwMA==adf17342675c57f75c6d590022e12646');

const DEFAULT_PRIORITY = '5';

export type CMenuItem = MenuItemDef | string;

@Component({
  selector: 'decisiontable-editor',
  templateUrl: './decisiontable-editor.component.html',
  styleUrls: ['./decisiontable-editor.component.css'],
})
export class DecisionTableEditorComponent extends EditorComponent<DecisionTable> implements OnInit {
  get rowHeight() {
    return this.themeClass === 'ag-theme-material' ? 48 : 25;
  }
  get headerHeight() {
    return this.themeClass === 'ag-theme-material' ? 48 : 31;
  }

  get tableStyles() {
    return [
      {
        name: 'Default',
        theme: 'ag-theme-balham'
      },
      {
        name: 'Default dark',
        theme: 'ag-theme-balham-dark'
      },
      {
        name: 'Material',
        theme: 'ag-material-theme'
      },
      {
        name: 'Bootstrap',
        theme: 'ag-material-bootstrap'
      },
      {
        name: 'Blue',
        theme: 'ag-material-blue'
      },
      {
        name: 'Dark',
        theme: 'ag-material-dark'
      },
      {
        name: 'Fresh',
        theme: 'ag-material-fresh'
      }
    ];
  }

  /**
   * Real effective time in decision table
   */
  get effectiveTime(): string {
    return this.decorator.getProperty(DecisionTable.EFFECTIVE_TIME_PARAM);
  }

  /**
   * Real effective time in decision table
   */
  set effectiveTime(v: string) {
    const oldVal = this.decorator.getProperty(DecisionTable.EFFECTIVE_TIME_PARAM);
    if (oldVal !== v) {
      if ((this.expirationDatetime) && (this.service.parseTimestamp(v) >= this.expirationDatetime)) {
        this.alert.flash(this.i18n('Expiration Date should be Greater than Effective Date.'), 'error');
      } else {
        const action = this.service.propertyEditAction(DecisionTable.EFFECTIVE_TIME_PARAM, oldVal, v);
        this.execute(action);
      }
    }
  }

  /**
   * The datetime shows up in the datetime picker
   */
  get effectiveDatetime(): Date {
    if (!this._effectiveDatetime || this.service.serializeDateTime(this._effectiveDatetime) !== this.effectiveTime) {
      if (this.effectiveTime) {
        this._effectiveDatetime = this.service.parseTimestamp(this.effectiveTime);
      } else {
        this._effectiveDatetime = null;
      }
    }
    return this._effectiveDatetime;
  }

  /**
   * The datetime shows up in the datetime picker.
   */
  set effectiveDatetime(dt: Date) {
    if (dt) {
      this._effectiveDatetime = dt;
      this.effectiveTime = this.service.serializeDateTime(dt);
    } else {
      this.effectiveTime = '';
    }
  }

  /**
   * Real expiration time in decision table
   */
  get expiryTime(): string {
    return this.decorator.getProperty(DecisionTable.EXPIRY_TIME_PARAM);
  }

  /**
   * Real expiration time in decision table
  */
  set expiryTime(v: string) {
    const oldVal = this.decorator.getProperty(DecisionTable.EXPIRY_TIME_PARAM);
    if (oldVal !== v) {
      if ((this.effectiveDatetime) && (this.service.parseTimestamp(v) <= this.effectiveDatetime)) {
        this.alert.flash(this.i18n('Expiration Date should be Greater than Effective Date.'), 'error');
      } else {
        const action = this.service.propertyEditAction(DecisionTable.EXPIRY_TIME_PARAM, oldVal, v);
        this.execute(action);
      }
    }
  }

  /**
   * Datetime in the picker
   */
  get expirationDatetime(): Date {
    if (!this._expirationDatetime || this.service.serializeDateTime(this._expirationDatetime) !== this.expiryTime) {
      if (this.expiryTime) {
        this._expirationDatetime = this.service.parseTimestamp(this.expiryTime);
      } else {
        this._expirationDatetime = null;
      }
    }
    return this._expirationDatetime;
  }

  /**
   * Datetime in the picker
   */
  set expirationDatetime(dt: Date) {
    if (dt) {
      this._expirationDatetime = dt;
      this.expiryTime = this.service.serializeDateTime(dt);
    } else {
      this.expiryTime = '';
    }
  }

  get priority() {
    const value = this.decorator.getProperty(DecisionTable.PRIORITY_PARAM);
    if (value) {
      return value;
    } else {
      return DEFAULT_PRIORITY;
    }
  }

  set priority(v: string) {
    const oldVal = this.decorator.getProperty(DecisionTable.PRIORITY_PARAM);
    if (oldVal !== v) {
      const action = this.service.propertyEditAction(DecisionTable.PRIORITY_PARAM, oldVal, v);
      this.execute(action);
    }
  }

  get singleExec(): boolean {
    const res = this.decorator.getProperty(DecisionTable.SINGLE_EXEC_PARAM);
    return res === 'true';
  }

  set singleExec(v: boolean) {
    const val = v ? 'true' : 'false';
    const oldVal = this.decorator.getProperty(DecisionTable.SINGLE_EXEC_PARAM);
    if (oldVal !== val) {
      const action = this.service.propertyEditAction(DecisionTable.SINGLE_EXEC_PARAM, oldVal, val);
      this.execute(action);
    }
  }

  get timestampAsUTC(): boolean {
    const res = this.decorator.getProperty(DecisionTable.TIMESTAMP_AS_UTC);
    return res === 'true';
  }

  set timestampAsUTC(v: boolean) {
    const val = v ? 'true' : 'false';
    const oldVal = this.decorator.getProperty(DecisionTable.TIMESTAMP_AS_UTC);
    if (oldVal !== val) {
      const action = this.service.propertyEditAction(DecisionTable.TIMESTAMP_AS_UTC, oldVal, val);
      this.execute(action);
    }
  }

  get isCheckedOut(): boolean {
    return this.params.artifact ? this.params.artifact.isCheckedOutArtifact : false;
  }

  get showLegend(): boolean {
    return this.isCheckedOut
      ? (this.editBuffer.isDirty()
        ? true
        : (this.params.artifact
          ? this.params.artifact.status === 'MODIFIED'
          : false))
      : false;
  }
  public themeClass = 'ag-theme-balham';
  // set by the loader
  params: EditorParams<DecisionTable>;

  isReady: boolean;
  editable: boolean;
  editBuffer: EditBuffer<DecisionTable>;
  base: any;
  decorator: DecisionTableDecorator;

  priorityOptions = [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  be_priorityOptions = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
  rowData: any[];
  columnDefs: (ColDef | ColGroupDef)[];
  defaultColDef: any;
  gridOptions: GridOptions = {
    defaultColDef: {
      resizable: true,
      filter: true
    },
    // floatingFilter: true, // enable this after adding logic to determine filter type
    getMainMenuItems: this.getMainMenuItemsHandler(),
    getContextMenuItems: this.getContextMenuItemsHandler(),
    enableRangeSelection: true,
    suppressCopyRowsToClipboard: true,
  };

  datetime = new Date();
  showEffectiveTimePicker: boolean;
  showExpirationTimePicker: boolean;
  private _effectiveDatetime: Date;
  private _expirationDatetime: Date;

  private state: StateChange;

  @ViewChild('agGrid', { static: true })
  private agGrid: AgGridAngular;

  constructor(
    public differ: Differ,
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
    public i18n: I18n
  ) {
    super();
  }
  public setLookAndFeel(laf) {
    this.themeClass = laf;
    this.gridOptions.rowHeight = this.rowHeight;
    const params = {};
    this.gridOptions.api.redrawRows(params);
    this.gridOptions.api.resetRowHeights();
    this.gridOptions.api.setHeaderHeight(this.headerHeight);
    this.gridOptions.api.refreshHeader();
    const allColumnIds = ['ID', 'PRIORITY']; // just reset these two columns, to avoid collapsing empty ones
    //    this.gridOptions.columnApi.getAllColumns().forEach(function(column) {
    //      allColumnIds.push(column.getId());
    //    });
    setTimeout(() => {
      // seems to want to be in a timeout to let the sizes update
      this.gridOptions.columnApi.autoSizeColumns(allColumnIds); // , 'autosizeColumns')
    }, 0);
  }

  getMainMenuItemsHandler() {
    return (params: GetMainMenuItemsParams): CMenuItem[] => {
      const columnMenu: CMenuItem[] = params.defaultItems.slice();
      const id = params.column.getColDef().colId;
      if (this.editable && this.editBuffer.getContent().hasColumn(id)) {
        columnMenu.push({
          icon: `<i class='fa fa-trash'></i>`,
          name: 'Remove Column',
          action: () => {
            this.onDeleteColumn(params.column.getColDef());
          }
        });

        columnMenu.push({
          name: 'Field Settings',
          action: () => {
            this.columnFieldsSetting(params.column.getColDef());
          }
        });

      }
      return columnMenu;
    };
  }

  getContextMenuItemsHandler() {
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
            action: () => this.gridOptions.api.copySelectedRangeToClipboard(false)
          },
          {
            icon: `<i class='fa fa-clipboard'></i>`,
            name: `Copy Row ${rule.getId()}`,
            action: () => this.gridOptions.api.copySelectedRowsToClipboard(false)
          },
          {
            icon: `<i class='fa fa-clipboard'></i>`,
            name: `Copy Row ${rule.getId()} with header`,
            action: () => this.gridOptions.api.copySelectedRowsToClipboard(true)
          },
          {
            icon: `<i class='fa fa-clipboard'></i>`,
            name: `Paste (Ctrl + V)`,
            action: () => this.modal.alert().message('Use Ctrl + V to do the pasting.').open()
          },
        ];
        if (this.editable && rule && this.editBuffer.getContent().hasRule(rule.getId())) {

          if (this.isEnabledRow(rule)) {
            contextMenu.push({
              icon: `<i class='fa fa-trash'></i>`,
              name: 'Disable Row ' + rule.getId(),
              action: () => this.onDisableRule(rule),
            });
          } else {
            contextMenu.push({
              icon: `<i class='fa fa-trash'></i>`,
              name: 'Enable Row ' + rule.getId(),
              action: () => this.onEnableRule(rule),
            });
          }

          contextMenu.push({
            icon: `<i class='fa fa-trash'></i>`,
            name: 'Remove Row ' + rule.getId(),
            action: () => this.onDeleteRule(rule),
          });

          contextMenu.push({
            icon: `<i class='fa fa-clipboard'></i>`,
            name: 'Duplicate Row ' + rule.getId(),
            action: () => this.onDuplicateRule(rule),
          });
        }
        // contextMenu.push('separator', 'toolPanel');
      } else {
        // contextMenu = ['toolPanel'];
      }
      return contextMenu;
    };
  }

  ngOnInit() {
    this.update();
  }

  focusOnLocations(locations: any[]) {
    if (locations && this.agGrid) {
      this.gridOptions.api.deselectAll();
      this.gridOptions.api.clearFocusedCell();
      this.gridOptions.api.clearRangeSelection();
      locations.forEach(location => {
        const rowId = parseInt(location.rowId, 10);
        const colId = location.colId;
        if (rowId !== NaN && rowId !== -1) {
          const idx = rowId - 1;
          this.gridOptions.api.ensureIndexVisible(idx);
          const column = this.editBuffer.getContent().getColumns().find(col => col.name === colId);
          if (column) {
            this.gridOptions.api.ensureColumnVisible(column.getId());
            this.gridOptions.api.getModel().getRow(idx).setSelected(true);
            this.gridOptions.api.setFocusedCell(idx, column.getId());
          } else {
            this.gridOptions.api.getModel().getRow(idx).setSelected(true);
            this.gridOptions.api.setFocusedCell(idx, 'ID');
          }
        }
      });
    }
  }

  enableEffectiveTimePicker() {
    if (this.editable) {
      this.showEffectiveTimePicker = true;
    }
  }

  disableEffectiveTimePicker() {
    this.showEffectiveTimePicker = false;
  }

  public reset() {
    this._effectiveDatetime = null;
    this._expirationDatetime = null;
  }

  public update() {
    if (this.params) {
      this.editable = this.params.editorMode === 'edit';
      this.editBuffer = this.params.editBuffer;
      if (!this.editBuffer) {
        throw this.i18n('EditBuffer can not be null');
      }
      if (this.params.editorInterface === EditorInterface.SB_DECISION_TABLE) {
        this.base = DecisionTable.fromXml(this.params.base);
      }

      switch (this.params.editorMode) {
        case 'display':
        case 'edit':
          this.editable = this.params.editorMode === 'edit';
          if (this.params.showChangeSet === 'rhs') {
            if (this.params.editorInterface === EditorInterface.BE_DECISION_TABLE) {
              this.base = BEDecisionTable.fromXml(this.params.base);
            } else {
              this.base = DecisionTable.fromXml(this.params.base);
            }
            const diffResult = this.differ.liveDiff(this.base, this.editBuffer.getContent());
            this.decorator = new DecisionTableDiffDecorator(diffResult, this.editBuffer, this.editable);
          } else {
            this.decorator = new DecisionTableRegularDecorator(this.editBuffer, this.editable);
          }
          break;
        case 'sync':
          this.editable = false;
          this.decorator = new DecisionTableMergeDecorator(this.params.mergeResult,
            this.editBuffer,
            this.params.showChangeSet,
            this.injector, this.i18n);
          // subscribe changes made by external entities
          this.editBuffer.onRefresh().pipe(takeUntil(this.whenDestroyed)).subscribe(() => {
            this.reset();
            this.updateAgGrid();
          });
          break;
      }

      // initial grid rendering
      this.updateAgGrid();
      this.isReady = true;
    }
  }

  onGridReady(params) {
    if (params.api) {
      params.api.closeToolPanel();
    }
  }

  updateAgGrid() {
    this.editBuffer.markForDirtyCheck();
    this.decorator.refresh();

    this.defaultColDef = {
      enableValue: true,
      enableRowGroup: true,
      enablePivot: true,
      sortable: true,
      filter: true,
      resizable: true
    };

    this.columnDefs = [
      <ColDef>{
        width: 55,
        headerName: this.i18n('ID'),
        pinned: this.getIDValue(),
        colId: 'ID',
        valueGetter: this.getValueGetter(),
        cellClassRules: this.decorator.getCellClassRules(undefined),
        cellRenderer: this.decorator.getCellRenderer(null),
        onCellDoubleClicked: this.decorator.getCellDoubleClickHandler(),
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

    this.rowData = this.decorator.getRows().sort((a, b) => {
      const idA = parseInt(a.getId(), 10);
      const idB = parseInt(b.getId(), 10);
      return idA - idB;
    });

    this.decorator.getColumns()
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
        if (col.associatedDM && this.params.editorMode !== 'display') {
          colDef = {
            colId: col.getId(),
            headerName: headerName,
            valueGetter: this.getValueGetter(col),
            newValueHandler: this.getNewValueHandler(col),
            cellEditorFramework: DomainCellEditorComponent,
            cellRenderer: this.decorator.getCellRenderer(col),
            editable: true,
            width: 250,
            cellEditorParams: this.getValuesNeeded(col)
          };
        } else if (col.propertyType.name === 'int' || col.propertyType.name === 'double' || col.propertyType.name === 'long') {
          colDef = {
            colId: col.getId(),
            editable: this.decorator.getCellEditable(col),
            headerName: headerName,
            headerClass: this.decorator.getColHeaderClass(col.getId()),
            valueGetter: this.getValueGetter(col),
            newValueHandler: this.getNewValueHandler(col),
            cellClassRules: this.decorator.getCellClassRules(col),
            cellRenderer: this.decorator.getCellRenderer(col),
            cellEditor: editor.editor,
            cellEditorParams: editor.params,
            onCellDoubleClicked: this.decorator.getCellDoubleClickHandler(),
            comparator: function (valueA, valueB, nodeA, nodeB, isInverted) {
              return valueA - valueB;
            }
          };
        } else if (col.propertyType.name === 'timestamp') {
          colDef = {
            colId: col.getId(),
            editable: this.decorator.getCellEditable(col),
            headerName: headerName,
            headerClass: this.decorator.getColHeaderClass(col.getId()),
            valueGetter: this.getValueGetter(col),
            newValueHandler: this.getNewValueHandler(col),
            cellClassRules: this.decorator.getCellClassRules(col),
            cellRenderer: this.decorator.getCellRenderer(col),
            cellEditor: editor.editor,
            cellEditorParams: editor.params,
            onCellDoubleClicked: this.editable ? this.getCellDoubleClickHandler(col) : this.decorator.getCellDoubleClickHandler(),
          };
        } else {
          colDef = {
            colId: col.getId(),
            editable: this.decorator.getCellEditable(col),
            headerName: headerName,
            headerClass: this.decorator.getColHeaderClass(col.getId()),
            valueGetter: this.getValueGetter(col),
            newValueHandler: this.getNewValueHandler(col),
            cellClassRules: this.decorator.getCellClassRules(col),
            cellRenderer: this.decorator.getCellRenderer(col),
            cellEditor: editor.editor,
            cellEditorParams: editor.params,
            onCellDoubleClicked: this.decorator.getCellDoubleClickHandler(),
          };
        }

        // dispatch to CONDITION or ACTION
        switch (col.columnType) {
          case ColumnType.CONDITION:
          case ColumnType.EXPR_CONDITION:
            (<ColGroupDef>this.columnDefs[1]).children.push(colDef);
            break;
          case ColumnType.ACTION:
          case ColumnType.EXPR_ACTION:
            (<ColGroupDef>this.columnDefs[2]).children.push(colDef);
            break;
          case ColumnType.PROPERTY:
            colDef.pinned = this.getPropertyValue();
            colDef.width = 100;
            colDef.suppressMenu = true;
            this.columnDefs.push(colDef);
            break;
        }
      });
  }

  getCellDoubleClickHandler(col: any) {
    return (params: any) => {
      this.modal
        .open(DateTimeModal, new DateTimeModalContext(params.value))
        .then((colInfo: any) => {
          const rule = <Rule>params.data;
          if (rule) {
            if (col) {
              const action = this.service.cellEditAction(rule.getId(), col.getId(), params.value, colInfo.cellDateValue);
              this.execute(action);
              this.gridOptions.api.redrawRows(params);
            }
          }
        },
          () => { });
      return null;
    };
  }

  getColumnAliasName(col: any) {
    return col.name + ` (${col.propertyType.name})`;
  }

  getValuesNeeded(col: Column) {
    return {
      editor: 'agRichSelectCellEditor',
      params: {
        artifactpath: col.property.substr(0, col.property.lastIndexOf('/')),
        propertyname: col.property.substr(col.property.lastIndexOf('/') + 1)
      }
    };
  }

  isStreamBaseExpressionColumn(col: Column) {
    return col.columnType === ColumnType.EXPR_ACTION || col.columnType === ColumnType.EXPR_CONDITION;
  }

  isPropertyLabelClickable(prop: string) {
    return this.decorator.isPropertyLabelClickable(prop);
  }

  onPropertyLabelClick(prop: string) {
    if (this.isPropertyLabelClickable(prop)) {
      const handler = this.decorator.getPropertyLabelClickHandler(prop);
      if (handler) {
        handler(prop);
      }
    }
  }

  redo() {
    super.redo();
    // redraw rows should not be done inside the cell edit action, as it destroys
    // the cell editor and causes an error.  It is only needed during a redo()
    this.gridOptions.api.redrawRows();
  }

  getValueGetter(col?: Column) {
    return this.decorator.getCellValueGetter(col);
  }

  getIDValue(): string {
    return (navigator.language.search('ar') !== -1 ? 'right' : 'left');
  }

  getPropertyValue(): string {
    return (navigator.language.search('ar') !== -1 ? 'left' : 'right');
  }

  getNewValueHandler(col: Column) {
    if (this.editable) {
      return (params: any) => {
        if (params.oldValue !== params.newValue) {
          const rule: Rule = params.data;
          if (col.isSubstitution) {
            const substituted = BEDecisionTableUtil.getFormattedString(col.name, params.newValue);
            const action = this.service.cellEditAction(rule.getId(), col.getId(), params.oldValue, substituted);
            this.execute(action);
          } else {
            const action = this.service.cellEditAction(rule.getId(), col.getId(), params.oldValue, params.newValue);
            this.execute(action);
          }
        }
        return params.newValue;
      };
    }
  }

  propertyDecorationText(prop: string) {
    return this.decorator.getPropertyDecorationText(prop);
  }

  showTooltip(prop: string) {
    return this.decorator.getPropertyDecorationText(prop) !== '';
  }

  labelClasses(prop: string): LabelClasses {
    return this.decorator.getPropertyLabelClasses(prop);
  }

  onDeleteColumn(col: ColDef) {
    const id = col.colId;
    this.modal.confirm()
      .message(this.i18n('Please confirm you want to delete the column : ') + col.headerName)
      .open().result
      .then(ok => {
        if (ok) {
          try {
            const dt = this.editBuffer.getBuffer();
            if (this.onlyConditionRemove(id) && environment.enableBEUI) {
              this.modal.alert()
                .message(this.i18n('Failed to remove column: there should be atleast one condition.'))
                .open();
            } else {
              const action = this.service.columnDeleteAction(id);
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

  onDeleteRule(rule: Rule) {
    this.modal.confirm()
      .message(this.i18n('Please confirm you want to delete the rule with id : ') + rule.getId())
      .open().result
      .then(ok => {
        if (ok) {
          try {
            const action = this.service.rowDeleteAction(rule.getId());
            this.execute(action);
          } catch (e) {
            this.modal.alert()
              .message(this.i18n('Fail to remove rule ') + rule.getId() + ': ' + e)
              .open();
          }
        }
      }, err => { if (err) { throw err; } });
  }

  isEnabledRow(rule: Rule): boolean {
    for (const cellObject of rule.getCells()) {
      if (!cellObject.isDisabled() && cellObject.getColId() !== 'PRIORITY') {
        return true;
      }
    }
    return false;
  }

  onDisableRule(rule: Rule) {
    for (const cellObject of rule.getCells()) {
      if (cellObject.getColId() !== 'PRIORITY') {
        cellObject.setDisabled(true);
      }
    }
    this.updateAgGrid();
  }

  onEnableRule(rule: Rule) {
    for (const cellObject of rule.getCells()) {
      if (cellObject.getColId() !== 'PRIORITY') {
        cellObject.setDisabled(false);
      }
    }
    this.updateAgGrid();
  }

  onDuplicateRule(rule: Rule) {
    try {
      const action = this.service.rowDuplicateAction(rule);
      this.execute(action);
    } catch (e) {
      this.modal.alert()
        .message(this.i18n('Fail to duplicate rule ') + rule.getId() + ': ' + e)
        .open();
    }
  }

  onEditSchema() {
    this.modal
      .open(
        SchemaEditorModal, new SchemaEditorContext(this.params.artifact.name, this.editBuffer.getBuffer().getColumns())
      ).then(
        (fields: any[]) => {
          // Get the DecisionTable. If a column in the ref exists in the content, update it according to the ref.
          // If the column does not exist, add it.
          // Replace the editBuffer's content with the new content
          const content: DecisionTable = this.editBuffer.getContent();
          // Filter for deleted columns
          // A column is deleted if it exists in content, but not in ref.
          content.getColumns().filter(col => {
            return !fields.some((field) => field.id === col.id);
          }).forEach(
            column => content.clearColumn(column.id)
          );

          fields.forEach((field) => {
            const obsolete = content.getColumn(field.id);
            if (obsolete) {
              // Field already exists in the grid, update it.
              obsolete.name = field.name;
              obsolete.propertyType = field.type as any as PropertyType;
              content.setColumn(obsolete);
            } else {
              content.createAndAddColumn(field.name, field.type as any as PropertyType, field.columnType);
            }
          }
          );

          this.update();
        }, () => { });
  }

  onAddColumn() {
    this.modal
      .open(AddColumnModal, new AddColumnContext(this.editBuffer.getContent()))
      .then((colInfo: any) => {
        this.editBuffer.markForDirtyCheck();
        let maxId: number;
        const rhs = this.editBuffer.getBuffer().maxColumnId();
        if (this.base) {
          const lhs = this.base.maxColumnId();
          maxId = lhs > rhs ? lhs : rhs;
        } else {
          maxId = rhs;
        }
        const id = (maxId + 1).toString();
        const action = this.service.columnCreateAction(id, colInfo.columnName, colInfo.propertyType, colInfo.columnType);
        try {
          this.execute(action);
        } catch (e) {
          this.log.debug(e);
          this.modal.alert()
            .message(this.i18n('Fail to add column: please make sure all fields are entered correctly.'))
            .open();
        }
      },
        () => { });
  }

  onAddRule() {
    let maxId: number;
    const rhs = this.editBuffer.getBuffer().maxRuleId();
    if (this.base) {
      const lhs = this.base.maxRuleId();
      maxId = lhs > rhs ? lhs : rhs;
    } else {
      maxId = rhs;
    }
    const id = (maxId + 1).toString();
    const action = this.service.rowCreateAction(id);
    this.execute(action);
  }

  columnFieldsSetting(col: ColDef) {
    const id = col.colId;
    const columnToSet = this.editBuffer.getBuffer().getColumn(id);

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
          this.updateRulesWithDefaultValue(columnToSet);
        }
        this.updateAgGrid();
      }, () => { });
  }

  updateRulesWithDefaultValue(col: Column) {
    const dt = this.editBuffer.getBuffer();
    for (const rule of dt.getRules()) {
      if (!rule.getCell(col.getId())) {
        if (col.columnType === ColumnType.CONDITION) {
          rule.setCell(col.getId(), col.defaultCellText, 'cond', false, '');

        } else if (col.columnType === ColumnType.ACTION) {
          rule.setCell(col.getId(), col.defaultCellText, 'act', false, '');
        }

      }
    }
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
