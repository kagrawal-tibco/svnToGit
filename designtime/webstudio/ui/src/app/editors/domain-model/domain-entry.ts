export class SingleDomainEntry {
    id: number;
    selected: boolean;
    value: string;
    description: string;

    constructor(selected: boolean, id: number, value: string, description: string) {
        this.id = id;
        this.selected = selected;
        this.value = value;
        this.description = description;
    }
}

export class RangeDomainEntry {
    id: number;
    selected: boolean;
    lower: string;
    upper: string;
    description: string;
    lowerInclusive: boolean;
    upperInclusive: boolean;

    constructor(selected: boolean, id: number, lower: string, upper: string, description: string, lowerInclusive: boolean, upperInclusive: boolean) {
        this.id = id;
        this.selected = selected;
        this.lower = lower;
        this.upper = upper;
        this.description = description;
        this.lowerInclusive = lowerInclusive;
        this.upperInclusive = upperInclusive;
    }

}
