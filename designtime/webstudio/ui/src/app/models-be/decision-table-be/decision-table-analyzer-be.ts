export class DecisionTableAnalyzer {
    projectName: string;
    artifactPath: string;
    filter: ColumnFilter[];
    artifactRecord: ArtifactDetail[];

    constructor(projectName: string, artifactPath: string, filter: ColumnFilter[], artifactRecord: ArtifactDetail[]) {
        this.projectName = projectName;
        this.artifactPath = artifactPath;
        this.filter = filter;
        this.artifactRecord = artifactRecord;
    }
}

export class ColumnFilter {
    columnName: string;
    isRangeFilter: boolean;
    minValue: string;
    maxValue: string;
    value: string[];
    sminValue: string;
    smaxValue: string;
    svalue: string[];

    constructor(columnName: string, isRangeFilter: boolean, minValue: string, maxValue: string, value: string[], sminValue: string, smaxValue: string, svalue: string[]) {
        this.columnName = columnName;
        this.isRangeFilter = isRangeFilter;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = value;
        this.sminValue = sminValue;
        this.smaxValue = smaxValue;
        this.svalue = svalue;

    }
}

export class ArtifactDetail {
    artifactPath: string;
    baseArtifactPath: string;
    fileExtension: string;
    artifactType: string;

    constructor(artifactPath: string, baseArtifactPath: string, fileExtension: string, artifactType: string) {
        this.artifactPath = artifactPath;
        this.baseArtifactPath = baseArtifactPath;
        this.fileExtension = fileExtension;
        this.artifactType = artifactType;

    }
}

export class ArtifactItem {
    artifactItem: DecisionTableAnalyzer;

    constructor(artifactItem: DecisionTableAnalyzer) {
        this.artifactItem = artifactItem;
    }
}
