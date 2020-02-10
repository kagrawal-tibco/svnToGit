import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {
  MatAutocompleteModule, MatButtonModule, MatButtonToggleModule, MatCardModule,
  MatCheckboxModule, MatDialogModule, MatDividerModule, MatFormFieldModule, MatGridListModule, MatInputModule, MatSelectModule
} from '@angular/material';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';

import { TabsModule, TooltipModule } from 'ngx-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { ModalModule } from 'ngx-modialog';

import { SharedModule } from 'app/shared/shared.module';
import { ContentGroupModule } from 'app/workspace/content-group/content-group.module';

import { ActivityStreamFilterComponent } from './activity-stream/activity-stream-filter.component';
import { ActivityStreamComponent } from './activity-stream/activity-stream.component';
import { DashboardComponent } from './dashboard.component';
import { DashboardService } from './dashboard.service';
import { OldDashboardComponent } from './old-dashboard.component';
import { OldDashboardService } from './old-dashboard.service';
import { WorklistModal } from './worklist.modal';

import { AuditTrailModule } from '../audit-trail/audit-trail.module';
import { CommitSharedModule } from '../commit-shared/commit-shared.module';

@NgModule({
  imports: [CommonModule, TabsModule, CommitSharedModule, AuditTrailModule, MatCardModule, MatSelectModule, MatAutocompleteModule,
    MatTooltipModule, InfiniteScrollModule, MatDialogModule, MatMenuModule, MatIconModule, MatButtonToggleModule,
    MatGridListModule, MatDividerModule, MatButtonModule, FlexLayoutModule, MatCheckboxModule, SharedModule, MatFormFieldModule,
    TooltipModule, FormsModule, MatInputModule, ReactiveFormsModule, ContentGroupModule, // ModalModule.withComponents([WorklistModal]),
  ],
  entryComponents: [
    WorklistModal
  ],
  declarations: [DashboardComponent, OldDashboardComponent, ActivityStreamComponent, WorklistModal, ActivityStreamFilterComponent],
  providers: [DashboardService, OldDashboardService],
  exports: [ActivityStreamFilterComponent, WorklistModal],
})
export class DashboardModule {
}
