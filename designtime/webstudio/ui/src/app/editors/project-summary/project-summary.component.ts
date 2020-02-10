import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ProjectSummary } from './project-summary';

import { EditBuffer } from '../../editables/edit-buffer';
import { EditorParams } from '../editor-params';
import { EditorComponent } from '../editor.component';

export const SECONDS_OF_DAY = 60 * 60 * 24;
export const SECONDS_OF_MINUTE = 60;
export const SECONDS_OF_HOUR = 60 * 60;
export const TITLE_LENGTH = 32;

@Component({
  selector: 'projectSummary',
  templateUrl: 'project-summary.component.html',
  styleUrls: ['project-summary.component.css']
})
export class ProjectSummaryComponent extends EditorComponent<ProjectSummary> implements OnInit {
  params: EditorParams<ProjectSummary>;
  editBuffer: EditBuffer<ProjectSummary>;
  _name: string;
  _path: string;
  _description: string;
  _size: string;
  _artifactsCount: number;
  _lastCheckoutBy: string;
  _lastCheckoutTime: string;
  _lastCommitBy: string;
  _lastCommitTime: string;
  _lastGenerateDeployableBy: string;
  _lastGenerateDeployableTime: string;
  _lastSyncBy: string;
  _lastSyncTime: string;
  _lastValidateBy: string;
  _lastValidateTime: string;
  _totalCommits: number;
  _totalApprovals: number;
  _totalDeployments: number;
  _totalRejections: number;

  constructor(public i18n: I18n) {
    super();
  }

  ngOnInit() {
    this.updateSummary();
  }

  updateSummary() {
    this._name = this.name();
    this._path = '/' + this.name();
    this._description = this.description();
    this._size = this.size();
    this._artifactsCount = this.artifactsCount();
    this._lastCheckoutBy = this.lastCheckoutBy();
    this._lastCheckoutTime = this.lastCheckoutTime();
    this._lastCommitBy = this.lastCommitBy();
    this._lastCommitTime = this.lastCommitTime();
    this._lastGenerateDeployableBy = this.lastGenerateDeployableBy();
    this._lastGenerateDeployableTime = this.lastGenerateDeployableTime();
    this._lastSyncBy = this.lastSyncBy();
    this._lastSyncTime = this.lastSyncTime();
    this._lastValidateBy = this.lastValidateBy();
    this._lastValidateTime = this.lastValidateTime();
    this._totalCommits = this.totalCommits();
    this._totalApprovals = this.totalApprovals();
    this._totalRejections = this.totalRejections();
    this._totalDeployments = this.totalDeployments();
  }

  name() {
    return this.params.editBuffer.getContent().name;
  }

  description() {
    return this.params.editBuffer.getContent().description;
  }

  size() {
    const sizeString = this.params.editBuffer.getContent().size;
    const sizeinKB = Number.parseInt(sizeString.substring(0, sizeString.indexOf('.')));
    const sizeinMB = sizeinKB / 1024;
    return sizeinMB.toFixed(2) + ' MB';
  }

  artifactsCount() {
    return this.params.editBuffer.getContent().artifactsCount;
  }

  lastCheckoutBy() {
    if (this.params.editBuffer.getContent().lastCheckoutBy) {
      return this.params.editBuffer.getContent().lastCheckoutBy;
    } else {
      return '-';
    }
  }

  lastCheckoutTime() {
    if (this.params.editBuffer.getContent().lastCheckoutTime != '0') {
      const date = new Date(this.params.editBuffer.getContent().lastCheckoutTime);
      return this.produceMessage(date);
    } else {
      return '-';
    }
  }

  lastCommitBy() {
    if (this.params.editBuffer.getContent().lastCommitBy) {
      return this.params.editBuffer.getContent().lastCommitBy;
    } else {
      return '-';
    }
  }

  lastCommitTime() {
    if (this.params.editBuffer.getContent().lastCommitTime != '0') {
      const date = new Date(this.params.editBuffer.getContent().lastCommitTime);
      return this.produceMessage(date);
    } else {
      return '-';
    }
  }

  lastGenerateDeployableBy() {
    if (this.params.editBuffer.getContent().lastGenerateDeployableBy) {
      return this.params.editBuffer.getContent().lastGenerateDeployableBy;
    } else {
      return '-';
    }
  }

  lastGenerateDeployableTime() {
    if (this.params.editBuffer.getContent().lastGenerateDeployableTime != '0') {
      const date = new Date(this.params.editBuffer.getContent().lastGenerateDeployableTime);
      return this.produceMessage(date);
    } else {
      return '-';
    }
  }

  lastSyncBy() {
    if (this.params.editBuffer.getContent().lastSyncBy) {
      return this.params.editBuffer.getContent().lastSyncBy;
    } else {
      return '-';
    }
  }

  lastSyncTime() {
    if (this.params.editBuffer.getContent().lastSyncTime != '0') {
      const date = new Date(this.params.editBuffer.getContent().lastSyncTime);
      return this.produceMessage(date);
    } else {
      return '-';
    }
  }

  lastValidateBy() {
    if (this.params.editBuffer.getContent().lastValidateBy) {
      return this.params.editBuffer.getContent().lastValidateBy;
    } else {
      return '-';
    }
  }

  lastValidateTime() {
    if (this.params.editBuffer.getContent().lastValidateTime != '0') {
      const date = new Date(this.params.editBuffer.getContent().lastValidateTime);
      return this.produceMessage(date);
    } else {
      return '-';
    }
  }

  totalCommits() {
    return this.params.editBuffer.getContent().totalCommits;
  }

  totalApprovals() {
    return this.params.editBuffer.getContent().totalApprovals;
  }

  totalDeployments() {
    return this.params.editBuffer.getContent().totalDeployments;
  }

  totalRejections() {
    return this.params.editBuffer.getContent().totalRejections;
  }

  private produceMessage(date: Date) {
    const t = date;
    const now = new Date();
    const elapsed = (now.getTime() - t.getTime()) / 1000;
    if (elapsed > SECONDS_OF_DAY * 10) {
      return `on ${date.toDateString()}`;
    } else if (elapsed >= SECONDS_OF_DAY) {
      const days = elapsed / SECONDS_OF_DAY;
      return this.formatMessage('day', days);
    } else if (elapsed >= SECONDS_OF_HOUR) {
      const hours = elapsed / SECONDS_OF_HOUR;
      return this.formatMessage('hour', hours);
    } else if (elapsed >= SECONDS_OF_MINUTE) {
      const minutes = elapsed / SECONDS_OF_MINUTE;
      return this.formatMessage('minute', minutes);
    } else {
      return this.i18n('Just now');
    }
  }

  private formatMessage(key: string, val: number) {
    val = Math.floor(val);
    // return `${val} ${key}${val > 1 ? 's' : ''} ago`;
    switch (key) {
      case 'day':
        return (val > 1 ? this.i18n('{{val}} days ago', { val: val }) : this.i18n('{{val}} day ago', { val: val }));
      case 'hour':
        return (val > 1 ? this.i18n('{{val}} hours ago', { val: val }) : this.i18n('{{val}} hour ago', { val: val }));
      case 'minute':
        return (val > 1 ? this.i18n('{{val}} minutes ago', { val: val }) : this.i18n('{{val}} minute ago', { val: val }));
      case 'second':
        return (val > 1 ? this.i18n('{{val}} seconds ago', { val: val }) : this.i18n('{{val}} second ago', { val: val }));
    }
  }
}
