/**
 * @Author: Rahil Khera
 * @Date:   2017-06-12T18:44:53+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-06-13T10:31:55+05:30
 */

import { Injectable } from '@angular/core';

import { RTIViewClient } from './RTIViewClient';

import { Properties } from '../../artifact-editor/properties';
import { RuleTemplateInstanceImpl } from '../../editables/rule-template-instance/rule-template-instance';
import { EditAction } from '../edit-action';

@Injectable()
export class RuleTemplateInstanceViewEditorService {

  propertyEditAction(bindingId: string, oldValue: string, newValue: string, elementId: string): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RTIViewClient) => {
        const rtiView = context.params.editBuffer.getBuffer().getView();
        if (rtiView) {
          const bindingInfoList = rtiView.getBindingInfo();
          for (let i = 0; i < bindingInfoList.length; i++) {
            if (bindingInfoList[i].getBindingId() === bindingId) {
              bindingInfoList[i].setValue(newValue);
              (<HTMLInputElement>document.getElementById(elementId)).value = newValue;
              break;
            }
          }
          context.params.editBuffer.markForDirtyCheck();
        }
      },
      revert: (context: RTIViewClient) => {
        const rtiView = context.params.editBuffer.getBuffer().getView();
        if (rtiView) {
          const bindingInfoList = rtiView.getBindingInfo();
          for (let i = 0; i < bindingInfoList.length; i++) {
            if (bindingInfoList[i].getBindingId() === bindingId) {
              bindingInfoList[i].setValue(oldValue);
              (<HTMLInputElement>document.getElementById(elementId)).value = oldValue;
              break;
            }
          }
          context.params.editBuffer.markForDirtyCheck();
        }
      }
    };
  }

  modifyProperties(oldProperties: Properties, newProperties: Properties): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RTIViewClient) => {
        context.base.setRulepriority(newProperties.rtiPriority);
        context.base.setDescription(newProperties.rtiDescription);
        context.params.editBuffer.markForDirtyCheck();

      },
      revert: (context: RTIViewClient) => {
        context.base.setRulepriority(oldProperties.rtiPriority);
        context.base.setDescription(oldProperties.rtiDescription);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }
}
