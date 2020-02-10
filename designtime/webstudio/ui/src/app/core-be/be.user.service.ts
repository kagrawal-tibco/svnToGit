
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';

import * as _ from 'lodash';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { AuthStateService } from '../core/auth-state.service';
import { RestService } from '../core/rest.service';
import { UserService } from '../core/user.service';

@Injectable()
export class BEUserService extends UserService implements CanActivate {

  isAdmin: boolean;
  constructor(
    protected rest: RestService,
    protected auth: AuthStateService
  ) {
    super(rest, auth);
  }

  hasPermission(permission: string): Promise<boolean> {
    if (this.doAdminRoleCheck(permission)) {
      return this.hasAdminRole();
    } else {
      // projectname:artifactPath:artifacType:permissionType Or projectName:permissionType
      const permissionItems = permission.split(':');
      let url = `projects/${permissionItems[0]}/permission.json?`;
      url += (permissionItems.length === 2) ? `artifactType=PROJECT&permissionType=${permissionItems[1]}` :
        `artifactPath=${permissionItems[1]}&artifactType=${permissionItems[2]}&permissionType=${permissionItems[3]}`;

      return this.rest.get(url).pipe(
        map(response => {
          if (response.ok()) {
            return response.record[0].allow;
          }
        }))
        .toPromise();
    }
  }

  hasAdminRole(): Promise<boolean> {
    return this.rest.get('preferences/app/permission.json').pipe(
      map(response => {
        this.isAdmin = response.ok() && response.status === 0;
        return this.isAdmin;
      }))
      .toPromise();
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    if (environment.enableTCEUI && (_.isEqual(state.url, '/management/be-users')
      || _.isEqual(state.url, '/management/be-roles')
      || _.isEqual(state.url, '/settings/be-deployment-preferences'))) {
      return Promise.resolve(false);
    }
    return this.hasAdminRole();
  }

  private doAdminRoleCheck(permission: string): boolean {
    if (permission === 'manage_permissions:*') {
      return true;
    } else {
      return false;
    }
  }

}
