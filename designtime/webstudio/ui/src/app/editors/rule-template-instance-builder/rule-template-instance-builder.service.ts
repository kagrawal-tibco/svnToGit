/**
 * @Author: Rahil Khera
 * @Date:   2017-06-13T10:28:46+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-06-13T10:33:54+05:30
 */

import { Injectable } from '@angular/core';

import { BuilderSubClauseImpl } from './BuilderSubClauseImpl';
import { MultiFilterClient } from './MultiFilterClient';
import { MultiFilterImpl } from './MultiFilterImpl';
import { MatchType } from './MultiFilterImpl';
import { SingleFilterClient } from './SingleFilterClient';
import { SingleFilterImpl } from './SingleFilterImpl';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder.component';

import { Properties } from '../../artifact-editor/properties';
import { RuleTemplateInstanceImpl } from '../../editables/rule-template-instance/rule-template-instance';
import { EditAction } from '../edit-action';

@Injectable()
export class RuleTemplateInstanceBuilderEditorService {

  addSingleFilter(newSingleFilterClient: SingleFilterClient,
    multiFilterClient: MultiFilterClient,
    callAction: boolean,
    position: number): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RuleTemplateInstanceBuilder) => {
        if (callAction) {
          const filterId = multiFilterClient.multiFilter.getFilterId();
          if (context.multiFilterclientMap.has(filterId)) {
            multiFilterClient = context.multiFilterclientMap.get(filterId);
          }
          newSingleFilterClient = multiFilterClient.onAddSingleFilter(null, newSingleFilterClient.filter, position);
        } else {
          callAction = true;
        }
        context.params.editBuffer.markForDirtyCheck();
      },
      revert: (context: RuleTemplateInstanceBuilder) => {
        position = newSingleFilterClient.onDeleteFilter(newSingleFilterClient);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }

  addMultiFilter(newMultiFilterClient: MultiFilterClient,
    parentMultiFilterClient: MultiFilterClient,
    callAction: boolean, position: number): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RuleTemplateInstanceBuilder) => {
        if (callAction) {
          newMultiFilterClient = parentMultiFilterClient.onAddMultiFilter(null, newMultiFilterClient._multiFilter, position);
          context.multiFilterclientMap.set(newMultiFilterClient.multiFilter.getFilterId(), newMultiFilterClient);
        } else {
          callAction = true;
        }
        context.params.editBuffer.markForDirtyCheck();
      },
      revert: (context: RuleTemplateInstanceBuilder) => {
        position = parentMultiFilterClient.onDeleteMultiFilter(newMultiFilterClient);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }

  deleteMultiFilter(oldMultiFilterClient: MultiFilterClient,
    parentMultiFilterClient: MultiFilterClient,
    callAction: boolean,
    position: number)
    : EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RuleTemplateInstanceBuilder) => {
        if (callAction) {
          position = parentMultiFilterClient.onDeleteMultiFilter(oldMultiFilterClient);
        } else {
          callAction = true;
        }
        context.params.editBuffer.markForDirtyCheck();
      },
      revert: (context: RuleTemplateInstanceBuilder) => {
        oldMultiFilterClient = parentMultiFilterClient.onAddMultiFilter(null, oldMultiFilterClient._multiFilter, position);
        context.multiFilterclientMap.set(oldMultiFilterClient._multiFilter.getFilterId(), oldMultiFilterClient);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }

  deleteSingleFilter(oldSingleFilterClient: SingleFilterClient,
    parentMultiFilterClient: MultiFilterClient,
    callAction: boolean,
    position: number): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RuleTemplateInstanceBuilder) => {
        if (callAction) {
          position = oldSingleFilterClient.onDeleteFilter(oldSingleFilterClient);
        } else {
          callAction = true;
        }
        context.params.editBuffer.markForDirtyCheck();
      },
      revert: (context: RuleTemplateInstanceBuilder) => {
        const filterId = parentMultiFilterClient._multiFilter.getFilterId();
        parentMultiFilterClient = context.multiFilterclientMap.get(filterId);
        oldSingleFilterClient = parentMultiFilterClient.onAddSingleFilter(null, oldSingleFilterClient.filter, position);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }

  modifySingleFilter(oldSingleFilter: SingleFilterImpl,
    newSingleFilter: SingleFilterImpl,
    singleFilterClient: SingleFilterClient,
    builderSubClauseImpl: BuilderSubClauseImpl
  ): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RuleTemplateInstanceBuilder) => {
        singleFilterClient.updateSingleFilterInEditBuffer(newSingleFilter, builderSubClauseImpl);
        context.params.editBuffer.markForDirtyCheck();
      },
      revert: (context: RuleTemplateInstanceBuilder) => {
        singleFilterClient.updateSingleFilterInEditBuffer(oldSingleFilter, builderSubClauseImpl);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }

  modifyMultiFilter(oldMatchType: MatchType,
    newMatchType: MatchType,
    multiFilterImpl: MultiFilterImpl): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RuleTemplateInstanceBuilder) => {
        multiFilterImpl.setMatchType(newMatchType);
        context.params.editBuffer.markForDirtyCheck();
      },
      revert: (context: RuleTemplateInstanceBuilder) => {
        multiFilterImpl.setMatchType(oldMatchType);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }

  modifyProperties(oldProperties: Properties, newProperties: Properties): EditAction<RuleTemplateInstanceImpl> {
    return {
      execute: (context: RuleTemplateInstanceBuilder) => {
        context.base.setRulepriority(newProperties.rtiPriority);
        context.base.setDescription(newProperties.rtiDescription);
        context.params.editBuffer.markForDirtyCheck();

      },
      revert: (context: RuleTemplateInstanceBuilder) => {
        context.base.setRulepriority(oldProperties.rtiPriority);
        context.base.setDescription(oldProperties.rtiDescription);
        context.params.editBuffer.markForDirtyCheck();
      }
    };
  }

}
