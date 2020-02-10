import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot
} from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { AlertService } from './alert.service';
import { AuthGuard } from './auth-guard.service';
import { UserService } from './user.service';

import { environment } from '../../environments/environment';

@Injectable()
export class ManagementGuard implements CanActivate {
  constructor(
    private user: UserService,
    private alert: AlertService,
    private authGuard: AuthGuard,
    public i18n: I18n
  ) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    if (this.authGuard.canActivate(route, state)) {
      if (environment.enableTCEUI && (_.isEqual(state.url, '/management/be-users') || _.isEqual(state.url, '/management/be-roles'))) {
        return Promise.resolve(false);
      }
      return this.user.hasPermission('manage_permissions:*').then(ok => {
        if (!ok) {
          this.alert.flash(this.i18n('You do not have the management permission.'), 'warning');
        }
        return ok;
      });
    } else {
      return Promise.resolve(false);
    }
  }
}
