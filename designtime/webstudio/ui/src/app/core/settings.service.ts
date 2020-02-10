
import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { Logger } from './logger.service';
import { RestService } from './rest.service';

import { Response } from '../models/response';
import { Settings } from '../models/settings';

@Injectable()
export class SettingsService {
  protected settings = new BehaviorSubject<Settings>(this.defaultSettings);

  constructor(
    protected rest: RestService,
    protected log: Logger
  ) { }

  init(): Promise<boolean> {
    return this.initImp();
  }

  initImp(): Promise<boolean> {
    return this.rest.get(`/settings/ui-settings`)
      .toPromise()
      .then((res: Response) => {
        if (res.ok()) {
          let settings: Settings;
          try {
            settings = JSON.parse(res.record[0].content);
          } catch (e) {
            settings = this.defaultSettings;
          }
          this.settings.next(settings);
          return true;
        } else {
          return false;
        }
      });
  }

  get defaultSettings(): Settings {
    return {
      showDiff: true, onlyDisplayCheckedOutArtifacts: false, autoArtifactValidation: true,
      showEmptyFolders: false, displayWorklistNotifications: true, hideInExplorer: {}
    };
  }

  get latestSettings() {
    return this.settings.value;
  }

  get uiSettings(): Observable<Settings> {
    return this.settings.pipe(filter(val => val != null));
  }

  onSaveUISettings(): Promise<boolean> {
    return this.rest.put(`/settings/ui-settings`, { content: JSON.stringify(this.latestSettings) }).pipe(
      map(record => record.ok()))
      .toPromise();
  }
}
