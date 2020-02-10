import { DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';

import { AgGridModule } from 'ag-grid-angular/main';
import { ModalModule } from 'ngx-modialog';

import { AuditTrailService } from 'app/audit-trail/audit-trail.service';

import { AuditTrailComponent } from './audit-trail.component';
import { AuditTrailModal } from './audit-trail.modal';

import { BEManagementService } from '../management/be-management.service';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    AgGridModule,
    MatCardModule,
    MatInputModule,
    MatSelectModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatButtonModule,
    MatTooltipModule,
    MatGridListModule,
    FormsModule,
    ReactiveFormsModule,
    ModalModule.withComponents([
      AuditTrailModal
    ])
  ],
  declarations: [AuditTrailModal, AuditTrailComponent],
  providers: [
    DatePipe,
    AuditTrailService,
    BEManagementService
  ],
  exports: [AuditTrailComponent]
})
export class AuditTrailModule {

}
