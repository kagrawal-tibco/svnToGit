/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:07:32+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:07:33+05:30
 */

import { Filter } from './Filter';
import { FilterValue } from './FilterValue';
import { RelatedLinkImpl } from './RelatedLinkImpl';
import { SimpleFilterValueImpl } from './SimpleFilterValueImpl';
export class SingleFilterImpl extends Filter {

  private link: Array<RelatedLinkImpl> = new Array<RelatedLinkImpl>(); // _links
  private operator: string = undefined; // _operator
  private value: FilterValue = undefined; // _filterValue

  constructor() {
    super();
    this.setFilterId(this.getFilterId());
    this.link = new Array<RelatedLinkImpl>();
    this.value = new SimpleFilterValueImpl();
  }

  setOperator(value: string) {
    this.operator = value;
  }

  getOperator(): string {
    return this.operator;
  }

  setValue(value: FilterValue) {
    this.value = value;
  }

  getValue(): FilterValue {
    if (this.value === '') {
      return new SimpleFilterValueImpl();
    }
    return this.value;
  }

  public getLink(): Array<RelatedLinkImpl> {
    if (this.link == null) {
      this.link = new Array<RelatedLinkImpl>();
    }
    return this.link;
  }

  public replaceFilterLinks(value: Array<RelatedLinkImpl>) {
    if (value != null) {
      this.link = value;
    }
  }

  public addRelatedLink(linkValue: RelatedLinkImpl): void {
    if (this.link.indexOf(linkValue) === -1) {
      this.link.push(linkValue);
    }
  }

  public removeRelatedLink(linkValue: RelatedLinkImpl): void {
    const index: number = this.link.indexOf(linkValue);
    if (index > -1) {
      this.link.splice(index, 1);
    }
  }
}
