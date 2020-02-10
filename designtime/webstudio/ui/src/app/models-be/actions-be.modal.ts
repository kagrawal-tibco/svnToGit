/**
 * @Author: Rahil Khera
 * @Date:   2017-08-29T16:10:41+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-29T16:10:47+05:30
 */
import { I18n } from '@ngx-translate/i18n-polyfill';
export class BEActions {

  get text(): string {
    return this._text;
  }

  set text(value: string) {
    this._text = value;
  }

  get type(): string {
    return this._type;
  }

  set type(value: string) {
    this._type = value;
  }

  get value(): string {
    return this._value;
  }

  set value(value: string) {
    this._value = value;
  }

  public static get actionText(): Map<string, string> {
    const actionTextMap = new Map<string, string>();
    actionTextMap.set('approval', BEActions.i18n('Approve'));
    actionTextMap.set('commit', BEActions.i18n('Commit'));
    actionTextMap.set('checkout', BEActions.i18n('Checkout'));
    actionTextMap.set('gen_deploy', BEActions.i18n('Generate Deploy'));
    actionTextMap.set('manage_locks', BEActions.i18n('Manage Locks'));
    actionTextMap.set('add_impl', BEActions.i18n('Add Instance'));
    actionTextMap.set('del_impl', BEActions.i18n('Delete Instance'));
    actionTextMap.set('add_inst', BEActions.i18n('Add Instance'));
    actionTextMap.set('del_inst', BEActions.i18n('Delete Instance'));
    actionTextMap.set('read', BEActions.i18n('Read'));
    return actionTextMap;
  }

  get allow(): boolean {
    return this.value === 'ALLOW' ? true : false;
  }

  set allow(allow: boolean) {
    this.value = allow ? 'ALLOW' : 'DENY';
  }
  public static i18n: I18n;
  private _type: string;
  private _value: string;
  private _text: string;
  private _allow: boolean;

  constructor(type: string, value: string, i18n: I18n) {
    this.type = type;
    this.value = value;
    BEActions.i18n = i18n;
    this.text = BEActions.actionText.get(this.type);

  }
}
