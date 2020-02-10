import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from '../../environments/environment';
import { BEUserService } from '../core-be/be.user.service';
@Component({
  selector: 'settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css'],
})
export class SettingsComponent implements OnInit {

  get userSettingsCollapse(): boolean {
    return this._userSettingsCollapse;
  }

  set userSettingsCollapse(value: boolean) {
    this._userSettingsCollapse = value;
  }

  get applicationSettingsCollapse(): boolean {
    return this._applicationSettingsCollapse;
  }

  set applicationSettingsCollapse(value: boolean) {
    this._applicationSettingsCollapse = value;
  }

  get available(): boolean {
    return this.showBEUI() && this._available;
  }
  selectedLink = '';

  private _userSettingsCollapse = true;
  private _applicationSettingsCollapse = true;
  private _available = false;

  constructor(
    private route: ActivatedRoute,
    public i18n: I18n,
    private bEUserService?: BEUserService,
  ) { }

  ngOnInit() {
    if (!this.showBEUI()) {
      this.userSettingsCollapse = false;
    }
    if (this.showBEUI()) {
      this.bEUserService.hasAdminRole()
        .then((permission) => {
          this._available = permission;
        });

      this.route.children[0].url.subscribe((urlSegment) => {
        const path = urlSegment[0].path;
        switch (path) {
          case 'preferences':
            this.userSettingsCollapse = false;
            break;
          case 'operator-preferences':
          case 'notification-preferences':
          case 'be-lock-management':
          case 'download-projects':
            this.applicationSettingsCollapse = false;
            break;

        }
      });
    }
  }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  /**
   * This function will be set the values of userSettingsCollapse and applicationSettingsCollapse.
   * Hence, hiding or shwoing the options under user settings and application settings.
   * @param parentId: 0 for user settings, 1 for application settings.
   */
  collapseChildEntries(parentId: number) {
    if (this.showBEUI()) {
      switch (parentId) {
        case 0:
          this.userSettingsCollapse = this.userSettingsCollapse ? false : true;
          break;
        case 1:
          this.applicationSettingsCollapse = this.applicationSettingsCollapse ? false : true;
          break;
      }
    }
  }

  onSelection(link: string) {
    this.selectedLink = link;
  }

  isTCEUI(): boolean {
    return environment.enableTCEUI;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
