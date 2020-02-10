/**
 * @Author: Rahil Khera
 * @Date:   2017-09-01T12:02:18+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-09-01T12:02:24+05:30
 */

import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { BEProjectService } from '../../core-be/be.project.service';
import { AlertService } from '../../core/alert.service';
import { BEActions } from '../../models-be/actions-be.modal';
import { BEPermissions } from '../../models-be/permission-be.modal';
import { BEUsers } from '../../models-be/users-be.modal';
import { ProjectMeta } from '../../models/project-meta';
import { BEManagementService } from '../be-management.service';

@Component({
  selector: 'be-roles',
  templateUrl: './be-roles.component.html',
  styleUrls: ['./be-roles.component.css']
})

export class BERolesComponent implements OnInit {

  public projectList: Array<ProjectMeta>;
  public selectedProject: ProjectMeta;

  public permissionMap: Map<string, Map<string, Array<BEActions>>>;
  public oldPermissionMap: Map<string, Map<string, Array<BEActions>>>;

  public rolesList: Array<string>;
  public selectedRole: string;
  public enableAddRole: boolean;
  public editRole: boolean;
  public oldRole: string;

  public userList: Array<BEUsers>;
  public oldUserList: Array<BEUsers>;
  // To display list of users corresponding to particular role.
  // selectedUserList would be used.
  public selectedUserList: Array<BEUsers>;
  public candidateUserList: Array<string> = new Array<string>();
  public selectedUser: BEUsers;
  public enableAddUser: boolean;
  public editUser: boolean;

  public roleUserMap: Map<string, Array<BEUsers>>;
  public resourceList: Array<BEPermissions>;
  public selectedResource: BEPermissions;

  public actionList: Array<BEActions>;

  // Members to display all ws users.

  constructor(
    private management: BEManagementService,
    private project: BEProjectService,
    private alert: AlertService,
    public i18n: I18n
  ) {
    this.init();
  }

  init() {
    this.projectList = [];
    this.selectedProject = new ProjectMeta();

    this.permissionMap = new Map<string, Map<string, Array<BEActions>>>();
    this.oldPermissionMap = new Map<string, Map<string, Array<BEActions>>>();

    this.rolesList = [];
    this.selectedRole = '';
    this.enableAddRole = false;
    this.editRole = false;

    this.userList = [];
    this.oldUserList = [];
    this.selectedUserList = [];
    this.selectedUser = new BEUsers();
    this.candidateUserList = new Array<string>();
    this.enableAddUser = false;
    this.editUser = false;

    this.roleUserMap = new Map<string, Array<BEUsers>>();
  }

  ngOnInit() {
    this.project.getAllRepoProjects()
      .then((projectList: Array<ProjectMeta>) => {
        this.projectList = projectList;
        this.management.fetchUsers()
          .then((response) => {
            if (response) {
              this.userList = response;
              this.oldUserList = _.cloneDeep(response);
              this.createRoleUserMap();
            }
          })
          .then(() => {
            this.onProjectSelection(this.projectList[0]);
          });
      });
  }

  createRoleUserMap() {
    for (let i = 0; i < this.rolesList.length; i++) {
      this.roleUserMap.set(this.rolesList[i], new Array<BEUsers>());
    }
    for (let i = 0; i < this.userList.length; i++) {
      const user: BEUsers = this.userList[i];
      const roleList: Array<string> = user.roleName;
      for (let j = 0; j < roleList.length; j++) {
        const role = roleList[j];
        if (!this.roleUserMap.has(role)) {
          this.roleUserMap.set(role, new Array<BEUsers>());
        }
        if (_.indexOf(this.roleUserMap.get(role), user, 0) < 0) {
          this.roleUserMap.get(role).push(user);
        }
      }
    }
  }

  onProjectSelection(project: ProjectMeta) {
    if (project) {
      this.selectedProject = project;
      this.management.fetchPermission(this.selectedProject.projectName)
        .then((permissionList: Map<string, Map<string, Array<BEActions>>>) => {
          this.permissionMap = permissionList;
          this.oldPermissionMap = _.cloneDeep(permissionList);
          this.rolesList = Array.from(this.permissionMap.keys());
          this.createRoleUserMap();
          this.onRoleSelection(this.rolesList[0]);
        });
    }
  }

  /**
   * Methods for operation related to roles.
   */
  onRoleSelection(role: string) {
    this.selectedRole = role;
    this.selectedUserList = this.roleUserMap.get(role);
    this.getCandidateUserList();
    const permissionList = this.permissionMap.get(this.selectedRole);
    this.insertMissingActions();
    this.resourceList = Array.from(BEPermissions.resoucePermissionMap.values());
    for (let i = 0; i < this.resourceList.length; i++) {
      this.resourceList[i].action = permissionList.get(this.resourceList[i].type);
    }
    this.onResourceSelection(this.resourceList[0]);
    this.actionList = this.resourceList[0].action;
    this.editRole = false;
  }

  getCandidateUserList() {
    this.candidateUserList = [];
    for (let i = 0; i < this.userList.length; i++) {
      const user = this.userList[i];
      if (_.indexOf(this.selectedUserList, user, 0) < 0) {
        this.candidateUserList.push(user.userName);
      }
    }
  }

  onAddRole() {
    this.enableAddRole = true;
  }

  confirmAddRole(role: string) {
    this.enableAddRole = false;
    this.rolesList.push(role);
    const resourceList = Array.from(BEPermissions.resoucePermissionMap.keys());
    const rolePermissionMap = new Map<string, Array<BEActions>>();
    for (let i = 0; i < resourceList.length; i++) {
      const permission = BEPermissions.resoucePermissionMap.get(resourceList[i]);
      rolePermissionMap.set(permission.type, permission.action);
    }
    this.permissionMap.set(role, rolePermissionMap);
    this.roleUserMap.set(role, new Array<BEUsers>());
  }

  cancelAddRole() {
    this.enableAddRole = false;
  }

  onDeleteRole() {
    if (this.rolesList.length === 1) {
      this.alert.flash(this.i18n('You cannot delete the only role associated with the project.'), 'warning');
    } else {
      for (let i = 0; i < this.userList.length; i++) {
        const roleList = this.userList[i].roleName;
        const index = roleList.indexOf(this.selectedRole);
        if (index > -1) {
          roleList.splice(index, 1);
        }
      }
      const index = this.rolesList.indexOf(this.selectedRole);
      this.rolesList.splice(index, 1);
      this.permissionMap.delete(this.selectedRole);
      this.onRoleSelection(this.rolesList[0]);
    }
  }

  onEditRole() {
    this.editRole = true;
    this.oldRole = this.selectedRole;
  }

  confirmEditRole(newRole: string) {
    for (let i = 0; i < this.userList.length; i++) {
      const roleList = this.userList[i].roleName;
      const index = roleList.indexOf(this.oldRole);
      if (index > -1) {
        roleList.splice(index, 1, newRole);
      }
    }
    this.createRoleUserMap();
    const permissions = this.permissionMap.get(this.oldRole);
    this.permissionMap.set(newRole, permissions);
    this.permissionMap.delete(this.oldRole);
    this.editRole = false;
    this.rolesList = Array.from(this.permissionMap.keys());
    this.onRoleSelection(this.rolesList[0]);
  }

  cancelEditRole() {
    this.editRole = false;
  }

  /**
   * Methods for User operations.
   */
  onUserSelection(user: BEUsers) {
    this.selectedUser = user;
  }

  onAddUser(event: Event) {
    event.preventDefault();
    this.enableAddUser = true;
  }

  confirmAddUser(users: Array<string>) {
    this.enableAddUser = false;
    for (let i = 0; i < users.length; i++) {
      const userName = users[i];
      const userObject: Array<BEUsers> = this.userList.filter((user) => {
        if (user.userName === userName) {
          user.roleName.push(this.selectedRole);
          this.selectedUserList.push(user);
        }
      });
    }
    this.getCandidateUserList();
  }

  cancelAddUser() {
    this.enableAddUser = false;
  }

  onDeleteUser() {
    const index = _.indexOf(this.selectedUserList, this.selectedUser);
    if (index > -1) {
      this.selectedUserList.splice(index, 1);
      this.roleUserMap.get(this.selectedRole).splice(index, 1);
      const userListIndex = _.indexOf(this.userList, this.selectedUser);
      const roleIndex = _.indexOf(this.userList[userListIndex].roleName, this.selectedRole);
      this.userList[userListIndex].roleName.splice(roleIndex, 1);
      this.selectedUser = this.selectedUserList[0];
    }
  }

  onResourceSelection(resource: BEPermissions) {
    this.selectedResource = resource;
    this.actionList = this.selectedResource.action;
  }

  get disableApply(): boolean {
    return _.isEqual(this.permissionMap, this.oldPermissionMap)
      && _.isEqual(this.userList, this.oldUserList);
  }

  updateRoles() {
    if (!_.isEqual(this.permissionMap, this.oldPermissionMap)) {
      this.management.updatePermissions(this.selectedProject.projectName, this.permissionMap);
      this.oldPermissionMap = _.cloneDeep(this.permissionMap);
    }
    if (!_.isEqual(this.userList, this.oldUserList)) {
      this.management.updateUsers(this.userList);
      this.oldUserList = _.cloneDeep(this.userList);
    }
  }

  /**
   * If an action is missing for any resource, this fuction will add that action.
   * The default permission would be 'DENY'
   */
  insertMissingActions() {
    const resources = Array.from(BEPermissions.resoucePermissionMap.keys());
    for (let i = 0; i < resources.length; i++) {
      const defaultActions: Array<BEActions> = BEPermissions.resoucePermissionMap.get(resources[i]).action;
      const currentActions: Array<BEActions> = this.permissionMap.get(this.selectedRole).get(resources[i]);
      const diffActions: Array<BEActions> = new Array<BEActions>();
      for (let j = 0; j < defaultActions.length; j++) {
        if (currentActions) {
          let actionFound = false;
          for (let k = 0; k < currentActions.length; k++) {
            if (defaultActions[j].type === currentActions[k].type) {
              actionFound = true;
              break;
            }
          }
          if (!actionFound) {
            diffActions.push(defaultActions[j]);
          }
        } else {
          this.permissionMap.get(this.selectedRole).set(resources[i], defaultActions);
        }

      }
      for (let j = 0; j < diffActions.length; j++) {
        currentActions.push(diffActions[j]);
      }
    }
  }

  onHeaderClick(event: Event) {
    console.log(event);
    if (event && this.enableAddUser) {
      event.stopPropagation();
    }
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
