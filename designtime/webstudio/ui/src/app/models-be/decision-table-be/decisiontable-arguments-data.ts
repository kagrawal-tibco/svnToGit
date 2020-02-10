export class ArgumentsData {
    argument: Argument[];

    constructor(argument: Argument[]) {
        this.argument = argument;
    }
}

export class Argument {
    displayValue: string;
    value: string;
    isExpanded: boolean;
    direction: string;
    path: string;
    argumentAlias: string;
    resourceType: string;
    isArray: boolean;
    property?: Property[];

    constructor(displayValue: string,
        value: string,
        isExpanded: boolean,
        direction: string,
        path: string,
        argumentAlias: string,
        resourceType: string,
        isArray: boolean,
        property?: Property[]) {

        this.displayValue = displayValue;
        this.value = value;
        this.isExpanded = isExpanded;
        this.direction = direction;
        this.path = path;
        this.argumentAlias = argumentAlias;
        this.resourceType = resourceType;
        this.isArray = isArray;
        this.property = property;
    }
}

export class Property {
    displayValue: string;
    value: string;
    isExpanded: boolean;
    name: string;
    type: string;
    ownerPath: string;
    isArray: boolean;
    associatedDomain: boolean;
    conceptTypePath?: string;

    constructor(displayValue: string,
        value: string,
        isExpanded: boolean,
        name: string,
        type: string,
        ownerPath: string,
        isArray: boolean,
        associatedDomain: boolean,
        conceptTypePath?: string) {
        this.displayValue = displayValue;
        this.value = value;
        this.isExpanded = isExpanded;
        this.name = name;
        this.type = type;
        this.ownerPath = ownerPath;
        this.isArray = isArray;
        this.associatedDomain = associatedDomain;
        if (conceptTypePath) {
            this.conceptTypePath = conceptTypePath;
        }

    }
}
