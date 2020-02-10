import { CommitCandidate } from '../models/commit-candidate';

export interface BECommitCandidate extends CommitCandidate {
    artifactType: string;
    artifactFileExtn: string;
    applicableStages: any;
    reviewStatus: string;
    applicableEnvironments: string;
    deployEnvironments: string;
    deployComments: string;
    reviewComments: string;
    revisionId: string;
    lastDeployTime: number;
    reviewerName: string;
    deployerName: string;
}
