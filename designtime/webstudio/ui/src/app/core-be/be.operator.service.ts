import { Injectable } from '@angular/core';

import * as _ from 'lodash';
import { BehaviorSubject } from 'rxjs';

import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { CommandOperator } from '../editors/rule-template-instance-builder/CommandOperators';
import { FilterOperator } from '../editors/rule-template-instance-builder/FilterOperators';
import { OperatorPreference } from '../models-be/operator-preferences.modal';

@Injectable()
export class BEOperatorService {

  protected operatorSettings = new BehaviorSubject<OperatorPreference[]>(this.defaultOperatorPreference);

  constructor(protected rest: RestService,
    protected log: Logger) {

  }

  get defaultOperatorPreference() {
    return new Array<OperatorPreference>();
  }

  init(): Promise<boolean> {
    FilterOperator.intialize();
    CommandOperator.initialize();
    this.initNotification();
    return this.initImp();
  }

  initNotification() {
    this.rest.get('preferences/notify.json')
      .toPromise().then((response) => {
      });
  }

  initImp(): Promise<boolean> {
    return this.rest.get('preferences/app.json')
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
          }
          this.latestOperatorPreference = operatorPreferenceList;
          // this.operatorSettings.next(operatorPreferenceList);
          return true;
        } else {
          // console.log('Failed');
          return false;
        }
      });
  }

  get latestOperatorPreference(): Array<OperatorPreference> {
    return this.operatorSettings.value;
  }

  set latestOperatorPreference(value: Array<OperatorPreference>) {
    this.operatorSettings.next(value);
  }

}
