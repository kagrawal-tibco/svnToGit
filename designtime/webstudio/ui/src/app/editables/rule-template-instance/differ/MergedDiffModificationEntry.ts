import { ModificationEntry } from './ModificationEntry';
import { ModificationType } from './ModificationType';

export class MergedDiffModificationEntry extends ModificationEntry {

  private _message: string;

  private _localVersion: string;
  private _baseVersion: string;

  private _localVersionObj: Object;
  private _baseVersionObj: Object;

  private _localChangeType: ModificationType;
  private _serverChangeType: ModificationType;

  constructor(modificationType: ModificationType, localChangeType: ModificationType, serverChangeType: ModificationType) {
    super(modificationType);
    this.localChangeType = localChangeType;
    this.serverChangeType = serverChangeType;
  }

  get message(): string {
    return this._message;
  }

  set message(value: string) {
    this._message = value;
  }

  get localVersion(): string {
    return this._localVersion;
  }

  set localVersion(value: string) {
    this._localVersion = value;
  }

  get baseVersion(): string {
    return this._baseVersion;
  }

  set baseVersion(value: string) {
    this._baseVersion = value;
  }

  get localVersionObj(): Object {
    return this._localVersionObj;
  }

  set localVersionObj(value: Object) {
    this._localVersionObj = value;
  }

  get baseVersionObj(): Object {
    return this._baseVersionObj;
  }

  set baseVersionObj(value: Object) {
    this._baseVersionObj = value;
  }

  get localChangeType(): ModificationType {
    return this._localChangeType;
  }

  set localChangeType(value: ModificationType) {
    this._localChangeType = value;
  }

  get serverChangeType(): ModificationType {
    return this._serverChangeType;
  }

  set serverChangeType(value: ModificationType) {
    this._serverChangeType = value;
  }

  public isLocalChange(): boolean {
    return this.localChangeType != null || this.localChangeType === ModificationType.UNCHANGED;
  }

  public isServerChange(): boolean {
    return this.serverChangeType != null || this.serverChangeType === ModificationType.UNCHANGED;
  }

}
