import { Injectable } from '@angular/core';

import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { AuthStateService } from '../core/auth-state.service';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { ReactService } from '../core/react.service';
import { SettingsService } from '../core/settings.service';
import { WebSocketService } from '../core/websocket.service';
import { DashboardService } from '../dashboard/dashboard.service';
import { NotificationCenterService } from '../notification-center/notification-center.service';

@Injectable()
export class BEReactService extends ReactService {

  constructor(
    protected log: Logger,
    protected authState: AuthStateService,
    protected websocket: WebSocketService,
    protected notification: NotificationCenterService,
    protected dashboard: DashboardService,
    protected artifact: ArtifactService,
    protected lifecycle: LifecycleService,
    protected alertService: AlertService,
    protected settings: SettingsService
  ) {
    super(log, authState, websocket, notification, dashboard, artifact, lifecycle, alertService, settings);
  }

  init() {
  }
}
