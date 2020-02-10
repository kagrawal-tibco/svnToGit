import { Component, ComponentFactoryResolver, ComponentRef, Input, OnChanges, Type, ViewContainerRef } from '@angular/core';

import { NavigableWizardContext } from './navigable-wizard-context';
import { NavigableWizardPageComponent } from './navigable-wizard-page.component';

@Component({
  template: `<div></div>`,
  selector: 'wizard-page-loader'
})
export class NavigableWizardLoaderComponent implements OnChanges {
  @Input()
  page: Type<NavigableWizardPageComponent<NavigableWizardContext>>;

  @Input()
  params: NavigableWizardContext;
  private curr: ComponentRef<NavigableWizardPageComponent<NavigableWizardContext>>;

  constructor(
    private vcr: ViewContainerRef,
    private cfr: ComponentFactoryResolver
  ) {

  }

  ngOnChanges() {
    if (this.page) {
      if (this.curr) {
        this.curr.destroy();
        this.curr = null;
      }
      const factory = this.cfr.resolveComponentFactory(this.page);
      this.curr = this.vcr.createComponent(factory);
      this.curr.instance.params = this.params;
      this.curr.changeDetectorRef.markForCheck();
    }
  }
}
