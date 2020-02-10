/**
 * @Author: Rahil Khera
 * @Date:   2017-08-29T14:05:35+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-29T14:05:40+05:30
 */

import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment';

import { AlertService } from '../core/alert.service';
import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { BEActions } from '../models-be/actions-be.modal';
import { BEPermissions } from '../models-be/permission-be.modal';
import { BEUsers } from '../models-be/users-be.modal';

@Injectable()
export class BEManagementService {

  public availableRoles: string[];

  constructor(
    private log: Logger,
    private rest: RestService,
    private alert: AlertService,
    public i18n: I18n
  ) {

  }

  fetchUsers(): Promise<Array<BEUsers>> {
    return this.rest.get('userData.json')
      .toPromise().then((response) => {
        if (response.ok()) {
          if (response.record[0].availableRoles) {
            this.availableRoles = response.record[0].availableRoles;
          }
          if (environment.enableTCEUI || response.record[0].authType === 'file') {
            const userList: Array<BEUsers> = new Array<BEUsers>();
            const jsonUserList = response.record[0].authEntry;
            for (let i = 0; i < jsonUserList.length; i++) {
              const user: BEUsers = new BEUsers();
              const jsonUser = jsonUserList[i];
              user.userName = jsonUser.userName;
              user.userPassword = jsonUser.userPassword;
              user.roleName = jsonUser.roleName.split(',');
              userList.push(user);
            }
            return userList;
          } else {
            this.alert.flash(this.i18n('Authentication type is not file based. Hence, the user(s) setting is not available.'), 'warning');
            return null;
          }
        }
      });
  }

  updateUsers(userList: Array<BEUsers>): Promise<boolean> {
    const url = `userData/update.json`;
    const payload = BEUsers.getSaveJson(userList);
    return this.rest.put(url, payload, undefined, true)
      .toPromise()
      .then(res => {
        return res.ok();
      });
  }

  /**
   * @param projectName
   * @return Map : key: roleName value: valueMap (Map of resourceref and corresponding permssions)
   *         valueMap key: resourceref value: array of BEAction objects having actions and its value.
   */
  fetchPermission(projectName: string): Promise<Map<string, Map<string, Array<BEActions>>>> {
    const endPt = environment.enableTCEUI ? 'acl.json' : 'aclData.json';
    return this.rest.get(`projects/${projectName}/${endPt}`)
      .toPromise()
      .then((response) => {
        if (response.ok()) {
          const jsonPermissionList = response.record[0].entries.entry;
          const rolePermissionMap: Map<string, Map<string, Array<BEActions>>> = new Map<string, Map<string, Array<BEActions>>>();
          for (let i = 0; i < jsonPermissionList.length; i++) {
            const currentRole = jsonPermissionList[i].role.name;
            if (!rolePermissionMap.has(currentRole)) {
              rolePermissionMap.set(currentRole, new Map<string, Array<BEActions>>());
            }
            const permissionMap: Map<string, Array<BEActions>> = rolePermissionMap.get(currentRole);
            const permissionJson = jsonPermissionList[i].permissions.permission;
            if (permissionJson) {
              for (let j = 0; j < permissionJson.length; j++) {
                const resourceref = permissionJson[j].resourceref;
                if (!permissionMap.has(resourceref)) {
                  permissionMap.set(resourceref, new Array<BEActions>());
                }
                permissionMap.get(resourceref).push(new BEActions(permissionJson[j].action.type,
                   permissionJson[j].action.value, this.i18n));
              }
            } else {
              const resourcrefList = Array.from(BEPermissions.resoucePermissionMap.keys());
              for (let j = 0; j < resourcrefList.length; j++) {
                permissionMap.set(resourcrefList[j], BEPermissions.resoucePermissionMap.get(resourcrefList[j]).action);
              }
            }
          }
          return rolePermissionMap;
        } else {
          return null;
        }
      });
  }

  updatePermissions(projectName: string, projectPermissions: Map<string, Map<string, Array<BEActions>>>): Promise<boolean> {
    const url = `aclData/update.json`;
    const payload = BEPermissions.getSaveJson(projectName, projectPermissions);

    return this.rest.put(url, payload, undefined, true)
      .toPromise()
      .then(res => {
        return res.ok();
      });
  }
}
