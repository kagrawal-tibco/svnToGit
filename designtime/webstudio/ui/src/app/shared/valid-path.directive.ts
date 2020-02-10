import { Directive, Input } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';
import { I18n } from '@ngx-translate/i18n-polyfill';
@Directive({
  selector: '[validPath][ngModel]',
  providers: [{ provide: NG_VALIDATORS, useExisting: ValidPathDirective, multi: true }]
})
export class ValidPathDirective implements Validator {

  private static FULL = new RegExp('^([a-zA-Z0-9_\.-]*\/)*([a-zA-Z0-9_\.-]*)$');
  private static FULL_ALLOW_EXTENSION = /^([a-zA-Z0-9_\.-]*\/)*([a-zA-Z0-9_\.-]*)([a-zA-Z0-9\.]*)$/;
  private static FULL_BE = new RegExp('^([a-zA-Z]+[a-zA-Z0-9_]*\/)*([a-zA-Z]+[a-zA-Z0-9_]*)$');
  private static FULL_ALLOW_EXTENSION_BE = /^([a-zA-Z]+[a-zA-Z0-9_]*\/)*([a-zA-Z]+[a-zA-Z0-9_]*)(\.[a-zA-Z0-9]+)$/;
  private static FULL_REQUIRE_EXTENSION = /^([a-zA-Z0-9_\.-]*\/)*([a-zA-Z0-9_-]*)(\.([a-zA-Z0-9])+)+$/;
  private static SIMPLE = new RegExp('^([a-zA-Z0-9]+[a-zA-Z0-9_-]*)$');
  private static IP = new RegExp(
    `^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$`
  );
  // RFC-1123
  private static HOST_NAME = new RegExp(
    `^([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])(\.([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]{0,61}[a-zA-Z0-9]))*$`
  );

  private static PORT = new RegExp('^[0-9]+$');

  @Input('validPath')
  validPath: 'FULL_PATH' | 'NAME' | 'HOST' | 'FULL_PATH_ALLOW_EXTENSION' | 'FULL_PATH_REQUIRE_EXTENSION' | 'FULL_PATH_ALLOW_EXTENSION_BE';

  constructor(public i18n: I18n) { }

  validate(c: AbstractControl): { [key: string]: any } {
    // self value
    const v: string = c.value;
    return this._validate(v);
  }

  public _validate(v: string): { [key: string]: any } {
    if (v) {
      let matched: boolean;
      let explanation: string;
      if (this.validPath === 'NAME') {
        matched = ValidPathDirective.SIMPLE.test(v);
        explanation = this.i18n('Only alphabetic characters, digits, -, and _ are allowed.');
      } else if (this.validPath === 'HOST') {
        const parts = v.split(':');
        if (parts.length === 2) {
          const hostValid = ValidPathDirective.IP.test(parts[0]) || ValidPathDirective.HOST_NAME.test(parts[0]);
          const portValid = ValidPathDirective.PORT.test(parts[1]);
          matched = hostValid && portValid;
        } else {
          matched = false;
        }
        explanation = this.i18n('Invalid hostname or port number.');
      } else if (this.validPath === 'FULL_PATH_ALLOW_EXTENSION') {
        matched = ValidPathDirective.FULL_ALLOW_EXTENSION.test(v) || ValidPathDirective.FULL.test(v);
        explanation = this.i18n('Only alphabetic characters, digits, -, and _ are allowed. \
          Only / can be used as separator and only alphabetic characters and digits \
          can be used as leading characters. A suffix starting with "." is optional');
      } else if (this.validPath === 'FULL_PATH_ALLOW_EXTENSION_BE') {
        matched = (ValidPathDirective.FULL_ALLOW_EXTENSION_BE.test(v) || ValidPathDirective.FULL_BE.test(v)) && v.length <= 64;
        explanation = this.i18n('Only alphabetic characters, digits and _ are allowed. \
          Only / can be used as separator and only alphabetic characters can be \
          used as leading characters. Length limit is 64 characters');
      } else if (this.validPath === 'FULL_PATH_REQUIRE_EXTENSION') {
        matched = ValidPathDirective.FULL_REQUIRE_EXTENSION.test(v);
        explanation = this.i18n('Only alphabetic characters, digits, -, and _ are allowed. \
          Only / can be used as separator and only alphabetic characters and digits \
          can be used as leading characters. An extension suffix starting with "." is required');
      } else {
        matched = ValidPathDirective.FULL.test(v);
        explanation = this.i18n('Only alphabetic characters, digits, -, and _ are allowed. \
          Only / can be used as separator and only alphabetic characters and digits \
          can be used as leading characters.');
      }
      if (matched) {
        return null;
      } else {
        return {
          invalidPath: explanation
        };
      }
    }
    return null;
  }

}
