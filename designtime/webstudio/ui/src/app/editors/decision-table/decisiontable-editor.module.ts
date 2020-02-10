import { HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { MatOptionModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AgGridModule } from 'ag-grid-angular/main';
import { CookieService } from 'ng2-cookies';
import { AccordionModule, TooltipModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { AddColumnModal } from './add-column.modal';
import { AddBEColumnModal } from './be-add-column.modal';
import { BEColumnSettingModal } from './be-column-fields-setting.modal';
import { BEDecisionTableEditorComponent } from './be-decisiontable-editor.component';
import { DecisionTableEditorComponent } from './decisiontable-editor.component';
import { DecisionTableEditorService } from './decisiontable-editor.service';
import { ConflictResolverModal } from './decorators/conflict-resolver.modal';
import { DateTimeModal, DateTimeModalContext } from './decorators/decisiontable-datetime-modal';
import { DomainCellEditorComponent } from './decorators/decisiontable-domain-cell-editor';
import { DeleteResolverModal } from './decorators/delete-resolver.modal';
import { SchemaEditorModule } from './schema-editor/schema-editor.module';

import { environment } from '../../../environments/environment';
import { ArtifactPropertiesService } from '../../artifact-editor/artifact-properties.service';
import { BERestService } from '../../core-be/be.rest.service';
import { AlertService } from '../../core/alert.service';
import { AuthStateService } from '../../core/auth-state.service';
import { Logger } from '../../core/logger.service';
import { RestService } from '../../core/rest.service';
import { SharedModule } from '../../shared/shared.module';
import { ReviewPropertiesComponent } from '../review-properties.component';
import { ReviewPropertiesModule } from '../review-properties.module';

@NgModule({
  imports: [
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatSlideToggleModule,
    SharedModule,
    TooltipModule,
    ReviewPropertiesModule,
    AccordionModule.forRoot(),
    ModalModule.withComponents([
      ConflictResolverModal,
      DateTimeModal,
      DeleteResolverModal,
      AddColumnModal,
      AddBEColumnModal,
      BEColumnSettingModal,
    ]),
    AgGridModule.withComponents([
    ]),
    SchemaEditorModule,
  ],
  providers: [
    DecisionTableEditorService,
    I18n,
    ArtifactPropertiesService,
    {
      provide: RestService,
      useFactory: restServiceFactory,
      deps: [HttpClient, AlertService, AuthStateService, Logger, CookieService, I18n]
    }
  ],
  declarations: [
    DecisionTableEditorComponent,
    BEDecisionTableEditorComponent,
    DomainCellEditorComponent,
    AddColumnModal,
    AddBEColumnModal,
    BEColumnSettingModal,
    ConflictResolverModal,
    DeleteResolverModal,
    DateTimeModal
  ]
})
export class DecisionTableEditorModule {

}

export function restServiceFactory(http: HttpClient, alert: AlertService, authState: AuthStateService, log: Logger, cookieService: CookieService, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BERestService(http, alert, authState, log, cookieService, i18n);
  } else {
    return new RestService(http, alert, authState, log, cookieService, i18n);
  }
}
