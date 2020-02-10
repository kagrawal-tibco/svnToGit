
import { Component, HostListener, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';
import { filter, mergeMap, takeUntil } from 'rxjs/operators';

import { DeploymentService } from './deployment.service';

import { ArtifactService } from '../core/artifact.service';
import { ReactService } from '../core/react.service';
import { TakeUntilDestroy } from '../core/take.until.destroy';
import { DeploymentHistoryRecord } from '../models/dto';
import { NotificationCenterService } from '../notification-center/notification-center.service';

interface EntryStyles {
  style: string;
  symbol: string;
}

export class DeploymentHistoryContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg modal-extra-wide';
  constructor(
    public id: string,
    public history?: DeploymentHistoryRecord
  ) {
    super();
  }
}

@Component({
  templateUrl: './deployment-history.modal.html',
})
export class DeploymentHistoryModal extends TakeUntilDestroy implements ModalComponent<DeploymentHistoryContext>, OnInit {
  context: DeploymentHistoryContext;
  entries: DeploymentHistoryRecord[];
  showDetails: Promise<boolean>;
  currentEntry: DeploymentHistoryRecord;

  constructor(
    public dialog: DialogRef<DeploymentHistoryContext>,
    private react: ReactService,
    private service: DeploymentService,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {
    super();
    this.context = dialog.context;
  }

  ngOnInit() {
    if (this.context.id) {
      this.service.getDeploymentHistoryFromArtifactId(this.context.id)
        .then(entries =>
          this.entries = entries
        )
        .then(() => {
          if (this.entries.length > 0) {
            if (this.context.history) {
              this.onSelect(this.entries.find(e => e.entityId === this.context.history.entityId));
            } else {
              this.onSelect(this.entries[0]);
            }
          }
        });
      this.react.deploymentStatusChange.pipe(
        takeUntil(this.whenDestroyed),
        filter(id => this.entries.find(entry => entry.entityId === id) != null),
        mergeMap(id => this.service.getDeploymentHistoryById(id)))
        .subscribe(record => {
          const old = this.entries.find(entry => entry.entityId === record.entityId);
          Object.assign(old, record);
        });
    }
  }

  @HostListener('document:keydown.escape', ['$event'])
  closeDialog(e?: Event) {
    if (e) {
      e.stopPropagation();
      e.preventDefault();
      if (e.type === 'keydown') {
        this.onClose();
        if (NotificationCenterService.notificationDialogOpen) {
          NotificationCenterService.notificationDialogOpen = false;
        }
      }
    }
  }

  onClose() {
    this.dialog.dismiss();
  }

  isActive(entry: DeploymentHistoryRecord) {
    return entry === this.currentEntry;
  }

  onSelect(entry: DeploymentHistoryRecord) {
    if (this.currentEntry !== entry) {
      this.currentEntry = entry;
      this.showDetails = Promise.resolve(true);
    }
  }

  getEntityBrief(entry: DeploymentHistoryRecord) {
    const descriptor = entry.deploymentDescriptor;
    const revision = descriptor.amsDecisionTableVersion;
    return `${entry.deploymentDescriptor.amsDecisionTablePath} (${revision === -1 ? 'latest' : revision})`;
  }

  getTargetBrief(entry: DeploymentHistoryRecord) {
    const descriptor = entry.deploymentDescriptor;
    return `${descriptor.streamBaseTarget}`;
  }

  getTimestamp(entry: DeploymentHistoryRecord): string {
    return this.service.getDeployTime(entry);
  }

  getIndicator(entry: DeploymentHistoryRecord): string {
    return this.service.getIndicator(entry);
  }

  getLabelStyle(entry: DeploymentHistoryRecord): string {
    return this.service.getLabelStyle(entry).slice(8); // Without changing the deployment service, I have to cut off the first word because
    // spaces cannot be added using the customClass input.
  }

  getDuration(entry: DeploymentHistoryRecord): string {
    return this.service.getDuration(entry);
  }
}
