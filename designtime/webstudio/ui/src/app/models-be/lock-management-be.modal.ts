/**
 * @Author: Rahil Khera
 * @Date:   2017-09-18T16:55:26+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-09-18T16:55:36+05:30
 */

export class BELockManagementModal {
  private _name: string;
  private _path: string;
  private _fileType: string;
  private _lockOwner: string;
  private _lockOwnerDisplayName: string;
  private _lockTime: string;
  private _locked = false;

  constructor() {
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get path(): string {
    return this._path;
  }

  set path(value: string) {
    this._path = value;
  }

  get fileType(): string {
    return this._fileType;
  }

  set fileType(value: string) {
    this._fileType = value;
  }

  get lockOwner(): string {
    return this._lockOwner;
  }

  set lockOwner(value: string) {
    this._lockOwner = value;
  }

  get lockTime(): string {
    return this._lockTime;
  }

  set lockTime(value: string) {
    this._lockTime = value;
  }

  get locked(): boolean {
    return this._locked;
  }

  set locked(value: boolean) {
    this._locked = value;
  }

  get lockOwnerDisplayName() : string {
    return this._lockOwnerDisplayName;
  }

  set lockOwnerDisplayName(value : string) {
    this._lockOwnerDisplayName = value;
  }

  public static getSaveJson(projectId: string, userName: string, artifacts: Array<BELockManagementModal>): any {
    const payload = {
      request: {
        data: {
          actionForcibly: true,
          project: [{
            name: projectId,
            artifactItem: BELockManagementModal.getArtifactToItem(userName, artifacts)
          }]
        }
      }
    };
    return payload;
  }

  private static getArtifactToItem(userName: string, artifacts: Array<BELockManagementModal>): any[] {
    const payload = [];
    for (let i = 0; i < artifacts.length; i++) {
      const artifact = artifacts[i];
      if (!artifact.locked) {
        const artifactJson = {
          artifactPath: artifact.path,
          artifactType: artifact.fileType,
          fileExtension: artifact.fileType,
          lockRequestor: userName
        };
        payload.push(artifactJson);
      }
    }
    return payload;
  }
}
