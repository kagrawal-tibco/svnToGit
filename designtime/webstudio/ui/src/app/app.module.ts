import { registerLocaleData, HashLocationStrategy, LocationStrategy } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import localear from '@angular/common/locales/ar';
import localede from '@angular/common/locales/de';
import localeen from '@angular/common/locales/en';
import localees from '@angular/common/locales/es';
import localefr from '@angular/common/locales/fr';
import localeit from '@angular/common/locales/it';
import localeko from '@angular/common/locales/ko';
import localezh from '@angular/common/locales/zh';
import { ErrorHandler, LOCALE_ID, NgModule, TRANSLATIONS, TRANSLATIONS_FORMAT } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { BsDropdownModule, DatepickerModule, ProgressbarModule, TabsModule, TimepickerModule, TooltipModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';
import { BootstrapModalModule } from 'ngx-modialog/plugins/bootstrap';
import 'platform';
import 'rxjs/Rx';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BEProjectImportModule } from './be-project-importer/be-project-importer.module';
import { CommitSharedModule } from './commit-shared/commit-shared.module';
import { AuthGuard } from './core/auth-guard.service';
import { CoreModule } from './core/core.module';
import { ManagementGuard } from './core/management-guard.service';
import { ModalService } from './core/modal.service';
import { DashboardModule } from './dashboard/dashboard.module';
import { Differ } from './editables/decision-table/differ/differ';
import { ManagedExceptionHandler } from './exception-handler/managed-exception-handler';
import { NavBarComponent } from './layout/nav-bar.component';
import { NotificationCenterModule } from './notification-center/notification-center.module';
import { RedirectComponent } from './redirect/redirect.component';
import { SCMIntegrationModule } from './scm-integration/scm-integration.module';
import { SharedModule } from './shared/shared.module';
// import { AuthModule } from './auth/auth.module';
// import { ManagementModule } from './management/management.module';
// import { SettingsModule } from './settings/settings.module';
import { TreeViewModule } from './widgets/tree-view/tree-view.module';
import { ContentGroupModule } from './workspace/content-group/content-group.module';
import { WorkspaceModule } from './workspace/workspace.module';



let locale = navigator.language as string;
if (locale.search('zh') === -1) {
  if (locale.indexOf('-') !== -1) {
    locale = locale.split('-')[0];
  }

  if (locale.indexOf('_') !== -1) {
    locale = locale.split('_')[0];
  }

}
const translations = require('raw-loader!../locale/messages_' + locale + '.xlf').default;

switch (locale) {
  case 'en': registerLocaleData(localeen, locale); break;
  case 'fr': registerLocaleData(localefr, locale); break;
  case 'it': registerLocaleData(localeit, locale); break;
  case 'de': registerLocaleData(localede, locale); break;
  case 'es': registerLocaleData(localees, locale); break;
  case 'ko': registerLocaleData(localeko, locale); break;
  case 'ar': registerLocaleData(localear, locale); break;
  case 'zh-CN': registerLocaleData(localezh, locale); break;
  case 'zh-HK': registerLocaleData(localezh, locale); break;
  case 'zh-TW': registerLocaleData(localezh, locale); break;
}

@NgModule({
  declarations: [
    AppComponent,
    NavBarComponent,
    RedirectComponent,
  ],
  imports: [
    // Angular Material Modules
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatDialogModule,
    MatSidenavModule,

    // All others
    AppRoutingModule,
    BrowserModule,
    HttpClientModule,
    SCMIntegrationModule,
    SharedModule,
    WorkspaceModule,
    DashboardModule,
    // These are lazily loaded, so we should not import them directly.  Doing so causes resolution errors
    //    SettingsModule,
    //    ManagementModule,
    //    AuthModule,
    CoreModule,
    ContentGroupModule,
    FormsModule,
    NotificationCenterModule,
    TreeViewModule,
    CommitSharedModule,
    BootstrapModalModule,
    BrowserAnimationsModule,
    TooltipModule.forRoot(),
    ModalModule.forRoot(),
    ProgressbarModule.forRoot(),
    BsDropdownModule.forRoot(),
    TabsModule.forRoot(),
    DatepickerModule.forRoot(),
    TimepickerModule.forRoot(),
    BEProjectImportModule,
  ],
  providers: [
    Differ,
    AuthGuard,
    ManagementGuard,
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: ErrorHandler, useClass: ManagedExceptionHandler },
    ModalService,
    I18n,
    [
      { provide: TRANSLATIONS, useValue: translations },
      { provide: TRANSLATIONS_FORMAT, useValue: 'xlf' },
      { provide: LOCALE_ID, useValue: locale },
    ]
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
