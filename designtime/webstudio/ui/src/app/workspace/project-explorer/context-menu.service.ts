import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Logger } from '../../core/logger.service';

declare var jQuery: any;

export interface Context {
  data: any;
  event: any;
  manager?: any;
}

export interface ItemMap {
  [act: string]: {
    name: string,
    accesskey: string,
    callback?: (key, opt) => void,
    disabled?: boolean,
    icon?: string,
    className?: string,
    items?: ItemMap,
  };
}

export type MenuBuilder = (ctx: Context) => ItemMap;

@Injectable()
export class ContextMenuService {
  context: Context;
  menuBuilder: MenuBuilder;
  selector: string;
  content: string;
  constructor(
    private log: Logger,
    public i18n: I18n
  ) { }

  initWithComponent(explorer: any, builder: MenuBuilder, selector?: string) {
    if (!selector) {
      selector = '#context-menu'; // the default selector
    }

    this.setMenuBuilder(builder)
      .setSelector(selector)
      .init();
  }

  setMenuBuilder(builder: MenuBuilder): ContextMenuService {
    this.menuBuilder = builder;
    return this;
  }

  setSelector(slt: string): ContextMenuService {
    this.selector = slt;
    return this;
  }

  init() {
    // need to destroy if there already exists,
    // otherwise the builder and context wound be updated
    jQuery.contextMenu('destroy', this.selector);
    jQuery.contextMenu({
      selector: this.selector,
      trigger: 'right',
      animation: { duration: 250, show: 'fadeIn', hide: 'fadeOut' },
      build: (ele, event) => ({
        callback: (key, opt) => {
          this.log.warn(this.i18n('Unhandled ContextMenu Action:'), key, opt);
        },
        items: this.menuBuilder(this.context)
      })
    });
  }

  showMenu(ctx: Context) {
    this.setContext(ctx);
    ctx.event.data = { 'trigger': 'demand' };
    ctx.event.originalEvent = false;
    jQuery(this.selector).contextMenu({
      x: ctx.event.clientX,
      y: ctx.event.clientY
    });
  }

  private setContext(context: Context): ContextMenuService {
    this.context = context;
    return this;
  }
}
