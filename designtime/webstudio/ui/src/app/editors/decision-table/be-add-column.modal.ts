
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext, Modal } from 'ngx-modialog/plugins/bootstrap';
import { map } from 'rxjs/operators';

import { Logger } from '../../core/logger.service';
import { RestService } from '../../core/rest.service';
import { BEDecisionTable } from '../../editables/decision-table/be-decision-table';
import { ColumnType, PropertyType } from '../../editables/decision-table/column';
import { DecisionTable } from '../../editables/decision-table/decision-table';
import { Argument, Property } from '../../models-be/decision-table-be/decisiontable-arguments-data';

export const ADD_COLUMN = 'ADD_COLUMN';

class ColumnForm {
  columnName: string;
  columnType = ColumnType.CONDITION.value; // default
  propertyType = PropertyType.STRING.value; // default
}

export class AddBEColumnContext extends BSModalContext {
  constructor(
    public table: DecisionTable,
    public pname: string,
    public apath: string
  ) {
    super();
  }

}

export class AddBEETColumnContext extends BSModalContext {
  constructor(
    public table: BEDecisionTable,
    public pname: string,
    public apath: string
  ) {
    super();
  }

}

@Component({
  templateUrl: './be-add-column.modal.html',
  styleUrls: ['be-add-column.modal.css']
})
export class AddBEColumnModal implements ModalComponent<AddBEColumnContext>, OnInit, AfterViewInit {

  get propertyTypes() {
    return PropertyType.TYPES.sort((a, b) => a.name.localeCompare(b.name));
  }

  get columnTypes() {
    return ColumnType.BE_TYPES;
  }

  get beColumnTypes() {
    if (ColumnType.BE_TYPES[0].name.indexOf('Condition') !== -1) {
      ColumnType.BE_TYPES[0].name = this.i18n('Condition');
    }
    if (ColumnType.BE_TYPES[1].name.indexOf('Action') !== -1) {
      ColumnType.BE_TYPES[1].name = this.i18n('Action');
    }
    return ColumnType.BE_TYPES;
  }

  get existingColumns() {
    const colType = ColumnType.fromValue(this.form.columnType);
    const res = this.table.getColumns()
      .filter(col => colType.isSameCategory(col.columnType))
      .map(col => col.name);
    return res;
  }

  get columnNameDuplicate() {
    return this.existingColumns.indexOf(this.form.columnName) !== -1;
  }

  form: ColumnForm;
  table: DecisionTable;
  pname: string;
  apath: string;
  entryArray: Array<any> = new Array<any>();
  finalArray: Array<any> = new Array<any>();
  propMap: Map<string, Property[]> = new Map<string, Property[]>();
  vrfBased = true;
  customBased = false;

  selectedColType = ColumnType.CONDITION.value;

  @ViewChild('columnNameInput', { static: false })
  private columnNameInput: ElementRef;

  constructor(
    private artifact: RestService,
    private log: Logger,
    private modal: Modal,
    public dialog: DialogRef<AddBEColumnContext>,
    public i18n: I18n
  ) {
    this.table = dialog.context.table;
    this.pname = dialog.context.pname;
    this.apath = dialog.context.apath;

  }

  ngOnInit() {
    this.artifact.get('decisiontable/arguments.json?projectName=' + this.pname + '&artifactPath=' + this.apath + '&artifactExtension=rulefunctionimpl&artifactType=RULEFUNCTIONIMPL').pipe(
      map(res => {
        if (res.ok()) {
          this.processArguments(res.record[0].artifactDetails);
        }
      }))
      .toPromise();
    this.form = new ColumnForm();
  }

  ngAfterViewInit() {
    // this.columnNameInput.nativeElement.focus();
  }

  onCancel() {
    this.dialog.dismiss();
  }

  onSubmit() {
    this.dialog.close({
      isVRF: !this.customBased,
      colArray: this.finalArray,
      colType: ColumnType.fromValue(this.selectedColType),
      columnName: this.form.columnName,
      columnType: this.getColumnType(),
      propertyType: PropertyType.fromValue(this.form.propertyType)
    });
  }

  getColumnType() {
    if (this.customBased) {
      if (this.form.columnType === 'CONDITION') {
        return ColumnType.fromValue('CUSTOM_CONDITION');
      } else {
        return ColumnType.fromValue('CUSTOM_ACTION');
      }
    } else {
      return ColumnType.fromValue(this.selectedColType);
    }
  }

  isArgumentPropper(entry: any) {
    if (entry instanceof Property) {
      return false;
    } else {
      return true;
    }
  }

  updateFinalList(entry: Property) {
    if (this.finalArray.indexOf(entry) !== -1) {
      this.finalArray.splice(this.finalArray.indexOf(entry), 1);
    } else {
      this.finalArray.push(entry);
    }
  }

  isItemSelected(entry: Property) {
    if (this.finalArray.indexOf(entry) !== -1) {
      return true;
    } else {
      return false;
    }
  }

  isVRFBased() {
    return this.vrfBased;
  }

  isCustomBased() {
    return this.customBased;
  }

  setCustomBased(value: boolean) {
    this.customBased = value;
  }

  setVRFBased(value: boolean) {
    this.vrfBased = value;
  }

  isSelectable(entry: any) {
    if (entry.resourceType) {
      if (entry.resourceType === 'PRIMITIVE') {
        return true;
      } else {
        return false;
      }
    } else if (entry.type) {
      if (entry.type === 'ContainedConcept' || entry.type === 'ConceptReference') {
        return false;
      } else {
        return true;
      }
    }
  }

  isExpanded(argument: Argument) {
    return argument.isExpanded;
  }

  getStyle(value: string) {
    return ((value.split('.').length) - 1) * 24 + 'px';
  }

  getIcons(value: any) {
    if (value instanceof Property) {
      if (value.type === 'int') {
        return 'assets/img/iconInteger16.gif';
      } else if (value.type === 'String') {
        return 'assets/img/iconString16.gif';
      } else if (value.type === 'long') {
        return 'assets/img/iconLong16.gif';
      } else if (value.type === 'double') {
        return 'assets/img/iconReal16.gif';
      } else if (value.type === 'boolean') {
        return 'assets/img/iconBoolean16.gif';
      } else if (value.type === 'DateTime') {
        return 'assets/img/iconDate16.gif';
      } else {
        return 'assets/img/concept.png';
      }

    } else {
      if (value.path === 'int') {
        return 'assets/img/iconInteger16.gif';
      } else if (value.path === 'String') {
        return 'assets/img/iconString16.gif';
      } else if (value.path === 'long') {
        return 'assets/img/iconLong16.gif';
      } else if (value.path === 'double') {
        return 'assets/img/iconReal16.gif';
      } else if (value.path === 'boolean') {
        return 'assets/img/iconBoolean16.gif';
      } else if (value.path === 'DateTime') {
        return 'assets/img/iconDate16.gif';
      } else {
        if (value.resourceType === 'EVENT') {
          return 'assets/img/event_argument.png';
        } else {
          return 'assets/img/concept_argument.png';
        }
      }

    }
  }

  setExpanded(argument: any) {
    let isalreadyFetched = true;
    if (argument instanceof Property) {
      const entries = this.propMap.get(argument.displayValue + '_' + argument.type);
      if (entries) {
        let count = 0;
        for (const entry of entries) {
          this.entryArray.splice(this.entryArray.indexOf(argument) + 1 + count, 0, entry);
          count = count + 1;
        }
      } else {
        isalreadyFetched = false;
      }
    } else {
      const entries = this.propMap.get(argument.argumentAlias + '_' + argument.resourceType);
      if (entries) {
        let count = 0;
        for (const entry of entries) {
          this.entryArray.splice(this.entryArray.indexOf(argument) + 1 + count, 0, entry);
          count = count + 1;
        }
      } else {
        isalreadyFetched = false;
      }
    }

    if (!isalreadyFetched) {
      this.fetchOtherArguments(argument);
    }

    argument.isExpanded = true;
  }

  setCollapsed(argument: any) {
    if (argument instanceof Property) {
      const entries = this.propMap.get(argument.displayValue + '_' + argument.type);
      for (const entry of entries) {
        if (this.propMap.get(entry.name + '_' + entry.type) && entry.isExpanded) {
          this.setCollapsed(entry);
          this.entryArray.splice(this.entryArray.indexOf(argument) + 1, 1);
        } else {
          this.entryArray.splice(this.entryArray.indexOf(argument) + 1, 1);
        }
      }
    } else {
      const entries = this.propMap.get(argument.argumentAlias + '_' + argument.resourceType);
      for (const entry of entries) {
        if (this.propMap.get(entry.name + '_' + entry.type) && entry.isExpanded) {
          this.setCollapsed(entry);
          this.entryArray.splice(this.entryArray.indexOf(argument) + 1, 1);
        } else {
          this.entryArray.splice(this.entryArray.indexOf(argument) + 1, 1);
        }
      }
    }
    argument.isExpanded = false;
  }

  processArguments(argObject: any) {
    for (const arg of argObject.argument) {
      const propArray: Array<Property> = new Array<Property>();
      if (arg.property) {
        for (const prop of arg.property) {
          const p = new Property(prop.isArray ? arg.argumentAlias + '.' + prop.name + '[]' : arg.argumentAlias + '.' + prop.name, prop.name, false, prop.name, prop.type, prop.ownerPath, prop.isArray, prop.associatedDomain, prop.conceptTypePath ? prop.conceptTypePath : undefined);
          propArray.push(p);
        }

        propArray.sort(function (a, b) {
          if (a.displayValue.toLowerCase() < b.displayValue.toLowerCase()) { return -1; }
          if (a.displayValue.toLowerCase() > b.displayValue.toLowerCase()) { return 1; }
          return 0;
        });

        const path: string = arg.path;
        const name = path.substring(path.lastIndexOf('/') + 1);

        const argument = new Argument(arg.argumentAlias, name, false, arg.direction, arg.path, arg.argumentAlias, arg.resourceType, arg.isArray, propArray);
        this.entryArray.push(argument);
        this.propMap.set(arg.argumentAlias + '_' + arg.resourceType, propArray);
      } else {
        const argument = new Argument(arg.argumentAlias, arg.argumentAlias, false, arg.direction, arg.path, arg.argumentAlias, arg.resourceType, arg.isArray);
        this.entryArray.push(argument);
      }

    }
  }

  processOtherArguments(argObject: any, argument1: Property) {
    for (const arg of argObject.argument) {
      const propArray: Array<Property> = new Array<Property>();
      if (arg.property) {
        for (const prop of arg.property) {
          const p = new Property(prop.isArray ? argument1.displayValue + '.' + prop.name + '[]' : argument1.displayValue + '.' + prop.name, prop.name, false, prop.name, prop.type, prop.ownerPath, prop.isArray, prop.associatedDomain, prop.conceptTypePath ? prop.conceptTypePath : undefined);
          propArray.push(p);
        }

        propArray.sort(function (a, b) {
          if (a.displayValue.toLowerCase() < b.displayValue.toLowerCase()) { return -1; }
          if (a.displayValue.toLowerCase() > b.displayValue.toLowerCase()) { return 1; }
          return 0;
        });

        let count = 0;
        for (const p of propArray) {
          this.entryArray.splice(this.entryArray.indexOf(argument1) + 1 + count, 0, p);
          count = count + 1;
        }
        this.propMap.set(argument1.displayValue + '_' + argument1.type, propArray);
      } else {
        const argument = new Argument(argument1.displayValue + '.' + arg.argumentAlias, arg.argumentAlias, true, arg.direction, arg.path, arg.argumentAlias, arg.resourceType, arg.isArray);
        this.entryArray.splice(this.entryArray.indexOf(argument1) + 1, 0, argument);
      }

    }
  }

  fetchOtherArguments(argument: Property) {
    this.artifact.get('decisiontable/arguments.json?projectName=' + this.pname + '&artifactPath=' + argument.conceptTypePath + '&artifactExtension=concept&artifactType=CONCEPT').pipe(
      map(res => {
        if (res.ok()) {
          this.processOtherArguments(res.record[0].artifactDetails, argument);
        }
      }))
      .toPromise();
  }
  getMessage(): string {
    const msg = this.i18n('Column {{columnName}} already exists.', { columnName: this.form.columnName });
    return msg;
  }
  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
