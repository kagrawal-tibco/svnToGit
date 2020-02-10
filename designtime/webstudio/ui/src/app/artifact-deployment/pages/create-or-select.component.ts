import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { DeploymentDescriptorItem } from '../../models/dto';
import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { DeploymentCreationContext } from '../deployment-creation.context';
import { DeploymentService } from '../deployment.service';

@Component({
  templateUrl: './create-or-select.component.html'
})
export class CreateOrSelectComponent extends NavigableWizardPageComponent<DeploymentCreationContext> implements OnInit {
  showDescriptor: Promise<boolean>;

  constructor(
    private service: DeploymentService,
    public i18n: I18n
  ) {
    super();
  }

  ngOnInit() {
    this.showDescriptor = Promise.resolve(true);
    if (!this.params.descriptor) {
      if (this.params.descriptors.length > 0) {
        this.onSelect(this.params.descriptors[0]);
      } else {
        this.onCreate();
      }
    }
  }

  showCopy() {
    return this.params.mode === 'SELECT';
  }

  onCopy() {
    this.onCreate(this.params.descriptor);
  }

  showClear() {
    return this.params.mode === 'CREATE';
  }

  onClear() {
    this.onCreate();
  }

  isActive(descriptor: DeploymentDescriptorItem) {
    return this.params.descriptor === descriptor;
  }

  get title() {
    if (this.params.mode === 'CREATE') {
      return this.i18n('Create new deployment descriptor for [{{name}}]', { name: this.params.artifact.name });
    } else if (this.params.descriptor) {
      return `Deploy [${this.getEntityBrief(this.params.descriptor)}] to [${this.getTargetBrief(this.params.descriptor)}]`;
    } else {
      return `Deploy [${this.params.artifact.name}]`;
    }
  }

  get selectSectionMsg() {
    if (this.params.descriptors && this.params.descriptors.length > 0) {
      return 'Available Descriptors';
    } else {
      return 'No Descriptors Available';
    }
  }

  onValidChange(valid: boolean) {
    this.enableNextStep(valid);
  }

  onSelect(selected: DeploymentDescriptorItem) {
    if (this.params.descriptor !== selected) {
      this.params.descriptor = selected;
      this.params.mode = 'SELECT';
      this.showDescriptor = Promise.resolve(true);
    }
  }

  onCreate(copy?: DeploymentDescriptorItem) {
    this.params.mode = 'CREATE';
    if (copy) {
      this.params.descriptor = _.cloneDeep(copy);
    } else {
      this.params.descriptor = this.service.createNewDescriptor(this.params.projectName, this.params.artifact);
    }
    this.showDescriptor = Promise.resolve(true);
  }

  getEntityBrief(descriptor: DeploymentDescriptorItem) {
    return this.service.getEntityBrief(this.params.artifact, descriptor);
  }

  getTargetBrief(descriptor: DeploymentDescriptorItem) {
    return this.service.getTargetBrief(this.params.artifact, descriptor);
  }

}
