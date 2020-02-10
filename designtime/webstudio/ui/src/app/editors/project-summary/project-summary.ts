export class ProjectSummary {

    static content: ProjectSummary;
    name: string;
    description: string;
    size: string;
    artifactsCount: number;
    lastCheckoutBy: string;
    lastCheckoutTime: string;
    lastCommitBy: string;
    lastCommitTime: string;
    lastGenerateDeployableBy: string;
    lastGenerateDeployableTime: string;
    lastSyncBy: string;
    lastSyncTime: string;
    lastValidateBy: string;
    lastValidateTime: string;
    totalCommits: number;
    totalApprovals: number;
    totalDeployments: number;
    totalRejections: number;

    constructor(name: string,
        description: string,
        size: string,
        artifactsCount: number,
        lastCheckoutBy: string,
        lastCheckoutTime: string,
        lastCommitBy: string,
        lastCommitTime: string,
        lastGenerateDeployableBy: string,
        lastGenerateDeployableTime: string,
        lastSyncBy: string,
        lastSyncTime: string,
        lastValidateBy: string,
        lastValidateTime: string,
        totalCommits: number,
        totalApprovals: number,
        totalDeployments: number,
        totalRejections: number) {

        this.name = name;
        this.description = description;
        this.size = size;
        this.artifactsCount = artifactsCount;
        this.lastCheckoutBy = lastCheckoutBy;
        this.lastCheckoutTime = lastCheckoutTime;
        this.lastCommitBy = lastCommitBy;
        this.lastCommitTime = lastCommitTime;
        this.lastGenerateDeployableBy = lastGenerateDeployableBy;
        this.lastGenerateDeployableTime = lastGenerateDeployableTime;
        this.lastSyncBy = lastSyncBy;
        this.lastSyncTime = lastSyncTime;
        this.lastValidateBy = lastValidateBy;
        this.lastValidateTime = lastValidateTime;
        this.totalCommits = totalCommits;
        this.totalApprovals = totalApprovals;
        this.totalDeployments = totalDeployments;
        this.totalRejections = totalRejections;
    }

    static fromXml(dmContent: string): ProjectSummary {

        if (dmContent === '') {
            dmContent = '{}';
        }

        const jsonObject = JSON.parse(dmContent);

        const name = jsonObject.name;
        const description = jsonObject.description;
        const size = jsonObject.size;
        const artifactsCount = jsonObject.artifactsCount;
        const lastCheckoutBy = jsonObject.lastCheckoutBy;
        const lastCheckoutTime = jsonObject.lastCheckoutTime;
        const lastCommitBy = jsonObject.lastCommitBy;
        const lastCommitTime = jsonObject.lastCommitTime;
        const lastGenerateDeployableBy = jsonObject.lastGenerateDeployableBy;
        const lastGenerateDeployableTime = jsonObject.lastGenerateDeployableTime;
        const lastSyncBy = jsonObject.lastSyncBy;
        const lastSyncTime = jsonObject.lastSyncTime;
        const lastValidateBy = jsonObject.lastValidateBy;
        const lastValidateTime = jsonObject.lastValidateTime;
        const totalCommits = jsonObject.totalCommits;
        const totalApprovals = jsonObject.totalApprovals;
        const totalDeployments = jsonObject.totalDeployments;
        const totalRejections = jsonObject.totalRejections;

        const pSummary = new ProjectSummary(name, description, size, artifactsCount, lastCheckoutBy,
            lastCheckoutTime, lastCommitBy, lastCommitTime, lastGenerateDeployableBy, lastGenerateDeployableTime, lastSyncBy,
            lastSyncTime, lastValidateBy, lastValidateTime, totalCommits, totalApprovals, totalDeployments, totalRejections);
        this.content = pSummary;
        return pSummary;
    }

    static getProjectSummaryContent(): ProjectSummary {
        return this.content;
    }

    toXml(): string {
        return JSON.stringify(this);
    }

    seralize() {
        return this.toXml();
    }
}
