export class Arguments {
    argument: Argument[];

    constructor(argument: Argument[]) {
        this.argument = argument;
    }
}

export class Argument {
    argumentAlias: string;
    direction: string;
    isArray: string;
    path: string;
    resourceType: string;

    constructor(argumentAlias: string, direction: string, isArray: string, path: string, resourceType: string) {
        this.argumentAlias = argumentAlias;
        this.direction = direction;
        this.isArray = isArray;
        this.path = path;
        this.resourceType = resourceType;

    }
}
