import * as _ from 'lodash';

import { ModificationEntry } from './ModificationEntry';
import { ModificationType } from './ModificationType';

import { Filter } from '../../../editors/rule-template-instance-builder/Filter';
import { MultiFilterImpl } from '../../../editors/rule-template-instance-builder/MultiFilterImpl';
import { RelatedFilterValueImpl } from '../../../editors/rule-template-instance-builder/RelatedFilterValueImpl';
import { SimpleFilterValueImpl } from '../../../editors/rule-template-instance-builder/SimpleFilterValueImpl';
import { SingleFilterImpl } from '../../../editors/rule-template-instance-builder/SingleFilterImpl';
import { BindingInfo } from '../../../editors/rule-template-instance-view/BindingInfo';

export class DiffHelper {

  public static getFilterAsString(filter: SingleFilterImpl): string {
    if (filter == null) {
      return '';
    }
    let links = '';
    const relatedLinkImplList = filter.getLink();
    if (relatedLinkImplList) {
      for (let i = 0; i < relatedLinkImplList.length; i++) {
        links += (i === 0 ? ' ' : '\'s ') + relatedLinkImplList[i].getName();
      }
    }
    const operator: string = filter.getOperator();
    let filterValue = '';
    if (filter.getValue() instanceof SimpleFilterValueImpl) {
      const value: SimpleFilterValueImpl = <SimpleFilterValueImpl>filter.getValue();
      if (value) {
        filterValue = value.value;
      }
    } else if (filter.getValue() instanceof RelatedFilterValueImpl) {
      const value: RelatedFilterValueImpl = <RelatedFilterValueImpl>filter.getValue();
      const relatedLinkImplValueList = value.getLinks();
      for (let i = 0; i < relatedLinkImplValueList.length; i++) {
        filterValue += (i === 0 ? '' : '\'s ') + relatedLinkImplValueList[i].getName();
      }
    }
    return links + ' ' + operator + ' ' + filterValue;
  }

  public static processMultiFilterDiff(multiFilter_v1: MultiFilterImpl, multiFilter_v2: MultiFilterImpl,
    modifications: Map<String, ModificationEntry>, checkMatchType?: boolean): void {
    if (multiFilter_v2.getBuilderSubClause() != null
      && multiFilter_v2.getBuilderSubClause().getFilters() != null) {
      const oldMatchType = multiFilter_v1.getMatchType();
      const newMatchType = multiFilter_v2.getMatchType();
      if (checkMatchType && !_.isEqual(oldMatchType, newMatchType)) {
        modifications.set(multiFilter_v2.getFilterId(), new ModificationEntry(ModificationType.MODIFIED, oldMatchType, newMatchType));
      }
      DiffHelper.processFiltersDiff(multiFilter_v1.getBuilderSubClause().getFilters(),
        multiFilter_v2.getBuilderSubClause().getFilters(),
        modifications, checkMatchType);
    }
  }

  public static processFiltersDiff(filterList_v1: Array<Filter>, filterList_v2: Array<Filter>,
    modifications: Map<String, ModificationEntry>, checkMatchType?: boolean): void {
   /**
    * Indexes of identified deleted filters (index in version1, index to be
    * added to in version2).
    */
    const deletedFilterIndices: Map<number, number> = new Map<number, number>();
    let newPointer = 0;
    for (let i = 0; i < filterList_v1.length; i++) {
      if (filterList_v1[i] != null && newPointer < filterList_v2.length
        && filterList_v2[newPointer] != null
        && filterList_v1[i].getFilterId() != null && filterList_v1[i].getFilterId() !== 'null'
        && filterList_v1[i].getFilterId() === filterList_v2[newPointer].getFilterId()) {
        if (filterList_v1[i] instanceof MultiFilterImpl
          && filterList_v2[newPointer] instanceof MultiFilterImpl) {// FilterId matched
          DiffHelper.processMultiFilterDiff(<MultiFilterImpl>filterList_v1[i], <MultiFilterImpl>filterList_v2[newPointer], modifications, checkMatchType);
        } else {
          if (DiffHelper.isFilterModified(<SingleFilterImpl>filterList_v1[i], <SingleFilterImpl>filterList_v2[newPointer])) {// MODIFIED
            modifications.set(filterList_v2[newPointer].getFilterId(),
              new ModificationEntry(ModificationType.MODIFIED, null, null, filterList_v1[i], filterList_v2[newPointer]));
          }
        }
        newPointer++;
      } else {// FilterId didn't match, i.e DELETED from v1
        modifications.set(filterList_v1[i].getFilterId(), new ModificationEntry(ModificationType.DELETED));
        deletedFilterIndices.set(i, newPointer);
      }
    }
    // ADDED
    for (let i = newPointer; i < filterList_v2.length; i++) {
      modifications.set(filterList_v2[i].getFilterId(), new ModificationEntry(ModificationType.ADDED));
    }

  /**
   * 	 This variable maintains count of additions to version2 list, since
   *  after each addition the index to-add-to will change.
   */
    let counter = 0;
    deletedFilterIndices.forEach((value: number, key: number) => {
      filterList_v2.splice(value + counter++, 0, filterList_v1[key]);
    });
  }

  /**
 	 * Process diff between the 2 lists of RTIView BindingInfos.
 	 * @param bindingInfos_v1
 	 * @param bindingInfos_v2
 	 * @param modifications
 	 */
  public static processRTIViewDiff(bindingInfos_v1: Array<BindingInfo>,
    bindingInfos_v2: Array<BindingInfo>,
    modifications: Map<BindingInfo, ModificationEntry>): void {
    const bindings_v1: Map<string, BindingInfo> = new Map<string, BindingInfo>();
    for (const v1 of bindingInfos_v1) {
      bindings_v1.set(v1.getBindingId(), v1);
    }
    for (const v2 of bindingInfos_v2) {
      const v1: BindingInfo = bindings_v1.get(v2.getBindingId());
      if (v1 != null && v2 != null
        && (!_.isEqual(v1.getValue(), v2.getValue()))) {
        try {
          modifications.set(v2, new ModificationEntry(ModificationType.MODIFIED, v1.getValue() == null ? '' : v1.getValue(), v2.getValue()));
        } catch (error) {
          // console.log(error);
        }
      }
    }
  }

  private static isFilterModified(filterV1: SingleFilterImpl,
    filterV2: SingleFilterImpl): boolean {
    if (_.isEqual(DiffHelper.getFilterAsString(filterV1).toLowerCase(),
      DiffHelper.getFilterAsString(filterV2).toLowerCase())) {
      return false;
    }
    return true;
  }
}
