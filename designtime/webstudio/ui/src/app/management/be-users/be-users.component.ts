/**
 * @Author: Rahil Khera
 * @Date:   2017-08-30T13:09:32+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-30T13:10:07+05:30
 */

import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { User } from 'app/models/user';

import { AddBEUser, AddBEUserComponent } from './add-be-user.component';
import { ChangePasswordBEUser, CurrentBEUser } from './change-password-be-user.component';

import { BEUserService } from '../../core-be/be.user.service';
import { AlertService } from '../../core/alert.service';
import { BEUsers } from '../../models-be/users-be.modal';
import { BEManagementService } from '../be-management.service';

@Component({
  selector: 'be-users',
  templateUrl: './be-users.component.html',
  styleUrls: ['./be-user.component.css']
})

export class BEUsersComponent implements OnInit {

  get disableApply(): boolean {
    return _.isEqual(this.oldUserList, this.userList);
  }
  public userList: Array<BEUsers>;
  public deletedList: Array<BEUsers> = new Array<BEUsers>();
  public selectedUser: BEUsers;
  user: BEUsers;
  availableRoles: string[];
  selectedUserRoles: string[];
  private oldUserList: Array<BEUsers>;

  constructor(
    private management: BEManagementService,
    private userService: BEUserService,
    private alertService: AlertService,
    private cdRef: ChangeDetectorRef,
    private changePasswordDialog: MatDialog,
    private addUserDialog: MatDialog,
    private alert: AlertService,
    public i18n: I18n
  ) {
    this.userList = new Array<BEUsers>();
    this.selectedUser = new BEUsers();
  }

  ngOnInit() {
    this.management.fetchUsers()
      .then((response) => {
        if (response) {
          this.userList = response;
          this.user = this.userList[0];
          this.oldUserList = _.cloneDeep(response);
          this.onUserSelection(this.userList[0]);
        }
        this.availableRoles = this.management.availableRoles;
      });
  }

  // ngAfterViewChecked() {
  // this.cdRef.detectChanges();
  // }

  onUserSelection(user: BEUsers) {
    this.selectedUser = user;
    this.selectedUserRoles = _.cloneDeep(user.roleName);
  }

  onRoleSelection() {
    if (this.selectedUserRoles.length > 0) {
      this.selectedUser.roleName = _.cloneDeep(this.selectedUserRoles);
    } else {
      this.selectedUserRoles = _.cloneDeep(this.selectedUser.roleName);
      this.alert.flash('You cannot delete the only role associated with the user.', 'warning');
    }
  }

  onDeleteUser() {
    const currentUser: User = this.userService.currentUser();
    if (currentUser.userName !== this.selectedUser.userName) {
      const index = _.indexOf(this.userList, this.selectedUser, 0);
      if (index > -1) {
        this.deletedList.push(this.selectedUser);
        this.userList.splice(index, 1);
      }
    } else {
      this.alertService.flash(this.i18n('You cannot delete currently the user who is currently logged in.'), 'error');
    }

  }

  updateUsers() {
    const finalUpdateList = new Array<BEUsers>();
    for (const user of this.userList) {
      finalUpdateList.push(user);
    }
    for (const deleted of this.deletedList) {
      deleted.actionType = 'REMOVE';
      finalUpdateList.push(deleted);
    }
    this.management.updateUsers(finalUpdateList);
    for (let i = 0; i < this.userList.length; i++) {
      this.userList[i].newUser = false;
    }
    this.oldUserList = _.cloneDeep(this.userList);
  }

  onChangePassword() {
    const dialogRef: MatDialogRef<ChangePasswordBEUser, CurrentBEUser>
      = this.changePasswordDialog.open(ChangePasswordBEUser, {
        width: '250px', data: {
          user: this.selectedUser,
          newUsername: this.selectedUser.userName,
          newPassword: null
        }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (result.newPassword && result.newPassword.trim().length > 0) {
          this.selectedUser.userPassword = result.newPassword;
          this.selectedUser.actionType = 'UPDATE';
        }
        if (this.selectedUser.newUser
          && result.newUsername
          && result.newUsername.trim().length > 0) {
          this.selectedUser.userName = result.newUsername;
        }
      }
    });
  }

  onCreateNewUser() {
    const dialogRef: MatDialogRef<AddBEUserComponent, AddBEUser>
      = this.addUserDialog.open(AddBEUserComponent, {
        width: '250px', data: {
          exisitingUsers: this.userList,
          availableRoles: this.availableRoles,
          newUser: null
        }
      });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result.newUser) {
        result.newUser.actionType = 'ADD';
        this.userList.push(result.newUser);
      }
    });
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
