/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:04:35+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:04:38+05:30
 */

import { FilterValue } from './FilterValue';

export class RangeFilterValueImpl extends FilterValue {

  private minValue: string = undefined;
  private maxValue: string = undefined;
  getMinValue(): string {
    return this.minValue;
  }
  setMinValue(value: string) {
    this.minValue = value;
  }

  getMaxValue(): string {
    return this.maxValue;
  }
  setMaxValue(value: string) {
    this.maxValue = value;
  }
}
