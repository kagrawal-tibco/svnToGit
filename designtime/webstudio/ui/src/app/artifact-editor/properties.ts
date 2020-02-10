export class Properties {

  private _lastmodifiedG: string;
  private _versionG: string;
  private _implementsG: string;
  private _ruleID: string;
  private _rulePriority: string;
  private _ruleComments: string;
  private _cellEnabled: boolean;
  private _cellComments: string;
  private _selected: string = null;
  private _isDT = false;
  private _rtiName: string;
  private _rtiPriority: number;
  private _rtiDescription: string;
  private _isRTI = false;

  constructor() {
  }

  set lastmodifiedG(value: string) {
    this._lastmodifiedG = value;
  }

  get lastmodifiedG(): string {
    return this._lastmodifiedG;
  }

  set versionG(value: string) {
    this._versionG = value;
  }

  get versionG(): string {
    return this._versionG;
  }

  set implementsG(value: string) {
    this._implementsG = value;
  }

  get implementsG(): string {
    return this._implementsG;
  }

  set ruleID(value: string) {
    this._ruleID = value;
  }

  get ruleID(): string {
    return this._ruleID;
  }

  set rulePriority(value: string) {
    this._rulePriority = value;
  }

  get rulePriority(): string {
    return this._rulePriority;
  }

  set ruleComments(value: string) {
    this._ruleComments = value;
  }

  get ruleComments(): string {
    return this._ruleComments;
  }

  set cellEnabled(value: boolean) {
    this._cellEnabled = value;
  }

  get cellEnabled(): boolean {
    return this._cellEnabled;
  }

  set cellComments(value: string) {
    this._cellComments = value;
  }

  get cellComments(): string {
    return this._cellComments;
  }

  set selected(value: string) {
    this._selected = value;
  }

  get selected(): string {
    return this._selected;
  }

  set isDT(value: boolean) {
    this._isDT = value;
  }

  get isDT(): boolean {
    return this._isDT;
  }

  set isRTI(value: boolean) {
    this._isRTI = value;
  }

  get isRTI(): boolean {
    return this._isRTI;
  }

  set rtiName(value: string) {
    this._rtiName = value;
  }

  get rtiName(): string {
    return this._rtiName;
  }

  set rtiPriority(value: number) {
    this._rtiPriority = value;
  }

  get rtiPriority(): number {
    return this._rtiPriority;
  }

  set rtiDescription(value: string) {
    this._rtiDescription = value;
  }

  get rtiDescription(): string {
    return this._rtiDescription;
  }

}
