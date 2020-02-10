import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TooltipModule } from 'ngx-bootstrap';

import { NotificationCenterComponent } from './notification-center.component';
import { NotificationCenterService } from './notification-center.service';
import { NotificationDetailComponent } from './notification-detail.component';

@NgModule({
  imports: [CommonModule, RouterModule, TooltipModule],
  declarations: [NotificationCenterComponent, NotificationDetailComponent],
  exports: [NotificationCenterComponent, NotificationDetailComponent],
  providers: [NotificationCenterService]
})
export class NotificationCenterModule {
}
