export class Metadata {
    property: Property[];

    constructor(property: Property[]) {
        this.property = property;
    }
}

export class Property {
    name: string;
    type: string;
    value: string;

    constructor(name: string, type: string, value: string) {
        this.name = name;
        this.type = type;
        this.value = value;
    }
}
