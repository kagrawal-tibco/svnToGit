import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as EmailValidator from 'email-validator';
import * as _ from 'lodash';

import { AlertService } from '../../../core/alert.service';
import { RestService } from '../../../core/rest.service';
import { EmailNotification } from '../../../models-be/email-notification.modal';

@Component({
  selector: 'email-notifications',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})

export class NotificationsComponent implements OnInit {
  emailNotificationList: EmailNotification[];
  selectedProject: EmailNotification;
  selectedEmailIdIndex = -1;
  editEmailId = false;
  previousEmailNotifications: EmailNotification[];
  currentEmailIdValue = '';
  selectedProjectIndex = 0;
  public enableAddEmailId = false;
  selectedEmailId = '';
  disableApply = true;

  constructor(private rest: RestService, private alert: AlertService, public i18n: I18n) {
    this.selectedProject = new EmailNotification('', this.i18n);
  }

  ngOnInit() {
    this.fetchEmailNotification();
  }

  fetchEmailNotification() {
    this.rest.get('preferences/notify.json')
      .toPromise()
      .then(result => {
        if (result.ok()) {
          this.emailNotificationList = new Array<EmailNotification>();
          const jsonEmailPreference = result.record[0].emailPreference;
          if (jsonEmailPreference) {
            for (let i = 0; i < jsonEmailPreference.length; i++) {
              const emailPreference = new EmailNotification(jsonEmailPreference[i].project, this.i18n);
              emailPreference.actions = EmailNotification.setActionArrayFromString(jsonEmailPreference[i].actions, this.i18n);
              if (jsonEmailPreference[i].emailIds) {
                const emailIdList: Array<string> = jsonEmailPreference[i].emailIds.split(',');
                emailPreference.emailIds = emailIdList.filter((value: string, index: number, array: string[]) => {
                  return value !== '' && value !== 'NO_EMAILS';
                });
              }
              this.emailNotificationList.push(emailPreference);
            }
            this.selectedProject = new EmailNotification(this.emailNotificationList[0].project, this.i18n);
            this.selectedProject = this.emailNotificationList[0];
            // console.log(this.emailNotificationList);
            this.previousEmailNotifications = new Array<EmailNotification>();
            this.previousEmailNotifications = _.cloneDeep(this.emailNotificationList);
          }
        } else {
          console.log('Failed');
        }
      });
  }

  updateEmailNotification() {
    const url = `preferences/notify/update.json`;
    const payload = EmailNotification.getSaveJson(this.emailNotificationList);
    this.disableApply = true;
    this.previousEmailNotifications = _.cloneDeep(this.emailNotificationList);
    return this.rest.put(url, payload, undefined, true)
      .toPromise()
      .then(res => {
        if (res.ok()) {
          console.log(res.record[0]);
        } else {
          console.log('Failure');
        }
      });
  }

  onProjectSelection(index: number) {
    this.selectedProject = this.emailNotificationList[index];
    this.selectedProjectIndex = index;
  }

  /**
   * This function will remove exisiting email id from the list.
   * @param: index
   */
  deleteEmailId(emailId: string) {
    const index = this.selectedProject.emailIds.indexOf(emailId);
    this.selectedProject.emailIds.splice(index, 1);
    this.disableApply = false;
  }

  onAddEmailId() {
    this.enableAddEmailId = true;
  }

  confirmAddEmailId(emailId: string) {
    if (EmailValidator.validate(emailId)) {
      this.selectedProject.emailIds.push(emailId);
      this.enableAddEmailId = false;
      this.disableApply = false;
    } else {
      this.enableAddEmailId = true;
      this.disableApply = true;
      this.alert.flash(this.i18n('Invalid Email Id. Please enter a valid Email - Id'), 'error');
    }
  }

  cancelAddEmailId() {
    this.enableAddEmailId = false;
  }

  onEmailSelection(selectedEmailId: string) {
    this.selectedEmailId = selectedEmailId;
  }

  onEditEmailId() {
    this.editEmailId = true;
  }

  confirmEditEmailId(newEmailId: string) {
    if (EmailValidator.validate(newEmailId)) {
      const index = this.selectedProject.emailIds.indexOf(this.selectedEmailId);
      this.selectedProject.emailIds[index] = newEmailId;
      this.selectedEmailId = newEmailId;
      this.editEmailId = false;
      this.disableApply = false;
    } else {
      this.disableApply = true;
      this.editEmailId = true;
      this.alert.flash(this.i18n('Invalid Email Id. Please enter a valid Email - Id'), 'error');
    }
  }

  cancelEditEmailId() {
    this.editEmailId = false;
  }

  onActionChange() {
    this.disableApply = false;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
