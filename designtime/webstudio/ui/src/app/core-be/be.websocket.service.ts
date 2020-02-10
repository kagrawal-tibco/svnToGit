import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ArtifactService } from 'app/core/artifact.service';

import { AlertService } from '../core/alert.service';
import { AuthStateService } from '../core/auth-state.service';
import { Logger } from '../core/logger.service';
import { ProjectService } from '../core/project.service';
import { RestService } from '../core/rest.service';
import { WebSocketService } from '../core/websocket.service';

@Injectable()
export class BEWebSocketService extends WebSocketService {
  constructor(
    protected rest: RestService,
    protected project: ProjectService,
    protected authState: AuthStateService,
    protected alert: AlertService,
    protected log: Logger,
    protected artifact: ArtifactService,
    public i18n: I18n
  ) {
    super(rest, project, authState, alert, log, artifact, i18n);
  }

  init(): Promise<void> {
    this.log.warn(this.i18n('WebSocket are not supported, notifiaction service might not be usable.'));
    return Promise.resolve();
  }
}
