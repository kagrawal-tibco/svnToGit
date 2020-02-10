import { Directive, Input } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';

import { environment } from '../../environments/environment';
@Directive({
  selector: '[noDuplicate][formControlName],[noDuplicate][formControl],[noDuplicate][ngModel]',
  providers: [{ provide: NG_VALIDATORS, useExisting: NoDuplicateDirective, multi: true }]
})
export class NoDuplicateDirective implements Validator {
  @Input('noDuplicate')
  public noDuplicate: string[];

  constructor(
  ) {
  }

  validate(c: AbstractControl): { [key: string]: any } {
    // self value
    const v = c.value;
    // existing values
    // TODO : there is a limitation here that it doesn't check full path, so /DTs/myDT is considered a dup of /DTs/other/myDT
    if (this.noDuplicate) {
      let dup;
      if (environment.enableBEUI && v) {
        dup = this.noDuplicate.find(existed => existed.toUpperCase() === v.toUpperCase());
      } else {
        dup = this.noDuplicate.find(existed => existed === v);
      }
      if (dup) {
        return {
          'duplicate': true
        };
      }
    }
    return null;
  }
}
