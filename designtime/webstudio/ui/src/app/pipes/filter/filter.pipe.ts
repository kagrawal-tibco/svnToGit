import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(values: any[], callback: (val: any) => boolean): any {
    if (!values || !callback) {
      return values;
    }
    return values.filter(value => callback(value));
  }

}
