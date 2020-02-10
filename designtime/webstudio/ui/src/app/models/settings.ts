export interface Settings {
  showDiff: boolean;
  onlyDisplayCheckedOutArtifacts: boolean;
  autoArtifactValidation: boolean;
  showEmptyFolders: boolean;
  displayWorklistNotifications: boolean;
  hideInExplorer: { [projectId: string]: boolean };
}
