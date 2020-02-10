import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ActionType } from 'app/audit-trail/audit-trail.component';
import { ProjectService } from 'app/core/project.service';
import { UserService } from 'app/core/user.service';
import { BEManagementService } from 'app/management/be-management.service';
import { ManagementService } from 'app/management/management.service';
import { Activity, MultiActivity } from 'app/models/activity';

import { environment } from './../../../environments/environment';
import { BEUserService } from './../../core-be/be.user.service';
import { AlertService } from './../../core/alert.service';

export class ActivityStreamFilter {
  projectList: string[];
  userList: string[];
  actionTypes: ActionType[];
  artifactPaths: string;
  beginningDate: Date;
  endingDate: Date;
  currentFilter: RegExp; // current compiled filter expression

  // 'true' will NOT filter the activity, 'false' WILL filter it
  public test(activity: Activity): boolean {
    let tested = false;
    if (activity instanceof MultiActivity) {
      let matched = false;
      activity.activities.forEach(sub => {
        if (this.test(sub)) {
          matched = true;
        }
        tested = true;
      });
      if (!matched) {
        // none of the sub-activities matched, return false
        return false;
      }
    }
    if (this.beginningDate) {
      if (this.beginningDate.getTime() > activity.timestamp) {
        return false;
      }
      tested = true;
    }
    if (this.endingDate) {
      if (this.endingDate.getTime() < activity.timestamp) {
        return false;
      }
      tested = true;
    }
    if (this.projectList && this.projectList.length > 0 && activity.projectName) {
      if (!this.projectList.includes(activity.projectName)) {
        return false;
      }
      tested = true;
    }
    if (this.userList && this.userList.length > 0 && activity.userName) {
      if (!this.userList.includes(activity.userName)) {
        return false;
      }
      tested = true;
    }
    if (this.actionTypes && this.actionTypes.length > 0) {
      const activityType = activity.subType ? activity.subType : activity.type;
      if (this.actionTypes.findIndex(type => {
        return type.value === activityType;
      }) === -1) {
        return false;
      }
      tested = true;
    }
    if (this.artifactPaths && this.artifactPaths.length > 0 && !(activity instanceof MultiActivity)) {
      const paths = this.artifactPaths.split(',');
      if (paths && paths.length > 0) {
        let found = false;
        paths.forEach(p => {
          // we don't track the individual artifacts, so check the details for the activity?
          if (activity.details && activity.details.toUpperCase().indexOf(p.toUpperCase()) !== -1) {
            found = true;
          }
        });
        if (!found) {
          return false;
        }
      }
      tested = true;
    }
    if (this.currentFilter && !(activity instanceof MultiActivity)) {
      if (activity.details && this.currentFilter.test(activity.details.toUpperCase())) {
        return true;
      }
      if (activity.projectName && this.currentFilter.test(activity.projectName.toUpperCase())) {
        return true;
      }
      if (activity.userName && this.currentFilter.test(activity.userName.toUpperCase())) {
        return true;
      }
      tested = true;
      return false;
    } else {
      return true;
    }
    // if (this.searchString) {
    //   if (activity.details && activity.details.toLowerCase().includes(this.searchString.toLowerCase())) {
    //     // return true;
    //   } else if (activity.data && activity.data.toLowerCase().includes(this.searchString.toLowerCase())) {
    //   } else {
    //     activity.state = 'filtered';
    //     return;
    //   }
    // }
    // return tested;
  }
}

@Component({
  selector: 'activity-stream-filter',
  templateUrl: './activity-stream-filter.component.html',
  styleUrls: ['./activity-stream-filter.component.css', '../dashboard.component.css']
})
export class ActivityStreamFilterComponent implements OnInit {

  @Input()
  activityFilter: ActivityStreamFilter;

  actions = new FormControl();
  users = new FormControl();
  projects = new FormControl();

  projectList: string[];
  userList: string[];
  beginningDate: Date;
  endingDate: Date;
  artifactPaths: string;
  admin: boolean;
  forceReset = false;

  constructor(private projectService: ProjectService,
    private _alertService: AlertService,
    private managementService: BEManagementService,
    private _userService: UserService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.getProjectList();
    this.getUserList();
    this.initFromFilter();
  }

  initFromFilter() {
    this.projects.setValue(this.activityFilter.projectList);
    this.users.setValue(this.activityFilter.userList);
    this.actions.setValue(this.activityFilter.actionTypes);
    this.beginningDate = this.activityFilter.beginningDate;
    this.endingDate = this.activityFilter.endingDate;
    this.artifactPaths = this.activityFilter.artifactPaths;
  }

  getProjectList() {
    this.projectList = [];
    this.projectService.projectMetaCache.forEach(entry => {
      this.projectList.push(entry.projectName);
    });
  }

  enableTCEUI() {
    return environment.enableTCEUI;
  }

  isAdmin() {
    if (this.admin === undefined) {
      this.admin = false;
      this._userService.hasAdminRole().then((adminRole) => { this.admin = adminRole; },
        err => {
          // throw err;
        });
    }
    return this.admin;
  }

  getUserList() {
    this.userList = [];
    this.managementService.fetchUsers()
      .then(users => {
        if (users) {
          users.forEach(entry => {
            this.userList.push(entry.userName);
          });
        }
      });
  }

  getActionTypes() {
    const types: ActionType[] = ActionType.TYPES.filter(t => {
      // if ((this.mode === 'ARTIFACT' && t.mode === 'ARTIFACT') ||
      //     (this.mode === 'PROJECT' && (t.mode === 'PROJECT' || t.mode === 'ARTIFACT')) ||
      //     (this.mode === 'MANAGEMENT'))
      return true;
      // else return false;
    });

    const actions = types.sort((t1, t2) => {
      return t1.name.toLowerCase() > t2.name.toLowerCase() ? 1 : -1;
    });
    return actions;
  }

  performApply() {
    if (this.beginningDate && this.endingDate) {
      if (this.beginningDate.getTime() > this.endingDate.getTime()) {
        this._alertService.flash(this.i18n('The ending date/time must be after the begin date/time'), 'warning');
        return;
      }
    }
    const actions = this.actions.value;
    const projects = this.projects.value;
    const users = this.users.value;
    this.activityFilter.actionTypes = actions;
    this.activityFilter.projectList = projects;
    this.activityFilter.userList = users;
    this.activityFilter.beginningDate = this.beginningDate;
    this.activityFilter.endingDate = this.endingDate;
    this.activityFilter.artifactPaths = this.artifactPaths;
  }

  performClear() {
    this.actions.reset();
    this.users.reset();
    this.projects.reset();
    this.artifactPaths = null;
    this.beginningDate = undefined;
    this.endingDate = null;
    this.forceReset = true;
    this.performApply();
  }

  onValueChange() {
    if (this.forceReset) {
      setTimeout(() => this.forceReset = false, 0);
    }
  }
}
