export interface ArtifactDetails {
    artifactPath: string;
    artifactType: string;
    artifactContent: ArtifactContent;
}

export interface ArtifactContent {
    description: string;
}
