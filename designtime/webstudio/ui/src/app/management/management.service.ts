
import { Injectable } from '@angular/core';

import { map } from 'rxjs/operators';

import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { AddRoleRequest, RolePermissionUpdateItem, RoleRecord, RoleUserUpdateItem, UpdateRoleRequest, UpdateUserRequest, UserRecord } from '../models/dto';

export type PermissionAction = { [key: string]: string };

export const ACTIONS: { [key: string]: PermissionAction } = {
  project: {
    import: 'Import',
    read: 'Read',
    write: 'Write',
    delete: 'Delete',
    approve: 'Approve',
  },
  artifact: {
    read: 'Read',
    write: 'Write',
    delete: 'Delete',
    deploy: 'Deploy',
  },
  group: {
    read: 'Read',
    write: 'Write',
    delete: 'Delete',
  },
  manage_server: {
    '*': 'All'
  },
  manage_permissions: {
    '*': 'All'
  },
};

export class Permission {
  [type: string]: {
    [action: string]: string[]
  }
}

@Injectable()
export class ManagementService {

  constructor(
    private log: Logger,
    private rest: RestService
  ) {

  }

  getAllUsers(): Promise<UserRecord[]> {
    return this.rest.get('/users').pipe(
      map(res => res.record))
      .toPromise();
  }

  getAllRoles(): Promise<RoleRecord[]> {
    return this.rest.get('/roles').pipe(
      map(res => res.record))
      .toPromise();
  }

  enableUser(record: UserRecord, enabled: boolean): Promise<boolean> {
    const payload: UpdateUserRequest = {
      user: {
        email: null,
        password: null,
        roles: record.roles,
        enabled: enabled
      }
    };
    return this.rest.put(`/users/${record.username}`, payload).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  updateUser(record: UserRecord, roles: string[]): Promise<boolean> {
    const payload: UpdateUserRequest = {
      user: {
        email: null,
        password: null,
        roles: roles,
        enabled: record.enabled
      }
    };
    return this.rest.put(`/users/${record.username}`, payload).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  addUsersToRole(role: string, users: string[]): Promise<RoleRecord> {
    const payload: UpdateRoleRequest = {
      role: {
        updatedPermissions: [],
        updatedUsers: users.map(user => <RoleUserUpdateItem>{
          action: 'ADD',
          user: user
        })
      }
    };
    return this.rest.put(`/roles/${role}`, payload)
      .toPromise()
      .then(result => result.record[0]);
  }

  deleteUserFromRole(role: string, users: string[]): Promise<RoleRecord> {
    const payload: UpdateRoleRequest = {
      role: {
        updatedPermissions: [],
        updatedUsers: users.map(user => <RoleUserUpdateItem>{
          action: 'REMOVE',
          user: user
        })
      }
    };
    return this.rest.put(`/roles/${role}`, payload)
      .toPromise()
      .then(result => result.record[0]);
  }

  updateRolePermissions(role: string, permissions: string[]): Promise<boolean> {
    return Promise.resolve(false);
  }

  getAllPermissionTypes(): string[] {
    return Object.keys(ACTIONS);
  }

  getActionsForPermissionType(permissionType: string): PermissionAction {
    if (permissionType) {
      return ACTIONS[permissionType];
    } else {
      return {};
    }
  }

  parseToPermission(perms: string[]): Permission {
    const permission: Permission = {};
    this.getAllPermissionTypes().forEach(t => {
      permission[t] = {};
    });

    const assignAct = (type, act, instance) => {
      if (!permission[type][act]) {
        permission[type][act] = [];
      }
      permission[type][act].push(instance);
    };

    perms.forEach(perm => {
      if (perm === '*') {
        this.getAllPermissionTypes().forEach(t => {
          Object.keys(this.getActionsForPermissionType(t)).forEach(act => assignAct(t, act, '*'));
        });
      } else {
        const parts = perm.split(':');
        if (parts.length > 3) {
          this.log.warn('Invalid permission string: ' + perm);
        } else {
          const instance = parts.length === 3 ? parts[2] : '*';
          if (parts.length === 1 || parts[1] === '*') {
            Object.keys(this.getActionsForPermissionType(parts[0])).forEach(act => assignAct(parts[0], act, instance));
          } else {
            assignAct(parts[0], parts[1], instance);
          }
        }
      }
    });
    return permission;
  }

  addRole(role: string): Promise<RoleRecord> {
    const payload: AddRoleRequest = {
      role: {
        updatedPermissions: [],
        updatedUsers: []
      }
    };
    return this.rest.post(`/roles/${role}`, payload)
      .toPromise()
      .then(result => result.record[0]);
  }

  deleteRole(role: string): Promise<boolean> {
    return this.rest.delete(`/roles/${role}`).toPromise().then(res => res.ok());
  }

  removePermssionFromRole(role: string, permissions: string[]): Promise<RoleRecord> {
    const payload: UpdateRoleRequest = {
      role: {
        updatedPermissions: permissions.map(perm => <RolePermissionUpdateItem>{
          action: 'REMOVE',
          permission: perm
        }),
        updatedUsers: []
      }
    };
    return this.rest.put(`/roles/${role}`, payload)
      .toPromise()
      .then(result => result.record[0]);
  }

  addPermissionToRole(role: string, permissions: string[]): Promise<RoleRecord> {
    const payload: UpdateRoleRequest = {
      role: {
        updatedPermissions: permissions.map(perm => <RolePermissionUpdateItem>{
          action: 'ADD',
          permission: perm
        }),
        updatedUsers: []
      }
    };
    return this.rest.put(`/roles/${role}`, payload)
      .toPromise()
      .then(result => result.record[0]);
  }
}
