export class Property {

  constructor(
    public key: string,
    public val: string,
    public type: string
  ) { }
  static fromDom(dom: Element): Property {
    const attrName = dom.getAttribute('name');
    const attrVal = dom.getAttribute('value');
    const attrType = dom.getAttribute('type');
    return new Property(attrName, attrVal, attrType);
  }

  toXml(indent: number) {
    const typeAttr = this.type ? `type="${this.type}" ` : '';
    return `${' '.repeat(indent)}<prop name="${this.key}" ${typeAttr}value="${this.val}"/>`;
  }

  toString() {
    return `{
      val: ${this.val}
    }`;
  }

}
