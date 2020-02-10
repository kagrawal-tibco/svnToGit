export class DecisionTableColumns {
    column: Column[];

    constructor(column: Column[]) {
        this.column = column;
    }
}
export class ExceptionTableColumns {
    column: Column[];

    constructor(column: Column[]) {
        this.column = column;
    }
}

export class Column {
    associatedDM: boolean;
    columnAlias: string;
    columnId: string;
    columnType: string;
    isArrayProperty: boolean;
    isSubstitution: boolean;
    name: string;
    property: string;
    propertyType: string;
    defaultCellText: string;

    constructor(
        associatedDM: boolean,
        columnAlias: string,
        columnId: string,
        columnType: string,
        isArrayProperty: boolean,
        isSubstitution: boolean,
        name: string,
        property: string,
        propertyType: string,
        defaultCellText: string) {

        this.associatedDM = associatedDM;
        this.columnAlias = columnAlias;
        this.columnId = columnId;
        this.columnType = columnType;
        this.isArrayProperty = isArrayProperty;
        this.isSubstitution = isSubstitution;
        this.name = name;
        this.property = property;
        this.propertyType = propertyType;
        this.defaultCellText = defaultCellText;
    }
}
