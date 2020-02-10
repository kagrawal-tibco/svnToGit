import { isString } from 'util';

import { Logger } from '../core/logger.service';

const log = new Logger();

export class Response {
  status: number;
  totalRows: number;
  record: any[];
  errorMessage: string;
  errorCode: string;
  responseMessage: string;

  constructor() {
    this.status = -1;
    this.totalRows = 0;
    this.record = [];
  }

  static create(raw: any): Response {
    try {
      let res = raw;
      res = (res.response) ? res.response : res;
      const r = new Response();
      r.status = isString(res.status) ? parseInt(res.status, 10) : res.status;
      r.totalRows = res.totalRows;
      const record = res.data ? res.data.record : res.errors ? res.errors.record : res.record;
      r.record = record ? record : [];
      r.errorMessage = res.errorMessage ? res.errorMessage : (res.data && (typeof res.data) === 'string') ? res.data : null;
      r.errorCode = res.errorCode;
      r.responseMessage = res.responseMessage;
      return r;
    } catch (e) {
      log.debug('Unable to parse response ' + raw + '\nbecause: ' + e);
      return null;
    }
  }

  static error(): Response {
    return new Response();
  }

  ok(): boolean {
    return this.status === 0;
  }

  unauthenticated(): boolean {
    return this.errorCode === 'ERR_1002';
  }

  first(): any {
    return this.record[0];
  }
}
