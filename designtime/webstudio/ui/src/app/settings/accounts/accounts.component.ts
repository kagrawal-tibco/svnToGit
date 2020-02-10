import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AuthService } from '../../auth/auth.service';
import { AuthStateService } from '../../core/auth-state.service';
import { RestService } from '../../core/rest.service';
import { UserService } from '../../core/user.service';
import { OpenIDConnectIdentityRecord } from '../../models/dto';
import { User } from '../../models/user';

interface UserPassword extends User {
  initialPassword?: string;
}

@Component({
  selector: 'accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {

  get identities() {
    return this._identities;
  }
  public changingPassword = false;
  public submitted = false;
  public user: UserPassword;
  private _identities: OpenIDConnectIdentityRecord[];
  constructor(
    private auth: AuthService,
    private rest: RestService,
    private authState: AuthStateService,
    private userService: UserService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.user = this.authState.currentState.userInfo;
    this.refresh();
  }

  enterChangePassword() {
    this.changingPassword = true;
  }

  changePassword(model: UserPassword, valid: boolean) {
    this.submitted = true;
    this.userService.changePassword(this.authState.currentState.userInfo.userName, model.initialPassword).then(result => {
      this.changingPassword = !result;
      this.submitted = false;
    });
  }

  onDelete(id: OpenIDConnectIdentityRecord) {
    this.rest.delete(`/oidc/unbindIdentity?uuid=${id.entityId}`).toPromise()
      .then(result => {
        if (result.ok()) {
          this.refresh();
        }
      });
    this.refresh();
  }

  getUIClass(provider: string) {
    return this.auth.convertProviderToUIClass(provider);
  }

  private refresh() {
    this.rest.get('/oidc/identities').toPromise()
      .then(result => {
        if (result.ok()) {
          this._identities = result.record;
        }
      });
  }
}
