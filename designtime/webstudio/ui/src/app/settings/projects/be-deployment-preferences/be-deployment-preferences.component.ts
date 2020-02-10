/**
 * @Author: Rahil Khera
 * @Date:   2018-01-20T12:16:48+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2018-01-20T12:17:04+05:30
 */

import {
  AfterViewChecked,
  ChangeDetectorRef,
  Component,
  OnInit,
  ViewChild
} from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { AddEditBEDeployment, AddEditBEDeploymentPreferences } from './add-edit-be-deployment-preferences.component';

import { ProjectService } from '../../../core/project.service';
import { RestService } from '../../../core/rest.service';
import { DeploymentPreferences } from '../../../models-be/deployment-preferences.modal';
import { InputWithConfirmComponent } from '../../../shared/input-with-confirm.component';

@Component({
  selector: 'be-deployment-preferences',
  templateUrl: './be-deployment-preferences.component.html',
  styleUrls: ['./be-deployment-preferences.component.css']
})

export class BEDeploymentPreferences implements OnInit {

  projects: string[] = [];
  deploymentPreferenceList: Array<DeploymentPreferences>;
  oldDeploymentPreferenceList: Array<DeploymentPreferences>;
  selectedDeploymentConfiguration: DeploymentPreferences;
  selectedDeploymentConfigurationIndex: number;
  isNewConfiguration: boolean;
  isDeletedConfiguration: boolean;
  isUpdatedConfiguration: boolean;
  updatedDeploymentConfiguration: DeploymentPreferences;
  newDeploymentConfiguration: DeploymentPreferences;
  deletedDeploymentConfiguration: DeploymentPreferences;

  constructor(private rest: RestService,
    private cdRef: ChangeDetectorRef,
    private projectService: ProjectService,
    private addEditDialog: MatDialog,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.init();
  }

  // ngAfterViewChecked() {
  //   this.cdRef.detectChanges();
  // }

  init() {
    this.deploymentPreferenceList = new Array<DeploymentPreferences>();
    this.oldDeploymentPreferenceList = new Array<DeploymentPreferences>();
    this.selectedDeploymentConfiguration = new DeploymentPreferences();
    this.updatedDeploymentConfiguration = new DeploymentPreferences();
    this.newDeploymentConfiguration = new DeploymentPreferences();
    this.deletedDeploymentConfiguration = new DeploymentPreferences();
    this.isNewConfiguration = false;
    this.isDeletedConfiguration = false;
    this.isUpdatedConfiguration = false;
    this.fetchProjectList();
    this.fetchDeploymentConfig();
  }

  fetchDeploymentConfig() {
    this.rest.get('deployConfigs.json', undefined, true)
      .toPromise()
      .then((response) => {
        if (response.ok()) {
          const record = response.record;
          if (record.length > 0) {
            for (let i = 0; i < record.length; i++) {
              const deployConfiguration: DeploymentPreferences = new DeploymentPreferences();
              deployConfiguration.agentName = record[i].agentName;
              deployConfiguration.clusterName = record[i].clusterName;
              deployConfiguration.name = record[i].name;
              deployConfiguration.oldName = record[i].name;
              deployConfiguration.userName = record[i].userName;
              deployConfiguration.password = record[i].password;
              deployConfiguration.enable = record[i].enabled;
              deployConfiguration.inMemory = record[i].inMemory;
              deployConfiguration.host = record[i].host;
              deployConfiguration.port = record[i].port;
              deployConfiguration.projectName = record[i].projectName;
              deployConfiguration.oldObject = {
                agentName: deployConfiguration.agentName,
                clusterName: deployConfiguration.clusterName,
                userName: deployConfiguration.userName,
                password: deployConfiguration.password,
                enable: deployConfiguration.enable,
                inMemory: deployConfiguration.inMemory,
                host: deployConfiguration.host,
                port: deployConfiguration.port,
                projectName: deployConfiguration.projectName
              };
              this.deploymentPreferenceList.push(deployConfiguration);
            }
            this.oldDeploymentPreferenceList = _.cloneDeep(this.deploymentPreferenceList);
            this.onDeploymentConfigurationSelection(0);
          }
        }
      });
  }

  onAddDeploymentConfiguration() {
    const dialogRef: MatDialogRef<AddEditBEDeploymentPreferences, AddEditBEDeployment>
      = this.addEditDialog.open(AddEditBEDeploymentPreferences, {
        width: '350px', data: {
          availableProjects: this.projects,
          deploymentList: this.deploymentPreferenceList,
          currentDeploymentPreference: new DeploymentPreferences(),
          isNew: true
        }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const newDC: DeploymentPreferences = new DeploymentPreferences();
        newDC.agentName = result.currentDeploymentPreference.agentName;
        newDC.enable = result.currentDeploymentPreference.enable;
        newDC.host = result.currentDeploymentPreference.host;
        newDC.port = result.currentDeploymentPreference.port;
        newDC.userName = result.currentDeploymentPreference.userName;
        newDC.password = result.currentDeploymentPreference.password;
        newDC.projectName = result.currentDeploymentPreference.projectName;
        newDC.clusterName = result.currentDeploymentPreference.clusterName;
        newDC.name = result.currentDeploymentPreference.name;
        newDC.inMemory = result.currentDeploymentPreference.inMemory;
        this.deploymentPreferenceList.push(newDC);
        this.selectedDeploymentConfiguration = this.deploymentPreferenceList[this.deploymentPreferenceList.length - 1];
        this.isNewConfiguration = true;
        this.newDeploymentConfiguration = result.currentDeploymentPreference;
      }
    });
  }

  onEditDeploymentConfiguration() {
    const dialogRef: MatDialogRef<AddEditBEDeploymentPreferences, AddEditBEDeployment>
      = this.addEditDialog.open(AddEditBEDeploymentPreferences, {
        width: '350px', data: {
          availableProjects: this.projects,
          deploymentList: this.deploymentPreferenceList,
          currentDeploymentPreference: _.cloneDeep(this.selectedDeploymentConfiguration),
          isNew: false
        }
      });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].agentName = result.currentDeploymentPreference.agentName;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].enable = result.currentDeploymentPreference.enable;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].host = result.currentDeploymentPreference.host;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].port = result.currentDeploymentPreference.port;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].userName = result.currentDeploymentPreference.userName;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].password = result.currentDeploymentPreference.password;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].projectName = result.currentDeploymentPreference.projectName;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].clusterName = result.currentDeploymentPreference.clusterName;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].name = result.currentDeploymentPreference.name;
        this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex].inMemory = result.currentDeploymentPreference.inMemory;
        this.selectedDeploymentConfiguration = this.deploymentPreferenceList[this.selectedDeploymentConfigurationIndex];
        this.isUpdatedConfiguration = true;
        this.updatedDeploymentConfiguration = this.selectedDeploymentConfiguration;
      }
    });
  }

  addDeploymentConfiguration() {
    const payload = {
      'request': {
        'data': {
          'deploymentConfig': {
            'deploymentConfigItem': {
              'host': this.newDeploymentConfiguration.host,
              'port': this.newDeploymentConfiguration.port,
              'name': this.newDeploymentConfiguration.name,
              'enabled': this.newDeploymentConfiguration.enable,
              'userName': this.newDeploymentConfiguration.userName,
              'password': this.newDeploymentConfiguration.password,
              'clusterName': this.newDeploymentConfiguration.clusterName,
              'agentName': this.newDeploymentConfiguration.agentName,
              'inMemory': this.newDeploymentConfiguration.inMemory,
              'projectName': this.newDeploymentConfiguration.projectName
            }
          }
        }
      }
    };
    this.rest.post('deployConfigs/add.json', payload, undefined, true)
      .toPromise()
      .then((response) => {
        if (response.ok()) {
          this.updateOldObjectinConfiguration(this.newDeploymentConfiguration);
          this.newDeploymentConfiguration = null;
          this.isNewConfiguration = false;
          this.updateOldPreferenceList();
        }
      });
  }

  confirmAddConfiguration() {
    if (!this.isDuplicateConfiguration(this.selectedDeploymentConfiguration.projectName, this.selectedDeploymentConfiguration.name)) {
      this.deploymentPreferenceList.push(this.selectedDeploymentConfiguration);
      this.newDeploymentConfiguration = this.selectedDeploymentConfiguration;
      this.selectedDeploymentConfiguration.oldName = this.selectedDeploymentConfiguration.name;
      this.selectedDeploymentConfigurationIndex = this.deploymentPreferenceList.length - 1;
      this.isNewConfiguration = true;
    }
  }

  onDeleteDeploymentConfiguration() {
    this.deletedDeploymentConfiguration = this.selectedDeploymentConfiguration;
    this.deploymentPreferenceList.splice(this.selectedDeploymentConfigurationIndex, 1);
    if (this.deploymentPreferenceList.length > 0) {
      this.onDeploymentConfigurationSelection(0);
    } else {
      this.onDeploymentConfigurationSelection(-1);
    }
    this.isDeletedConfiguration = true;
  }

  deleteDeploymentConfiguration() {
    this.rest.delete(`deployConfigs/delete.json?name=${this.deletedDeploymentConfiguration.oldName}&projectName=${this.deletedDeploymentConfiguration.projectName}`, undefined, undefined, true)
      .toPromise()
      .then((response) => {
        if (response.ok()) {
          this.isDeletedConfiguration = false;
          this.deleteDeploymentConfiguration = null;
          this.updateOldPreferenceList();
        }
      });
  }

  updateDeploymentConfiguration() {
    const payload = {
      'request': {
        'data': {
          'deploymentConfig': {
            'deploymentConfigItem': {
              'host': this.updatedDeploymentConfiguration.host,
              'port': this.updatedDeploymentConfiguration.port,
              'name': this.updatedDeploymentConfiguration.name,
              'enabled': this.updatedDeploymentConfiguration.enable,
              'userName': this.updatedDeploymentConfiguration.userName,
              'password': this.updatedDeploymentConfiguration.password,
              'clusterName': this.updatedDeploymentConfiguration.clusterName,
              'agentName': this.updatedDeploymentConfiguration.agentName,
              'inMemory': this.updatedDeploymentConfiguration.inMemory,
              'projectName': this.updatedDeploymentConfiguration.projectName
            }
          }
        }
      }
    };
    this.rest.put(`deployConfigs/update.json?name=${this.updatedDeploymentConfiguration.oldName}&projectName=${this.updatedDeploymentConfiguration.oldObject['projectName']}`, payload, undefined, true)
      .toPromise()
      .then((response) => {
        if (response.ok()) {
          this.updateOldObjectinConfiguration(this.updatedDeploymentConfiguration);
          this.isUpdatedConfiguration = false;
          this.updateOldPreferenceList();
          this.updatedDeploymentConfiguration = null;
        }
      });

  }

  onApply() {
    if (this.isNewConfiguration) {
      this.addDeploymentConfiguration();
    }
    if (this.isUpdatedConfiguration) {
      this.updateDeploymentConfiguration();
    }
    if (this.isDeletedConfiguration) {
      this.deleteDeploymentConfiguration();
    }
  }

  updateOldPreferenceList() {
    if (!(this.isNewConfiguration || this.isDeletedConfiguration || this.isUpdatedConfiguration)) {
      this.oldDeploymentPreferenceList = _.cloneDeep(this.deploymentPreferenceList);
    }
  }

  onDeploymentConfigurationSelection(i: number) {
    this.selectedDeploymentConfigurationIndex = i;
    if (i > -1) {
      this.selectedDeploymentConfiguration = this.deploymentPreferenceList[i];
    }
  }

  get allowEdit(): boolean {
    if (this.selectedDeploymentConfiguration.isAdded) {
      return true;
    } else if (this.isNewConfiguration) {
      return false;
    }

    if (this.isDeletedConfiguration) {
      return false;
    }

    if (this.isUpdatedConfiguration) {
      return this.isUpdated(this.selectedDeploymentConfiguration);
    } else {
      return true;
    }
  }

  fetchProjectList() {
    this.projectService.getAllProjects()
      .then(projects => {
        projects.map(p => (this.projects.push(p.projectName)));
      });
  }

  isUpdated(deployConfiguration): boolean {
    if (deployConfiguration.isAdded) {
      return false;
    }
    return deployConfiguration.oldName !== deployConfiguration.name
      || deployConfiguration.enable !== deployConfiguration.oldObject.enable
      || deployConfiguration.inMemory !== deployConfiguration.oldObject.inMemory
      || deployConfiguration.host !== deployConfiguration.oldObject.host
      || deployConfiguration.port !== deployConfiguration.oldObject.port
      || deployConfiguration.userName !== deployConfiguration.oldObject.userName
      || deployConfiguration.password !== deployConfiguration.oldObject.password
      || deployConfiguration.agentName !== deployConfiguration.oldObject.agentName
      || deployConfiguration.clusterName !== deployConfiguration.oldObject.clusterName
      || deployConfiguration.projectName !== deployConfiguration.oldObject.projectName;
  }

  isDuplicateConfiguration(configProjectName: string, configName: string): boolean {
    return this.deploymentPreferenceList.find(
      config => (config.projectName.trim() === configProjectName.trim()
        && config.name.trim().toUpperCase() === configName.trim().toUpperCase()
      ))
      ? true : false;
  }

  get disableApply(): boolean {
    return _.isEqual(this.deploymentPreferenceList, this.oldDeploymentPreferenceList);
  }

  /**
   * After, successfull updation and addition of the configuration, the old Object
   * present in the configuration should be updated with the new values to avoid
   * false marking of configuration object as updated. To check if the configuration
   * object is updated or not, it's current field value is compared with the
   * ones present in its old project.
   */

  updateOldObjectinConfiguration(deployConfiguration: DeploymentPreferences) {
    deployConfiguration.oldName = deployConfiguration.name;
    deployConfiguration.isAdded = false;
    console.log('Old object before updation: ' + deployConfiguration.oldObject);
    deployConfiguration.oldObject = {
      enable: deployConfiguration.enable,
      inMemory: deployConfiguration.inMemory,
      host: deployConfiguration.host,
      port: deployConfiguration.port,
      userName: deployConfiguration.userName,
      password: deployConfiguration.password,
      agentName: deployConfiguration.agentName,
      clusterName: deployConfiguration.clusterName,
      projectName: deployConfiguration.projectName
    };
    console.log('Old object after updation' + deployConfiguration.oldObject);
  }

  isEqual(config: DeploymentPreferences): boolean {
    return _.isEqual(config, this.selectedDeploymentConfiguration);
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
