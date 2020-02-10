import { CellType } from './cell';
export class PropertyType {
  static TYPES: PropertyType[] = [];
  static STRING = new PropertyType('string', '0');
  static INT = new PropertyType('int', '1');
  static LIST = new PropertyType('list', '10');
  static LONG = new PropertyType('long', '2');
  static DOUBLE = new PropertyType('double', '3');
  static BOOLEAN = new PropertyType('bool', '4');
  static TIMESTAMP = new PropertyType('timestamp', '5');

  constructor(
    public name: string,
    public value: string
  ) {
    PropertyType.TYPES.push(this);
  }

  static fromName(name: string) {
    switch (name) {
      case 'string':
        return PropertyType.STRING;
      case 'int':
        return PropertyType.INT;
      case 'list':
        return PropertyType.LIST;
      case 'long':
        return PropertyType.LONG;
      case 'double':
        return PropertyType.DOUBLE;
      case 'bool':
        return PropertyType.BOOLEAN;
      case 'timestamp':
        return PropertyType.TIMESTAMP;
    }
  }

  static fromValue(val: string) {
    switch (val) {
      case '0':
        return PropertyType.STRING;
      case '1':
        return PropertyType.INT;
      case '2':
        return PropertyType.LONG;
      case '3':
        return PropertyType.DOUBLE;
      case '4':
        return PropertyType.BOOLEAN;
      case '5':
        return PropertyType.TIMESTAMP;
      case '10':
        return PropertyType.LIST;
      default:
        return PropertyType.STRING;
    }
  }
}

export class ColumnType {

  get cellType(): CellType {
    switch (this) {
      case ColumnType.ACTION:
      case ColumnType.EXPR_ACTION:
        return 'act';
      case ColumnType.CONDITION:
      case ColumnType.EXPR_CONDITION:
        return 'cond';
    }
  }
  static TYPES = [];
  static BE_TYPES = [];
  static CONDITION = new ColumnType('Condition', 'CONDITION');
  static ACTION = new ColumnType('Action', 'ACTION');
  static EXPR_CONDITION = new ColumnType('StreamBase Condition', 'CUSTOM_CONDITION', [PropertyType.BOOLEAN]);
  static EXPR_ACTION = new ColumnType('StreamBase Action', 'CUSTOM_ACTION');
  static PROPERTY = new ColumnType('Property', 'Property');

  constructor(
    public name: string,
    public value: string,
    public allowedTypes?: PropertyType[]
  ) {
    if (!allowedTypes) {
      this.allowedTypes = PropertyType.TYPES;
    }
    ColumnType.TYPES.push(this);

    if (name === 'Condition' || name === 'Action') {
      ColumnType.BE_TYPES.push(this);
    }
  }

  static fromValue(value: string): ColumnType {
    switch (value) {
      case 'CONDITION':
        return ColumnType.CONDITION;
      case 'ACTION':
        return ColumnType.ACTION;
      case 'CUSTOM_CONDITION':
        return ColumnType.EXPR_CONDITION;
      case 'CUSTOM_ACTION':
        return ColumnType.EXPR_ACTION;
      case 'Property':
        return ColumnType.PROPERTY;
      default:
        throw 'Unrecognized column type: ' + value;
    }
  }

  isSameCategory(colType: ColumnType) {
    switch (colType) {
      case ColumnType.ACTION:
      case ColumnType.EXPR_ACTION:
        return this === ColumnType.ACTION || this === ColumnType.EXPR_ACTION;
      case ColumnType.CONDITION:
      case ColumnType.EXPR_CONDITION:
        return this === ColumnType.CONDITION || this === ColumnType.EXPR_CONDITION;
      case ColumnType.PROPERTY:
        return this === ColumnType.PROPERTY;
    }
  }
}

export class Column {

  get propertyPath() {
    return this.name;
  }

  constructor(
    public id: string,
    public name: string,
    public propertyType: PropertyType,
    public columnType: ColumnType,
    public property?: string,
    public isArrayProperty?: boolean,
    public isSubstitution?: boolean,
    public associatedDM?: boolean,
    public alias?: string,
    public defaultCellText?: string
  ) { }
  /**
   * Factory method to create a Column. type is string representation,
   * it can only be following values:
   * CONDITION,
   * ACTION,
   * EXPR_CONDITION,
   * EXPR_ACTION
   */
  static createColumn(id: string,
    name: string,
    propertyType: string,
    columnType: string,
    property?: string,
    isArrayProperty?: boolean,
    isSubstitution?: boolean,
    associatedDM?: boolean,
    alias?: string,
    defaultCellText?: string

  ) {
    const ct: ColumnType = ColumnType.fromValue(columnType);
    const pt: PropertyType = PropertyType.fromValue(propertyType);
    return new Column(id, name, pt, ct, property, isArrayProperty, isSubstitution, associatedDM, alias, defaultCellText);
  }

  getId(): string {
    return this.id;
  }

  toXml(indent: number) {
    const idAttr = `id="${this.id}" `;
    const nameAttr = `name="${this.name}" `;
    const propertyPathAttr = `propertyPath="${this.propertyPath}" `;
    const propertyType = this.propertyType !== PropertyType.STRING ? `propertyType="${this.propertyType.value}" ` : '';
    const columnType = `columnType="${this.columnType.value}"`;
    return `${' '.repeat(indent)}<column ${idAttr}${nameAttr}${propertyPathAttr}${propertyType}${columnType}/>`;
  }

}
