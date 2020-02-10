import { Component, ViewContainerRef } from '@angular/core';
import { OnInit } from '@angular/core';
import { HostListener } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Overlay } from 'ngx-modialog';

import { AuthStateService } from './core/auth-state.service';
import { RestService } from './core/rest.service';
import { UIService } from './core/ui.service';
import { BEBuildInfoRecord } from './models/dto';
import { MultitabEditorService } from './workspace/multitab-editor/multitab-editor.service';

import { environment } from '../environments/environment';
const locale = navigator.language as string;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {

  get isLoggedIn() {
    return this._isLoggedIn;
  }

  get userName() {
    return this._userName;
  }

  get copyRightYear() {
    return (environment.enableBEUI) ? '2020' : '2016-2017';
  }

  get buildTimestamp() {
    const buildInfo = this.rest.getBuildInfo();
    if (buildInfo) {
      return (environment.enableBEUI) ? (<BEBuildInfoRecord>buildInfo).buildDate : this.rest.buildInfo.timestamp;
    }
  }

  get buildInfo() {
    const buildInfo = this.rest.getBuildInfo();
    if (buildInfo) {
      return (environment.enableBEUI) ? (this.rest.buildInfo.version + '.'
        + (<BEBuildInfoRecord>buildInfo).build) : this.rest.buildInfo.version;
    } else {
      return '';
    }
  }

  get bodyMaxHeight(): number {
    return this._mainBodyMaxHeight;
  }

  get productName() {
    if (environment.enableTCEUI) {
      return 'Cloud Events WebStudio';
    }
    return (environment.enableBEUI) ? 'BusinessEvents WebStudio' : 'Artifact Management Server';
  }
  public dirValue: string;

  private _mainBodyMaxHeight: number;
  private _headerHeight = 84;
  private _userName: string;
  private _isLoggedIn = false;

  constructor(private ui: UIService,
    private authState: AuthStateService,
    private tabService: MultitabEditorService,
    private rest: RestService,
    public i18n: I18n,
    overlay: Overlay, vcRef: ViewContainerRef, ) {
    //    overlay.defaultViewContainer = vcRef; // this doesn't appear to be needed with latest ngx-modialog
  }

  ngOnInit() {
    locale.search('ar') !== -1 ? this.dirValue = 'rtl' : this.dirValue = 'ltr';
    this._mainBodyMaxHeight = window.window.innerHeight; // - this._headerHeight;
    this.ui.maxHeight = this._mainBodyMaxHeight;

    //      this.authState.state.subscribe(auth => {
    //      switch (auth.action) {
    //        case 'RESUME':
    //          break;
    //        case 'INIT':
    //          this._isLoggedIn = false;
    //          this._userName = '';
    //          break;
    //        case 'SUCCESS':
    //          this._isLoggedIn = true;
    //          this._userName = auth.userInfo.userName;
    //          break;
    //        default:
    //          throw new Error('Unrecognizable auth action: ' + auth.action);
    //      }
    //    });

  }

  @HostListener('window:resize', ['$event'])
  onResize(event) {
    const w: Window = event.target;
    this._mainBodyMaxHeight = w.innerHeight - this._headerHeight;
    this.ui.maxHeight = this._mainBodyMaxHeight;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
