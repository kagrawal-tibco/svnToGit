import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ProjectMeta } from '../models/project-meta';

@Component({
  selector: 'project-info',
  templateUrl: './project-info.component.html',
})
export class ProjectInfoComponent implements AfterViewInit, OnInit {
  @Input()
  input: ProjectMeta;

  @Input()
  edit: boolean;

  @Input()
  checkProjectNameUnique: string[];

  @Output()
  checkProjectNameUniqueSuccess = new EventEmitter<boolean>();

  @ViewChild('projectForm', { static: false })
  form: NgForm;

  @ViewChild('projectNameInput', { static: false })
  inputBox: ElementRef;

  @Output()
  projectNameValid = new EventEmitter<boolean>();

  constructor(public i18n: I18n) { }

  ngOnInit() {
    this.form.statusChanges.subscribe(status => this.checkProjectNameUniqueSuccess.emit(status === 'VALID'));
  }

  ngAfterViewInit() {
    this.inputBox.nativeElement.focus();
  }

}
