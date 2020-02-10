/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:08:06+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:08:07+05:30
 */

import { DomainInfo } from './DomainInfo';

export class BindingInfo {

  private bindingId: string = undefined;
  private type: string = undefined;
  private value: string = undefined;
  private domainInfo: DomainInfo = null;
  private style: string = undefined;

  constructor(id?: string, type?: string, value?: string, domainInfo?: DomainInfo) {
    this.bindingId = id;
    this.type = type;
    this.value = value;
    this.domainInfo = domainInfo;
    this.style = '';
  }

  setBindingId(id: string) {
    this.bindingId = id;
  }

  getBindingId(): string {
    return this.bindingId;
  }

  setType(type: string) {
    this.type = type;
  }

  getType(): string {
    return this.type;
  }

  setValue(value: string) {
    this.value = value;
  }

  getValue(): string {
    return this.value;
  }

  setDomainInfo(domainInfo: DomainInfo) {
    this.domainInfo = domainInfo;
  }

  getDomainInfo(): DomainInfo {
    return this.domainInfo;
  }

  setStyle(style: string) {
    this.style = style;
  }

  getStyle(): string {
    return this.style;
  }
}
