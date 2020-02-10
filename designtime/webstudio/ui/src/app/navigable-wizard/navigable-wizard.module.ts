import { CommonModule } from '@angular/common';
import { ANALYZE_FOR_ENTRY_COMPONENTS, ModuleWithProviders, NgModule, Type } from '@angular/core';

import { NavigableWizardContext } from './navigable-wizard-context';
import { NavigableWizardLoaderComponent } from './navigable-wizard-loader.component';
import { NavigableWizardPageComponent } from './navigable-wizard-page.component';
import { NavigableWizardComponent } from './navigable-wizard.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [NavigableWizardComponent, NavigableWizardLoaderComponent],
  exports: [NavigableWizardComponent]
})
export class NavigableWizardModule {
  static withComponent(entryComponents: Type<NavigableWizardPageComponent<NavigableWizardContext>>[]): ModuleWithProviders {
    return {
      ngModule: NavigableWizardModule,
      providers: [
        { provide: ANALYZE_FOR_ENTRY_COMPONENTS, useValue: entryComponents, multi: true }
      ]
    };
  }
}
