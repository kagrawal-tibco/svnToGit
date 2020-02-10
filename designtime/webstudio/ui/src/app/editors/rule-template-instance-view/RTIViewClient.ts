/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:08:23+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-05-04T15:30:41+05:30
 */

import {
  AfterViewInit,
  Component,
  ComponentFactory,
  ComponentFactoryResolver,
  ComponentRef,
  Injector,
  OnInit,
  ViewContainerRef
} from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { BindingInfo } from './BindingInfo';
import { DomainInfo } from './DomainInfo';
import { ViewInfo } from './ViewInfo';
import { RuleTemplateInstanceViewEditorService } from './rule-template-instance-view.service';

import { ArtifactPropertiesService } from '../../artifact-editor/artifact-properties.service';
import { ModalService } from '../../core/modal.service';
import { SettingsService } from '../../core/settings.service';
import { DiffHelper } from '../../editables/rule-template-instance/differ/DiffHelper';
import { MergedDiffHelper } from '../../editables/rule-template-instance/differ/MergeDiffHelper';
import { MergedDiffModificationEntry } from '../../editables/rule-template-instance/differ/MergedDiffModificationEntry';
import { ModificationEntry } from '../../editables/rule-template-instance/differ/ModificationEntry';
import { RTIConfilctResolverModal, RTIConflictResolverContext } from '../../editables/rule-template-instance/differ/rti-conflict-resolver.modal';
import { RuleTemplateInstance, RuleTemplateInstanceImpl } from '../../editables/rule-template-instance/rule-template-instance';
import { BESettings, RTIStringWidget } from '../../models-be/settings-be';
import { DateTimePickerComponent } from '../../shared/be-date-time-picker/DateTimePickerComponent';
import { EditorParams } from '../editor-params';
import { EditorComponent } from '../editor.component';

@Component({
  selector: 'rtiView',
  templateUrl: './RTIView.html'
})

export class RTIViewClient extends EditorComponent<RuleTemplateInstanceImpl> implements OnInit, AfterViewInit {

  get rtiViewInfo(): ViewInfo {
    return this._rtiViewInfo;
  }

  set rtiViewInfo(rtiViewInfo: ViewInfo) {
    this._rtiViewInfo = rtiViewInfo;
  }

  get editorService(): RuleTemplateInstanceViewEditorService {
    return this._editorService;
  }

  get showProperties(): boolean {
    if (this.params.editorMode === 'display') {
      return true;
    } else {
      return false;
    }
  }

  get initializationObject() {
    if (this.params.editBuffer.getBuffer()) {
      return this.params.editBuffer.getBuffer();
    } else if (this.params.editBuffer.getBase()) {
      return this.params.editBuffer.getBase();
    }
  }

  base: RuleTemplateInstanceImpl;
  params: EditorParams<RuleTemplateInstanceImpl>;
  public rtiName = '';
  public disable = false;
  public stringWidget: string;
  private _rtiViewInfo: ViewInfo;
  private DATE_TIME_PICKER_FACTORY: ComponentFactory<DateTimePickerComponent>;
  private oldViewInfo: ViewInfo;
  private showDiff = false;
  private modifications: Map<BindingInfo, ModificationEntry>;
  private modal: ModalService;
  private rtName = '';
  /**
   * This member variable will store bindingId and the corresponding HTMLElement.
   * In case of problem entry, location will be containing bindingId, and hence,
   * HTMLElement from this map can be used to focus the element on UI.
   */
  private problemBindingMap: Map<string, HTMLElement>;

  constructor(private _viewContainerRef: ViewContainerRef,
    private _componentFactoryResolver: ComponentFactoryResolver,
    private _editorService: RuleTemplateInstanceViewEditorService,
    private injector: Injector,
    private propertiesService: ArtifactPropertiesService,
    private settings: SettingsService,
    public i18n: I18n) {
    super();
    this.DATE_TIME_PICKER_FACTORY = this._componentFactoryResolver.resolveComponentFactory(DateTimePickerComponent);
    this.modal = injector.get(ModalService);
    this.problemBindingMap = new Map<string, HTMLElement>();
  }

  static desanitizeHTML(html: string): string {
    let ret = html.replace(/&amp;/g, '&');
    ret = ret.replace(/&lt;/g, '<');
    ret = ret.replace(/&quot;/g, '"');
    ret = ret.replace(/&apos;/g, '\'');
    ret = ret.replace(/&gt;/g, '>');
    if (ret.indexOf('&amp;') > 0 || ret.indexOf('&lt;') > 0 || ret.indexOf('&quot;') > 0 || ret.indexOf('&apos;') > 0 || ret.indexOf('&gt;') > 0) {
      ret = RTIViewClient.desanitizeHTML(ret);
    }
    return ret;
  }

  /**
 	 * Removes the script element with the given id, if present.
 	 */
  public static removeExistingScriptElement(rtiName: string): void {
    const scriptElement: HTMLScriptElement = <HTMLScriptElement>document.getElementById(rtiName + '_script');
    const head: HTMLHeadElement = document.head || document.getElementsByTagName('head')[0];
    if (scriptElement && head.contains(scriptElement)) {
      head.removeChild(scriptElement);
    }
  }

  ngOnInit() {
    this.stringWidget = (<BESettings>this.settings.latestSettings).rtiViewStringWidget;
    if (this.params.editorName) {
      this.rtiName = this.params.editorName;
      this.propertiesService.setRTIViewProperties(this);
    }
    if (this.params.editBuffer.getBuffer()) {
      this.base = this.params.editBuffer.getBuffer();
    } else {
      this.base = this.params.editBuffer.getBase();
    }

    const implementsPath = this.base.getImplementsPath().split('\/');
    this.rtName = implementsPath[implementsPath.length - 1];
    this.rtiViewInfo = this.base.getView();

    let script = this.base.getView().getScript();
    if (script) {
      const regExp = new RegExp('%RTIName%', 'g');
      script = script.replace(regExp, this.rtiName);
      this.base.getView().setScript(script);
    }

    this.showDiff = (this.params.editorMode === 'display'
      && this.params.showChangeSet === 'rhs');
    if (this.showDiff) {
      this.oldViewInfo = RuleTemplateInstance.deserialize(this.params.base).getView();
      this.modifications = new Map<BindingInfo, ModificationEntry>();
      DiffHelper.processRTIViewDiff(this.oldViewInfo.getBindingInfo(),
        this._rtiViewInfo.getBindingInfo(),
        this.modifications);
      this.rtiName = 'diff';
    }
    if (this.params.editorMode === 'sync') {
      this.rtiName = 'sync';
      this.params.editBuffer.getBuffer().setisSyncMerge('true');
      this.params.editBuffer.getBase().setisSyncMerge('true');
    }
    if (this.params.showChangeSet === 'none' && (this.params.editorName == null || this.params.editorName === '')) {
      this.rtiName = 'none';
    }

    this.disable = this.params.editorMode !== 'edit';
  }

  ngAfterViewInit() {
    /*
    * Async call to avoid ExpressionChangedAfterIthasbeenCheckedError
    */
    Promise.resolve(true).then(() => {
      this.setupBindingWidgets().then((response: boolean) => {
        if (!(this.showDiff || this.params.editorMode === 'sync')) {
          RTIViewClient.removeExistingScriptElement(this.rtiName);
          this.createAndInjectScriptElement();
        }
      });
    });
  }

  setupBindingWidgets(): Promise<boolean> {
    const rtiElement = document.getElementById(this.rtiName);
    if (rtiElement) {
      rtiElement.innerHTML = RTIViewClient.desanitizeHTML(this.rtiViewInfo.getHtmlText());
      const bindings: Array<BindingInfo> = this.rtiViewInfo.getBindingInfo();
      // console.log("Replacing bindings with widgets for " + bindings.length + "entries");
      const view = document.getElementById(this.rtiName);
      for (let i = 0; i < bindings.length; i++) {
        const bindingElement = document.getElementById(bindings[i].getBindingId());
        if (bindingElement != null) {
          const widgetTypeAttr: Attr = bindingElement.attributes.getNamedItem('widgetType');
          const widgetType: RTIStringWidget = this.getWidgetType(widgetTypeAttr ? widgetTypeAttr.value : undefined);
          let widget: HTMLElement;
          if (widgetType) {
            widget = this.createBindingWidget(bindings[i], widgetType.value);
          } else {
            widget = this.createBindingWidget(bindings[i], this.stringWidget);
          }

          this.problemBindingMap.set(bindings[i].getBindingId(), widget);
          if (widget instanceof HTMLElement) {
            try {
              const parentElement = bindingElement.parentElement;
              parentElement.replaceChild(widget, bindingElement);
            } catch (error) {
              console.log(error);
            }
          } else {
            view.removeChild(bindingElement);
          }
        }
      }
    }
    return Promise.resolve(true);
  }

  createTextBox(bindingInfo: BindingInfo, isDate?: boolean): HTMLInputElement {
    const textBox = document.createElement('input');
    const value: string = bindingInfo.getValue();
    const id: string = bindingInfo.getBindingId();
    const style: string = bindingInfo.getStyle();
    textBox.id = this.rtiName + '_' + id;
    if (isDate) {
      textBox.value = value != null ? DateTimePickerComponent.convertStringToDate(value).toString() : ' ';
    } else {
      textBox.value = value != null ? value : '0';
    }

    textBox.className = this.rtName + '_' + id;
    textBox.readOnly = this.showDiff;
    textBox.setAttribute('style', style);
    this.modifyBindingInfo(textBox, bindingInfo, value);
    // console.log('Created Text Box Widget');
    return textBox;
  }

  createTextArea(bindingInfo: BindingInfo): HTMLTextAreaElement {
    const textArea = document.createElement('textarea');
    const value: string = bindingInfo.getValue();
    const id: string = bindingInfo.getBindingId();
    const style: string = bindingInfo.getStyle();
    textArea.id = this.rtiName + '_' + id;
    value != null ? textArea.value = value : textArea.value = '';
    textArea.rows = 5;
    textArea.cols = 20;
    textArea.className = this.rtName + '_' + id;
    textArea.readOnly = this.showDiff;
    textArea.setAttribute('style', style);
    this.modifyBindingInfo(textArea, bindingInfo, value);
    // console.log('Created Text Area Widget');
    return textArea;
  }

  createCheckBox(bindingInfo: BindingInfo): HTMLDivElement {
    const parentDiv = document.createElement('div');
    const checkBox = document.createElement('input');
    checkBox.setAttribute('type', 'checkbox');
    const value: string = bindingInfo.getValue();
    const id: string = bindingInfo.getBindingId();
    const style: string = bindingInfo.getStyle();
    checkBox.id = this.rtiName + '_' + id;
    checkBox.checked = value === 'true' ? true : false;
    checkBox.className = this.rtName + '_' + id;
    checkBox.disabled = this.showDiff || this.disable;
    checkBox.setAttribute('style', style);
    parentDiv.appendChild(checkBox);
    parentDiv.setAttribute('class', 'parentDiv');
    this.modifyBindingInfo(parentDiv, bindingInfo, value);
    // console.log('Created Check Box Widget');
    return parentDiv;
  }

  createDropDown(bindingInfo: BindingInfo): HTMLSelectElement {
    const selectBox = document.createElement('select');
    const mapOfOptions = bindingInfo.getDomainInfo().getValues();
    const value: string = bindingInfo.getValue();
    const id: string = bindingInfo.getBindingId();
    const style: string = bindingInfo.getStyle();
    const defaultOption = document.createElement('option');
    const defaultOptionText = document.createTextNode('Select');
    defaultOption.appendChild(defaultOptionText);
    defaultOption.value = '';
    selectBox.add(defaultOption);
    for (let i = 0; i < mapOfOptions.length; i++) {
      const option = document.createElement('option');
      const optionText = document.createTextNode(mapOfOptions[i].getValue());
      option.appendChild(optionText);
      option.value = mapOfOptions[i].getValue();
      selectBox.add(option);
    }
    selectBox.id = this.rtiName + '_' + id;
    if (value != null) {
      selectBox.value = value;
    }
    selectBox.className = this.rtName + '_' + id;
    selectBox.disabled = this.showDiff;
    selectBox.setAttribute('style', style);
    this.modifyBindingInfo(selectBox, bindingInfo, value);
    // console.log('Drop down box created');
    return selectBox;
  }

  createDateTimePicker(bindingInfo: BindingInfo): HTMLElement {
    let dateTimePickerComponent: ComponentRef<DateTimePickerComponent>;
    let dateTimePickerHtmlElement: HTMLElement;
    const id: string = bindingInfo.getBindingId();
    const style: string = bindingInfo.getStyle();
    try {
      dateTimePickerComponent = this._viewContainerRef.createComponent(this.DATE_TIME_PICKER_FACTORY);
      dateTimePickerHtmlElement = dateTimePickerComponent.location.nativeElement;
      dateTimePickerHtmlElement.id = this.rtiName + '_' + id;
      dateTimePickerHtmlElement.className = this.rtName + '_' + id;
      dateTimePickerHtmlElement.setAttribute('style', style);
      dateTimePickerComponent.instance.currentDate = bindingInfo.getValue();
      dateTimePickerComponent.instance.disable = this.disable;
      dateTimePickerComponent.instance.dateChange.subscribe((value: string) => {
        // console.log("Selected Date" + value);
        const bindingId = bindingInfo.getBindingId();
        const index: number = this.getBinding(bindingId);
        const oldValue = this.params.editBuffer.getBuffer().getView().getBindingInfo()[index].getValue();
        const newValue = value;
        const action = this._editorService.propertyEditAction(bindingId, oldValue, newValue, this.rtiName + '_' + bindingId);
        this.execute(action);
      });
      // if (this.showDiff) {
      //   let index: number = this.getBinding(bindingInfo.getBindingId());
      //   let oldValue: string = this.oldViewInfo.getBindingInfo()[index].getValue();
      //   let value: string = bindingInfo.getValue();
      //   if (oldValue != value) {
      //     dateTimePickerHtmlElement.title = 'was ' + oldValue;
      //     dateTimePickerHtmlElement.setAttribute('style', 'background-color: yellow');
      //   }
      // }
      this.modifyBindingInfo(dateTimePickerHtmlElement, bindingInfo, bindingInfo.getValue());
    } catch (error) {
      // console.log(error);
    }
    return dateTimePickerHtmlElement;
  }

  getBinding(id: string): number {
    const bindings: Array<BindingInfo> = this.base.getView().getBindingInfo();
    let i = 0;
    for (const binding of bindings) {
      if (binding.getBindingId() === id) {
        return i;
      }
      i++;
    }
    return -1;
  }

  modifyBindingInfo(htmlElement: HTMLElement, bindingInfo: BindingInfo, value: string): void {
    if (this.showDiff && this.modifications.has(bindingInfo)) {
      htmlElement.title = 'was ' + this.modifications.get(bindingInfo).getPreviousValue();
      const currentClass = htmlElement.getAttribute('class');
      if (currentClass) {
        htmlElement.setAttribute('class', currentClass + ' ws-diff-background-modify');
      } else {
        htmlElement.setAttribute('class', 'ws-diff-background-modify');
      }
    } else if (this.params.editorMode === 'sync') {
      let modificationList: Map<BindingInfo, MergedDiffModificationEntry> = new Map<BindingInfo, MergedDiffModificationEntry>();
      let modificationEntry;
      switch (this.params.showChangeSet) {
        case 'lhs':
          modificationList = MergedDiffHelper.getLhsDiff(this.params.mergeResultRTIView);
          break;
        case 'rhs':
          modificationList = MergedDiffHelper.getRhsDiff(this.params.mergeResultRTIView);
          break;
        case 'both':
          modificationList = this.params.mergeResultRTIView;
          break;
        case 'none':
          modificationList = MergedDiffHelper.getConflictModifications(this.params.mergeResultRTIView);
          break;
      }
      if (modificationList.has(bindingInfo)) {
        modificationEntry = modificationList.get(bindingInfo);
        if (MergedDiffHelper.isBindingConflict(modificationEntry)) {
          htmlElement.title = 'Previous Value: ' + modificationEntry.baseVersion +
            '\n Working Copy Value:' + modificationEntry.localVersion;
          const currentClass = htmlElement.getAttribute('class');
          if (currentClass) {
            htmlElement.setAttribute('class', currentClass + ' ws-diff-background-conflict');
          } else {
            htmlElement.setAttribute('class', 'ws-diff-background-conflict');
          }
          htmlElement.setAttribute('class', 'ws-diff-background-conflict');
          htmlElement.ondblclick = (event) => {
            const modificationList: Map<BindingInfo, MergedDiffModificationEntry> = this.params.mergeResultRTIView;
            let modificationEntry;
            if (modificationList.has(bindingInfo)) {
              modificationEntry = modificationList.get(bindingInfo);
              if (!modificationEntry.isApplied()) {
                this.modal
                  .open(RTIConfilctResolverModal, new RTIConflictResolverContext(bindingInfo,
                    this.params.editBuffer, modificationEntry, 'latest', 'working', true))
                  .then((resolution: string) => {
                    modificationEntry.setApplied(true);
                    if (resolution === 'rhs') {
                      bindingInfo.setValue(modificationEntry.localVersion);
                      (<HTMLInputElement>htmlElement).value = modificationEntry.localVersion;
                      htmlElement.setAttribute('class', 'ws-diff-background-modify');
                    } else { // Change class here, to apply different color code for representing server change.
                      htmlElement.setAttribute('class', 'ws-diff-background-modify');
                    }
                    const currentClass = htmlElement.getAttribute('class');
                    if (currentClass) {
                      htmlElement.setAttribute('class', currentClass + ' ws-diff-background-modify');
                    } else {
                      htmlElement.setAttribute('class', 'ws-diff-background-modify');
                    }
                    htmlElement.title = '';
                    htmlElement.title = 'Previous Value: ' + modificationEntry.baseVersion;
                  }, () => { });
              }
            }
          };
        } else if (modificationEntry.isServerChange()) {
          // Change class here, to apply different color code for representing server change.
          // htmlElement.setAttribute('class', 'ws-diff-background-modify');
          const currentClass = htmlElement.getAttribute('class');
          if (currentClass) {
            htmlElement.setAttribute('class', currentClass + ' ws-diff-background-modify');
          } else {
            htmlElement.setAttribute('class', 'ws-diff-background-modify');
          }
          htmlElement.title = 'Previous Value: ' + modificationEntry.baseVersion;
        } else if (modificationEntry.isLocalChange()) {
          htmlElement.setAttribute('class', 'ws-diff-background-modify');
          htmlElement.title = 'Previous Value: ' + modificationEntry.baseVersion;
          bindingInfo.setValue(modificationEntry.localVersion);
          (<HTMLInputElement>htmlElement).value = modificationEntry.localVersion;
        }
      }
    } else {
      // htmlElement.setAttribute('readOnly', this.disable? 'true' : 'false');
      htmlElement.onchange = (event) => {
        const bindingId = bindingInfo.getBindingId();
        const index: number = this.getBinding(bindingId);
        const oldValue = this.params.editBuffer.getBuffer().getView().getBindingInfo()[index].getValue();
        let newValue: string;
        if (bindingInfo.getType() === 'boolean' && bindingInfo.getDomainInfo() == null) {
          newValue = (<HTMLInputElement>event.target).checked ? 'true' : 'false';
        } else {
          newValue = (<HTMLInputElement>event.target).value;
        }
        const action = this._editorService.propertyEditAction(bindingId, oldValue, newValue, this.rtiName + '_' + bindingId);
        this.execute(action);
      };
    }
    if (this.disable) {
      htmlElement.setAttribute('readOnly', 'true');
    }
  }

  public getShowDiff(): boolean {
    return this.showDiff;
  }

  focusOnLocations(locations: any[]): void {
    let bindingId: string = locations[0].colId;
    if (bindingId.indexOf(',') > -1) {
      bindingId = bindingId.split(',')[0];
    }
    bindingId = bindingId.trim();
    const htmlElement: HTMLElement = this.problemBindingMap.get(bindingId);
    if (htmlElement) {
      htmlElement.focus();
    }
  }

  private createBindingWidget(bindingInfo: BindingInfo, widgetType: string): HTMLElement {
    const domainInfo: DomainInfo = bindingInfo.getDomainInfo();
    if (domainInfo != null) {
      if (!this.disable) {
        return this.createDropDown(bindingInfo);
      } else {
        return this.createTextBox(bindingInfo);
      }
    }
    const type: string = bindingInfo.getType();
    if (type === 'int' || type === 'long' || type === 'double') {
      return this.createTextBox(bindingInfo);
    } else if (type === 'String') {
      if (widgetType === RTIStringWidget.TEXTAREA.value) {
        return this.createTextArea(bindingInfo);
      }
      return this.createTextBox(bindingInfo);
    } else if (type === 'boolean') {
      return this.createCheckBox(bindingInfo);
    } else if (type === 'DateTime') {
      if (!this.disable) {
        return this.createDateTimePicker(bindingInfo);
      } else {
        return this.createTextBox(bindingInfo, true);
      }

    }
  }

  /**
   * Creates new script element with given id and script. This script
   * element is appended to the head element.
   */
  private createAndInjectScriptElement(): void {
    const head: HTMLHeadElement = document.head || document.getElementsByTagName('head')[0];
    const scriptElement: HTMLScriptElement = document.createElement('script');
    scriptElement.id = this.rtiName + '_script';
    scriptElement.setAttribute('type', 'text/javascript');
    scriptElement.innerHTML = this.base.getView().getScript();
    head.appendChild(scriptElement);
  }

  private getWidgetType(widgetValue: string): RTIStringWidget {
    if (widgetValue === RTIStringWidget.TEXTAREA.value) {
      return RTIStringWidget.TEXTAREA;
    } else if (widgetValue === RTIStringWidget.TEXTBOX.value) {
      return RTIStringWidget.TEXTBOX;
    }
    return null;
  }

}
