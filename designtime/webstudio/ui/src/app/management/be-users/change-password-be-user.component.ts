import { Component, Inject } from '@angular/core';
import { AbstractControl, FormControl, FormGroupDirective, NgForm, Validators, ValidatorFn } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ErrorStateMatcher } from '@angular/material/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { BEUsers } from '../../models-be/users-be.modal';

export class InputErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
        const isSubmitted = form && form.submitted;
        return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
    }
}

export class CurrentBEUser {
    user: BEUsers;
    newPassword: string;
    newUsername: string;
}

@Component({
    selector: 'change-password-be-user',
    templateUrl: 'change-password-be-user.component.html',
    styleUrls: ['./change-password-be-user.component.css']
})
export class ChangePasswordBEUser {

    public errorStateMatcher = new InputErrorStateMatcher();
    public userNameFormControl = new FormControl('');
    public passwordFormControl = new FormControl('', [Validators.required, Validators.minLength(4), this.forbiddenSpacesValidator()]);
    public confirmPasswordFormControl = new FormControl('', [Validators.required, this.comparePasswords()]);
    public confirmPassword: string;

    constructor(
        public dialogRef: MatDialogRef<ChangePasswordBEUser>,
        @Inject(MAT_DIALOG_DATA) public currentUser: CurrentBEUser,
        public i18n: I18n) {
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    comparePasswords(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const forbidden = !_.isEqual(control.value, this.currentUser.newPassword);
            return forbidden ? { 'notMatchingPassword': { value: control.value } } : null;
        };
    }

    forbiddenSpacesValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            let forbidden = false;
            const input: string = control.value;
            forbidden = input && input.indexOf(' ') >= 0;
            return forbidden ? { 'forbiddenSpaces': { value: control.value } } : null;
        };
    }

    canConfirm(): boolean {
        return this.userNameFormControl.errors === null
            && this.passwordFormControl.errors === null
            && this.confirmPasswordFormControl.errors === null && this.currentUser.newPassword === this.confirmPassword;
    }

    getMessage(type: String): string {
        let msg;
        if (type === 'PassReq') {
            msg = this.i18n('Password is {{0}}required.{{1}}', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'PassforbiddenSpaces') {
            msg = this.i18n('Password cannot consists {{0}}spaces.{{1}}', { 0: `<strong>`, 1: `</strong>` });
        }
        return msg;
    }
}
