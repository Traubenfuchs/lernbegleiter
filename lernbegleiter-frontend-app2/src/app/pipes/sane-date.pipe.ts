import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'saneDate'
})
export class SaneDatePipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    if (!value) {
      return value
    }

    const d = new Date(Date.parse(value))

    return `${this.lp(d.getDay())}.${this.lp(d.getMonth())}.${this.lp(d.getFullYear())}`;
  }

  lp(input) {
    if (input < 10) {
      return '0'+input
    }
    return input
  }

}
