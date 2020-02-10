import { Input } from '@angular/core';

import { NavigableWizardContext } from './navigable-wizard-context';

export abstract class NavigableWizardPageComponent<T extends NavigableWizardContext> {
  @Input()
  params: T;

  constructor() {

  }

  enableNextStep(canNext: boolean) {
    this.params.canNextStep = Promise.resolve(canNext);
  }

  enableFinish(canFinish: boolean) {
    this.params.canFinish = Promise.resolve(canFinish);
  }

}
