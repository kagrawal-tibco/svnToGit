/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:07:46+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:07:47+05:30
 */

import { DomainInfo } from '../rule-template-instance-view/DomainInfo';
export class SymbolInfo {

  private domainInfo: DomainInfo = new DomainInfo(); // _domain
  private symbolAlias: string = undefined; // _alias
  private symbolInfo: Array<SymbolInfo> = new Array<SymbolInfo>(); // _containedSymbols
  private type: string = undefined; // _type
  private visited = false; // _visited

  constructor(type?: string, alias?: string) {
    if (type != null) {
    }
    this.type = type;
    if (alias != null) {
      this.symbolAlias = alias;
    }
  }

  getSymbolInfo(): Array<SymbolInfo> {
    return this.symbolInfo;
  }

  setSymbolInfo(value: Array<SymbolInfo>) {
    this.symbolInfo = value;
  }

  getSymbolAlias(): string {
    return this.symbolAlias;
  }

  setSymbolAlias(alias: string) {
    this.symbolAlias = alias;
  }

  getType(): string {
    return this.type;
  }

  setType(type: string) {
    this.type = type;
  }

  getDomainInfo(): DomainInfo {
    return this.domainInfo;
  }

  setDomainInfo(value: DomainInfo) {
    this.domainInfo = value;
  }

  getVisited(): boolean {
    return this.visited;
  }

  setVisited(value: boolean) {
    this.visited = value;
  }
}
