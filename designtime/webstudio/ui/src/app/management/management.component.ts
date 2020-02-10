import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from '../../environments/environment';

@Component({
  selector: 'management',
  templateUrl: './management.component.html',
  styleUrls: ['./management.component.css']
})
export class ManagementComponent {

  constructor(public i18n: I18n) { }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  isTCEUI(): boolean {
    return environment.enableTCEUI;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
