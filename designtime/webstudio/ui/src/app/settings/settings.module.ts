import { HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTooltipModule } from '@angular/material/tooltip';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { CookieService } from 'ng2-cookies';
import { TabsModule } from 'ngx-bootstrap';

import { AccountsComponent } from './accounts/accounts.component';
import { DownloadProjectsComponent } from './download-projects/download-projects.component';
import { DownloadProjectsService } from './download-projects/download-projects.service';
import { PreferenceComponent } from './preference/preference.component';
import { AddEditBEDeploymentPreferences } from './projects/be-deployment-preferences/add-edit-be-deployment-preferences.component';
import { BEDeploymentPreferences } from './projects/be-deployment-preferences/be-deployment-preferences.component';
import { BEProjectsComponent } from './projects/be-projects.component';
import { NotificationsComponent } from './projects/notification/notification.component';
import { OperatorPreferenceComponent } from './projects/operator-preferences/operator-preferences.component';
import { ProjectsComponent } from './projects/projects.component';
import { SettingsRoutingModule } from './settings-routing.module';
import { SettingsComponent } from './settings.component';

import { environment } from '../../environments/environment';
import { BEProjectService } from '../core-be/be.project.service';
import { BERestService } from '../core-be/be.rest.service';
import { BEUserService } from '../core-be/be.user.service';
import { AlertService } from '../core/alert.service';
import { AuthStateService } from '../core/auth-state.service';
import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { PasswordEditorModule } from '../shared/password-editor/password-editor.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    SettingsRoutingModule,
    TabsModule,
    PasswordEditorModule,
    MatGridListModule,
    MatCardModule,
    MatDividerModule,
    MatListModule,
    MatIconModule,
    MatTooltipModule,
    MatSidenavModule,
    MatExpansionModule,
    MatButtonModule,
    MatMenuModule,
    MatFormFieldModule,
    MatDialogModule,
    MatInputModule,
    MatSelectModule,
    MatCheckboxModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    SettingsComponent,
    AccountsComponent,
    ProjectsComponent,
    BEProjectsComponent,
    PreferenceComponent,
    NotificationsComponent,
    OperatorPreferenceComponent,
    BEDeploymentPreferences,
    AddEditBEDeploymentPreferences,
    DownloadProjectsComponent
  ],
  providers: [
    BEUserService,
    BEProjectService,
    {
      provide: RestService,
      useFactory: restServiceFactory,
      deps: [HttpClient, AlertService, AuthStateService, Logger, CookieService, I18n]
    },
    DownloadProjectsService,
    I18n
  ],
  exports: [
  ],
  entryComponents: [
    AddEditBEDeploymentPreferences
  ]
})
export class SettingsModule {

}

export function restServiceFactory(http: HttpClient, alert: AlertService, authState: AuthStateService, log: Logger, cookieService: CookieService, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BERestService(http, alert, authState, log, cookieService, i18n);
  } else {
    return new RestService(http, alert, authState, log, cookieService, i18n);
  }
}
