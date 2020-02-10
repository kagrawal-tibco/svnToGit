
export type CellType = 'cond' | 'act' | 'prop';

export class Cell {
  /**
   * Shall not call this constructor directly. Use Rule#createAndAddCell instead.
   */
  constructor(
    private _ruleId: string,
    private _colId: string,
    private _expr: string,
    private _type: CellType,
    private _disabled: boolean,
    private _comment?: string
  ) { }

  getExpr() {
    return this._expr ? this._expr : '';
  }

  setExpr(expr: string) {
    this._expr = expr;
    return this;
  }

  isEmpty(): boolean {
    return this.getExpr() === '';
  }

  isDisabled(): boolean {
    return this._disabled;
  }

  setDisabled(val: boolean) {
    return this._disabled = val;
  }

  getId() {
    return `${this._ruleId}_${this._colId}`;
  }

  getRuleId() {
    return this._ruleId;
  }

  getType() {
    return this._type;
  }

  getColId() {
    return this._colId;
  }

  getComment() {
    return this._comment;
  }

  setComment(val: string) {
    return this._comment = val;
  }

  toXml(indent: number): string {
    const tagName: string = this._type;
    const idAttr = `id="${this.getId()}"`;
    const colIdAttr = `colId="${this._colId}"`;
    const exprAttr = `expr="${this.escapeExpr()}"`;
    return `${' '.repeat(indent)}<${tagName} ${idAttr} ${colIdAttr} ${exprAttr}/>`;
  }

  toString() {
    return this.getExpr();
  }

  escapeExpr(): string {
    return this._expr.replace(/[<>&'"]/g, c => {
      switch (c) {
        case '<':
          return '&lt;';
        case '>': // not escape >
          return c;
        case '&':
          return '&amp;';
        case '\'':
          return '&apos;';
        case '"':
          return '&quot;';
      }
    });
  }

}
