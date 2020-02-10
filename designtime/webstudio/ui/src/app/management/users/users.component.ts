import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';
import { IOption } from 'ng-select';
import { Subject } from 'rxjs';

import { ModalService } from '../../core/modal.service';
import { UserService } from '../../core/user.service';
import { UserRecord } from '../../models/dto';
import { SignupForm } from '../../models/signup-form';
import { AddUserContext, AddUserModal } from '../../shared/add-user.modal';
import { ManagementService } from '../management.service';

@Component({
  selector: 'users',
  templateUrl: './users.component.html',
})
export class UsersComponent implements OnInit {
  public userItemsSub = new Subject<IOption[]>();
  public roleItems: IOption[];

  public activeRoles: string[];
  public activeRolesSub = new Subject<string[]>();
  public selectedUser: UserRecord;

  private userDetails: Map<string, UserRecord>;

  constructor(
    private managementService: ManagementService,
    private modalService: ModalService,
    private userService: UserService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.userDetails = new Map<string, UserRecord>();
    this.managementService.getAllUsers()
      .then(records => {
        records.forEach(record => this.userDetails.set(record.entityId, record));
        this.emitUsersItems();
      });
    this.managementService.getAllRoles()
      .then(records => {
        this.roleItems = records.map(record => {
          return {
            value: record.name,
            label: record.name
          };
        });
        this.emitActiveRolesItems();
      });
  }

  onUserAdd() {
    this.modalService
      .open(AddUserModal, new AddUserContext(new SignupForm()))
      .then(
        (userInfo: any) =>
          this.userService.addUser(userInfo.userName, { user: { password: userInfo.password } })
        ,
        (rejected) => { /* NOOP */ }
      ).then(
        (status) => this.managementService.getAllUsers()
      ).then(
        (records) => {
          records.forEach(record => this.userDetails.set(record.entityId, record));
          this.emitUsersItems();
        }
      )
      .catch(
        (error) => { /* TODO: Error handling */ }
      );
  }

  onUserSelected(data: IOption) {
    if (data) {
      this.selectedUser = Object.assign({}, this.userDetails.get(data.value));
      this.userEnabled = this.selectedUser.enabled;
      this.emitActiveRolesItems();
    }
  }

  onUserRemoved() {
    this.modalService
      .confirm()
      .message(this.i18n('This will delete the user ') + this.selectedUser.username + '.')
      .okBtn('Delete')
      .cancelBtn('Cancel')
      .open().result
      .then(() => this.userService.deleteUser(this.selectedUser.username), () => false)
      .then(ok => {
        if (ok) {
          this.userDetails.delete(this.selectedUser.entityId);
          this.emitUsersItems();
          this.selectedUser = null;
        }
      },
      );
    /**
     * Samoore commented 8/29/17
     * I commented this line of code out because it didn't seem to cause any changes doing so.
     * This is a candidate for having unexpected fallout, hence this comment.
     */
    // this.emitActiveRolesItems();
  }

  saveUserEdit() {
    if (this.selectedUser) {
      this.managementService.updateUser(this.selectedUser, this.selectedUser.roles)
        .then(ok => {
          if (ok) {
            this.userDetails.set(this.selectedUser.entityId, Object.assign({}, this.selectedUser));
            this.emitUsersItems();
          }
        });
    }
  }

  isDirty() {
    if (this.selectedUser) {
      const origin = this.userDetails.get(this.selectedUser.entityId);
      return !_.isEqual(this.selectedUser, origin);
    } else {
      return false;
    }
  }

  cancelUserEdit() {
    this.selectedUser = Object.assign({}, this.userDetails.get(this.selectedUser.entityId));
    this.emitActiveRolesItems();
  }

  onRoleRemoved(item: IOption) {
    const idx = this.selectedUser.roles.indexOf(item.value);
    if (idx !== -1) {
      // imitate the immutable behavior
      const roles = this.selectedUser.roles.slice();
      roles.splice(idx, 1);
      this.selectedUser.roles = roles;
    }
  }

  onRoleAdded(item: IOption) {
    const idx = this.selectedUser.roles.indexOf(item.value);
    if (idx === -1) {
      // imitate the immutable behavior
      const roles = this.selectedUser.roles.slice();
      roles.push(item.value);
      this.selectedUser.roles = roles;
    }
  }

  get userEnabled() {
    return !!this.selectedUser && !!this.selectedUser.enabled;
  }

  set userEnabled(val: boolean) {
    if (this.selectedUser && this.selectedUser.enabled !== val) {
      this.selectedUser.enabled = val;
    }
  }

  get getSelectedUser() {
    return this.selectedUser;
  }
  get selectedUserItems() {
    return this.selectedUser ? [this.toSelectItem(this.selectedUser)] : [];
  }

  private toSelectItem(record: UserRecord): IOption {
    return <IOption>{
      value: record.entityId,
      label: record.username + (record.enabled ? '' : ' (disabled)')
    };
  }

  private emitUsersItems() {
    const items = Array.from(this.userDetails.values())
      .sort((a, b) => a.username.localeCompare(b.username))
      .map(record => this.toSelectItem(record));
    this.userItemsSub.next(items);
  }

  private emitActiveRolesItems() {
    this.activeRoles = [];
    if (this.selectedUser) {
      this.selectedUser.roles.forEach(role => {
        const roleItem = this.roleItems.find(r => { return r.value === role; });
        if (roleItem) {
          //          this.activeRoles.push(idx.toString());
          this.activeRoles.push(roleItem.value);
        }
      });
      //      this.activeRolesSub.next(this.selectedUser.roles.slice());
    } else {
      //      this.activeRolesSub.next([]);
    }
  }

}
