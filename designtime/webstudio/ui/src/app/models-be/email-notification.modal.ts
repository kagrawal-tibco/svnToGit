/**
 * @Author: Rahil Khera
 * @Date:   2017-08-01T13:29:14+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-01T13:35:33+05:30
 */
import { I18n } from '@ngx-translate/i18n-polyfill';

export class EmailNotification {

  set project(value: string) {
    if (true) {
      this._project = value;
    }
  }

  get project(): string {
    return this._project;
  }

  set actions(value: Array<Actions>) {
    if (value) {
      this._actions = value;
    }
  }

  get actions(): Array<Actions> {
    if (this._actions == null) {
      this.actions = new Array<Actions>();
      this.actions = [new Actions('commit', true, this.i18n('Commit')),
      new Actions('approve', true, this.i18n('Approve')),
      new Actions('reject', true, this.i18n('Reject')),
      new Actions('deploy', true, this.i18n('Deploy'))];
    }
    return this._actions;
  }

  set emailIds(value: Array<string>) {
    if (value) {
      this._emailIds = value;
    }
  }

  get emailIds(): Array<string> {
    if (this._emailIds == null) {
      this.emailIds = new Array<string>();
    }
    return this._emailIds;
  }
  private _project: string;
  private _actions: Array<Actions>;
  private _emailIds: Array<string>;

  constructor(project: string, public i18n: I18n, actions?: Array<Actions>, emailIds?: Array<string>) {
    this.project = project;
    this.actions = actions;
    this.emailIds = emailIds;
  }

  public static setActionArrayFromString(actionString: string, i18n: I18n): Array<Actions> {

    let actionArray: Array<string> = new Array<string>();
    if (actionString) {
      actionArray = actionString.split(',');
    }

    const actions = new Array<Actions>();
    actionArray.indexOf('commit') > -1 ?
      actions.push(new Actions('commit', true, i18n('Commit'))) :
      actions.push(new Actions('commit', false, i18n('Commit')));

    actionArray.indexOf('approve') > -1 ?
      actions.push(new Actions('approve', true, i18n('Approve'))) :
      actions.push(new Actions('approve', false, i18n('Approve')));

    actionArray.indexOf('reject') > -1 ?
      actions.push(new Actions('reject', true, i18n('Reject'))) :
      actions.push(new Actions('reject', false, i18n('Reject')));

    actionArray.indexOf('deploy') > -1 ?
      actions.push(new Actions('deploy', true, i18n('Deploy'))) :
      actions.push(new Actions('deploy', false, i18n('Deploy')));

    return actions;
  }

  public static getSaveJson(emailNotificationList: Array<EmailNotification>): any {
    const payload = {
      'request': {
        'data': {
          'notificationPreference': {
            'notificationPreferenceItem': {
              'emailPreference': EmailNotification.emailNotificationListToString(emailNotificationList)
            }
          }
        }
      }
    };
    return payload;
  }

  private static actionsToString(actionArray: Array<Actions>): string {
    let actionString = '';
    for (let i = 0; i < actionArray.length; i++) {
      if (actionArray[i].permission) {
        actionString += actionArray[i].action + ',';
      }
    }
    return actionString;
  }

  private static emailIdsToString(emailIdsArray: Array<string>): string {
    let emailIdsString = '';
    for (let i = 0; i < emailIdsArray.length; i++) {
      emailIdsString += emailIdsArray[i] + ',';
    }
    if (emailIdsString === '') {
      return undefined;
    }
    return emailIdsString;
  }

  private static emailNotificationListToString(emailNotificationList: Array<EmailNotification>): any[] {
    const emailPreference = [];
    for (let i = 0; i < emailNotificationList.length; i++) {
      const projectEntry = {
        project: emailNotificationList[i].project,
        actions: EmailNotification.actionsToString(emailNotificationList[i].actions),
        emailIds: EmailNotification.emailIdsToString(emailNotificationList[i].emailIds)
      };
      emailPreference.push(projectEntry);
    }
    return emailPreference;
  }

}

export class Actions {
  private _action: string;
  private _permission: boolean;
  private _displayText: string;
  constructor(action: string, permission: boolean, displayText: string) {
    this._action = action;
    this._permission = permission;
    this._displayText = displayText;
  }

  set action(value: string) {
    this._action = value;
  }

  get action(): string {
    return this._action;
  }

  set permission(value: boolean) {
    this._permission = value;
  }

  get permission(): boolean {
    return this._permission;
  }

  set displayText(value: string) {
    this._displayText = value;
  }

  get displayText(): string {
    return this._displayText;
  }
}
