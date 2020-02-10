/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:02:55+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:02:59+05:30
 */

import { BuilderSubClauseImpl } from './BuilderSubClauseImpl';
import { SymbolInfo } from './SymbolInfo';

export class CommandImpl {

  private actionType: string = undefined;
  private alias: string = undefined;
  private type: string = undefined;
  private filter: BuilderSubClauseImpl; // _subClause
  private symbols: SymbolInfo;

  getActionType(): string {
    return this.actionType;
  }

  setActionType(actionType: string) {
    this.actionType = actionType;
  }

  getAlias(): string {
    return this.alias;
  }

  setAlias(alias: string) {
    this.alias = alias;
  }

  getType(): string {
    return this.type;
  }

  setType(type: string) {
    this.type = type;
  }

  getFilter(): BuilderSubClauseImpl {
    return this.filter;
  }

  setFilter(value: BuilderSubClauseImpl) {
    this.filter = value;
  }

  getSymbols(): SymbolInfo {
    return this.symbols;
  }

  setSymbols(value: SymbolInfo) {
    this.symbols = value;
  }
}
