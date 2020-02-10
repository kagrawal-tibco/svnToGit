/**
 * @Author: Rahil Khera
 */

import {
  Component,
  ComponentFactory,
  ComponentFactoryResolver,
  ComponentRef,
  Input,
  OnInit,
  ViewContainerRef
} from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ActionsImpl } from './ActionsImpl';
import { BuilderSubClauseImpl } from './BuilderSubClauseImpl';
import { CommandImpl } from './CommandImpl';
import { MultiFilterClient } from './MultiFilterClient';
import { MultiFilterImpl } from './MultiFilterImpl';
import { SymbolInfo } from './SymbolInfo';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder.component';

import { RuleTemplateInstanceImpl } from '../../editables/rule-template-instance/rule-template-instance';
import { EditorParams } from '../editor-params';

@Component({
  selector: 'rtiThen',
  templateUrl: 'RTIBuilderThenSection.html',
  styleUrls: ['rtiBuilder.css']
})

export class ActionClient implements OnInit {

  @Input()
  symbols: Array<SymbolInfo>;

  @Input()
  actionsImpl: ActionsImpl = new ActionsImpl();

  @Input()
  params: EditorParams<RuleTemplateInstanceImpl>;

  @Input()
  parentRTIEditor: RuleTemplateInstanceBuilder;
  _selectedCommand: CommandImpl = new CommandImpl();

  private MULTI_FILTER_FACTORY: ComponentFactory<MultiFilterClient>;
  private multiFilterMap: Map<string, ComponentRef<MultiFilterClient>>;

  constructor(private _viewContainerRef: ViewContainerRef,
    private _componentFactoryResolver: ComponentFactoryResolver,
    public i18n: I18n
  ) {
    this.MULTI_FILTER_FACTORY = this._componentFactoryResolver.resolveComponentFactory(MultiFilterClient);
    this.multiFilterMap = new Map<string, ComponentRef<MultiFilterClient>>();
  }

  ngOnInit() {
    if (this.actionsImpl == null) {
      this.actionsImpl = new ActionsImpl();
    } else {
      for (let i = 0; i < this.actionsImpl.getActions().length; i++) {

        const actions = this.actionsImpl.getActions()[i];
        if (actions.getFilter()
          && actions.getFilter().getFilters()
          && actions.getFilter().getFilters().length > 0) {
          this._selectedCommand = actions;
          this.onSelection(null, i);
        }
      }
    }
  }

  public onSelection(event?: any, selectedIndex?: number): void {
    let index = 0;
    if (event) {
      index = event.target.selectedIndex - 1;
    }
    if (selectedIndex) {
      index = selectedIndex;
    }
    if (index < 0) {
      return;
    }
    const selectedAlias = this.actionsImpl.getActions()[index].getAlias();
    if (!this.multiFilterMap.has(selectedAlias)) {
      let multiFilter: ComponentRef<MultiFilterClient>;
      this._selectedCommand = this.actionsImpl.getActions()[index];
      multiFilter = this._viewContainerRef.createComponent(this.MULTI_FILTER_FACTORY);
      multiFilter.instance.parentRTIEditor = this.parentRTIEditor;
      multiFilter.instance.allowNestedClause = false;
      multiFilter.instance.showMatchType = false;
      multiFilter.instance.allowRemoveClause = false;
      multiFilter.instance.symbols = this.symbols;
      multiFilter.instance.aliasConceptName = this._selectedCommand.getAlias();
      multiFilter.instance.actionConceptType = this._selectedCommand.getType();
      multiFilter.instance.actionType = this._selectedCommand.getActionType();
      const aliasType = this._selectedCommand.getType().split('.')[1];
      if (aliasType && this._selectedCommand.getSymbols()) {
        // multiFilter.instance.symbols.push(this._selectedCommand.getSymbols());
        multiFilter.instance.commandInfoSymbols = this._selectedCommand.getSymbols();
      }
      multiFilter.instance.allowAddFilter = multiFilter.instance.getContainedSymbols().length > 0;
      multiFilter.instance.readOnly = this.parentRTIEditor.readOnly;
      multiFilter.instance.multiFilter = new MultiFilterImpl();
      multiFilter.instance.params = this.params;
      multiFilter.instance.showDiff = this.params.editorMode === 'display' && this.params.showChangeSet === 'rhs';
      const subClauseFilter = this.actionsImpl.getActions()[index].getFilter();
      if (subClauseFilter) {
        multiFilter.instance.multiFilter.setBuilderSubClause(subClauseFilter);
      } else {
        multiFilter.instance.multiFilter.setBuilderSubClause(new BuilderSubClauseImpl());
        const buffer = this.params.editBuffer.getBuffer();
        if (buffer) {
          buffer.getCommands()
            .getActions()[index]
            .setFilter(multiFilter.instance.multiFilter.getBuilderSubClause());
        } else {
          this.params.editBuffer.getBase()
            .getCommands()
            .getActions()[index]
            .setFilter(multiFilter.instance.multiFilter.getBuilderSubClause());
        }

      }
      multiFilter.instance.isMultiFilter = false;
      multiFilter.instance.generateActionFilter(multiFilter.instance.multiFilter);
      this.multiFilterMap.set(selectedAlias, multiFilter);
    }
  }
}
