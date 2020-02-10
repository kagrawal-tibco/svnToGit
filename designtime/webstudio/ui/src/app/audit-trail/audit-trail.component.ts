import { DatePipe } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { GridOptions } from 'ag-grid-community/main';

import { AuditTrailService } from 'app/audit-trail/audit-trail.service';

import { environment } from './../../environments/environment';

import { ProjectService } from '../core/project.service';
import { BEManagementService } from '../management/be-management.service';

export type AuditTrailMode = 'PROJECT' | 'ARTIFACT' | 'MANAGEMENT';

export class ActionType {
    static TYPES = [];
    static LOGIN = new ActionType('Login', 'LOGIN', 'MANAGEMENT');
    static CREATE = new ActionType('Create', 'CREATE', 'ARTIFACT');
    static MODIFY = new ActionType('Modify', 'MODIFY', 'ARTIFACT');
    static COMMIT = new ActionType('Commit', 'COMMIT', 'ARTIFACT');
    static CHECKOUT = new ActionType('Checkout', 'CHECKOUT', 'ARTIFACT');
    static APPROVE = new ActionType('Approve', 'APPROVE', 'ARTIFACT');
    static VALIDATE = new ActionType('Validate', 'VALIDATE', 'ARTIFACT');
    static REVERT = new ActionType('Revert', 'REVERT', 'ARTIFACT');
    static DELETE = new ActionType('Delete', 'DELETE', 'ARTIFACT');
    static LOGOUT = new ActionType('Logout', 'LOGOUT', 'MANAGEMENT');
    static BUILDANDDEPLOY = new ActionType('Build And Deploy', 'BUILDANDDEPLOY', 'ARTIFACT');
    static GENERATEDEPLOYABLE = new ActionType('Generate Deployable', 'GENERATE_DEPLOYABLE', 'PROJECT');
    static EXTERNALSYNC = new ActionType('External Synchronize', 'EXTERNAL_SYNC', 'ARTIFACT');
    static GROUP = new ActionType('Group', 'GROUP', 'MANAGEMENT');
    static PREFERENCES = new ActionType('Preferences', 'PREFERENCES', 'MANAGEMENT');
    static IMPORT = new ActionType('Import', 'IMPORT', 'ARTIFACT');
    static EXPORT = new ActionType('Export', 'EXPORT', 'ARTIFACT');
    static WORKLIST = new ActionType('Worklist', 'WORKLIST', 'PROJECT');

    static REJECT = new ActionType('Reject', 'REJECT', 'ARTIFACT');
    static SYNC = new ActionType('Synchronize', 'SYNC', 'ARTIFACT');
    static DEPLOY_CONFIG = new ActionType('Deployment Config', 'DEPLOY_CONFIG', 'PROJECT', false);
    static DELEGATE = new ActionType('Delegate', 'DELEGATE', 'ARTIFACT', false);
    static LOCK = new ActionType('Lock', 'LOCK', 'ARTIFACT');
    static UNLOCK = new ActionType('UnLock', 'UNLOCK', 'ARTIFACT');
    static ACL = new ActionType('Acl', 'ACL', 'PROJECT', false);
    static USER = new ActionType('User', 'USER', 'MANAGEMENT', false);
    static RENAME = new ActionType('Rename', 'RENAME', 'ARTIFACT');

    constructor(
        public name: string,
        public value: string,
        public mode: AuditTrailMode,
        public validForTce?: boolean
    ) { 
        if (!(validForTce === false && environment.enableTCEUI)) {
            ActionType.TYPES.push(this); // add this to available action types for TCE
        }
    }

    static getByValue(value: string): ActionType {
        let actionType: ActionType;

        ActionType.TYPES.forEach(entry => {
            if (value === entry.value) {
                actionType = entry;
            }
        });

        return actionType;
    }
}

@Component({
    selector: 'audit-trail',
    templateUrl: './audit-trail.component.html',
    styleUrls: ['./audit-trail.component.css']
})
export class AuditTrailComponent implements OnInit {

    get isManagement() {
        return (this.mode === 'MANAGEMENT');
    }

    get isProject() {
        return (this.mode === 'PROJECT');
    }

    get isDirty() {
        let isDirty;
        if (this.mode === 'MANAGEMENT') {
            isDirty = (this.userName || this.projectName || this.artifactPath || this.artifactType
                || this.actions.value || this.beforeDate || this.afterDate);
        } else {
            isDirty = (this.afterDate || this.beforeDate);
        }
        return isDirty;
    }

    get logFile() {
        return this.isLogFile;
    }

    set logFile(logFile: boolean) {
        this.isLogFile = logFile;
    }
    userList: string[];
    gridOptions: GridOptions = {
        defaultColDef: {
            resizable: true,
        }
    };
    rowData: any[];
    columnDefs: any[];

    actions = new FormControl();

    userName: string;
    // actionType: string;
    beforeDate: Date;
    afterDate: Date;
    isLogFile: boolean;

    @Input()
    artifactPath: string;
    @Input()
    artifactType: string;
    @Input()
    projectName: string;
    @Input()
    mode: AuditTrailMode;
    forceReset: boolean;

    constructor(
        private auditTrailService: AuditTrailService,
        private projectService: ProjectService,
        private managementService: BEManagementService,
        private datePipe: DatePipe,
        public i18n: I18n
    ) {
        this.logFile = false;
        this.getUserList();
    }

    ngOnInit() {
        if (!this.mode) { this.mode = 'MANAGEMENT'; }

        this.columnDefs = this.getColumnDefs();

        if (this.mode !== 'MANAGEMENT') {
            this.getRowData();
        }
    }

    clearFilter() {
        this.userName = undefined;
        this.projectName = undefined;
        this.artifactPath = undefined;
        this.artifactType = undefined;
        this.userList = undefined;
        this.beforeDate = undefined;
        this.afterDate = undefined;
        this.actions.reset();
        this.forceReset = true;
        this.getRowData();
    }

    onValueChange() {
        if (this.forceReset) {
            setTimeout(() => this.forceReset = false, 0);
        }
    }

    getRowData() {
        this.rowData = [];

        this.cleanse();

        let beforeTime, afterTime;
        if (this.beforeDate) { beforeTime = this.beforeDate.valueOf(); }
        if (this.afterDate) { afterTime = this.afterDate.valueOf(); }

        if (this.isLogFile) {
            this.auditTrailService.getLogfile(this.userName, this.projectName, this.artifactPath,
                this.artifactType, this.actions.value, beforeTime, afterTime);
        } else {
            this.auditTrailService.getAuditTrail(this.userName, this.projectName, this.artifactPath,
                this.artifactType, this.actions.value, beforeTime, afterTime)
                .then(auditTrail => {
                    auditTrail.map(entry => {
                        if (entry.comment !== undefined) {
                            if (entry.comment.search('Modify Successful') !== -1) {
                                entry.comment = this.i18n('Modify Successful');
                            }
                            if (entry.comment.search('Create Successful') !== -1) {
                                entry.comment = this.i18n('Create Successful');
                            }
                            if (entry.comment.search('Delete Successful') !== -1) {
                                entry.comment = this.i18n('Delete Successful');
                            }
                            if (entry.comment.search('Synchronization Successful') !== -1) {
                                entry.comment = this.i18n('Synchronization Successful');
                            }
                            if (entry.comment.search('Updated User preferences') !== -1) {
                                entry.comment = this.i18n('Updated User preferences');
                            }
                            if (entry.comment.search('Updated Notification preferences') !== -1) {
                                entry.comment = this.i18n('Updated Notification preferences');
                            }
                            if (entry.comment.search('Updated Operator preferences') !== -1) {
                                entry.comment = this.i18n('Updated Operator preferences');
                            }
                            if (entry.comment.startsWith('Group') && entry.comment.endsWith('Created')) {
                                const groupname = entry.comment.split('Group')[1].split('Created')[0];
                                entry.comment = this.i18n('Group{{0}}Created', { 0: groupname });
                            }
                        }
                        if (this.mode === 'ARTIFACT') {
                            this.rowData.push({
                                userName: entry.userName,
                                actionType: ActionType.getByValue(entry.actionType).name,
                                actionTime: this.datePipe.transform(new Date(entry.actionTime), 'yyyy MMM dd HH:mm:ss'),
                                comment: entry.comment
                            });
                        } else if (this.mode === 'PROJECT') {
                            this.rowData.push({
                                userName: entry.userName,
                                artifactPath: entry.artifactPath,
                                actionType: ActionType.getByValue(entry.actionType).name,
                                actionTime: this.datePipe.transform(new Date(entry.actionTime), 'yyyy MMM dd HH:mm:ss'),
                                comment: entry.comment
                            });
                        } else {
                            this.rowData.push({
                                userName: entry.userName,
                                projectName: entry.projectName,
                                artifactPath: entry.artifactPath,
                                actionType: ActionType.getByValue(entry.actionType).name,
                                actionTime: this.datePipe.transform(new Date(entry.actionTime), 'yyyy MMM dd HH:mm:ss'),
                                comment: entry.comment
                            });
                        }
                    });
                });
        }
    }

    getColumnDefs() {
        const columnDefs: any[] = [];

        const userWidth: number = (this.mode === 'PROJECT') ? 75 : 100;
        columnDefs.push({
            headerName: this.i18n('User'),
            field: 'userName',
            width: userWidth,
        });

        if (this.mode === 'MANAGEMENT') {
            columnDefs.push({
                headerName: this.i18n('Project'),
                field: 'projectName',
                width: 175,
            });
        }

        const artifactWidth: number = (this.mode === 'MANAGEMENT') ? 225 : 150;
        if (this.mode === 'MANAGEMENT' || this.mode === 'PROJECT') {
            columnDefs.push({
                headerName: this.i18n('Artifact'),
                field: 'artifactPath',
                width: artifactWidth,
            });
        }

        const actionTypeWidth: number = (this.mode === 'PROJECT') ? 75 : 125;
        columnDefs.push({
            headerName: this.i18n('Action'),
            field: 'actionType',
            width: actionTypeWidth,
        });

        columnDefs.push({
            headerName: this.i18n('Time'),
            field: 'actionTime',
            width: 150,
        });

        const commentWidth: number = (this.mode === 'MANAGEMENT') ? 225 : 150;
        columnDefs.push({
            headerName: this.i18n('Comment'),
            field: 'comment',
            width: commentWidth,
        });

        return columnDefs;
    }

    projectList() {
        const projectList: string[] = [];
        this.projectService.projectMetaCache.forEach(entry => {
            projectList.push(entry.projectName);
        });
        return projectList;
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

    actionTypes() {
        const types: ActionType[] = ActionType.TYPES.filter(t => {

            switch (t.value) {
                case 'LOGIN': t.name = this.i18n('Login'); break;
                case 'CREATE': t.name = this.i18n('Create'); break;
                case 'MODIFY': t.name = this.i18n('Modify'); break;
                case 'COMMIT': t.name = this.i18n('Commit'); break;
                case 'CHECKOUT': t.name = this.i18n('Checkout'); break;
                case 'APPROVE': t.name = this.i18n('Approve'); break;
                case 'REJECT': t.name = this.i18n('Reject'); break;
                case 'VALIDATE': t.name = this.i18n('Validate'); break;
                case 'REVERT': t.name = this.i18n('Revert'); break;
                case 'DELETE': t.name = this.i18n('Delete'); break;
                case 'SYNC': t.name = this.i18n('Synchronize'); break;
                case 'LOGOUT': t.name = this.i18n('Logout'); break;
                case 'BUILDANDDEPLOY': t.name = this.i18n('Build And Deploy'); break;
                case 'GENERATE_DEPLOYABLE': t.name = this.i18n('Generate Deployable'); break;
                case 'EXTERNAL_SYNC': t.name = this.i18n('External Synchronize'); break;
                case 'GROUP': t.name = this.i18n('Group'); break;
                case 'PREFERENCES': t.name = this.i18n('Preferences'); break;
                case 'DEPLOY_CONFIG': t.name = this.i18n('Deployment Config'); break;
                case 'DELEGATE': t.name = this.i18n('Delegate'); break;
                case 'LOCK': t.name = this.i18n('Lock'); break;
                case 'UNLOCK': t.name = this.i18n('UnLock'); break;
                case 'ACL': t.name = this.i18n('ACL'); break;
                case 'USER': t.name = this.i18n('User'); break;
                case 'IMPORT': t.name = this.i18n('Import'); break;
                case 'EXPORT': t.name = this.i18n('Export'); break;
                case 'WORKLIST': t.name = this.i18n('Worklist'); break;
                case 'RENAME': t.name = this.i18n('Rename'); break;
            }
            if ((this.mode === 'ARTIFACT' && t.mode === 'ARTIFACT') ||
                (this.mode === 'PROJECT' && (t.mode === 'PROJECT' || t.mode === 'ARTIFACT')) ||
                (this.mode === 'MANAGEMENT')) {
                return true;
            } else { return false; }
        });

        return types.sort((t1, t2) => {
            if (t1.name.toLowerCase() > t2.name.toLowerCase()) { return 1; }
            if (t1.name.toLowerCase() < t2.name.toLowerCase()) { return -1; }
            return 0;
        });
    }

    isRtl(): Boolean {
        return (navigator.language.search('ar') !== -1 ? true : false);
    }

    private cleanse() {
        if (this.userName && (this.userName === 'null' || this.userName === 'undefined')) { this.userName = undefined; }
        if (this.projectName && (this.projectName === 'null' || this.projectName === 'undefined')) { this.projectName = undefined; }
        if (this.actions.value && (this.actions.value === 'null' || this.actions.value === 'undefined')) { this.actions.reset(); }
    }
}
