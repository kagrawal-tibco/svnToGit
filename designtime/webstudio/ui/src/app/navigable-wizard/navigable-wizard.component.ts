import {
  Component,
  ComponentFactoryResolver,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
  Type,
  ViewContainerRef,
} from '@angular/core';

import { NavigableWizardContext } from './navigable-wizard-context';
import { NavigableWizardPageComponent } from './navigable-wizard-page.component';

import { Logger } from '../core/logger.service';
import { ProviderService } from '../core/provider.service';

@Component({
  selector: 'navigable-wizard',
  template: `
  <div class='holder' id='holder'>
    <wizard-page-loader [page]='curr' [params]='params'></wizard-page-loader>
  </div>`,
})
export class NavigableWizardComponent implements OnInit, OnChanges {
  @Input()
  pages: Type<NavigableWizardPageComponent<NavigableWizardContext>>[];

  @Input()
  params: NavigableWizardContext;

  @Input()
  position: number;

  curr: Type<NavigableWizardPageComponent<NavigableWizardContext>>;
  pos: number;

  constructor(
    private log: Logger,
    private providers: ProviderService,
    private vcr: ViewContainerRef,
    private cfr: ComponentFactoryResolver
  ) {
    this.pos = 0;
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['position']) {
      const change = changes['position'];
      this.moveTo(change.currentValue);
    }
  }

  ngOnInit() {
  }

  moveTo(pos) {
    this.pos = pos;
    const page = this.pages[pos];
    if (page) {
      this.curr = page;
    }
  }
}
