/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:02:39+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:02:43+05:30
 */

import * as _ from 'lodash';

import { Filter } from './Filter';

export class BuilderSubClauseImpl extends Filter {

  private filter: Array<Filter> = new Array<Filter>();

  getFilters(): Array<Filter> {
    if (this.filter == null) {
      this.filter = new Array<Filter>();
    }
    return this.filter;
  }

  addFilter(newFilter: Filter, position?: number): void {
    if (this.filter.indexOf(newFilter) === -1) {
      if (position != null && position > -1) {
        this.filter.splice(position, 0, newFilter);
      } else {
        this.filter.push(newFilter);
      }
    }
  }

  removeFilter(currentFilter: Filter): number {
    for (let i = 0; i < this.filter.length; i++) {
      if (_.isEqual(this.filter[i], currentFilter)) {
        this.filter.splice(i, 1);
        return i;
      }
    }
    return -1;
  }
}
