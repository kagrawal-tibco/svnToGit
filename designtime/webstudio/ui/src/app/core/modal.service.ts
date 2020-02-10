import { Injectable, Type, ViewContainerRef } from '@angular/core';

import { ModalContext } from 'ngx-modialog';
import { Modal, OneButtonPresetBuilder, TwoButtonPresetBuilder } from 'ngx-modialog/plugins/bootstrap';
import { PromptPresetBuilder } from 'ngx-modialog/plugins/bootstrap';

import { Logger } from './logger.service';
import { ProviderService } from './provider.service';

@Injectable()
export class ModalService {
  // we cannot DI this as it's circular
  private provider: ProviderService;

  constructor(
    private log: Logger,
    public modal: Modal
  ) { }

  setProvider(provider: ProviderService) {
    this.provider = provider;
  }

  open(componentType: Type<any>, context?: ModalContext, viewContainer?: ViewContainerRef, inside?: boolean): Promise<any> {
    const mod = this.modal.open(componentType, { context: context });
    const res = mod.result;
    return res;
  }

  alert(): OneButtonPresetBuilder {
    return this.modal.alert().okBtn('Close').okBtnClass('btn btn-sm btn-outline-primary');
  }

  confirm(): TwoButtonPresetBuilder {
    return this.modal.confirm()
      .okBtn('Confirm')
      .cancelBtn('Cancel')
      .okBtnClass('btn btn-sm btn-danger')
      .cancelBtnClass('btn btn-sm btn-outline-primary');
  }

  prompt(): PromptPresetBuilder {
    return this.modal.prompt();
  }
}
