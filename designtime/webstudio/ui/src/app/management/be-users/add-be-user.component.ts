import { Component, Inject } from '@angular/core';
import { AbstractControl, FormControl, FormGroupDirective, NgForm, Validators, ValidatorFn } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ErrorStateMatcher } from '@angular/material/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { BEUsers } from '../../models-be/users-be.modal';

export class AddBEUser {
    exisitingUsers: Array<BEUsers>;
    availableRoles: Array<string>;
    newUser: BEUsers;
}

export class InputErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
        const isSubmitted = form && form.submitted;
        return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
    }
}

@Component({
    selector: 'add-be-user',
    templateUrl: 'add-be-user.component.html',
    styleUrls: ['./add-be-user.component.css']
})
export class AddBEUserComponent {

    public errorStateMatcher = new InputErrorStateMatcher();
    public userNameFormControl = new FormControl('', [Validators.required, Validators.minLength(4), this.forbiddenUserNameValidator(), this.forbiddenSpacesValidator()]);
    public passwordFormControl = new FormControl('', [Validators.required, Validators.minLength(4), this.forbiddenSpacesValidator()]);
    public confirmPasswordFormControl = new FormControl('', [Validators.required, this.comparePasswords()]);
    public rolesFormControl = new FormControl('', [Validators.required]);
    public roleString = '';
    public confirmPassword: string;

    constructor(
        public dialogRef: MatDialogRef<AddBEUserComponent>,
        @Inject(MAT_DIALOG_DATA) public data: AddBEUser,
        public i18n: I18n) {
        this.data.newUser = new BEUsers();
        this.data.newUser.newUser = true;
    }

    onNoClick(): void {
        this.dialogRef.close();
    }

    forbiddenUserNameValidator(): ValidatorFn {
        const exisitingUsers: Array<BEUsers> = this.data.exisitingUsers;
        return (control: AbstractControl): { [key: string]: any } | null => {
            let forbidden = false;
            for (let i = 0; i < exisitingUsers.length; i++) {
                if (exisitingUsers[i].userName === control.value) {
                    forbidden = true;
                    break;
                }
            }
            return forbidden ? { 'forbiddenName': { value: control.value } } : null;
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

    comparePasswords(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const forbidden = !_.isEqual(control.value, this.passwordFormControl.value);
            return forbidden ? { 'notMatchingPassword': { value: control.value } } : null;
        };
    }

    canConfirm(): boolean {
        return this.userNameFormControl.errors === null
            && this.passwordFormControl.errors === null
            && this.rolesFormControl.errors === null
            && this.data.newUser.userPassword === this.confirmPassword;
    }

    getMessage(type: String): string {
        let msg;
        if (type === 'required') {
            msg = this.i18n('Username is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'forbiddenName') {
            msg = this.i18n('Username {{0}} already exists.{{1}}', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'forbiddenSpaces') {
            msg = this.i18n('Username cannot consists {{0}} spaces {{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'RoleReq') {
            msg = this.i18n('Role is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'PassReq') {
            msg = this.i18n('Password is {{0}}required.{{1}}', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'PassforbiddenSpaces') {
            msg = this.i18n('Password cannot consists {{0}}spaces.{{1}}', { 0: `<strong>`, 1: `</strong>` });
        }
        return msg;
    }
}
