// Generated using typescript-generator version 1.10.220 on 2018-08-20 16:28:01.

export interface Ambiguity extends Result {
}

export interface Duplication extends Result {
}

export interface Error extends Result {
}

export interface ExprError extends Error {
}

export interface ExprWarning extends Result {
}

export interface Overlap extends Result {
}

export interface PropertyError extends Error {
}

export interface Result {
    message: string;
    locations: Pair<number, string>[];
    error: boolean;
}

export interface Triviality extends Result {
}

export interface AddArtifactRequest extends Cloneable, CopyTo2, ToString2 {
    artifact: ArtifactItem;
}

export interface AddRoleRequest extends Cloneable, CopyTo2, ToString2 {
    role: RoleItem;
}

export interface AddUserRequest extends Cloneable, CopyTo2, ToString2 {
    user: UserItem;
}

export interface ArtifactConflictResolutionItem extends Cloneable, CopyTo2, ToString2 {
    path: string;
    content: string;
}

export interface ArtifactGroupUpdateItem extends Cloneable, CopyTo2, ToString2 {
    action: AddOrRemoveItem;
    artifact: string;
}

export interface ArtifactItem extends Cloneable, CopyTo2, ToString2 {
    path: string;
    description: string;
    type: string;
    content: string;
    encoding: string;
    metadata: string;
}

export interface BEArtifactItem extends ArtifactItem, Cloneable, CopyTo2, ToString2 {
    artifactPath: string;
    artifactType: string;
    fileExtension: string;
    baseArtifactPath: string;
    artifactContent: string;
    lockRequestor: string;
    changeType: string;
    lockRequestorId: string;
}

export interface BECheckoutArtifactsRequest extends CheckoutArtifactsRequest, Cloneable, CopyTo2, ToString2 {
    name: string;
    artifactItem: BEArtifactItem[];
}

export interface CheckoutArtifactsRequest extends Cloneable, CopyTo2, ToString2 {
    projectId: string;
    artifactIds: string[];
    ignoreAlreadyCheckedOutArtifacts: boolean;
}

export interface CommitRequest extends Cloneable, CopyTo2, ToString2 {
    checkedOutArtifactIds: string[];
    commitMessage: string;
}

export interface CreateDeploymentDescriptorRequest extends Cloneable, CopyTo2, ToString2 {
    deploymentDescriptor: DeploymentDescriptorItem;
}

export interface CreateDeploymentTargetRequest extends Cloneable, CopyTo2, ToString2 {
    deploymentTarget: DeploymentTargetItem;
}

export interface CreateGroupRequest extends Cloneable, CopyTo2, ToString2 {
    name: string;
    type: string;
    artifacts: string[];
}

export interface Data extends Cloneable, CopyTo2, ToString2 {
    data: any;
}

export interface DeploymentDescriptorItem extends Cloneable, CopyTo2, ToString2 {
    type: string;
    description: string;
    streamBaseTarget: string;
    decisionTableOperatorName: string;
    amsProjectName: string;
    amsDecisionTablePath: string;
    amsDecisionTableVersion: number;
}

export interface DeploymentTargetItem extends Cloneable, CopyTo2, ToString2 {
    type: string;
    streamBaseTarget: string;
    decisionTableOperatorName: string;
}

export interface ImportProjectRequest extends Cloneable, CopyTo2, ToString2 {
    comment: string;
    description: string;
    artifacts: ArtifactItem[];
    roleItem: RoleItem[];
}

export interface ImportScmProjectRequest extends Cloneable, CopyTo2, ToString2 {
    comment: string;
    description: string;
    projectPath: string;
    filePaths: string[];
}

export interface PublishArtifactRequest extends Cloneable, CopyTo2, ToString2 {
    commitMessage: string;
    artifact: ArtifactItem;
}

export interface Request extends Cloneable, CopyTo2, ToString2 {
    data: Data;
}

export interface ResolveScmConflictsRequest extends Cloneable, CopyTo2, ToString2 {
    artifacts: ArtifactConflictResolutionItem[];
}

export interface RoleItem extends Cloneable, CopyTo2, ToString2 {
    updatedUsers: RoleUserUpdateItem[];
    updatedPermissions: RolePermissionUpdateItem[];
}

export interface RolePermissionUpdateItem extends Cloneable, CopyTo2, ToString2 {
    action: AddOrRemoveItem;
    permission: string;
}

export interface RoleUserUpdateItem extends Cloneable, CopyTo2, ToString2 {
    action: AddOrRemoveItem;
    user: string;
}

export interface SignUpRequest extends Cloneable, CopyTo2, ToString2 {
    username: string;
    password: string;
    bindingId: string;
}

export interface SynchronizeArtifactRequest extends Cloneable, CopyTo2, ToString2 {
    artifactId: string;
    revision: number;
    strategy: string;
    content: string;
}

export interface SynchronizeRequest extends Cloneable, CopyTo2, ToString2 {
    projectName: string;
    artifactIds: string[];
    discardLocalChanges: boolean;
}

export interface UncheckoutArtifactsRequest extends Cloneable, CopyTo2, ToString2 {
    checkedOutArtifactIds: string[];
}

export interface UpdateArtifactRequest extends Cloneable, CopyTo2, ToString2 {
    artifact: ArtifactItem;
    performValidation: boolean;
}

export interface UpdateDeploymentDescriptorRequest extends Cloneable, CopyTo2, ToString2 {
    deploymentDescriptor: DeploymentDescriptorItem;
}

export interface UpdateDeploymentTargetRequest extends Cloneable, CopyTo2, ToString2 {
    deploymentTarget: DeploymentTargetItem;
}

export interface UpdateGroupRequest extends Cloneable, CopyTo2, ToString2 {
    name: string;
    type: string;
    artifactUpdates: ArtifactGroupUpdateItem[];
}

export interface UpdateRoleRequest extends Cloneable, CopyTo2, ToString2 {
    role: RoleItem;
}

export interface UpdateUISettingsRequest extends Cloneable, CopyTo2, ToString2 {
    content: string;
}

export interface UpdateUserRequest extends Cloneable, CopyTo2, ToString2 {
    user: UserItem;
}

export interface UserItem extends Cloneable, CopyTo2, ToString2 {
    password: string;
    email: string;
    roles: string[];
    enabled: boolean;
}

export interface ValidateArtifactRequest extends Cloneable, CopyTo2, ToString2 {
    artifactId: string;
    isCheckedOut: boolean;
}

export interface VerifyConnectRequest extends Cloneable, CopyTo2, ToString2 {
    artifactId: string;
    webSocketId: string;
    isCheckedOutArtifact: boolean;
    showSimpleResult: boolean;
    showJSONResult: boolean;
}

export interface VerifyExecuteRequest extends Cloneable, CopyTo2, ToString2 {
    recordId: string;
    command: string;
}

export interface ArtifactDetail extends Cloneable, CopyTo2, ToString2 {
    artifactPath: string;
    artifactType: string;
    artifactContent: any;
}

export interface ArtifactHistoryRecord extends Record, Cloneable, CopyTo2, ToString2 {
    revisionNumber: number;
    commitMessage: string;
    userName: string;
    commitId: string;
}

export interface ArtifactRecord extends Record, Cloneable, CopyTo2, ToString2 {
    type: string;
    revisionId: string;
    revisionNumber: number;
    commitMessage: string;
    path: string;
    description: string;
    changeType: string;
    imageSrc: string;
    locked: boolean;
    content: string;
    encoding: string;
    metadata: string;
    status: string;
    projectId: string;
    analyzeResult: string[];
}

export interface ArtifactValidationDetail extends ArtifactDetail, Cloneable, CopyTo2, ToString2 {
    projectName: string;
    problem: Problem[];
}

export interface ArtifactValidationRecord extends Record, Cloneable, CopyTo2, ToString2 {
    analyzeResult: string[];
}

export interface ArtifactVersionsRecordDetails extends ArtifactRecord, Cloneable, CopyTo2, ToString2 {
    artifactPath: string;
    artifactType: string;
    currentVersionContents: any;
    previousVersionContents: any;
    serverContents: any;
}

export interface AuditTrailRecord extends Record, Cloneable, CopyTo2, ToString2 {
    projectName: string;
    userName: string;
    artifactPath: string;
    artifactType: string;
    actionType: string;
    actionTime: number;
    comment: string;
}

export interface BEArtifactHistoryRecord extends ArtifactHistoryRecord, Cloneable, CopyTo2, ToString2 {
    revisionId: string;
    checkinComments: string;
    checkinTime: string;
}

export interface BEArtifactRecord extends ArtifactRecord, Cloneable, CopyTo2, ToString2 {
    no: string;
    artifactPath: string;
    artifactType: string;
    fileExtension: string;
    baseArtifactPath: string;
}

export interface BEArtifactRecordDetails extends ArtifactRecord, Cloneable, CopyTo2, ToString2 {
    artifactDetails: ArtifactDetail;
}

export interface BEBuildInfoRecord extends BuildInfoRecord, Cloneable, CopyTo2, ToString2 {
    build: string;
    buildDate: string;
}

export interface BECheckedOutArtifactRecord extends BEArtifactRecord, Cloneable, CopyTo2, ToString2 {
    stale: boolean;
    disposed: boolean;
    parentId: string;
    checkedOutFromRevision: number;
    latestRevision: number;
}

export interface BEGroupRecord extends GroupRecord, Cloneable, CopyTo2, ToString2 {
    systemGroup: string;
    fileType: string;
    artifactIds: string[];
}

export interface BELockRecord extends LockRecord, Cloneable, CopyTo2, ToString2 {
    artifactType: string;
    fileExtension: string;
    requestor: string;
    lockingEnabled: boolean;
    lockAcquired: boolean;
    lockReleased: boolean;
    lockOwner: string;
    lockOwnerDisplayId: string;
}

export interface BEWorkListRecord extends WorkListRecord, Cloneable, CopyTo2, ToString2 {
    revisionId: string;
    username: string;
    managedProjectName: string;
    checkinComments: string;
    checkinTime: string;
}

export interface BuildInfoRecord extends Record, Cloneable, CopyTo2, ToString2 {
    version: string;
    timestamp: string;
    url: string;
    name: string;
    gitCommitId: string;
}

export interface CheckPermissionsResponseRecord extends Record, Cloneable, CopyTo2, ToString2 {
    type: string;
    action: string;
    resource: string;
    isPermitted: boolean;
}

export interface CheckedOutArtifactRecord extends ArtifactRecord, Cloneable, CopyTo2, ToString2 {
    stale: boolean;
    disposed: boolean;
    parentId: string;
    checkedOutFromRevision: number;
    latestRevision: number;
}

export interface CheckoutRecord extends Record, Cloneable, CopyTo2, ToString2 {
    tag: string;
    projectId: string;
    projectName: string;
    artifacts: CheckedOutArtifactRecord[];
}

export interface CommitCandidateRecord extends Record, Cloneable, CopyTo2, ToString2 {
    parentId: string;
    parentRevision: number;
    committedFrom: string;
    status: string;
    artifactType: string;
    content: string;
    metadata: string;
    path: string;
}

export interface CommitRecord extends Record, Cloneable, CopyTo2, ToString2 {
    projectName: string;
    message: string;
    version: number;
    status: string;
    commitTime: number;
    resolveTime: number;
    committerName: string;
    committerId: string;
    resolverName: string;
    resolverId: string;
    resolutionComment: string;
    changeList: CommitCandidateRecord[];
}

export interface DeploymentDescriptorRecord extends Record, Cloneable, CopyTo2, ToString2 {
    type: string;
    description: string;
    streamBaseTarget: string;
    decisionTableOperatorName: string;
    amsProjectName: string;
    amsDecisionTablePath: string;
    amsDecisionTableVersion: number;
    createTime: number;
    lastUpdateTime: number;
}

export interface DeploymentHistoryRecord extends Record, Cloneable, CopyTo2, ToString2 {
    user: string;
    action: string;
    artifactId: string;
    artifactVersion: number;
    deployStatus: string;
    deployMessage: string;
    deploymentDescriptor: DeploymentDescriptorRecord;
    oldDeploymentDescriptor: DeploymentDescriptorRecord;
    createTime: number;
    lastUpdateTime: number;
    deployTime: number;
}

export interface DeploymentTargetRecord extends Record, Cloneable, CopyTo2, ToString2 {
    streamBaseTarget: string;
    decisionTableOperatorName: string;
}

export interface GroupRecord extends Record, Cloneable, CopyTo2, ToString2 {
    name: string;
    type: string;
    shared: boolean;
    artifacts: string[];
}

export interface IdentityProviderRecord extends Record, Cloneable, CopyTo2, ToString2 {
    name: string;
}

export interface LockRecord extends ArtifactRecord, Cloneable, CopyTo2, ToString2 {
    user: string;
    projectName: string;
    artifactPath: string;
    artifactId: string;
    tag: string;
}

export interface LoginRecord extends Record, Cloneable, CopyTo2, ToString2 {
    apiToken: string;
    userInfo: UserRecord;
}

export interface LoginSessionRecord extends Record, Cloneable, CopyTo2, ToString2 {
    userName: string;
    token: string;
    ttl: string;
    expires: string;
    readonly: boolean;
}

export interface OpenIDConnectIdentityRecord extends Record, Cloneable, CopyTo2, ToString2 {
    user: string;
    issuer: string;
    providerName: string;
    identity: string;
    picture: string;
    subject: string;
}

export interface Problem extends Cloneable, CopyTo2, ToString2 {
    errorMessage: string;
    location: number;
    problemType: number;
    columnName: number;
    pageNum: number;
    columnUIName: string;
    errorCode: number;
    severity: number;
    isWarning: boolean;
}

export interface ProjectRecord extends Record, Cloneable, CopyTo2, ToString2 {
    projectName: string;
    projectType: string;
}

export interface Response extends Cloneable, CopyTo2, ToString2 {
    status: number;
    startRow: number;
    endRow: number;
    totalRows: number;
    errorCode: string;
    errorMessage: string;
    responseMessage: string;
    record: Record[];
}

export interface RoleRecord extends Record, Cloneable, CopyTo2, ToString2 {
    name: string;
    users: string[];
    permissions: string[];
}

export interface ScmConflictRecord extends Record, Cloneable, CopyTo2, ToString2 {
    path: string;
    content: string;
}

export interface ScmRepositoryDirectoryEntryRecord extends Record, Cloneable, CopyTo2, ToString2 {
    path: string;
    isDirectory: boolean;
}

export interface ScmRepositoryRecord extends Record, Cloneable, CopyTo2, ToString2 {
    name: string;
    isEnabled: boolean;
    type: string;
    url: string;
    branch: string;
}

export interface ServerConfigurationRecord extends Record, Cloneable, CopyTo2, ToString2 {
    allowUserManagement: boolean;
    allowPasswordChange: boolean;
}

export interface UISettingsRecord extends Record, Cloneable, CopyTo2, ToString2 {
    content: string;
}

export interface UserRecord extends Record, Cloneable, CopyTo2, ToString2 {
    username: string;
    email: string;
    roles: string[];
    enabled: boolean;
    deleted: boolean;
}

export interface ValidateTokenRecord extends Record, Cloneable, CopyTo2, ToString2 {
    authToken: string;
    userInfo: UserRecord;
    readOnly: boolean;
    ttl: number;
    lastRefreshed: number;
}

export interface ValidationProblemRecord extends Record, Cloneable, CopyTo2, ToString2 {
    allowUserManagement: boolean;
    allowPasswordChange: boolean;
    artifactDetails: ArtifactValidationDetail;
}

export interface VerifyResponseRecord extends Record, Cloneable, CopyTo2, ToString2 {
    verifyRecordId: string;
    responseType: string;
    responseMessage: string;
}

export interface WorkListItemRecord extends CommitCandidateRecord, Cloneable, CopyTo2, ToString2 {
    revisionId: string;
    artifactPath: string;
    commitOperation: string;
    artifactFileExtn: string;
    applicableStages: string;
    reviewStatus: string;
    applicableEnvironments: string;
    deployEnvironments: string;
    reviewComments: string;
    deployComments: string;
    lastDeployTime: number;
    deployerName: string;
    reviewerName: string;
}

export interface WorkListRecord extends Record, Cloneable, CopyTo2, ToString2 {
    workListStatus: string;
    commit: CommitRecord;
}

export interface Pair<L, R> extends Entry<L, R>, Comparable<Pair<L, R>>, Serializable {
}

export interface Cloneable {
}

export interface CopyTo2 {
}

export interface ToString2 {
}

export interface Record extends Cloneable, CopyTo2, ToString2 {
    entityId: string;
}

export interface Serializable {
}

export interface Entry<K, V> {
}

export interface Comparable<T> {
}

export type AddOrRemoveItem = 'ADD' | 'REMOVE';
