export interface AuditTrail {
    artifactPath: string;
    artifactType: string;
    userName: string;
    projectName: string;
    actionType: string;
    actionTime: number;
    comment: string;
}
