/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:07:12+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:07:15+05:30
 */

import { FilterValue } from './FilterValue';
export class SimpleFilterValueImpl extends FilterValue {

  private simple: string = undefined; // _value

  public get value(): string {
    return this.simple;
  }
  public set value(value: string) {
    this.simple = value;
  }
}
