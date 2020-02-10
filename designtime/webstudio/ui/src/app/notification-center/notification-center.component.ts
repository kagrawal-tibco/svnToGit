import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { NotificationCenterService } from './notification-center.service';

import { DeploymentHistoryContext, DeploymentHistoryModal } from '../artifact-deployment/deployment-history.modal';
import { DeploymentService } from '../artifact-deployment/deployment.service';
import { AlertService, AlertType } from '../core/alert.service';
import { ModalService } from '../core/modal.service';
import { Notification } from '../models/notification';

@Component({
  selector: 'notification-center',
  templateUrl: './notification-center.component.html',
  styleUrls: ['./notification-center.component.css'],
})
export class NotificationCenterComponent {
  constructor(
    private serviceNotification: NotificationCenterService,
    private serviceModal: ModalService,
    private serviceDeployment: DeploymentService,
    private router: Router,
    private alert: AlertService,
    public i18n: I18n
  ) { }

  get notifications() {
    return this.serviceNotification.getNotifications();
  }

  openDetails(notification: Notification) {
    let severity = 'info' as AlertType;
    switch (notification.icon) {
      case 'danger':
        severity = 'error';
        break;
      case 'success':
        severity = 'success';
        break;
      case 'warning':
        severity = 'warning';
        break;
    }
    this.alert.flashDetail(notification, severity, true, -1);
  }

  handleClick(link: string[]) {
    /**
     * This will need to be improved for a future release.
     * Routing was working before, but in order to keep the notifications window open, I've gotten rid of it.
     */
    if (link[0].startsWith('/deployment/history')) {
      NotificationCenterService.notificationDialogOpen = true;
      this.serviceDeployment.getDeploymentHistoryById(link[1]).then(
        (deplHistRec) => {
          if (!deplHistRec) {
            NotificationCenterService.notificationDialogOpen = false;
            // this.router.navigate(['/workspace']);
          } else {
            this.serviceModal.open(DeploymentHistoryModal, new DeploymentHistoryContext(deplHistRec.artifactId, deplHistRec))
              .then((nothing) => {/** * This is only called with a success response. Not expecting anything from the modal, do nothing. */ },
                (error?) => {
                  if (error) {
                    throw error;
                  } else { /** Just closed */
                    /**
                     * Eventually this might be restored, in order to restore url based navigation. But for the time being it's commented out.
                     */
                    // this.router.navigate(['/workspace']);
                  }
                }
              );
          }
        }
      );
    } else {
      this.router.navigate(link);
    }
  }

  remove(item: Notification) {
    this.serviceNotification.remove(item);
  }

  markAllAsRead() {
    this.serviceNotification.clearAll();
  }
}
