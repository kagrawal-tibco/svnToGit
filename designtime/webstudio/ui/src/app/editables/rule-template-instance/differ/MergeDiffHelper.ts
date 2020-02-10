import { DiffHelper } from './DiffHelper';
import { MergedDiffModificationEntry } from './MergedDiffModificationEntry';
import { ModificationEntry } from './ModificationEntry';
import { ModificationType } from './ModificationType';

import { MultiFilterImpl } from '../../../editors/rule-template-instance-builder/MultiFilterImpl';
import { SingleFilterClient } from '../../../editors/rule-template-instance-builder/SingleFilterClient';
import { BindingInfo } from '../../../editors/rule-template-instance-view/BindingInfo';

export class MergedDiffHelper {

  private static isConflict: boolean;
  private static viewLocalChanges: boolean;
  private static builderLocalChanges: boolean;
  /*
  * multiFilter_v1: base copy.
  * multiFilter_v2: local copy.
  * multiFilter_v3: current server copy.
  */
  public static processMultiFilterMergedDiff(multiFilter_v1: MultiFilterImpl,
    multiFilter_v2: MultiFilterImpl,
    multiFilter_v3: MultiFilterImpl,
    modifications: Map<string, ModificationEntry>): MultiFilterImpl {
    /**
     * Map to collect local changes i.e between v1 and v2.
     */
    const modifications_i1: Map<string, ModificationEntry> = new Map<string, ModificationEntry>();

    DiffHelper.processFiltersDiff(multiFilter_v1.getBuilderSubClause().getFilters(),
      multiFilter_v2.getBuilderSubClause().getFilters(),
      modifications_i1); // Find out Local changes.

    const modifications_i2: Map<string, ModificationEntry> = new Map<string, ModificationEntry>();
    DiffHelper.processFiltersDiff(multiFilter_v1.getBuilderSubClause().getFilters(),
      multiFilter_v3.getBuilderSubClause().getFilters(),
      modifications_i2); // Find out server side changes

    /**
     * Map to collect server side changes i.e between v1 and v3.
     */
    const ignore: Map<string, ModificationEntry> = new Map<string, ModificationEntry>();
    DiffHelper.processFiltersDiff(multiFilter_v2.getBuilderSubClause().getFilters(),
      multiFilter_v3.getBuilderSubClause().getFilters(),
      ignore); // Merge all Filters into server copy ignoring modifications.

    MergedDiffHelper.mergeFilterDiffs(modifications_i1, modifications_i2, modifications);
    return multiFilter_v3;
  }

  /**
   * For RTI Builder
   * Checks wheter any conflicts are remaining in the modificationList.
   * @param modificationList
   * @return boolean
   */
  public static hasConflict(modificationsList: Map<string, MergedDiffModificationEntry>): boolean {
    MergedDiffHelper.isConflict = false;
    modificationsList.forEach((value: MergedDiffModificationEntry, key: string) => {
      if (MergedDiffHelper.isFilterConflict(value) && !value.isApplied()) {
        MergedDiffHelper.isConflict = true;
      }
    });
    return MergedDiffHelper.isConflict;
  }

  /**
   * For RTIBuilder
   * Check wheter there are any local changes.
   * If yes, set synchronization strategy as merge.
   * Else, set synchronization strategy as local.
   * @param modificationList
   * @return boolean
   */
  public static hasBuilderLocalChanges(modificationsList: Map<string, MergedDiffModificationEntry>): boolean {
    MergedDiffHelper.builderLocalChanges = false;
    modificationsList.forEach((value: MergedDiffModificationEntry, key: string) => {
      if (value.isLocalChange()) {
        MergedDiffHelper.builderLocalChanges = true;
        return;
      }
    });
    return MergedDiffHelper.builderLocalChanges;
  }

  /**
   * For RTI View
   * Checks wheter any conflicts are remaining in the modificationList.
   * @param modificationList
   * @return boolean
   */
  public static hasViewConflict(modificationsList: Map<BindingInfo, MergedDiffModificationEntry>): boolean {
    MergedDiffHelper.isConflict = false;
    modificationsList.forEach((value: MergedDiffModificationEntry, key: BindingInfo) => {
      if (MergedDiffHelper.isBindingConflict(value) && !value.isApplied()) {
        MergedDiffHelper.isConflict = true;
      }
    });
    return MergedDiffHelper.isConflict;
  }

  /**
   * For RTIView
   * Check wheter there are any local changes.
   * If yes, set synchronization strategy as merge.
   * Else, set synchronization strategy as local.
   * @param modificationList
   * @return boolean
   */
  public static hasViewLocalChanges(modificationsList: Map<BindingInfo, MergedDiffModificationEntry>): boolean {
    MergedDiffHelper.viewLocalChanges = false;
    modificationsList.forEach((value: MergedDiffModificationEntry, key: BindingInfo) => {
      if (value.isLocalChange()) {
        MergedDiffHelper.viewLocalChanges = true;
        return;
      }
    });
    return MergedDiffHelper.viewLocalChanges;
  }

  /**
 	 * Process diff between 3 versions of a RTI bindings</br>
 	 * viz: User's base copy[v1], user's working copy(local changes)[v2], server copy[v3].</br>
 	 * First compares v1 with v2 then compares v1 with v3.
 	 * @param bindingInfos_v1
 	 * @param bindingInfos_v2
 	 * @param bindingInfos_v3
 	 * @param modifications
 	 */
  public static processRTIViewMergedDiff(
    bindingInfos_v1: Array<BindingInfo>,
    bindingInfos_v2: Array<BindingInfo>,
    bindingInfos_v3: Array<BindingInfo>,
    modifications: Map<BindingInfo, ModificationEntry>): void {

    const modifications_i1: Map<BindingInfo, ModificationEntry> = new Map<BindingInfo, ModificationEntry>();
    DiffHelper.processRTIViewDiff(bindingInfos_v1, bindingInfos_v2, modifications_i1); // Collect local changes.

    const modifications_i2: Map<BindingInfo, ModificationEntry> = new Map<BindingInfo, ModificationEntry>();
    DiffHelper.processRTIViewDiff(bindingInfos_v1, bindingInfos_v3, modifications_i2); // Collect server changes.

    const mappings: Map<string, BindingInfo> = new Map<string, BindingInfo>();
    for (const bi of bindingInfos_v3) {
      mappings.set(bi.getBindingId(), bi);
    }

    MergedDiffHelper.mergeRTIViewDiffs(modifications_i1, modifications_i2, mappings, modifications);
  }

  /**
   * Check if binding has conflict, returns true if there is a conflict. .
   * @param modificationEntry
   * @return boolean
   */
  public static isBindingConflict(modificationEntry: MergedDiffModificationEntry): boolean {
    return modificationEntry.isLocalChange()
      && modificationEntry.isServerChange();
  }

  /**
   * Check if filter has conflict, returns true if there is a conflict. .
   * @param modificationEntry
   * @return conflict
   */
  public static isFilterConflict(modificationEntry: MergedDiffModificationEntry): number {
    let conflict = 0;
    if (modificationEntry.isLocalChange() && modificationEntry.isServerChange()) {
      if (modificationEntry.serverChangeType === ModificationType.DELETED && modificationEntry.localChangeType === ModificationType.MODIFIED) {
        conflict = 2;
      } else if (modificationEntry.serverChangeType === ModificationType.MODIFIED && modificationEntry.localChangeType === ModificationType.MODIFIED) {
        conflict = 1;
      } else if (modificationEntry.serverChangeType === ModificationType.MODIFIED && modificationEntry.localChangeType === ModificationType.DELETED) {
        conflict = 3;
      }
    }
    return conflict;
  }

  /**
   * To get local changes, i.e. local changes only in RTIView.
   * It returns a map of modification list containing modification entries of
   * server side.
   *  @param modificationList
   *  @return filterdModificationList
   */
  public static getRhsDiff(modificationList: Map<BindingInfo, MergedDiffModificationEntry>): Map<BindingInfo, MergedDiffModificationEntry> {
    const filterdModificationList: Map<BindingInfo, MergedDiffModificationEntry> = new Map<BindingInfo, MergedDiffModificationEntry>();
    modificationList.forEach((value: MergedDiffModificationEntry, key: BindingInfo) => {
      if (value.isLocalChange()) {
        filterdModificationList.set(key, value);
      }
    });
    return filterdModificationList;
  }

  /**
   * To get server changes, i.e. server changes only in RTIView.
   * It returns a map of modification list containing modification entries of
   * server side.
   * @param modificationList
   * @return filterdModificationList
   */
  public static getLhsDiff(modificationList: Map<BindingInfo, MergedDiffModificationEntry>): Map<BindingInfo, MergedDiffModificationEntry> {
    const filterdModificationList: Map<BindingInfo, MergedDiffModificationEntry> = new Map<BindingInfo, MergedDiffModificationEntry>();
    modificationList.forEach((value: MergedDiffModificationEntry, key: BindingInfo) => {
      if (value.isServerChange()) {
        filterdModificationList.set(key, value);
      }
    });
    return filterdModificationList;
  }

  /**
   * To get local & server side changes of RTIView.
   * It returns a map of modification list containing modification entries of
   * server side.
   *  @param modificationList
   *  @return filterdModificationList
   */
  public static getConflictModifications(modificationList: Map<BindingInfo, MergedDiffModificationEntry>): Map<BindingInfo, MergedDiffModificationEntry> {
    const filterdModificationList: Map<BindingInfo, MergedDiffModificationEntry> = new Map<BindingInfo, MergedDiffModificationEntry>();
    modificationList.forEach((value: MergedDiffModificationEntry, key: BindingInfo) => {
      if (value.isLocalChange() && value.isServerChange()) {
        filterdModificationList.set(key, value);
      }
    });
    return filterdModificationList;
  }

  /**
   * To get local changes, i.e. server changes only in RTIBuilder.
   * It returns a map of modification list containing modification entries of
   * server side.
   *  @param modificationList
   *  @return filterdModificationList
   */
  public static getRhsBuilderDiff(modificationList: Map<string, MergedDiffModificationEntry>): Map<string, MergedDiffModificationEntry> {
    const filterdModificationList: Map<string, MergedDiffModificationEntry> = new Map<string, MergedDiffModificationEntry>();
    modificationList.forEach((value: MergedDiffModificationEntry, key: string) => {
      if (value.isLocalChange()) {
        filterdModificationList.set(key, value);
      }
    });
    return filterdModificationList;
  }

  /**
   * To get server changes, i.e. server changes only in RTIBuilder.
   * It returns a map of modification list containing modification entries of
   * server side.
   * @param modificationList
   * @return filterdModificationList
   */
  public static getLhsBuilderDiff(modificationList: Map<string, MergedDiffModificationEntry>): Map<string, MergedDiffModificationEntry> {
    const filterdModificationList: Map<string, MergedDiffModificationEntry> = new Map<string, MergedDiffModificationEntry>();
    modificationList.forEach((value: MergedDiffModificationEntry, key: string) => {
      if (value.isServerChange()) {
        filterdModificationList.set(key, value);
      }
    });
    return filterdModificationList;
  }

  /**
   * To get local & server side changes of RTIBuilder.
   * It returns a map of modification list containing modification entries of
   * server side.
   *  @param modificationList
   *  @return filterdModificationList
   */
  public static getBuilderConflictModifications(modificationList: Map<string, MergedDiffModificationEntry>): Map<string, MergedDiffModificationEntry> {
    const filterdModificationList: Map<string, MergedDiffModificationEntry> = new Map<string, MergedDiffModificationEntry>();
    modificationList.forEach((value: MergedDiffModificationEntry, key: string) => {
      if (value.isLocalChange() && value.isServerChange()) {
        filterdModificationList.set(key, value);
      }
    });
    return filterdModificationList;
  }

  public static syncMerge(filterInstance: SingleFilterClient | any, showChangeSet: string, fullModificationList: Map<string, MergedDiffModificationEntry>) {
    let filterId: string;
    if (filterInstance instanceof SingleFilterClient) {
      filterId = filterInstance.filter.getFilterId();
    } else {
      filterId = filterInstance.multiFilter.getFilterId();
    }
    let modificationEntry: MergedDiffModificationEntry;
    let modificationList: Map<string, MergedDiffModificationEntry> = new Map<string, MergedDiffModificationEntry>();
    switch (showChangeSet) {
      case 'lhs':
        modificationList = MergedDiffHelper.getLhsBuilderDiff(fullModificationList);
        break;
      case 'rhs':
        modificationList = MergedDiffHelper.getRhsBuilderDiff(fullModificationList);
        break;
      case 'both':
        modificationList = fullModificationList;
        break;
      case 'none':
        modificationList = MergedDiffHelper.getBuilderConflictModifications(fullModificationList);
    }
    if (modificationList.has(filterId)) {
      modificationEntry = modificationList.get(filterId);
      const conflict = MergedDiffHelper.isFilterConflict(modificationEntry);
      if (conflict > 0) {
        filterInstance.isConflict = true;
        switch (conflict) {
          case 1:
            filterInstance.previousValue = 'was ' + modificationEntry.baseVersion; // + ' Working Copy Value: ' + modificationEntry.localVersion;
            break;
          case 2:
            filterInstance.previousValue = 'Repository: Deleted'; // + ' Working Copy Value: ' + modificationEntry.localVersion;
            break;
          case 3:
            filterInstance.previousValue = 'was ' + modificationEntry.baseVersion; // + ' Working Copy: Deleted';
        }
      } else {
        filterInstance.isServerChange = modificationEntry.isServerChange() ? true : false;
        modificationEntry.baseVersion ? MergedDiffHelper.applyCss(modificationEntry, filterInstance, modificationEntry.baseVersion)
          : MergedDiffHelper.applyCss(modificationEntry, filterInstance);

      }
    }
  }

  public static applyCss(modificationEntry: ModificationEntry, filterInstance: SingleFilterClient | any, previousValue?: string) {
    switch (modificationEntry.getModificationType()) {
      case ModificationType.ADDED:
        filterInstance.isAdded = true;
        break;
      case ModificationType.DELETED:
        filterInstance.isDeleted = true;
        break;
      case ModificationType.MODIFIED:
        filterInstance.isModified = true;
        if (previousValue) {
          filterInstance.previousValue = 'was ' + previousValue;
        }
        break;
    }
  }

  private static mergeFilterDiffs(
    modifications_i1: Map<string, ModificationEntry>,
    modifications_i2: Map<string, ModificationEntry>,
    mergedModifications: Map<string, ModificationEntry>): void {

    modifications_i1.forEach((value: ModificationEntry, key: string) => { // All local changes
      const filterId: string = key;
      const modificationEntry: ModificationEntry = value;
      if (modificationEntry.getModificationType() === ModificationType.MODIFIED) {// MODIFIED on local
        const serverModificationEntry: ModificationEntry = modifications_i2.get(filterId);
        if (serverModificationEntry != null && serverModificationEntry.getModificationType() === ModificationType.MODIFIED) {
          // modified locally and on server
          const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED,
            ModificationType.MODIFIED,
            ModificationType.MODIFIED);
          mergeEntry.message = 'Filter Modified Locally and on Server.';
          mergeEntry.baseVersion = modificationEntry.getPreviousValue();
          mergeEntry.baseVersionObj = modificationEntry.getPreviousValueObj();
          mergeEntry.localVersion = modificationEntry.getCurrentValue();
          mergeEntry.localVersionObj = modificationEntry.getCurrentValueObj();
          mergedModifications.set(filterId, mergeEntry);
        } else if (serverModificationEntry != null && serverModificationEntry.getModificationType() === ModificationType.DELETED) {
          // modified locally, deleted on server
          const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.DELETED,
            ModificationType.MODIFIED,
            ModificationType.DELETED);
          mergeEntry.message = 'Filter Modified Locally, Deleted on Server.';
          mergeEntry.localVersion = modificationEntry.getCurrentValue();
          mergeEntry.localVersionObj = modificationEntry.getCurrentValueObj();
          mergeEntry.baseVersion = modificationEntry.getPreviousValue();
          mergeEntry.baseVersionObj = modificationEntry.getPreviousValueObj();
          mergedModifications.set(filterId, mergeEntry);
        } else {
          // modified locally
          const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED,
            ModificationType.MODIFIED,
            null);
          mergeEntry.message = 'Filter Modified Locally.';
          mergeEntry.baseVersion = modificationEntry.getPreviousValue();
          mergeEntry.baseVersionObj = modificationEntry.getPreviousValueObj();
          mergeEntry.localVersion = modificationEntry.getCurrentValue();
          mergeEntry.localVersionObj = modificationEntry.getCurrentValueObj();
          mergedModifications.set(filterId, mergeEntry);
        }
      } else if (modificationEntry.getModificationType() === ModificationType.ADDED) {// ADDED on local
        // added locally
        const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.ADDED,
          ModificationType.ADDED,
          null);
        mergeEntry.message = 'Filter Added Locally.';
        mergedModifications.set(filterId, mergeEntry);
      } else if (modificationEntry.getModificationType() === ModificationType.DELETED) {// DELETED on local
        const serverModificationEntry: ModificationEntry = modifications_i2.get(filterId);
        if (serverModificationEntry != null && serverModificationEntry.getModificationType() === ModificationType.DELETED) {
          // deleted locally and on server
          const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.DELETED,
            ModificationType.DELETED,
            ModificationType.DELETED);
          mergeEntry.message = 'Filter deleted locally and on server.';
          mergedModifications.set(filterId, mergeEntry);
        } else if (serverModificationEntry != null && serverModificationEntry.getModificationType() === ModificationType.MODIFIED) {
          // deleted locally, modified on server
          const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.DELETED,
            ModificationType.DELETED,
            ModificationType.MODIFIED); // Showing as DELETED if filter is deleted in either of the versions.
          mergeEntry.message = 'Filter Deleted Locally, Modified on Server.';
          mergeEntry.baseVersion = serverModificationEntry.getPreviousValue();
          mergeEntry.baseVersionObj = serverModificationEntry.getPreviousValueObj();
          mergedModifications.set(filterId, mergeEntry);
        } else {
          // deleted locally
          const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, null);
          mergeEntry.message = 'Filter deleted locally.';
          mergedModifications.set(filterId, mergeEntry);
        }
      }
      const deletestatus = modifications_i2.delete(filterId);
      // console.log('Filterid deleted from 2nd modification list ' + deletestatus);
    });
    modifications_i2.forEach((value: ModificationEntry, key: string) => { // All server side changes
      const filterId: string = key;
      const modificationEntry: ModificationEntry = value;
      if (modificationEntry.getModificationType() === ModificationType.MODIFIED) {// MODIFIED on server
        const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, null, ModificationType.MODIFIED);
        mergeEntry.message = 'Filter Modified on Server.';
        mergeEntry.baseVersion = modificationEntry.getPreviousValue();
        mergeEntry.baseVersionObj = modificationEntry.getPreviousValueObj();
        mergedModifications.set(filterId, mergeEntry);
      } else if (modificationEntry.getModificationType() === ModificationType.ADDED) {
        const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.ADDED, null, ModificationType.ADDED);
        mergeEntry.message = 'Filter Added on Server.';
        mergedModifications.set(filterId, mergeEntry);
      } else if (modificationEntry.getModificationType() === ModificationType.DELETED) {
        const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.DELETED, null, ModificationType.DELETED);
        mergeEntry.message = 'Filter Deleted on Server.';
        mergedModifications.set(filterId, mergeEntry);
      }
    });
  }

 /**
	 * Merges modifications.
	 * @param modifications_i1	v1 vs v2 (local changes)
	 * @param modifications_i2	v1 vs v3 (server changes)
	 * @param mappings	Mapping for BindingInfo-Id to BindingInfo(of server/v3 copy).
	 * @param mergedModifications
	 */
  private static mergeRTIViewDiffs(
    modifications_i1: Map<BindingInfo, ModificationEntry>,
    modifications_i2: Map<BindingInfo, ModificationEntry>,
    mappings: Map<string, BindingInfo>,
    mergedModifications: Map<BindingInfo, ModificationEntry>): void {

    modifications_i2.forEach((value: ModificationEntry, key: BindingInfo) => {
      const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, null, ModificationType.MODIFIED);
      mergeEntry.message = 'Value modified on Server.';
      mergeEntry.baseVersion = value.getPreviousValue();
      mergedModifications.set(key, mergeEntry);
    });

    modifications_i1.forEach((value: ModificationEntry, key: BindingInfo) => {
      if (mergedModifications.has(mappings.get(key.getBindingId()))) {
        const mergeEntry: MergedDiffModificationEntry = <MergedDiffModificationEntry>mergedModifications.get(mappings.get(key.getBindingId()));
        mergeEntry.localChangeType = ModificationType.MODIFIED;
        mergeEntry.serverChangeType = ModificationType.MODIFIED;
        mergeEntry.message = 'Value modified Locally and on Server.';
        mergeEntry.localVersion = value.getCurrentValue();
      } else {
        const mergeEntry: MergedDiffModificationEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.MODIFIED, null);
        mergeEntry.message = 'Value modified Locally.';
        mergeEntry.baseVersion = value.getPreviousValue();
        mergeEntry.localVersion = value.getCurrentValue();
        mergedModifications.set(mappings.get(key.getBindingId()), mergeEntry);
      }
    });
  }
}
