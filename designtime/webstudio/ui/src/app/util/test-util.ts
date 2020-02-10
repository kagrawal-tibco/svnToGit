import { HttpClient } from '@angular/common/http';

import * as RestResponse from '../models/response';
export class TestUtil {
  static mockRespond(/*connection: MockConnection,*/ payload: any[], errorCode?: string, errorMessage?: string) {
    const r = TestUtil.makeRestResponse(payload, errorCode, errorMessage);

    // connection.mockRespond(new Response(<ResponseOptions>{
    //   status: 200,
    //   statusText: 'ok',
    //   merge: null,
    //   headers: new Headers(),
    //   body: r,
    //   url: connection.request.url,
    //   type: ResponseType.Basic
    // }));
  }
  static makeRestResponse(payload: any[], errorCode?: string, errorMessage?: string): RestResponse.Response {
    // let res = RestResponse.Response.create(<Response>{
    //   json: () => ({
    //     status: errorCode ? -1 : 0,
    //     errorMessage: errorMessage ? errorMessage : null,
    //     errorCode: errorCode ? errorCode : null,
    //     record: payload,
    //     totalRows: payload.length,
    //   })
    // });
    // return res;
    return null;
  }
}
