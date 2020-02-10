import { Injectable } from '@angular/core';

import { environment } from '../../environments/environment';

@Injectable()
export class Logger {

  constructor() { }

  debug(...o: any[]) {
    if (!environment.production) {
      console.log(this.head(), ...o);
    }
  }

  log(...o: any[]) {
    console.log(this.head(), ...o);
  }
  err(...o: any[]) {
    console.error(this.head(), ...o);
  }
  warn(...o: any[]) {
    console.warn(this.head(), ...o);
  }

  private head() {
    const err = this.getErrorObject();
    const callerLine = err.stack.split('\n')[6];
    const index = callerLine.indexOf('at ');
    const clean = callerLine.slice(index + 2, callerLine.length).trim();
    return `${new Date().toISOString()}: ${clean}`;
  }

  private getErrorObject() {
    try { throw Error(''); } catch (err) { return err; }
  }
}
