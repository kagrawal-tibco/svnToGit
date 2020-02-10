import { Component, OnInit, Type } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { DeploymentCreationContext } from './deployment-creation.context';
import { DeploymentService } from './deployment.service';
import { CreateOrSelectComponent } from './pages/create-or-select.component';
import { DeployConfigComponent } from './pages/deploy-config.component';

import { ArtifactService } from '../core/artifact.service';
import { Artifact } from '../models/artifact';
import { NavigableWizardPageComponent } from '../navigable-wizard/navigable-wizard-page.component';
import { ResizableModal } from '../shared/resizablemodal';

export class DeploymentCreationModalContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-extra-wide';
  constructor(
    public projectName: string,
    public artifact: Artifact,
    public i18n: I18n
  ) {
    super();
  }
}

@Component({
  templateUrl: './deployment-creation.modal.html'
})
export class DeploymentCreationModal extends ResizableModal implements ModalComponent<DeploymentCreationModalContext>, OnInit {
  context: DeploymentCreationModalContext;
  params: DeploymentCreationContext;
  position = 0;
  pages: Type<NavigableWizardPageComponent<DeploymentCreationContext>>[] = [
    CreateOrSelectComponent,
    DeployConfigComponent
  ];

  constructor(
    public dialog: DialogRef<DeploymentCreationModalContext>,
    private artifact: ArtifactService,
    private service: DeploymentService
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.context = dialog.context;
  }

  ngOnInit() {
    const params: DeploymentCreationContext = {
      artifact: this.context.artifact,
      revisions: [],
      projectName: this.context.projectName,
      descriptors: [],
      mode: 'CREATE',
      descriptor: null,
      deployTime: null,
      futureDeploy: false,
      showCalendar: false,
      canFinish: Promise.resolve(false),
      canNextStep: Promise.resolve(false)
    };
    this.service.getAllDescriptors(this.context.projectName, this.context.artifact.path)
      .then(descriptors => params.descriptors = descriptors)
      .then(() => this.artifact.getArtifactHistory(this.context.artifact))
      .then(entries => params.revisions = entries ? entries.map(entry => entry.version) : [])
      .then(() => this.params = params);
  }

  onCancel() {
    this.dialog.dismiss();
  }

  showNext() {
    return this.position === 0;
  }

  onNext() {
    this.position++;
  }

  onPrev() {
    this.position--;
  }

  showPrev() {
    return this.position > 0;
  }

  canPrev() {
    return this.position > 0;
  }

  showFinish() {
    return this.position === 1;
  }

  onFinish() {
    Promise.resolve()
      .then(() => {
        if (this.params.mode === 'CREATE') {
          return this.service.saveDescriptor(this.params.descriptor);
        } else {
          return Promise.resolve(this.params.descriptors[0]);
        }
      })
      .then(descriptor => {
        if (descriptor) {
          return this.service.deploy(descriptor.entityId, this.params.deployTime);
        }
      })
      .then(ok => {
        if (ok) {
          this.dialog.close();
        }
      });
  }

}
