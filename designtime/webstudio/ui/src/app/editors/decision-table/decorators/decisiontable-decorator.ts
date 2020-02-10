import { ICellEditor, ICellRenderer, ICellRendererFunc } from 'ag-grid-community/main';

import { Column } from '../../../editables/decision-table/column';
import { Rule } from '../../../editables/decision-table/rule';
export type CellClassRules = { [k: string]: ((any) => boolean) };
export type LabelClasses = { [k: string]: boolean };
export type CellEditor = ICellEditor | 'select' | 'text' | 'popupText' | 'popupSelect' | 'largetText' | 'agRichSelectCellEditor';
export type CellRenderer = ICellRenderer | ICellRendererFunc;
export interface DecisionTableDecorator {
  refresh();
  getRows(): Rule[];
  getColumns(): Column[];
  getColHeaderClass(string): string;
  getCellRenderer(col: Column): any;
  getCellEditor(col: Column): { editor: any, params: any };
  getCellEditable(col: Column): (any) => boolean;
  getCellClassRules(col: Column): CellClassRules;
  getCellValueGetter(col: Column);
  getCellDoubleClickHandler(): (any) => void;
  getProperty(param: string): string;
  setProperty(param: string, val: string);
  getPropertyLabelClasses(prop: string): LabelClasses;
  getPropertyDecorationText(prop: string): string;
  getPropertyLabelClickHandler(prop: string): (any) => void;
  isPropertyLabelClickable(prop: string);
}
