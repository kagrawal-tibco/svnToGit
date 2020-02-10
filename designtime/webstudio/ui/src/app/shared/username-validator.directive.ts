import { Attribute, Directive } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator } from '@angular/forms';

import { ManagementService } from '../management/management.service';
import { UserRecord } from '../models/dto';
@Directive({
    selector: '[validateUsername]',
    providers: [
        { provide: NG_VALIDATORS, useExisting: UsernameValidator, multi: true }
    ]
})
export class UsernameValidator implements Validator {
    users: UserRecord[];

    constructor(
        @Attribute('validateUsername') public validateUsername: string,
        private managementService: ManagementService
    ) {
        managementService.getAllUsers().then(
            (users) => {
                this.users = users;
            }
        );
    }

    validate(c: AbstractControl): { [key: string]: any } {
        // self value (e.g. retype password)
        const v = this.users;
        // control value (e.g. password)
        const e = c.root.get(this.validateUsername).value;
        // username already taken
        if (e && !!v.find(user => user.username === e)) {
            return { validateUsername: false };
        }
        return null;
    }
}
