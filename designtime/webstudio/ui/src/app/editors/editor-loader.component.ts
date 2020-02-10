import {
  Component, ComponentFactoryResolver, ComponentRef, EventEmitter, Input, OnChanges, OnDestroy, Output, Type, ViewContainerRef,
} from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { EditorParams } from './editor-params';
import { EditorComponent } from './editor.component';

import { Logger } from '../core/logger.service';
import { ProviderService } from '../core/provider.service';

/**
 * A loader component to dynamically load the specific editor implementation.
 */
@Component({
  selector: 'editor-loader',
  template: `<div></div>`,
})
export class EditorLoaderComponent implements OnDestroy, OnChanges {

  @Input()
  params: EditorParams<any>;

  @Output()
  editorLoader = new EventEmitter<EditorLoaderComponent>();
  private cmp: ComponentRef<EditorComponent<any>> = null;

  constructor(
    private log: Logger,
    private providers: ProviderService,
    private cfr: ComponentFactoryResolver,
    private vcr: ViewContainerRef,
    public i18n: I18n
  ) {
  }

  ngOnChanges() {
    if (this.params) {
      // Commenting this code for synchronization (latest, working) functinality issue.
      // if (!this.cmp || this.cmp.instance.params !== this.params) {
      const factory = this.cfr.resolveComponentFactory(this.params.editorInterface.type);
      this.ngOnDestroy();
      try {
        this.cmp = this.vcr.createComponent(factory, null, this.vcr.injector);
        this.cmp.instance.params = this.params;
        this.cmp.changeDetectorRef.markForCheck();
      } catch (e) {
        this.log.err(this.i18n('Unable to load the editor interface. Reason: '), e);
      }
      // } else if (this.cmp.instance.params !== this.params) {
      // this.cmp.instance.params = this.params;
      // }
      this.editorLoader.emit(this);
    }
  }

  ngOnDestroy() {
    if (this.cmp) {
      this.cmp.destroy();
    }
  }

  isLoaded() {
    return this.cmp != null;
  }

  getEditor(): EditorComponent<any> {
    return this.cmp ? this.cmp.instance : null;
  }
}
