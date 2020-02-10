import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';
import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { DeploymentService } from './deployment.service';

import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { Artifact } from '../models/artifact';
import { DeploymentDescriptorItem, DeploymentDescriptorRecord } from '../models/dto';
import { ResizableModal } from '../shared/resizablemodal';

export class DescriptorManageModalContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg';
  constructor(
    public projectName: string,
    public artifact: Artifact
  ) {
    super();
  }

}

type MODE = 'CREATE' | 'READ';

@Component({
  templateUrl: './descriptor-manage.modal.html'
})
export class DescriptorManageModal extends ResizableModal implements ModalComponent<DescriptorManageModalContext>, OnInit {
  context: DescriptorManageModalContext;
  descriptors: DeploymentDescriptorRecord[];
  selected: DeploymentDescriptorRecord;
  newDescriptor: DeploymentDescriptorItem;
  showDescriptor: Promise<boolean>;
  revisions: number[];
  valid: boolean;
  mode: MODE;

  constructor(
    private log: Logger,
    private service: DeploymentService,
    private modal: ModalService,
    private artifact: ArtifactService,
    public i18n: I18n,
    public dialog: DialogRef<DescriptorManageModalContext>
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.context = dialog.context;
  }

  ngOnInit() {
    this.refresh();
  }

  refresh() {
    this.service.getAllDescriptors(this.context.projectName, this.context.artifact.path)
      .then(entries => this.descriptors = entries)
      .then(() => this.artifact.getArtifactHistory(this.context.artifact))
      .then(entries => this.revisions = entries.map(entry => entry.version))
      .then(() => {
        if (this.descriptors.length > 0) {
          this.onSelect(this.descriptors[0]);
        } else {
          this.setInCreateMode();
        }
      });
  }

  get title() {
    return this.i18n('Manage Deployment Descriptors of {{name}}', { name: this.context.artifact.name });
  }

  isActive(descriptor: DeploymentDescriptorRecord) {
    return this.mode === 'READ' && this.selected === descriptor;
  }

  onSelect(descriptor: DeploymentDescriptorRecord) {
    if (this.selected !== descriptor) {
      this.mode = 'READ';
      this.selected = descriptor;
      this.showDescriptor = Promise.resolve(true);
      this.newDescriptor = null;
    }
  }

  onSave() {
    if (this.inCreateMode) {
      return this.service.saveDescriptor(this.newDescriptor)
        .then(record => {
          if (record) {
            this.descriptors.unshift(record);
            this.onSelect(record);
          }
        });
    } else {
      throw Error(this.i18n('Shall not save when not in create mode'));
    }
  }

  onDelete() {
    if (this.selected) {
      const toDelete = this.selected;
      this.modal.confirm()
        .message(this.i18n('Please confirm you want to remove the deployment descriptor'))
        .okBtn(this.i18n('Confirm'))
        .cancelBtn(this.i18n('Cancel'))
        .okBtnClass('btn btn-danger btn-sm')
        .cancelBtnClass('btn btn-sm btn-outline-primary')
        .open().result
        .then(() => this.service.remove(toDelete.entityId)
          , err => {
            if (err) {
              throw err;
            }
          })
        .then(ok => {
          if (ok) {
            this.refresh();
          }
        });
    }
  }

  onCopy() {
    this.setInCreateMode(this.selected);
  }

  setInCreateMode(copy?: DeploymentDescriptorItem) {
    if (!this.inCreateMode) {
      this.mode = 'CREATE';
      this.selected = null;
      if (copy) {
        this.newDescriptor = _.cloneDeep(copy);
      } else {
        this.newDescriptor = this.service.createNewDescriptor(this.context.projectName, this.context.artifact);
      }
      this.showDescriptor = Promise.resolve(true);
    }
  }

  onValidChange(valid: boolean) {
    this.valid = valid;
  }

  get inCreateMode() {
    return this.mode === 'CREATE';
  }

  onClose() {
    this.dialog.dismiss();
  }

  getEntityBrief(descriptor: DeploymentDescriptorRecord) {
    return this.service.getEntityBrief(this.context.artifact, descriptor);
  }

  getTargetBrief(descriptor: DeploymentDescriptorRecord) {
    return this.service.getTargetBrief(this.context.artifact, descriptor);
  }
}
