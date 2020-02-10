import { Injector } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { MergeResult } from 'app/editables/decision-table/differ/merge-result';

import { DiffResult } from './../../../editables/decision-table/differ/diff-result';
import { ShowChangeSetOption } from './../../changeset-options';
import { ConflictResolverContext, ConflictResolverModal } from './conflict-resolver.modal';
import { CellClassRules, DecisionTableDecorator, LabelClasses } from './decisiontable-decorator';
import { DecisionTableRegularDecorator } from './decisiontable-regular-decorator';
import { DeleteResolverContext, DeleteResolverModal } from './delete-resolver.modal';

import { environment } from '../../../../environments/environment';
import { ModalService } from '../../../core/modal.service';
import { BEDecisionTable } from '../../../editables/decision-table/be-decision-table';
import { Cell } from '../../../editables/decision-table/cell';
import { Column } from '../../../editables/decision-table/column';
import * as differ from '../../../editables/decision-table/differ/differ';
import { Rule } from '../../../editables/decision-table/rule';
import { EditBuffer } from '../../../editables/edit-buffer';

export class DecisionTableMergeDecorator extends DecisionTableRegularDecorator implements DecisionTableDecorator {
  private rows: Rule[];
  private columns: Column[];
  private deletedRules: Map<string, Rule> = new Map<string, Rule>();
  private deletedColumns: Map<string, Column> = new Map<string, Column>();
  private rowsET: Rule[];
  private columnsET: Column[];
  private deletedETRules: Map<string, Rule> = new Map<string, Rule>();
  private deletedETColumns: Map<string, Column> = new Map<string, Column>();
  private base: any;
  private lhs: any;
  private modal: ModalService;
  constructor(
    private mergeResult: MergeResult,
    private rhs: EditBuffer<any>,
    private showChangeSetOption: ShowChangeSetOption,
    private injector: Injector,
    public i18n: I18n
  ) {
    super(rhs, false);

    // prepare dependency
    this.modal = injector.get(ModalService);

    // prepare data
    this.base = mergeResult.getLhsDiff().getLhs();
    this.lhs = mergeResult.getLhsDiff().getRhs();

    if (environment.enableBEUI) {
      this.initializeDeleatedEntries();
    }

  }

  initializeDeleatedEntries() {
    this.base.getRules()
      .filter(r => {
        const conflictItem = this.mergeResult.getConflict().getRuleItems().get(r.getId());
        if (this.isConflict(conflictItem)) {
          return false;
        } else {
          const ruleItem = this.getRuleItem(r.getId());
          return ruleItem && ruleItem.diff.kind === differ.DELETE;
        }
      })
      .forEach(r => {
        if (!this.mergeResult.getMerged().hasRule(r.getId())) {
          this.mergeResult.getMerged().addRule(r);
        }
        // !this.mergeResult.getMerged().hasRule(r.getId()) ? this.mergeResult.getMerged().addRule(r) : console.log("");
      });

    if (this.base instanceof BEDecisionTable) {
      this.base.getETRules()
        .filter(r => {
          const conflictItem = this.mergeResult.getConflict().getETRuleItems().get(r.getId());
          if (this.isConflict(conflictItem)) {
            return false;
          } else {
            const ruleItem = this.getETRuleItem(r.getId());
            return ruleItem && ruleItem.diff.kind === differ.DELETE;
          }
        })
        .forEach(r => {
          if (!this.mergeResult.getMerged().hasETRule(r.getId())) {
            this.mergeResult.getMerged().addETRule(r);
          }
        });
    }
  }

  refresh() {
    this.rows = [];
    this.columns = [];
    this.deletedRules.clear();
    this.deletedColumns.clear();
    this.rowsET = [];
    this.columnsET = [];
    this.deletedETRules.clear();
    this.deletedETColumns.clear();

    if (this.showChangeSetOption !== 'none') {
      this.base.getRules()
        .filter(r => {
          const conflictItem = this.mergeResult.getConflict().getRuleItems().get(r.getId());
          if (this.isConflict(conflictItem)) {
            return false;
          } else {
            const ruleItem = this.getRuleItem(r.getId());
            return ruleItem && ruleItem.diff.kind === differ.DELETE;
          }
        })
        .forEach(r => this.deletedRules.set(r.getId(), r));

      if (this.base instanceof BEDecisionTable) {
        this.base.getETRules()
          .filter(r => {
            const conflictItem = this.mergeResult.getConflict().getETRuleItems().get(r.getId());
            if (this.isConflict(conflictItem)) {
              return false;
            } else {
              const ruleItem = this.getETRuleItem(r.getId());
              return ruleItem && ruleItem.diff.kind === differ.DELETE;
            }
          })
          .forEach(r => this.deletedETRules.set(r.getId(), r));
      }

      this.base.getColumns()
        .filter(col => {
          const conflictItem = this.mergeResult.getConflict().getColumnItems().get(col.getId());
          if (this.isConflict(conflictItem)) {
            return false;
          } else {
            const colItem = this.getColItem(col.getId());
            return colItem && colItem.diff.kind === differ.DELETE;
          }
        })
        .forEach(col => this.deletedColumns.set(col.getId(), col));

      if (this.base instanceof BEDecisionTable) {
        this.base.getETColumns()
          .filter(col => {
            const conflictItem = this.mergeResult.getConflict().getETColumnItems().get(col.getId());
            if (this.isConflict(conflictItem)) {
              return false;
            } else {
              const colItem = this.getETColItem(col.getId());
              return colItem && colItem.diff.kind === differ.DELETE;
            }
          })
          .forEach(col => this.deletedETColumns.set(col.getId(), col));
      }
    }

    if (environment.enableBEUI) {
      this.rows = this.rhs.getBuffer().getRules();
    } else {
      this.rows = this.rhs.getBuffer().getRules().concat(Array.from(this.deletedRules.values()));
    }

    this.columns = this.rhs.getBuffer().getColumns().concat(Array.from(this.deletedColumns.values()));

    // Exception table
    if (this.rhs.getBuffer() instanceof BEDecisionTable) {
      this.rowsET = this.rhs.getBuffer().getETRules();
      this.columnsET = this.rhs.getBuffer().getETColumns().concat(Array.from(this.deletedETColumns.values()));
    }
  }

  getRows() {
    return this.rows;
  }

  getColumns() {
    return this.columns;
  }

  getETRows() {
    return this.rowsET;
  }

  getETColumns() {
    return this.columnsET;
  }

  getColHeaderClass(id: string) {
    const conflictItem = this.mergeResult.getConflict().getColumnItems().get(id);
    if (this.isConflict(conflictItem)) {
      return this.getClassHelper(this.getDiffClassWithSrc(this.getRuleOrColumnItemFromConflict(conflictItem)));
    } else {
      const classes: string[] = [];
      const colItem = this.getColItem(id);
      classes.push(this.getClassHelper(this.getDiffClassWithSrc(colItem)));
      return classes.join(' ');
    }
  }

  getClassHelper(diffClass: string) {
    if (diffClass === 'diff-new') {
      return 'ws-diff-background-add';
    } else if (diffClass === 'diff-edit') {
      return 'ws-diff-background-modify';
    } else if (diffClass === 'diff-delete') {
      return 'ws-diff-background-delete';
    } else {
      return '';
    }
  }

  getCellRenderer(col: Column) {
    return (params) => {
      if (this.isIdColumn(params)) {
        return params.value;
      } else {
        const cellId = this.getCellId(params);
        const cellConflict = this.mergeResult.getConflict().getCellItems().get(cellId);
        if (this.isConflict(cellConflict)) {
          let lVal: string;
          let rVal: string;
          switch (cellConflict.type) {
            case differ.ConflictType.CELL:
              // we need to care about deletion, so get the value via the helper
              lVal = this.cellTemplateFromCellDiff('lhs', cellConflict.a);
              rVal = this.cellTemplateFromCellDiff('rhs', cellConflict.b);
              break;
            case differ.ConflictType.CELL_COLUMN:
            case differ.ConflictType.CELL_BOTH:
            case differ.ConflictType.CELL_RULE:
              lVal = this.partialCellTemplate('lhs', cellConflict.a.rhs, false);
              // the deleted value is the base value, which is the same as the lhs
              rVal = this.partialCellTemplate('rhs', cellConflict.a.lhs, true);
              break;
            case differ.ConflictType.COLUMN_CELL:
            case differ.ConflictType.BOTH_CELL:
            case differ.ConflictType.RULE_CELL:
              // the deleted value is the base value, which is the same as the lhs
              lVal = this.partialCellTemplate('lhs', cellConflict.b.lhs, true);
              rVal = this.partialCellTemplate('rhs', cellConflict.b.rhs, false);
              break;
            default:
              lVal = this.partialCellTemplate('lhs', cellConflict.a.rhs, true);
              rVal = this.partialCellTemplate('rhs', cellConflict.b.rhs, false);
              this.log.debug(this.i18n('Seeing CELL_IN_NEW conflict in merge, something must be wrong'));
              break;
          }
          return `${lVal}${rVal}`;
        } else {
          const ids = this.getIds(cellId);
          const stripSrc = (itemWithSrc) => {
            return itemWithSrc ? itemWithSrc.diff : null;
          };
          const cellItem = stripSrc(this.getCellItem(cellId));
          const ruleItem = stripSrc(this.getRuleItem(ids.ruleId));
          const colItem = stripSrc(this.getColItem(ids.colId));
          return this.getCellTemplateByDiffItems(params.value, cellItem, ruleItem, colItem);
        }
      }
    };
  }

  getETCellRenderer(col: Column) {
    return (params) => {
      if (this.isIdColumn(params)) {
        return params.value;
      } else {
        const cellId = this.getCellId(params);
        const cellConflict = this.mergeResult.getConflict().getETCellItems().get(cellId);
        if (this.isConflict(cellConflict)) {
          let lVal: string;
          let rVal: string;
          switch (cellConflict.type) {
            case differ.ConflictType.CELL:
              // we need to care about deletion, so get the value via the helper
              lVal = this.cellTemplateFromCellDiff('lhs', cellConflict.a);
              rVal = this.cellTemplateFromCellDiff('rhs', cellConflict.b);
              break;
            case differ.ConflictType.CELL_COLUMN:
            case differ.ConflictType.CELL_BOTH:
            case differ.ConflictType.CELL_RULE:
              lVal = this.partialCellTemplate('lhs', cellConflict.a.rhs, false);
              // the deleted value is the base value, which is the same as the lhs
              rVal = this.partialCellTemplate('rhs', cellConflict.a.lhs, true);
              break;
            case differ.ConflictType.COLUMN_CELL:
            case differ.ConflictType.BOTH_CELL:
            case differ.ConflictType.RULE_CELL:
              // the deleted value is the base value, which is the same as the lhs
              lVal = this.partialCellTemplate('lhs', cellConflict.b.lhs, true);
              rVal = this.partialCellTemplate('rhs', cellConflict.b.rhs, false);
              break;
            default:
              lVal = this.partialCellTemplate('lhs', cellConflict.a.rhs, true);
              rVal = this.partialCellTemplate('rhs', cellConflict.b.rhs, false);
              this.log.debug(this.i18n('Seeing CELL_IN_NEW conflict in merge, something must be wrong'));
              break;
          }
          return `${lVal}${rVal}`;
        } else {
          const ids = this.getIds(cellId);
          const stripSrc = (itemWithSrc) => {
            return itemWithSrc ? itemWithSrc.diff : null;
          };
          const cellItem = stripSrc(this.getETCellItem(cellId));
          const ruleItem = stripSrc(this.getETRuleItem(ids.ruleId));
          const colItem = stripSrc(this.getETColItem(ids.colId));
          return this.getCellTemplateByDiffItems(params.value, cellItem, ruleItem, colItem);
        }
      }
    };
  }

  // helper function to extract deleted cell value
  cellTemplateFromCellDiff(src: 'lhs' | 'rhs', diffItem: differ.DiffItem) {
    if (diffItem.kind === differ.DELETE) {
      return this.partialCellTemplate(src, diffItem.lhs, true);
    } else {
      return this.partialCellTemplate(src, diffItem.rhs, false);
    }
  }

  partialCellTemplate(src: 'lhs' | 'rhs', cell: Cell, deleted: boolean) {
    let indicator: string;
    if (src === 'lhs') {
      indicator = 'R';
    } else {
      indicator = 'W';
    }
    return `<span class='label label-as-indicator label-default'>${indicator}: \
    <span class='${deleted ? 'line-cross' : ''}' >${this.safeExpr(cell)}</span></span>`;
  }

  getCellClassRules(col: Column) {
    return <CellClassRules>{
      'ws-diff-background-add': (params) => {
        return this.getCellDiffClassByParams(params) === 'diff-new' || this.getCellDiffClassByParams(params) === 'diff-new-lhs';
      },
      'ws-diff-background-modify': (params) => {
        return this.getCellDiffClassByParams(params) === 'diff-edit' || this.getCellDiffClassByParams(params) === 'diff-edit-lhs';
      },
      'ws-diff-background-delete': (params) => {
        return this.getCellDiffClassByParams(params) === 'diff-delete' || this.getCellDiffClassByParams(params) === 'diff-delete-lhs';
      },
      'ws-diff-background-conflict': (params) => {
        return this.getCellDiffClassByParams(params) === 'diff-conflict';
      },
    };
  }

  getETCellClassRules() {
    return <CellClassRules>{
      'ws-diff-background-add': (params) => {
        return this.getETCellDiffClassByParams(params) === 'diff-new' || this.getETCellDiffClassByParams(params) === 'diff-new-lhs';
      },
      'ws-diff-background-modify': (params) => {
        return this.getETCellDiffClassByParams(params) === 'diff-edit' || this.getETCellDiffClassByParams(params) === 'diff-edit-lhs';
      },
      'ws-diff-background-delete': (params) => {
        return this.getETCellDiffClassByParams(params) === 'diff-delete' || this.getETCellDiffClassByParams(params) === 'diff-delete-lhs';
      },
      'ws-diff-background-conflict': (params) => {
        return this.getCellDiffClassByParams(params) === 'diff-conflict';
      },
    };
  }

  getCellDoubleClickHandler() {
    return (params) => {
      const cellId = this.getCellId(params);
      const cellConflict = this.mergeResult.getConflict().getCellItems().get(cellId);
      if (this.isConflict(cellConflict)) {
        if (environment.enableBEUI && this.rhs && this.rhs.getBase()) {
          for (const rule of this.rhs.getBase().getRules()) {
            if (params.data.getId() === rule.getId()) {
              for (const cellObject of rule.getCells()) {
                const found = params.data.cellMap[cellObject.getColId()];
                if (!found) {
                  params.data.setCell(cellObject.getColId(), cellObject.getExpr(), cellObject.getType(), cellObject.isDisabled(), cellObject.getComment());
                }
              }
            }
          }
        }
        this.modal
          .open(ConflictResolverModal, new ConflictResolverContext(this.base, this.table, cellConflict, 'latest', 'working'))
          .then((resolution: differ.Resolution) => this.resolveCellConflict(cellId, cellConflict, resolution), () => { });
      } else if (environment.enableBEUI && this.deletedRules.has(params.data.getId())) {
        this.modal
          .open(DeleteResolverModal, new DeleteResolverContext(params.data.getId()))
          .then((deleted: string) => this.deletedRowRemove(deleted, params.data.getId()), () => { });
      }
    };
  }

  deletedRowRemove(deleted: string, rId: string) {
    if (deleted === 'Y') {
      this.mergeResult.getMerged().clearRule(rId);
      this.rhs.markForRefresh();
    } else {

    }
  }

  deletedRowRemoveET(deleted: string, rId: string) {
    if (deleted === 'Y') {
      this.mergeResult.getMerged().clearETRule(rId);
      this.rhs.markForRefresh();
    } else {

    }
  }

  getETCellDoubleClickHandler() {
    return (params) => {
      const cellId = this.getCellId(params);
      const cellConflict = this.mergeResult.getConflict().getETCellItems().get(cellId);
      if (this.isConflict(cellConflict)) {
        if (environment.enableBEUI && this.rhs && this.rhs.getBase()) {
          for (const rule of this.rhs.getBase().getETRules()) {
            if (params.data.getId() === rule.getId()) {
              for (const cellObject of rule.getCells()) {
                params.data.setCell(cellObject.getColId(), cellObject.getExpr(), cellObject.getType(), cellObject.isDisabled(), cellObject.getComment());
              }
            }
          }
        }
        this.modal
          .open(ConflictResolverModal, new ConflictResolverContext(this.base, this.table, cellConflict, 'latest', 'working'))
          .then((resolution: differ.Resolution) => this.resolveETCellConflict(cellId, cellConflict, resolution), () => { });
      } else if (environment.enableBEUI && this.deletedETRules.has(params.data.getId())) {
        this.modal
          .open(DeleteResolverModal, new DeleteResolverContext(params.data.getId()))
          .then((deleted: string) => this.deletedRowRemoveET(deleted, params.data.getId()), () => { });
      }
    };
  }

  getPropertyLabelClickHandler(prop: string) {
    const conflictItem = this.mergeResult.getConflict().getPropItems().get(prop);
    if (this.isConflict(conflictItem)) {
      return () => {
        this.modal
          .open(ConflictResolverModal, new ConflictResolverContext(this.base, this.table, conflictItem, 'latest', 'working'))
          .then((resolution: differ.Resolution) => this.resolvePropertyConflict(prop, conflictItem, resolution), () => { });
      };
    }
  }

  isPropertyLabelClickable(prop: string): boolean {
    const conflictItem = this.mergeResult.getConflict().getPropItems().get(prop);
    return this.isConflict(conflictItem);
  }

  resolveCellConflict(cellId: string, item: differ.ConflictItem, resolution: differ.Resolution) {
    this.mergeResult.resolveCellConflict(cellId, resolution);
    this.rhs.markForRefresh();
  }

  resolveETCellConflict(cellId: string, item: differ.ConflictItem, resolution: differ.Resolution) {
    this.mergeResult.resolveETCellConflict(cellId, resolution);
    this.rhs.markForRefresh();
  }

  resolvePropertyConflict(prop: string, item: differ.ConflictItem, resolution: differ.Resolution) {
    this.mergeResult.resolvePropertyConflict(prop, resolution);
    this.rhs.markForRefresh();
  }

  getProperty(prop: string) {
    return this.mergeResult.getMerged().getProperty(prop);
  }

  setProperty(prop: string, val: string) {
    return this.mergeResult.getMerged().setProperty(prop, val);
  }

  getCellValueGetter(col: Column) {
    return super.getCellValueGetter(col);
  }

  getCellEditable(col: Column) {
    return (params) => !this.isConflict(this.mergeResult.getConflict().getCellItems().get(this.getCellId(params)));
  }

  getCellDiffClassByParams(params) {
    const cellId = this.getCellId(params);
    const colId = this.getColId(params);
    const ruleId = this.getRuleId(params);
    if (this.isIdColumn(params)) {
      const ruleConflict = this.mergeResult.getConflict().getRuleItems().get(ruleId);
      if (this.isConflict(ruleConflict)) {
        return this.getDiffClassWithSrc(this.getRuleOrColumnItemFromConflict(ruleConflict));
      } else {
        const ruleItem = this.getRuleItem(ruleId);
        if (ruleItem) {
          return this.getDiffClassWithSrc(ruleItem);
        } else {
          return '';
        }
      }
    } else {
      const cellConflict = this.mergeResult.getConflict().getCellItems().get(cellId);
      if (this.isConflict(cellConflict)) {
        return 'diff-conflict';
      } else {
        const cellItem = this.getCellItem(cellId);
        const colItem = this.getColItem(colId);
        const ruleItem = this.getRuleItem(ruleId);
        const ruleClass = this.getDiffClassWithSrc(ruleItem);
        const colClass = this.getDiffClassWithSrc(colItem);
        if (ruleClass.startsWith('diff-delete')) {
          return ruleClass;
        } else if (colClass.startsWith('diff-delete')) {
          return colClass;
        } else if (ruleClass) {
          return ruleClass;
        } else if (colClass) {
          return colClass;
        } else {
          return this.getDiffClassWithSrc(cellItem, true);
        }
      }
    }
  }

  getETCellDiffClassByParams(params) {
    const cellId = this.getCellId(params);
    const colId = this.getColId(params);
    const ruleId = this.getRuleId(params);
    if (this.isIdColumn(params)) {
      const ruleConflict = this.mergeResult.getConflict().getETRuleItems().get(ruleId);
      if (this.isConflict(ruleConflict)) {
        return this.getDiffClassWithSrc(this.getRuleOrColumnItemFromConflict(ruleConflict));
      } else {
        const ruleItem = this.getETRuleItem(ruleId);
        if (ruleItem) {
          return this.getDiffClassWithSrc(ruleItem);
        } else {
          return '';
        }
      }
    } else {
      const cellConflict = this.mergeResult.getConflict().getETCellItems().get(cellId);
      if (this.isConflict(cellConflict)) {
        return 'diff-conflict';
      } else {
        const cellItem = this.getETCellItem(cellId);
        const colItem = this.getETColItem(colId);
        const ruleItem = this.getETRuleItem(ruleId);
        const ruleClass = this.getDiffClassWithSrc(ruleItem);
        const colClass = this.getDiffClassWithSrc(colItem);
        if (ruleClass.startsWith('diff-delete')) {
          return ruleClass;
        } else if (colClass.startsWith('diff-delete')) {
          return colClass;
        } else if (ruleClass) {
          return ruleClass;
        } else if (colClass) {
          return colClass;
        } else {
          return this.getDiffClassWithSrc(cellItem, true);
        }
      }
    }
  }

  getPropertyLabelClasses(prop: string) {
    const classes: LabelClasses = super.getPropertyLabelClasses(prop);
    const conflictItem = this.mergeResult.getConflict().getPropItems().get(prop);
    if (this.isConflict(conflictItem)) {
      classes['ws-diff-background-conflict'] = true;
    } else {
      const propItem = this.getPropItem(prop);
      const clazz = this.getClassHelper(this.getDiffClassWithSrc(propItem));
      if (clazz) {
        classes[clazz] = true;
      }
    }
    return classes;
  }

  getPropertyDecorationText(prop: string) {
    const conflictItem = this.mergeResult.getConflict().getPropItems().get(prop);
    if (this.isConflict(conflictItem)) {
      return '';
    } else {
      return super.getPropertyDecorationText(prop);
    }
  }

  getCellItem(id: string): { src: string, diff: differ.DiffItem } {
    return this.getDiffItemWithSrc(differ.CELL, id);
  }

  getETCellItem(id: string): { src: string, diff: differ.DiffItem } {
    return this.getETDiffItemWithSrc(differ.CELL, id);
  }

  getRuleItem(id: string) {
    return this.getDiffItemWithSrc(differ.RULE, id);
  }

  getETRuleItem(id: string) {
    return this.getETDiffItemWithSrc(differ.RULE, id);
  }

  getDiffItemWithSrc(type: string, id: string): { src: string, diff: differ.DiffItem } {
    let lhsDiff: differ.DiffItem;
    if (this.showLhs()) {
      lhsDiff = this.getDiffItemByType(this.mergeResult.getLhsDiff(), type, id);
    }
    let rhsDiff: differ.DiffItem;
    if (this.showRhs()) {
      rhsDiff = this.getDiffItemByType(this.mergeResult.getRhsDiff(), type, id);
    }
    if (lhsDiff && rhsDiff && !_.isEqual(lhsDiff, rhsDiff)) {
      this.log.err(id, lhsDiff, rhsDiff);
      throw new Error(this.i18n('There are items from both side, can not decide which one you want.'));
    } else if (rhsDiff) {
      return {
        src: 'rhs',
        diff: rhsDiff,
      };
    } else if (lhsDiff) {
      return {
        diff: lhsDiff,
        src: 'lhs'
      };
    } else {
      return null;
    }
  }

  getETDiffItemWithSrc(type: string, id: string): { src: string, diff: differ.DiffItem } {
    let lhsDiff: differ.DiffItem;
    if (this.showLhs()) {
      lhsDiff = this.getETDiffItemByType(this.mergeResult.getLhsDiff(), type, id);
    }
    let rhsDiff: differ.DiffItem;
    if (this.showRhs()) {
      rhsDiff = this.getETDiffItemByType(this.mergeResult.getRhsDiff(), type, id);
    }
    if (lhsDiff && rhsDiff && !_.isEqual(lhsDiff, rhsDiff)) {

      // temporary change
      if (this.lhs instanceof BEDecisionTable) {
        return {
          src: 'rhs',
          diff: rhsDiff,
        };
      }
      this.log.err(id, lhsDiff, rhsDiff);
      throw new Error(this.i18n('There are items from both side, can not decide which one you want.'));
    } else if (rhsDiff) {
      return {
        src: 'rhs',
        diff: rhsDiff,
      };
    } else if (lhsDiff) {
      return {
        diff: lhsDiff,
        src: 'lhs'
      };
    } else {
      return null;
    }
  }

  getDiffItemByType(diffResult: DiffResult, type: string, id: string) {
    switch (type) {
      case differ.RULE:
        return diffResult.getRuleItem(id);
      case differ.COLUMN:
        return diffResult.getColumnItem(id);
      case differ.CELL:
        return diffResult.getCellItem(id);
      case differ.META:
        return diffResult.getMetaItem(id);
      case differ.PROP:
        return diffResult.getPropItem(id);
      default:
        throw new Error(this.i18n('unrecognizable type:') + type);
    }
  }

  getETDiffItemByType(diffResult: DiffResult, type: string, id: string) {
    switch (type) {
      case differ.RULE:
        return diffResult.getETRuleItem(id);
      case differ.COLUMN:
        return diffResult.getETColumnItem(id);
      case differ.CELL:
        return diffResult.getETCellItem(id);
      case differ.META:
        return diffResult.getMetaItem(id);
      case differ.PROP:
        return diffResult.getPropItem(id);
      default:
        throw new Error(this.i18n('unrecognizable type:') + type);
    }
  }

  getConflictItemByType(type: string, id) {
    switch (type) {
      case differ.RULE:
        return this.mergeResult.getConflict().getRuleItems().get(id);
      case differ.META:
        return this.mergeResult.getConflict().getMetaItems().get(id);
      case differ.PROP:
        return this.mergeResult.getConflict().getPropItems().get(id);
      case differ.COLUMN:
        return this.mergeResult.getConflict().getColumnItems().get(id);
      case differ.CELL:
        return this.mergeResult.getConflict().getCellItems().get(id);
      default:
        throw this.i18n('unrecognizable type:') + type;
    }
  }

  getColItem(id: string) {
    return this.getDiffItemWithSrc(differ.COLUMN, id);
  }

  getETColItem(id: string) {
    return this.getETDiffItemWithSrc(differ.COLUMN, id);
  }

  getPropItem(id: string) {
    return this.getDiffItemWithSrc(differ.PROP, id);
  }

  isConflict(conflict: differ.ConflictItem): boolean {
    return conflict && !conflict.resolved;
  }

  showLhs(): boolean {
    return this.showChangeSetOption === 'lhs' || this.showChangeSetOption === 'both';
  }

  showRhs(): boolean {
    return this.showChangeSetOption === 'rhs' || this.showChangeSetOption === 'both';
  }

  getDiffClassWithSrc(item: { src: string, diff: differ.DiffItem }, deleteAsEdit?: boolean) {
    if (item) {
      const clazz = this.getDiffClassByKind(item.diff.kind, deleteAsEdit);
      if (item.src === 'lhs') {
        return `${clazz}-lhs`;
      } else if (item.src === 'rhs') {
        return clazz;
      }
    } else {
      return '';
    }
  }

  getRuleOrColumnItemFromConflict(conflictItem: differ.ConflictItem) {
    let lhs;
    let rhs;
    if (conflictItem.a) {
      const type = conflictItem.a.type;
      if (type === differ.RULE || type === differ.COLUMN) {
        lhs = {
          src: 'lhs',
          diff: conflictItem.a
        };
      }
    }
    if (conflictItem.b) {
      const type = conflictItem.b.type;
      if (type === differ.RULE || type === differ.COLUMN) {
        rhs = {
          src: 'rhs',
          diff: conflictItem.b
        };
      }
    }
    if (lhs && rhs) {
      throw Error(this.i18n('Both side is a column/rule diff, something is wrong.'));
    } else if (rhs) {
      return rhs;
    } else if (lhs) {
      return lhs;
    } else {
      return null;
    }
  }
}
