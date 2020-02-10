/**
 * @Author: Rahil Khera
 * @Date:   2017-08-03T10:45:01+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-03T11:41:43+05:30
 */
import { OperatorTypes } from '../editors/rule-template-instance-builder/OperatorTypes';

export class OperatorPreference {

  set fieldType(value: string) {
    this._fieldType = value;
  }

  get fieldType(): string {
    return this._fieldType;
  }

  set filterOperators(value: Array<OperatorTypes>) {
    this._filterOperators = value;
  }

  get filterOperators(): Array<OperatorTypes> {
    if (this._filterOperators == null) {
      this.filterOperators = new Array<OperatorTypes>();
    }
    return this._filterOperators;
  }

  set commandOperators(value: Array<OperatorTypes>) {
    this._commandOperators = value;
  }

  get commandOperators(): Array<OperatorTypes> {
    if (this._commandOperators == null) {
      this.commandOperators = new Array<OperatorTypes>();
    }
    return this._commandOperators;
  }
  private _fieldType: string;
  private _filterOperators: Array<OperatorTypes>;
  private _commandOperators: Array<OperatorTypes>;

  constructor(fieldType: string, filterOperators?: Array<OperatorTypes>, commandOperators?: Array<OperatorTypes>) {
    this.fieldType = fieldType;
    this.filterOperators = filterOperators;
    this.commandOperators = commandOperators;
  }

  // public static getSaveJson(operatorPreferenceList: BehaviorSubject<Array<OperatorPreference>>): any {
  //   let payload =
  //     {
  //       "request": {
  //         "data": {
  //           "applicationPreference": {
  //             "applicationPreferenceItem":{
  //                 "operatorPreferences": OperatorPreference.operatorPreferenceListToString(operatorPreferenceList.getValue())
  //             }
  //           }
  //         }
  //       }
  //     }
  //   return payload;
  // }

  public static getSaveJson(operatorPreferenceList: Array<OperatorPreference>): any {
    const payload = {
      'request': {
        'data': {
          'applicationPreference': {
            'applicationPreferenceItem': {
              'operatorPreferences': OperatorPreference.operatorPreferenceListToString(operatorPreferenceList)
            }
          }
        }
      }
    };
    return payload;
  }

  private static enabledOperatorListToString(operatorTypes: Array<OperatorTypes>): any[] {
    const jsonOperators = [];
    for (let i = 0; i < operatorTypes.length; i++) {
      if (operatorTypes[i].available) {
        jsonOperators.push(operatorTypes[i].operator);
      }
    }
    return jsonOperators;
  }

  private static operatorPreferenceListToString(operatorPreferenceList: Array<OperatorPreference>): any[] {
    const operatorPreference = [];
    for (let i = 0; i < operatorPreferenceList.length; i++) {
      const projectEntry = {
        fieldType: operatorPreferenceList[i].fieldType,
        filterOperators: OperatorPreference.enabledOperatorListToString(operatorPreferenceList[i].filterOperators),
        commandOperators: OperatorPreference.enabledOperatorListToString(operatorPreferenceList[i].commandOperators)
      };

      operatorPreference.push(projectEntry);
    }
    return operatorPreference;
  }
}
