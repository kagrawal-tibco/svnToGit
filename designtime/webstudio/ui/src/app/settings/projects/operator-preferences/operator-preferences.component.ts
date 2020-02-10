/**
 * @Author: Rahil Khera
 * @Date:   2017-08-03T09:28:27+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-03T11:20:08+05:30
 */

import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { BEOperatorService } from '../../../core-be/be.operator.service';
import { RestService } from '../../../core/rest.service';
import { CommandOperator } from '../../../editors/rule-template-instance-builder/CommandOperators';
import { FilterOperator } from '../../../editors/rule-template-instance-builder/FilterOperators';
import { OperatorTypes } from '../../../editors/rule-template-instance-builder/OperatorTypes';
import { OperatorPreference } from '../../../models-be/operator-preferences.modal';

@Component({
  selector: 'operator-preference',
  templateUrl: './operator-preferences.component.html',
  styleUrls: ['./operator-preferences.component.css']
})

export class OperatorPreferenceComponent {

  get disableApply(): boolean {
    return _.isEqual(this.operatorPreferenceList, this.previousOperatorPreferenceList);
  }
  private static filterOperatorPreferenceMap: Map<string, OperatorTypes[]> = new Map<string, OperatorTypes[]>();
  private static commandOperatorPreferenceMap: Map<string, OperatorTypes[]> = new Map<string, OperatorTypes[]>();

  selectedFieldTypeIndex = -1;
  selectedFieldType: OperatorPreference;
  operatorPreferenceList: Array<OperatorPreference>;
  previousOperatorPreferenceList: Array<OperatorPreference>;

  constructor(private rest: RestService, private operator: BEOperatorService, public i18n: I18n) {
    this.init();
  }

  init() {
    this.selectedFieldType = new OperatorPreference('', new Array<OperatorTypes>(), new Array<OperatorTypes>());
    this.operatorPreferenceList = new Array<OperatorPreference>();
    this.rest.get('preferences/app.json')
      .toPromise().then((response) => {
        if (response.ok()) {
          const operatorPreferenceList = new Array<OperatorPreference>();
          const jsonOperatorPreferenceList = response.record[0].operatorPreferences;
          /**
           * When the operator settings are set, the server will provide operator preference.
           * Hence, if will be executed.
           * For first time, setting are not set, the response is empty. Hence, else will be executed.
           * I.e. Default Value.
           */
          if (jsonOperatorPreferenceList != null) {
            for (let i = 0; i < jsonOperatorPreferenceList.length; i++) {
              const operatorPreference = new OperatorPreference(jsonOperatorPreferenceList[i].fieldType);
              operatorPreference.filterOperators = _.cloneDeep(FilterOperator.getOperators(operatorPreference.fieldType));
              operatorPreference.commandOperators = _.cloneDeep(CommandOperator.getOperators(operatorPreference.fieldType));
              if (jsonOperatorPreferenceList[i].filterOperators) {
                for (let j = 0; j < operatorPreference.filterOperators.length; j++) {
                  const currentOperator = operatorPreference.filterOperators[j];
                  if (jsonOperatorPreferenceList[i].filterOperators.indexOf(currentOperator.operator) > -1) {
                    currentOperator.available = true;
                  }
                }
              }
              if (jsonOperatorPreferenceList[i].commandOperators) {
                for (let j = 0; j < operatorPreference.commandOperators.length; j++) {
                  const currentOperator = operatorPreference.commandOperators[j];
                  if (jsonOperatorPreferenceList[i].commandOperators.indexOf(currentOperator.operator) > -1) {
                    currentOperator.available = true;
                  }
                }
              }
              operatorPreferenceList.push(operatorPreference);
            }
            this.operatorPreferenceList = operatorPreferenceList;
          } else {
            FilterOperator.intialize();
            CommandOperator.initialize();
            this.defaultOperatorPreferences();
            this.operator.latestOperatorPreference = this.operatorPreferenceList;
          }
          this.previousOperatorPreferenceList = new Array<OperatorPreference>();
          this.previousOperatorPreferenceList = _.cloneDeep(this.operatorPreferenceList);
          this.selectedFieldTypeIndex = 0;
          this.selectedFieldType = this.operatorPreferenceList[0];
          return true;
        } else {
          console.log('Failed');
          return false;
        }
      });
  }

  updateOperatorPreferences() {
    this.previousOperatorPreferenceList = _.cloneDeep(this.operatorPreferenceList);
    this.operator.latestOperatorPreference = this.operatorPreferenceList;
    const url = `preferences/app/update.json`;
    const payload = OperatorPreference.getSaveJson(this.operatorPreferenceList);
    return this.rest.put(url, payload)
      .toPromise()
      .then(res => {
        if (res.ok()) {
          return true;
        } else {
          return false;
        }
      });
  }

  onFieldTypeSelection(index: number) {
    this.selectedFieldType = this.operatorPreferenceList[index];
    this.selectedFieldTypeIndex = index;
  }

  getImageSrc(fieldType: string): string {
    switch (fieldType) {
      case 'integer': return 'assets/img/iconInteger16.gif';
      case 'String': return 'assets/img/iconString16.gif';
      case 'long': return 'assets/img/iconLong16.gif';
      case 'DateTime': return 'assets/img/iconDate16.gif';
      case 'boolean': return 'assets/img/iconBoolean16.gif';
      case 'double': return 'assets/img/iconReal16.gif';
      case 'Concept/Event': return 'assets/img/iconConcept16.gif';
    }
  }

  defaultOperatorPreferences() {
    const fieldType: string[] = ['String', 'integer', 'long', 'double', 'boolean', 'DateTime', 'Concept/Event'];
    for (let i = 0; i < fieldType.length; i++) {
      const operatorPreference = new OperatorPreference(fieldType[i]);
      operatorPreference.filterOperators = _.cloneDeep(FilterOperator.getOperators(operatorPreference.fieldType));
      operatorPreference.commandOperators = _.cloneDeep(CommandOperator.getOperators(operatorPreference.fieldType));
      for (let j = 0; j < operatorPreference.filterOperators.length; j++) {
        operatorPreference.filterOperators[j].available = true;
      }
      for (let j = 0; j < operatorPreference.commandOperators.length; j++) {
        operatorPreference.commandOperators[j].available = true;
      }
      this.operatorPreferenceList.push(operatorPreference);
    }
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
