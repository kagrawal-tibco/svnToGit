import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { BehaviorSubject } from 'rxjs';

import { ModalService } from '../../core/modal.service';
import { RoleRecord, UserRecord } from '../../models/dto';
import { ManagementService, Permission } from '../management.service';

@Component({
  selector: 'roles',
  templateUrl: './roles.component.html',
  styleUrls: ['./roles.component.css']
})
export class RolesComponent implements OnInit {

  public roleRecordSub = new BehaviorSubject<RoleRecord[]>([]);
  public selectedRole: RoleRecord;
  public selectedUser: string;
  public selectedPermissionType: string;
  public selectedPermissionAction: string;
  public selectedPermissionInstance: string;
  public roleDetails: Map<string, { record: RoleRecord, permissions: Permission }>;
  public permissionActions: { key: string, text: string }[] = [];

  public addRoleEnabled = false;
  public addUserEnabled = false;
  public addInstanceEnabled = false;

  public userDetails: Map<string, UserRecord>;

  constructor(
    private service: ManagementService,
    private modal: ModalService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.refreshRoles()
      .then(() => this.refreshUsers())
      .then(() => this.roleRecordSub.getValue())
      .then(records => {
        if (records.length > 0) {
          this.onRoleSelected(records[0].name);
        }
      });
  }

  refreshUsers() {
    this.userDetails = new Map<string, UserRecord>();
    this.service.getAllUsers()
      .then(records => {
        records.forEach(record => this.userDetails.set(record.entityId, record));
      });
  }

  refreshRoles() {
    return this.service.getAllRoles()
      .then(records => {
        this.roleDetails = new Map<string, { record: RoleRecord, permissions: Permission }>();
        records.forEach(record => {
          this.roleDetails.set(record.name, {
            record: record,
            permissions: this.service.parseToPermission(record.permissions)
          });
        });
        this.emitRoles();
      });
  }

  onRoleSelected(role: string) {
    if (role) {
      this.selectedRole = Object.assign({}, this.roleDetails.get(role).record);
      this.selectedUser = null;
      this.selectedPermissionType = null;
      this.selectedPermissionAction = null;
      const types = this.permissionTypes;
      if (types.length > 0) {
        this.onPermissionTypeSelected(types[0]);
      }
    } else {
      this.onPermissionActionSelected(null);
    }
  }

  onUserSelected(user: string) {
    this.selectedUser = user;
  }

  onPermissionTypeSelected(item: string) {
    this.selectedPermissionType = item;
    const actions = this.service.getActionsForPermissionType(this.selectedPermissionType);
    this.permissionActions = Object.keys(actions).map(key => ({ key: key, text: actions[key] }));
    this.selectedPermissionAction = null;
    if (this.permissionActions.length > 0) {
      this.onPermissionActionSelected(this.permissionActions[0].key);
    }
  }

  onPermissionActionSelected(key: string) {
    this.selectedPermissionInstance = null;
    this.selectedPermissionAction = key;
  }

  onPermissionInstanceSelected(instance: string) {
    this.selectedPermissionInstance = instance;
  }

  onAddRole() {
    this.addRoleEnabled = true;
  }

  confirmAddRole(role: string) {
    this.service.addRole(role)
      .then(record => {
        if (record) {
          this.addRoleEnabled = false;
          this.roleDetails.set(record.name, {
            record: record,
            permissions: this.service.parseToPermission(record.permissions)
          });
          this.emitRoles();
          this.onRoleSelected(record.name);
        }
      });
  }

  cancelAddRole() {
    this.addRoleEnabled = false;
  }

  onDeleteRole() {
    if (this.selectedRole) {
      this.modal.confirm()
        .message(this.i18n('Please confirm you want to delete role ') + this.selectedRole.name)
        .open().result
        .then(() => this.service.deleteRole(this.selectedRole.name))
        .then(ok => {
          if (ok) {
            const records = this.roleRecordSub.value;
            const idx = records.findIndex(item => this.selectedRole.name === item.name);
            this.roleDetails.delete(this.selectedRole.name);
            this.emitRoles();
            this.selectClosestRole(idx);
          }
        },
          () => { /* noop */ });
    }
  }

  onAddUser() {
    this.addUserEnabled = true;
  }

  onDeleteUser() {
    if (this.selectedRole && this.selectedUser) {
      this.modal.confirm()
        .message(this.i18n('Please confirm you want to delete user {{user}} from role {{role}}', { user: this.selectedUser, role: this.selectedRole.name }))
        .open().result
        .then(() => this.service.deleteUserFromRole(this.selectedRole.name, [this.selectedUser]))
        .then(record => {
          if (record) {
            const idx = this.selectedRole.users.indexOf(this.selectedUser);
            this.selectedRole = Object.assign({}, record);
            this.roleDetails.get(record.name).record = record;
            this.selectClosestUser(idx);
          }
        }, () => {/* noop */ });
    }

  }

  confirmAddUser(users: string[]) {
    if (this.selectedRole) {
      this.service.addUsersToRole(this.selectedRole.name, users)
        .then(record => {
          if (record) {
            this.selectedRole = Object.assign({}, record);
            this.roleDetails.get(record.name).record = record;
            this.addUserEnabled = false;
          }
        });
    }
  }

  cancelAddUser() {
    this.addUserEnabled = false;
  }

  onAddInstance() {
    this.addInstanceEnabled = true;
  }

  onDeleteInstance() {
    if (this.selectedRole && this.selectedPermissionType && this.selectedPermissionAction && this.selectedPermissionInstance) {
      const perm = `${this.selectedPermissionType}:${this.selectedPermissionAction}:${this.selectedPermissionInstance}`;
      this.modal.confirm()
        .message(this.i18n('Please confirm you want to remove permission {{perm}} from role {{name}}', { perm: perm, name: this.selectedRole.name }))
        .open().result
        .then(() => this.service.removePermssionFromRole(this.selectedRole.name, [perm]))
        .then(record => {
          if (record) {
            this.selectedPermissionInstance = null;
            this.selectedRole = Object.assign({}, record);
            this.roleDetails.set(record.name, {
              record: record,
              permissions: this.service.parseToPermission(record.permissions)
            });
          }
        }, () => { /* noop */ });
    }

  }

  confirmAddInstance(instance: string) {
    if (this.selectedRole && this.selectedPermissionType && this.selectedPermissionAction) {
      const perm = `${this.selectedPermissionType}:${this.selectedPermissionAction}:${instance}`;
      this.service.addPermissionToRole(this.selectedRole.name, [perm])
        .then(record => {
          if (record) {
            this.addInstanceEnabled = false;
            this.selectedRole = Object.assign({}, record);
            this.roleDetails.set(record.name, {
              record: record,
              permissions: this.service.parseToPermission(record.permissions)
            });
            this.onPermissionInstanceSelected(instance);
          }
        });
    }
  }

  getPermission(type: string): boolean {
    return !!this.roleDetails.get(this.selectedRole.name).permissions[type]['*'];
  }

  setManageServerOrPermissions(enable: boolean) {
    if (enable) {
      this.confirmAddInstance('*');
    } else {
      this.selectedPermissionInstance = '*';
      this.onDeleteInstance();
    }
  }

  cancelAddInstance() {
    this.addInstanceEnabled = false;
  }

  get permissionTypes(): string[] {
    return this.service.getAllPermissionTypes();
  }

  get permissionInstances() {
    if (this.selectedPermissionType && this.selectedPermissionAction) {
      const permission = this.roleDetails.get(this.selectedRole.name).permissions;
      const actions = permission[this.selectedPermissionType];
      if (actions) {
        if (actions[this.selectedPermissionAction]) {
          return actions[this.selectedPermissionAction];
        } else {
          return [];
        }
      } else {
        return [];
      }
    } else {
      return [];
    }
  }

  get instanceSectionMessage() {
    if (this.selectedPermissionType && this.selectedPermissionAction) {
      if (this.permissionInstances && this.permissionInstances.length > 0) {
        return '';
      } else {
        return this.i18n('No instance is permitted for this type and action.');
      }
    } else {
      return this.i18n('Select type and action to view the instances.');
    }
  }

  get actionSectionMessage() {
    if (this.selectedPermissionType) {
      return '';
    } else {
      return this.i18n('Select a type to view actions.');
    }
  }
  get typeSectionMessage() {
    if (this.selectedRole) {
      return '';
    } else {
      return this.i18n('Select a role to view permission types.');
    }
  }

  get userCandidates() {
    if (this.selectedRole) {
      const active = new Set<string>();
      this.selectedRole.users.forEach(user => active.add(user));
      const items = Array.from(this.userDetails.values())
        .sort((a, b) => a.username.localeCompare(b.username));
      return items.filter(item => !active.has(item.username)).map(item => item.username);
    } else {
      return [];
    }
  }

  private emitRoles() {
    const records = Array.from(this.roleDetails.values()).map(detail => detail.record).sort((a, b) => a.name.localeCompare(b.name));
    this.roleRecordSub.next(records);
  }

  private selectClosestRole(idx: number) {
    // select the closest role
    const records = this.roleRecordSub.value;
    if (records.length === 0) {
      this.onRoleSelected(null);
    } else {
      if (idx === records.length) {
        this.onRoleSelected(records[idx - 1].name);
      } else {
        this.onRoleSelected(records[idx].name);
      }
    }
  }

  private selectClosestUser(idx: number) {
    // select the closest role
    const users = this.selectedRole.users;
    if (users.length === 0) {
      this.onUserSelected(null);
    } else {
      if (idx === users.length) {
        this.onUserSelected(users[idx - 1]);
      } else {
        this.onUserSelected(users[idx]);
      }
    }
  }

}
