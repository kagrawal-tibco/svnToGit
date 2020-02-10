import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Logger } from '../../core/logger.service';
import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { DeploymentCreationContext } from '../deployment-creation.context';

@Component({
  templateUrl: './deploy-config.component.html'
})
export class DeployConfigComponent extends NavigableWizardPageComponent<DeploymentCreationContext> implements OnInit {
  pastTime: boolean;

  constructor(
    private log: Logger,
    public i18n: I18n
  ) {
    super();
  }

  ngOnInit() {
    this.enableFinish(!this.futureDeploy);
  }

  toggleCalendar() {
    this.params.showCalendar = !this.params.showCalendar;
  }

  get showCalendar() {
    return this.params.showCalendar;
  }

  get futureDeploy(): boolean {
    return this.params.futureDeploy;
  }

  set futureDeploy(val: boolean) {
    if (val) {
      this.params.deployTime = new Date(new Date().getTime() + 24 * 60 * 60 * 1000);
      this.params.deployTime.setHours(0);
      this.params.deployTime.setMinutes(0);
      this.params.deployTime.setSeconds(0);
      this.params.deployTime.setMilliseconds(0);
      this.params.showCalendar = true;
      this.params.futureDeploy = true;
    } else {
      this.params.futureDeploy = false;
      this.params.deployTime = null;
      this.enableFinish(true);
    }
  }

  get deployTime() {
    return this.params.deployTime;
  }

  set deployTime(dt: Date) {
    if (dt) {
      this.params.deployTime = dt;
      this.pastTime = dt.getTime() <= new Date().getTime();
      this.enableFinish(!this.pastTime);
    } else {
      this.pastTime = false;
      this.params.deployTime = null;
      this.enableFinish(false);
    }
  }
}
