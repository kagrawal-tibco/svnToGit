/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:03:23+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-28T20:58:47+05:30
 */

export class Filter {

  private filterId: string = undefined;

  private static getUniqueFilterId(): string {
    return String(new Date().getTime() + Math.random() * 1000 + Math.random() * 1000 + Math.random() * 1000);
  }

  getFilterId(): string {
    if (this.filterId == null) {
      this.filterId = Filter.getUniqueFilterId();
    }
    return this.filterId;
  }

  setFilterId(filterId: string) {
    this.filterId = filterId;
  }
}
