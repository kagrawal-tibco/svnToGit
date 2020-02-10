
import { Injectable } from '@angular/core';

import { map } from 'rxjs/operators';

import { RestService } from './rest.service';

import { AuthStateService } from '../core/auth-state.service';
import { CheckPermissionsResponseRecord } from '../models/dto';
import { User } from '../models/user';

@Injectable()
export class UserService {
  isAdmin: boolean;

  constructor(
    protected rest: RestService,
    protected authState: AuthStateService
  ) {

  }
  /**
   * Returns the currently logged in user.
   */
  currentUser(): User {
    return this.authState.currentState.userInfo;
  }

  /**
   * API to change any user's password. Changing one's own password is permitted. Only admins can change others' passwords.
   * @param username The username of the user whose password will be changed.
   * @param password The new password for the specified user.
   */
  changePassword(username: string, password: string): Promise<boolean> {
    return this.rest.put('/users/' + username,
      { 'user': { 'password': password } }
    ).pipe(
      map(result => result.ok()))
      .toPromise();
  }

  /**
   * API to add a new user.
   * @param username The username of the user to be added.
   * @param payload A payload containing the new user's password, any roles, and whether the new user should be enabled.
   */
  addUser(username: string, payload: { user: { password: string, roles?: string[], enabled?: boolean } }): Promise<boolean> {
    return this.rest.post(/users/ + username, {
      'user': {
        'password': payload.user.password,
        'roles': payload.user.roles || [],
        'enabled': payload.user.enabled || false
      }
    }).pipe(
      map(result => result.ok()))
      .toPromise();
  }
  /**
   * API to delete a user.
   * @param username The username of the account to delete.
   */
  deleteUser(username: string): Promise<boolean> {
    return this.rest.delete(/users/ + username,
      { 'params': { 'username': username } }
    ).pipe(map(result => result.ok()))
      .toPromise();
  }

  /**
   * API to check whether the current user has a specific permission.
   * @param permission The permission to check
   */
  hasPermission(permission: string): Promise<boolean> {
    return this.rest.get(`/management/permissions?permissions=${permission}`).pipe(
      map(res => res.ok() && (<CheckPermissionsResponseRecord>res.record[0]).isPermitted))
      .toPromise();
  }

  hasAdminRole(): Promise<boolean> {
    return this.rest.get('preferences/app/permission.json')
      .map(response => response.ok() && response.status === 0)
      .toPromise();
  }
}
