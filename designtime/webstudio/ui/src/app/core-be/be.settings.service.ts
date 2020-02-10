import { Injectable } from '@angular/core';

import { map } from 'rxjs/operators';

import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { SettingsService } from '../core/settings.service';
import { BESettings, RTIStringWidget } from '../models-be/settings-be';
import { Response } from '../models/response';

@Injectable()
export class BESettingsService extends SettingsService {

  constructor(
    protected rest: RestService,
    protected log: Logger
  ) {
    super(rest, log);
  }

  initImp(): Promise<boolean> {
    return this.rest.get(`preferences/user.json`)
      .toPromise()
      .then((res: Response) => {
        if (res.ok()) {
          let settings: BESettings;
          try {
            settings = res.first();

            // Setting field missing in BE to default values
            settings.onlyDisplayCheckedOutArtifacts = true;
            settings.hideInExplorer = {};
            settings.showDiff = false;
          } catch (e) {
            // setting default values
            settings = {
              showDiff: true,
              displayWorklistNotifications: true,
              onlyDisplayCheckedOutArtifacts: true,
              hideInExplorer: {},
              portalColumns: 2,
              recentlyOpenedArtifactLimit: 10,
              favoriteArtifactLimit: 10,
              customURL: '',
              itemView: 'List',
              decisionTablePageSize: 20,
              scsUserName: '',
              scsUserPassword: '',
              autoUnLockOnReview: true,
              groupRelatedArtifacts: true,
              allowCustomDomainValues: true,
              showColumnAliasIfPresent: true,
              showEmptyFolders: false,
              isGroupingPropertyChanged: true,
              autoFitColumnsApproch: '',
              defaultRTIFilterType: 'Match Any',
              autoArtifactValidation: false,
              rtiViewStringWidget: 'TextArea'
            };
          }
          this.settings.next(settings);
          return true;
        } else {
          return false;
        }
      });
  }

  onSaveUISettings(): Promise<boolean> {
    const payload = {
      request: {
        data: {
          userPreference: {
            userPreferenceItem: JSON.parse(JSON.stringify(this.latestSettings, this.preferencePropertyReplacer))
          }
        }
      }
    };

    return this.rest.put(`preferences/update.json`, payload).pipe(
      map(record => record.ok()))
      .toPromise();
  }

  private preferencePropertyReplacer(key: string, value: string): any {
    if (key === 'attributes' || key === 'onlyDisplayCheckedOutArtifacts' || key === 'hideInExplorer' || key === 'showDiff' || key === 'showEmptyFolders') { return undefined; }
    return value;
  }
}
