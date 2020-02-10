
import { Injectable } from '@angular/core';

import { filter, map } from 'rxjs/operators';

import { RestService } from '../core/rest.service';
import { WebSocketService } from '../core/websocket.service';
import { ArtifactKind } from '../models/artifact';
import { VerifyConnectRequest, VerifyExecuteRequest, VerifyResponseRecord } from '../models/dto';

export class VerifyConfig {
  artifactId: string;
  artifactKind: ArtifactKind;
  showSimpleResult: boolean;
  showJSONResult: boolean;
}

@Injectable()
export class VerifyService {
  constructor(
    private rest: RestService,
    private webSocket: WebSocketService
  ) { }

  connect(config: VerifyConfig): Promise<VerifyResponseRecord> {
    const request = <VerifyConnectRequest>{
      artifactId: config.artifactId,
      isCheckedOutArtifact: config.artifactKind === 'CHECKOUT',
      showJSONResult: config.showJSONResult,
      showSimpleResult: config.showSimpleResult,
      webSocketId: this.webSocket.webSocketId
    };
    return this.rest.post('/verify/connect', request).pipe(
      filter(res => res.ok()),
      map(res => <VerifyResponseRecord>res.record[0]))
      .toPromise();
  }

  disconnect(verifyRecordId: string): Promise<boolean> {
    return this.rest.put(`/verify/disconnect/${verifyRecordId}`, {}).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  execute(verifyRecordId: string, command: string): Promise<boolean> {
    const payload = <VerifyExecuteRequest>{
      command: command,
      recordId: verifyRecordId
    };
    return this.rest.post(`/verify/execute`, payload).pipe(
      map(res => res.ok()))
      .toPromise();
  }
}
