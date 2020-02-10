/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:04:45+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:04:46+05:30
 */

import { FilterValue } from './FilterValue';
import { RelatedLinkImpl } from './RelatedLinkImpl';

export class RelatedFilterValueImpl extends FilterValue {

  private links: Array<RelatedLinkImpl> = new Array<RelatedLinkImpl>();
  constructor() {
    super();
  }

  public getLinks(): Array<RelatedLinkImpl> {
    if (this.links == null) {
      this.links = new Array<RelatedLinkImpl>();
    }
    return this.links;
  }

  addLink(link: RelatedLinkImpl): void {
    if (this.links.indexOf(link) === -1) {
      this.links.push(link);
    }
  }

  removeLink(link: RelatedLinkImpl): void {
    const index: number = this.links.indexOf(link);
    if (index > -1) {
      this.links.splice(index, 1);
    }
  }
}
