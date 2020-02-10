import { RangeDomainEntry, SingleDomainEntry } from './domain-entry';
export class DomainModelContent {

    get totalEntries(): Array<any> {
        this.sortEntries();
        return this.entries;
    }
    static content: DomainModelContent;
    name: string;
    folder: string;
    description: string;
    ownerProjectName: string;
    dataType: string;
    superDomainPath: string;
    singleEntry?: SingleDomainEntry[];
    rangeEntry?: RangeDomainEntry[];
    entries: Array<any> = new Array<any>();

    constructor(name: string, folder: string, description: string, ownerProjectName: string, dataType: string, superDomainPath: string, entries: Array<any>, singleEntry?: SingleDomainEntry[], rangeEntry?: RangeDomainEntry[]) {
        this.name = name;
        this.folder = folder;
        this.description = description;
        this.ownerProjectName = ownerProjectName;
        this.dataType = dataType;
        this.superDomainPath = superDomainPath;
        this.singleEntry = singleEntry;
        this.rangeEntry = rangeEntry;
        this.entries = entries;
    }

    static fromXml(dmContent: string): DomainModelContent {
        if (dmContent === '') {
            dmContent = '{}';
        }

        const jsonObject = JSON.parse(dmContent);
        const name = jsonObject.name;
        const folder = jsonObject.folder;
        const description = jsonObject.description;
        const ownerProjectName = jsonObject.ownerProjectName;
        const dataType = jsonObject.dataType;
        const superDomainPath = jsonObject.superDomainPath ? jsonObject.superDomainPath : '';
        let singleEntry: Array<SingleDomainEntry> = new Array<SingleDomainEntry>();
        let rangeEntry: Array<RangeDomainEntry> = new Array<RangeDomainEntry>();
        const entries: Array<any> = new Array<any>();

        if (jsonObject.singleEntry) {
            for (const sEntry of jsonObject.singleEntry) {
                const value = sEntry.value;
                const description = sEntry.description;
                let entryID = 1;
                if (entries.length > 0) {
                    const entry = entries[entries.length - 1];
                    entryID = entry.id + 1;
                }
                const sentry = new SingleDomainEntry(false, entryID, value, description);
                singleEntry.push(sentry);
                entries.push(sentry);
            }
        } else {
            singleEntry = undefined;
        }

        if (jsonObject.rangeEntry) {
            for (const rEntry of jsonObject.rangeEntry) {
                const lowervalue = rEntry.lower;
                const uppervalue = rEntry.upper;
                const description = rEntry.description;
                let entryID = 1;
                if (entries.length > 0) {
                    const entry = entries[entries.length - 1];
                    entryID = entry.id + 1;
                }
                const rentry = new RangeDomainEntry(false, entryID, lowervalue, uppervalue, description, rEntry.lowerInclusive ? rEntry.lowerInclusive : false, rEntry.upperInclusive ? rEntry.upperInclusive : false);
                rangeEntry.push(rentry);
                entries.push(rentry);

            }
        } else {
            rangeEntry = undefined;
        }

        const dmFinal = new DomainModelContent(name, folder, description, ownerProjectName, dataType, superDomainPath, entries, singleEntry, rangeEntry);
        this.content = dmFinal;
        // console.log(JSON.stringify(dmFinal));

        return dmFinal;
    }

    static getDomainContent(): DomainModelContent {
        return this.content;
    }

    toXml(): string {
        const tempList = this.entries;
        this.setLists();
        this.entries = undefined;

        if (this.superDomainPath === '') {
            this.superDomainPath = undefined;
         }

        const stringDM = JSON.stringify(this);
        // console.log(stringDM);
        this.entries = tempList;
        return stringDM;
    }

    seralize() {
        return this.toXml();
    }

    public getList(): any[] {
        return this.entries;
    }

    public setLists() {
        if (this.singleEntry) {
            this.singleEntry.splice(0, this.singleEntry.length);
        }
        if (this.rangeEntry) {
            this.rangeEntry.splice(0, this.rangeEntry.length);
        }
        for (const test of this.entries) {
            // test.id = undefined;
            // test.selected = undefined;
            if (test instanceof SingleDomainEntry) {
                if (this.singleEntry) {
                    this.singleEntry.push(test);
                } else {
                    this.singleEntry = new Array<SingleDomainEntry>();
                    this.singleEntry.push(test);
                }

            } else if (test instanceof RangeDomainEntry) {
                if (this.rangeEntry) {
                    this.rangeEntry.push(test);
                } else {
                    this.rangeEntry = new Array<RangeDomainEntry>();
                    this.rangeEntry.push(test);
                }

            }
        }

    }

    public sortEntries() {
        this.entries.sort((leftSide, rightSide): number => {
            if (leftSide.id > rightSide.id) { return 1; }
            if (leftSide.id < rightSide.id) { return -1; }
            return 0;
        });
    }
}
