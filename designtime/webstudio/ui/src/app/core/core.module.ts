import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ToasterModule, ToasterService } from 'angular2-toaster';
import { CookieService } from 'ng2-cookies';
import { AlertModule } from 'ngx-bootstrap';

import { AlertDetailComponent } from './alert-detail.component';
import { AlertDisplayComponent } from './alert-display.component';
import { AlertService } from './alert.service';
import { ArtifactService } from './artifact.service';
import { AuthStateService } from './auth-state.service';
import { GroupService } from './group.service';
import { LifecycleService } from './lifecycle.service';
import { Logger } from './logger.service';
import { ProjectService } from './project.service';
import { ProviderService } from './provider.service';
import { ReactService } from './react.service';
import { RecordService } from './record.service';
import { RestService } from './rest.service';
import { SCMService } from './scm.service';
import { SettingsService } from './settings.service';
import { UIService } from './ui.service';
import { UserService } from './user.service';
import { WebSocketService } from './websocket.service';

import { environment } from '../../environments/environment';
import { ArtifactProblemsService } from '../artifact-editor/artifact-problems.service';
import { BEArtifactProblemsService } from '../artifact-editor/be.artifact-problems.service';
import { AuthService } from '../auth/auth.service';
import { BEArtifactService } from '../core-be/be.artifact.service';
import { BEGroupService } from '../core-be/be.group.service';
import { BELifecycleService } from '../core-be/be.lifecycle.service';
import { BEOperatorService } from '../core-be/be.operator.service';
import { BEProjectService } from '../core-be/be.project.service';
import { BEReactService } from '../core-be/be.react.service';
import { BERecordService } from '../core-be/be.record.service';
import { BERestService } from '../core-be/be.rest.service';
import { BESettingsService } from '../core-be/be.settings.service';
import { BEUserService } from '../core-be/be.user.service';
import { BEWebSocketService } from '../core-be/be.websocket.service';
import { DashboardService } from '../dashboard/dashboard.service';
import { NotificationCenterModule } from '../notification-center/notification-center.module';
import { NotificationCenterService } from '../notification-center/notification-center.service';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';

@NgModule({
  imports: [
    CommonModule,
    AlertModule,
    ToasterModule,
    NotificationCenterModule
  ],
  declarations: [AlertDisplayComponent, AlertDetailComponent],
  providers: [
    CookieService,
    ToasterService,
    AlertService,
    AuthService,
    AuthStateService,
    Logger,
    ProviderService,
    I18n,
    UIService,
    {
      provide: UserService,
      useFactory: userServiceFactory,
      deps: [RestService, AuthStateService]
    },
    {
      provide: GroupService,
      useFactory: groupServiceFactory,
      deps: [Logger, RestService, RecordService, ArtifactService]
    },
    {
      provide: WebSocketService,
      useFactory: webSocketServiceFactory,
      deps: [RestService, ProjectService, AuthStateService, AlertService, Logger, ArtifactService, I18n]
    },
    {
      provide: ArtifactService,
      useFactory: artifactServiceFactory,
      deps: [RestService, RecordService, SettingsService, AlertService, Logger, I18n]
    },
    {
      provide: SCMService,
      useFactory: scmServiceFactory,
      deps: [RestService]
    },
    {
      provide: LifecycleService,
      useFactory: lifecycleServiceFactory,
      deps: [Logger, RecordService, RestService, ArtifactService, ProjectService, SettingsService]
    },
    {
      provide: ProjectService,
      useFactory: projectServiceFactory,
      deps: [Logger, ArtifactService, RestService, RecordService, GroupService, I18n]
    },
    {
      provide: ReactService,
      useFactory: reactServiceFactory,
      deps: [Logger, AuthStateService, WebSocketService, NotificationCenterService, DashboardService,
        ArtifactService, LifecycleService, AlertService, SettingsService]
    },
    {
      provide: RestService,
      useFactory: restServiceFactory,
      deps: [HttpClient, AlertService, AuthStateService, Logger, CookieService, I18n]
    },
    {
      provide: ArtifactProblemsService,
      useFactory: artifactProblemServiceFactory,
      deps: [ArtifactService, AlertService, UserService, MultitabEditorService, I18n]
    },
    {
      provide: SettingsService,
      useFactory: settingServiceFactory,
      deps: [RestService, Logger]
    },
    {
      provide: RecordService,
      useFactory: recordServiceFactory,
      deps: [RestService, Logger, I18n]
    },
    {
      provide: BEOperatorService,
      useFactory: operatorServiceFactory,
      deps: [RestService, Logger]
    }
  ],
  entryComponents: [AlertDetailComponent],
  exports: [AlertDisplayComponent]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import it in the AppModule only');
    }
  }

}

export function scmServiceFactory(rest: RestService) {
  return new SCMService(rest);
}

export function userServiceFactory(rest: RestService, auth: AuthStateService) {
  if (environment.enableBEUI) {
    return new BEUserService(rest, auth);
  } else {
    return new UserService(rest, auth);
  }
}

export function groupServiceFactory(log: Logger, rest: RestService, record: RecordService, artifact: ArtifactService) {
  if (environment.enableBEUI) {
    return new BEGroupService(log, rest, record, artifact);
  } else {
    return new GroupService(log, rest, record);
  }
}

export function webSocketServiceFactory(rest: RestService, project: ProjectService, authState: AuthStateService, alert: AlertService, log: Logger, artifact: ArtifactService, i18n: I18n) {
  return new WebSocketService(rest, project, authState, alert, log, artifact, i18n);
}

export function artifactServiceFactory(rest: RestService, record: RecordService, settings: SettingsService, alert: AlertService, log: Logger, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BEArtifactService(rest, record, settings, alert, log, i18n);
  } else {
    return new ArtifactService(rest, record, settings, alert, log, i18n);
  }
}

export function lifecycleServiceFactory(log: Logger, record: RecordService, rest: RestService, artifact: ArtifactService, project: ProjectService, settings: SettingsService) {
  if (environment.enableBEUI) {
    return new BELifecycleService(log, record, rest, artifact, project, settings);
  } else {
    return new LifecycleService(log, record, rest, artifact, project);
  }
}

export function projectServiceFactory(log: Logger, artifact: ArtifactService, rest: RestService, record: RecordService, group: GroupService, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BEProjectService(log, artifact, rest, record, group, i18n);
  } else {
    return new ProjectService(log, artifact, rest, record, i18n);
  }
}

export function reactServiceFactory(log: Logger, authState: AuthStateService, websocket: WebSocketService,
  notification: NotificationCenterService, dashboard: DashboardService, artifact: ArtifactService,
  lifecycle: LifecycleService, alertService: AlertService, settings: SettingsService) {
  return new ReactService(log, authState, websocket, notification, dashboard, artifact, lifecycle, alertService, settings);
}

export function restServiceFactory(http: HttpClient, alert: AlertService, authState: AuthStateService, log: Logger, cookieService: CookieService, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BERestService(http, alert, authState, log, cookieService, i18n);
  } else {
    return new RestService(http, alert, authState, log, cookieService, i18n);
  }
}

export function artifactProblemServiceFactory(artifact: ArtifactService, alert: AlertService, user: UserService, multitab: MultitabEditorService, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BEArtifactProblemsService(artifact, alert, user, multitab, i18n);
  } else {
    return new ArtifactProblemsService(artifact, alert, user, multitab);
  }
}

export function settingServiceFactory(rest: RestService, logger: Logger) {
  if (environment.enableBEUI) {
    return new BESettingsService(rest, logger);
  } else {
    return new SettingsService(rest, logger);
  }
}

export function recordServiceFactory(rest: RestService, logger: Logger, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BERecordService(rest, logger, i18n);
  } else {
    return new RecordService(logger, i18n);
  }
}

export function operatorServiceFactory(rest: BERestService, log: Logger) {
  if (environment.enableBEUI) {
    return new BEOperatorService(rest, log);
  }
}
