import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { environment } from '../../../environments/environment';
import { ArtifactService } from '../../core/artifact.service';
import { SettingsService } from '../../core/settings.service';
import { BESettings, ProjectViewType, RTIFilterType, RTIStringWidget } from '../../models-be/settings-be';
import { WorkspaceService } from '../../workspace/workspace.service';

export class InputErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'preference',
  templateUrl: './preference.component.html',
  styleUrls: ['./preference.component.css'],
})
export class PreferenceComponent implements OnInit {

  get autoArtifactValidation() {
    return this.service.latestSettings.autoArtifactValidation;
  }

  set autoArtifactValidation(val: boolean) {
    this.service.latestSettings.autoArtifactValidation = val;
    this.service.onSaveUISettings()
      .then(() => {
        this.workspace.refresh();
      });
  }

  get showEmptyFolders() {
    return this.service.latestSettings.showEmptyFolders;
  }

  set showEmptyFolders(val: boolean) {
    this.service.latestSettings.showEmptyFolders = val;
    this.service.onSaveUISettings()
      .then(() => {
        this.workspace.refresh();
      });
  }

  get displayWorklistNotifications() {
    return this.service.latestSettings.displayWorklistNotifications;
  }

  set displayWorklistNotifications(val: boolean) {
    this.service.latestSettings.displayWorklistNotifications = val;
    this.service.onSaveUISettings()
      .then(() => {
        // no need to refresh
      });
  }

  get onlyDisplayCheckedOutArtifacts() {
    return this.service.latestSettings.onlyDisplayCheckedOutArtifacts;
  }

  set onlyDisplayCheckedOutArtifacts(val: boolean) {
    this.service.latestSettings.onlyDisplayCheckedOutArtifacts = val;
    this.service.onSaveUISettings()
      .then(() => this.workspace.refresh());
  }

  get showDiff() {
    return this.service.latestSettings.showDiff;
  }

  set showDiff(val: boolean) {
    this.service.latestSettings.showDiff = val;
    this.service.onSaveUISettings()
      .then(() => {
        this.artifact.markAsRerender();
      });
  }

  get autoUnLockOnReview() {
    return (<BESettings>this.service.latestSettings).autoUnLockOnReview;
  }

  set autoUnLockOnReview(autoUnlock: boolean) {
    this.dirty = true;
    (<BESettings>this.service.latestSettings).autoUnLockOnReview = autoUnlock;
  }

  get groupRelatedArtifacts() {
    return (<BESettings>this.service.latestSettings).groupRelatedArtifacts;
  }

  set groupRelatedArtifacts(groupRelatedArtifacts: boolean) {
    this.dirty = true;
    (<BESettings>this.service.latestSettings).groupRelatedArtifacts = groupRelatedArtifacts;
  }

  get allowCustomDomainValues() {
    return (<BESettings>this.service.latestSettings).allowCustomDomainValues;
  }

  set allowCustomDomainValues(allowCustomDomainValues: boolean) {
    this.dirty = true;
    (<BESettings>this.service.latestSettings).allowCustomDomainValues = allowCustomDomainValues;
  }

  get showColumnAliasIfPresent() {
    return (<BESettings>this.service.latestSettings).showColumnAliasIfPresent;
  }

  set showColumnAliasIfPresent(showColumnAlias: boolean) {
    this.dirty = true;
    (<BESettings>this.service.latestSettings).showColumnAliasIfPresent = showColumnAlias;
  }

  get projectViewTypes() {
    for (const type of ProjectViewType.TYPES) {
      if (type.name.match('groups')) {
        type.name = this.i18n('Basic Tree (no groups)');
      } else if (type.name.match('Tree')) {
        type.name = this.i18n('Tree');
      } else if (type.name.match('List')) {
        type.name = this.i18n('List');
      } else if (type.name.match('BreadCrumbs')) {
        type.name = this.i18n('Breadcrumbs');
      }
    }
    return ProjectViewType.TYPES;
  }

  // No need of this preference as ag-grid support this functionality
  //  get columnAutoFitTypes() {
  //    return ColumnAutoFitType.TYPES;
  //  }

  get rtiFilterTypes() {
    for (const type of RTIFilterType.TYPES) {
      if (type.name.match('Any')) {
        type.name = this.i18n('Match Any');
      } else if (type.name.match('All')) {
        type.name = this.i18n('Match All');
      } else if (type.name.match('None')) {
        type.name = this.i18n('Match None');
      }
    }
    return RTIFilterType.TYPES;
  }

  get rtiStringWidgets() {
    for (const widget of RTIStringWidget.WIDGETS) {
      if (widget.name.match('Area')) {
        widget.name = this.i18n('TextArea');
      }
      if (widget.name.match('Box')) {
        widget.name = this.i18n('TextBox');
      }
    }
    return RTIStringWidget.WIDGETS;
  }

  get disableApply(): boolean {
    return (!((<BESettings>this.service.latestSettings).defaultRTIFilterType !== this.form.defaultRTIFilterType
      || (<BESettings>this.service.latestSettings).rtiViewStringWidget !== this.form.rtiViewStringWidget
      || (<BESettings>this.service.latestSettings).decisionTablePageSize !== this.form.decisionTablePageSize
      || (<BESettings>this.service.latestSettings).itemView !== this.form.itemView
      || (<BESettings>this.service.latestSettings).scsUserPassword !== this.form.scsUserPassword
      || (<BESettings>this.service.latestSettings).scsUserName !== this.form.scsUserName
      || (<BESettings>this.service.latestSettings).recentlyOpenedArtifactLimit !== this.form.recentlyOpenedArtifactLimit
      || (<BESettings>this.service.latestSettings).autoUnLockOnReview !== this.form.autoUnLockOnReview
      || (<BESettings>this.service.latestSettings).groupRelatedArtifacts !== this.form.groupRelatedArtifacts
      || (<BESettings>this.service.latestSettings).allowCustomDomainValues !== this.form.allowCustomDomainValues
      || (<BESettings>this.service.latestSettings).showColumnAliasIfPresent !== this.form.showColumnAliasIfPresent
    )) || (this.recentlyOpenedItemsFormControl.errors !== null || this.descisionTablePageSizeFormControl.errors !== null);

  }
  public recentlyOpenedItemsFormControl = new FormControl('', [Validators.required, Validators.min(1), Validators.max(25)]);
  public descisionTablePageSizeFormControl = new FormControl('', [Validators.required, Validators.min(10), Validators.max(500)]);

  dirty = false;
  form: BESettings;

  constructor(
    private service: SettingsService,
    private workspace: WorkspaceService,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {

  }

  ngOnInit() {
    if (this.showBEUI()) {
      this.form = _.cloneDeep(<BESettings>this.service.latestSettings);
      this.recentlyOpenedItemsFormControl.setValue(this.form.recentlyOpenedArtifactLimit);
      this.recentlyOpenedItemsFormControl.valueChanges.subscribe(val => {
        this.dirty = true;
        this.form.recentlyOpenedArtifactLimit = val;
      });

      this.descisionTablePageSizeFormControl.setValue(this.form.decisionTablePageSize);
      this.descisionTablePageSizeFormControl.valueChanges.subscribe(val => {
        this.dirty = true;
        this.form.decisionTablePageSize = val;
      });
    }
  }

  // BE methods ===

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  showTCEUI(): boolean {
    return environment.enableTCEUI;
  }

  onSubmit() {
    this.dirty = false;
    if (this.descisionTablePageSizeFormControl.errors === null && this.recentlyOpenedItemsFormControl.errors === null) {
      this.updateSettings();
      this.artifact.clear();
      this.service.onSaveUISettings()
        .then(() => this.workspace.refresh());
    }
  }

  isDirty(): boolean {
    return this.dirty;
  }

  getMessage(type: string): string {
    let msg;
    if (type.match('required')) {
      msg = this.i18n('Number of recently opened items to be displayed is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
    } else if (type.match('min')) {
      msg = this.i18n('Number of recently opened items to be  dispalyed cannot be {{0}}smaller than 1{{1}}.', { 0: `<strong>`, 1: `</strong>` });
    } else if (type.match('max')) {
      msg = this.i18n('Number of recently opened items to be  dispalyed cannot be {{0}}larger than 25{{1}}.', { 0: `<strong>`, 1: `</strong>` });
    } else if (type.match('tablereq')) {
      msg = this.i18n('Descision table page size is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
    } else if (type.match('mvalue')) {
      msg = this.i18n('Descision table page size cannot be larger than {{0}}500{{1}}.', { 0: `<strong>`, 1: `</strong>` });
    } else if (type.match('lvalue')) {
      msg = this.i18n('Descision table page size cannot be smaller than {{0}}10{{1}}.', { 0: `<strong>`, 1: `</strong>` });
    }
    return msg;
  }

  private updateSettings() {
    (<BESettings>this.service.latestSettings).defaultRTIFilterType = this.form.defaultRTIFilterType;
    (<BESettings>this.service.latestSettings).rtiViewStringWidget = this.form.rtiViewStringWidget;
    // (<BESettings> this.service.latestSettings).autoFitColumnsApproch = this.form.autoFitColumnsApproch;
    (<BESettings>this.service.latestSettings).decisionTablePageSize = this.form.decisionTablePageSize;
    (<BESettings>this.service.latestSettings).itemView = this.form.itemView;
    (<BESettings>this.service.latestSettings).scsUserPassword = this.form.scsUserPassword;
    (<BESettings>this.service.latestSettings).scsUserName = this.form.scsUserName;
    (<BESettings>this.service.latestSettings).recentlyOpenedArtifactLimit = this.form.recentlyOpenedArtifactLimit;
    this.form.autoUnLockOnReview = (<BESettings>this.service.latestSettings).autoUnLockOnReview;
    this.form.groupRelatedArtifacts = (<BESettings>this.service.latestSettings).groupRelatedArtifacts;
    this.form.allowCustomDomainValues = (<BESettings>this.service.latestSettings).allowCustomDomainValues;
    this.form.showColumnAliasIfPresent = (<BESettings>this.service.latestSettings).showColumnAliasIfPresent;
  }
}
