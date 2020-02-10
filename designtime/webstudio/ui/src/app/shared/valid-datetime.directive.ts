import { Directive, Input } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';

import * as moment from 'moment';

@Directive({
  selector: '[validDatetime][ngModel]',
  providers: [{ provide: NG_VALIDATORS, useExisting: ValidDateTimeDirective, multi: true }]
})
export class ValidDateTimeDirective implements Validator {
  @Input()
  future: boolean;

  @Input()
  min: Date;

  @Input()
  max: Date;

  validate(c: AbstractControl): { [key: string]: any } {
    // self value
    const v: string = c.value;
    if (v) {
      const m = moment(v, moment.ISO_8601, true);
      if (m.isValid()) {
        if (this.future && m.isBefore(new Date())) {
          return {
            invalidDateTime: `Invalid date/time, it shall be sometime in the future.`
          };
        } else if (this.min && m.isBefore(this.min)) {
          return {
            invalidDateTime: `Invalid date/time, it shall be sometime after ${moment(this.min).format()}.`
          };
        } else if (this.max && m.isAfter(this.max)) {
          return {
            invalidDateTime: `Invalid date/time, it shall be sometime before ${moment(this.max).format()}.`
          };
        }
        return null;
      } else {
        return {
          invalidDateTime: 'Invalid date/time, expecting ISO-8061 or YYYY-MM-DD hh:mm:ss-hh:mm'
        };
      }
    }
    return null;
  }
}
