/**
 * @Author: Rahil Khera
 * @Date:   2017-07-10T11:41:35+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-07-10T12:00:24+05:30
 */
import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { DiffHelper } from './DiffHelper';
import { MergedDiffModificationEntry } from './MergedDiffModificationEntry';
import { ModificationType } from './ModificationType';

import { EditBuffer } from '../../../editables/edit-buffer';
import { Filter } from '../../../editors/rule-template-instance-builder/Filter';
import { MultiFilterImpl } from '../../../editors/rule-template-instance-builder/MultiFilterImpl';
import { SingleFilterImpl } from '../../../editors/rule-template-instance-builder/SingleFilterImpl';
import { BindingInfo } from '../../../editors/rule-template-instance-view/BindingInfo';
import { RuleTemplateInstanceImpl } from '../rule-template-instance';

enum Mode {
  CHOOSE,
  CONFIRM,
}

export class RTIConflictResolverContext extends BSModalContext {

  constructor(
    public base: Filter | BindingInfo,
    public buffer: EditBuffer<RuleTemplateInstanceImpl>,
    public modificationEntry: MergedDiffModificationEntry,
    public lhsTitle: string,
    public rhsTitle: string,
    public isRTIView: boolean
  ) {
    super();
  }
}

@Component({
  selector: 'conflict-resolver',
  templateUrl: './rti-conflict-resolver.modal.html'
})

export class RTIConfilctResolverModal implements ModalComponent<RTIConflictResolverContext> {

  context: RTIConflictResolverContext;
  resolution: string;
  explanation: string;
  lhsDescription: string;
  rhsDescription: string;
  mode: Mode;
  title: string;

  constructor(
    public dialog: DialogRef<RTIConflictResolverContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
    this.init();
  }

  init() {
    this.title = this.i18n('Conflict in this {{name}}', { name: this.entityName });
    this.lhsDescription = '';
    this.rhsDescription = '';
    this.processConflict(this.context.modificationEntry, this.context.base);
  }

  processConflict(modificationEntry: MergedDiffModificationEntry,
    base: Filter | BindingInfo) {
    let filter: Filter;
    let bindingInfo: BindingInfo;
    if (base instanceof Filter) {
      filter = base;
    } else {
      bindingInfo = base;
    }
    if (filter instanceof MultiFilterImpl) {

    } else if (!modificationEntry.isLocalChange()) {
      modificationEntry.setApplied(true);
    }

    if (modificationEntry.localChangeType === ModificationType.DELETED
      && modificationEntry.serverChangeType === ModificationType.DELETED) {
      modificationEntry.setApplied(true);
    }

    switch (modificationEntry.getModificationType()) {
      case ModificationType.MODIFIED:
        this.lhsDescription = this.i18n('Repository : {{repo}}', { repo: this.getRepositioryValue(base) });
        this.rhsDescription = this.i18n('Working Copy : {{copy}}', { copy: modificationEntry.localVersion });
        this.mode = Mode.CHOOSE;
        this.explanation = this.prepareChooseExplanation();
        break;
      case ModificationType.DELETED:
        this.lhsDescription = this.i18n('Update this {{name}}', { name: this.entityName });
        this.rhsDescription = this.i18n('Delete this {{name}}', { name: this.entityName });
        if (modificationEntry.localChangeType === ModificationType.DELETED) {
          this.explanation = this.prepareConfirmExplanation('rhs');
        } else if (modificationEntry.serverChangeType === ModificationType.DELETED) {
          this.explanation = this.prepareConfirmExplanation('lhs');
        }
        this.mode = Mode.CONFIRM;
        break;
    }
  }

  getRepositioryValue(base: Filter | BindingInfo): string {
    if (base instanceof BindingInfo) {
      return base.getValue();
    } else {
      if (base instanceof MultiFilterImpl) {
        return base.getMatchType();
      } else {
        const singleFilterImpl = <SingleFilterImpl>base;
        return DiffHelper.getFilterAsString(singleFilterImpl);
      }
    }
  }

  prepareChooseExplanation() {
    return (this.i18n('{{entityName}} was set to different values in the repository and your working copy. \
    Please select one of them to resolve the conflict.', { entityName: this.entityName }));
  }

  prepareConfirmExplanation(deleteFrom: 'lhs' | 'rhs') {
    let first: string;
    let second: string;
    if (deleteFrom === 'lhs') {
      first = 'the repository';
      second = 'your working copy';

    } else {
      first = 'your working copy';
      second = 'the repository';
    }
    return (this.i18n('{{entityName}} was deleted in {{first}} and {{entityName}} was updated in {{second}}.Please select the change to apply to your working copy.', { first: first, entityName: this.entityName, second: second }));
  }

  get entityName(): string {
    return this.context.isRTIView ? 'Binding' : 'Filter';
  }

  onSelectedChange(idx: number) {
    if (idx === 0) {
      this.resolution = 'lhs';
    } else if (idx === 1) {
      this.resolution = 'rhs';
    } else {
      this.resolution = null;
    }
  }

  onClose() {
    this.dialog.dismiss();
  }

  onConfirm() {
    this.dialog.close(this.resolution);
  }

  canConfirm() {
    return this.resolution != null;
  }

}
