import { Component, Input } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { AuthStateService } from '../../core/auth-state.service';
import { ModalService } from '../../core/modal.service';
import { ReactService } from '../../core/react.service';
import { UserService } from '../../core/user.service';
import { UserRecord } from '../../models/dto';
import { User } from '../../models/user';

class Password implements UserRecord {
    username: string;
    entityId: string;
    deleted: boolean;
    enabled: boolean;
    email: string;
    roles: string[];
    password: string;
    passwordRepeat: string;

    constructor(user: UserRecord & User) {
        if (user.userName) { // If this is a User
            this.username = user.userName;
        } else {
            this.username = user.username;
        }
    }
}

export class PasswordEditorContext extends BSModalContext {
    constructor(
        public user: Password
    ) {
        super();
    }
}

@Component({
    selector: 'password-button',
    template: '<button type="button" [disabled]="disabled" class="btn btn-info btn-sm" (click)="onChangePassword()">{{buttonText}}</button>'
})
export class PasswordButton {
    @Input()
    public user: User & UserRecord;

    @Input()
    public disabled?: boolean;

    @Input()
    public buttonText?: string;

    constructor(
        private serviceModal: ModalService
    ) {

    }
    onChangePassword() {
        this.serviceModal.open(PasswordEditorModal, new PasswordEditorContext(new Password(this.user)));
    }
}

@Component({
    template: '<password-editor [user]="context.user" [afterSubmit]="afterSubmit" [cancel]="onCancel"></password-editor>'
})
export class PasswordEditorModal implements ModalComponent<PasswordEditorContext> {
    context: PasswordEditorContext;
    afterSubmit;
    onCancel;

    constructor(
        public dialog: DialogRef<PasswordEditorContext>,
        private authState: AuthStateService,
        private react: ReactService
    ) {
        this.context = dialog.context;
        this.afterSubmit = ((d, r, a) => {
            d.close();
            if (this.context.user.username === this.authState.currentState.userInfo.userName) {
                r.terminate();
                a.logout();
            }
        }).bind(this, this.dialog, this.react, this.authState);
        this.onCancel = this.dialog.dismiss.bind(this.dialog);
    }

}

@Component({
    selector: 'password-editor',
    templateUrl: './password-editor.component.html'
})
export class PasswordEditor {
    public submitted = false;

    @Input()
    public user: Password;

    @Input()
    public afterSubmit?;

    @Input()
    public cancel;

    constructor(
        private servicesUser: UserService,
        public i18n: I18n
    ) { }

    onChangePassword(model: Password, valid: boolean) {
        if (valid) {
            this.submitted = true;
            this.servicesUser.changePassword(this.user.username, model.password).then(
                (response) => {
                    if (response) {
                        this.submitted = false;
                        if (this.afterSubmit) {
                            this.afterSubmit();
                        }
                    } else {
                        this.submitted = false;
                        // Failed.
                    }
                }
            ).catch(
                (error) => {
                    /**
                     * TODO: error handling
                     */
                }
            );
        }
    }

    getMessage(): string {
        return this.i18n('Change password for {{username}}', { username: this.user.username });
    }
}
