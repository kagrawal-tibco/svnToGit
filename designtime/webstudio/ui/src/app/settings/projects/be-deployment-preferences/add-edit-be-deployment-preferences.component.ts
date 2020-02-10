import { Component, Inject } from '@angular/core';
import { AbstractControl, FormControl, FormGroupDirective, NgForm, Validators, ValidatorFn } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ErrorStateMatcher } from '@angular/material/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { DeploymentPreferences } from '../../../models-be/deployment-preferences.modal';

export class AddEditBEDeployment {
    availableProjects: string[];
    deploymentList: DeploymentPreferences[];
    currentDeploymentPreference: DeploymentPreferences;
    isNew: boolean;
}

export class InputErrorStateMatcher implements ErrorStateMatcher {
    isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
        const isSubmitted = form && form.submitted;
        return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
    }
}

@Component({
    selector: 'add-edit-be-deployment-preferences',
    templateUrl: 'add-edit-be-deployment-preferences.component.html',
    styleUrls: ['./add-edit-be-deployment-preferences.component.css']
})

export class AddEditBEDeploymentPreferences {
    errorStateMatcher: InputErrorStateMatcher;

    public nameFormControl = new FormControl('', [Validators.required, this.forbiddenSpacesValidator(), this.forbiddenDuplicateConfiguration()]);
    public projectFormControl = new FormControl('', [Validators.required, this.forbiddenDuplicateConfiguration()]);
    public hostFormControl = new FormControl('', [Validators.required, this.hostNameValidator()]);
    public portFormControl = new FormControl('', [this.portRequireValidator(), Validators.min(0), this.numberValidator()]);
    public clusterNameFormControl = new FormControl('', [Validators.required, this.forbiddenSpacesValidator()]);
    public agentNameFormControl = new FormControl('', [Validators.required, this.forbiddenSpacesValidator()]);
    public userNameFormControl = new FormControl('');
    public passwordFormControl = new FormControl('');

    constructor(
        public dialogRef: MatDialogRef<AddEditBEDeploymentPreferences>,
        @Inject(MAT_DIALOG_DATA) public data: AddEditBEDeployment,
        public i18n: I18n) {
        if (this.data.isNew) {
            this.data.currentDeploymentPreference = new DeploymentPreferences();
            this.data.currentDeploymentPreference.isAdded = true;
        }
        this.errorStateMatcher = new InputErrorStateMatcher();
    }

    forbiddenSpacesValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            let forbidden = false;
            const input: string = control.value;
            forbidden = input && input.indexOf(' ') >= 0;
            return forbidden ? { 'forbiddenSpaces': { value: control.value } } : null;
        };
    }

    portRequireValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const forbidden = control.value == null;
            return forbidden ? { 'portRequired': { value: control.value } } : null;
        };
    }

    numberValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const regExp = new RegExp('^[0-9]+$');
            const forbidden = !regExp.test(control.value);
            return forbidden ? { 'number': { value: control.value } } : null;
        };
    }

    hostNameValidator(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const regExp = new RegExp('^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\-]*[a-zA-Z0-9])\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\-]*[A-Za-z0-9])$');
            const forbidden = !regExp.test(control.value);
            return forbidden ? { 'invalidHostName': { value: control.value } } : null;
        };
    }

    forbiddenDuplicateConfiguration(): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            if (control.dirty) {
                return this.isDuplicateConfiguration(
                    this.data.currentDeploymentPreference.projectName,
                    this.data.currentDeploymentPreference.name
                ) ? { 'duplicateConfiguration': { value: control.value } } : null;
            } else {
                return null;
            }
        };
    }

    isDuplicateConfiguration(configProjectName: string, configName: string): boolean {
        return this.data.deploymentList.find(
            config => (config.projectName.trim() === configProjectName.trim()
                && config.name.trim().toUpperCase() === configName.trim().toUpperCase()
            ))
            ? true : false;
    }

    canConfirm(): boolean {
        return this.nameFormControl.errors === null
            && this.projectFormControl.errors === null
            && this.hostFormControl.errors === null
            && this.portFormControl.errors === null
            && this.clusterNameFormControl.errors === null
            && this.agentNameFormControl.errors === null
            && this.userNameFormControl.errors === null
            && this.passwordFormControl.errors === null;
    }

    getMessage(type: String): string {
        let msg;
        if (type === 'required') {
            msg = this.i18n('Cluster name is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'forbiddenSpaces') {
            msg = this.i18n('Cluster name cannot consists {{0}}spaces{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'namerequired') {
            msg = this.i18n('Name is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'nameforbiddenSpaces') {
            msg = this.i18n('Name cannot consists {{0}}spaces{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'projectrequired') {
            msg = this.i18n('Project is  {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'hostrequired') {
            msg = this.i18n('Host is  {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'invalidHostName') {
            msg = this.i18n('Hostname is {{0}}invalid{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'portrequired') {
            msg = this.i18n('Port is either {{0}}empty{{1}} or {{0}}invalid{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'portnumber') {
            msg = this.i18n('Port should be a {{0}} non-negative number{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'agentnamerequired') {
            msg = this.i18n('Agent name is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'agentforbiddenSpacesrequired') {
            msg = this.i18n('Agent name cannot consists {{0}}spaces{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        } else if (type === 'number') {
            msg = this.i18n('Port number is {{0}}invalid{{1}}.', { 0: `<strong>`, 1: `</strong>` });
        }
        return msg;
    }

}
