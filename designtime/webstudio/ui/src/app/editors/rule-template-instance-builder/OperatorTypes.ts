/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:04:28+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:04:29+05:30
 */
import { I18n } from '@ngx-translate/i18n-polyfill';
export class OperatorTypes {
  private _operator: string;
  private _operatorType?: string;
  private _available = false;
  private i18n: I18n;

  constructor(operator: string, operatorType?: string, available?: boolean) {
    this._operator = operator;
    this._operatorType = operatorType;
    if (available != null) {
      this._available = available;
    }
  }

  set operator(operator: string) {
    this._operator = operator;
  }

  get operator(): string {
    return this._operator;
  }

  set operatorType(operatorType: string) {
    this._operatorType = operatorType;
  }

  get operatorType(): string {
    return this._operatorType;
  }

  set available(available: boolean) {
    this._available = available;
  }

  get available(): boolean {
    return this._available;
  }
}
