/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:04:51+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:04:52+05:30
 */

export class RelatedLinkImpl {

  private name: string = undefined; // _linkText
  private type: string = undefined; // _linkType

  constructor(linkText?: string, linkType?: string) {
    this.name = linkText;
    this.type = linkType;
  }

  getName(): string {
    return this.name;
  }

  setName(linkText: string) {
    this.name = linkText;
  }

  getType(): string {
    return this.type;
  }

  setType(linkType: string) {
    this.type = linkType;
  }
}
