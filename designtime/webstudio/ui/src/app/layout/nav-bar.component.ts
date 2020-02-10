
import { AfterViewInit, Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { timer as observableTimer, Observable, Subscription } from 'rxjs';
import { take } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { BEOperatorService } from '../core-be/be.operator.service';
import { ArtifactService } from '../core/artifact.service';
import { AuthStateService } from '../core/auth-state.service';
import { ProjectService } from '../core/project.service';
import { ReactService } from '../core/react.service';
import { RestService } from '../core/rest.service';
import { SettingsService } from '../core/settings.service';
import { UserService } from '../core/user.service';
import { DashboardService } from '../dashboard/dashboard.service';
import { BEUser } from '../models-be/user-be';
import { Notification } from '../models/notification';
import { NotificationCenterService } from '../notification-center/notification-center.service';
import { WorkspaceService } from '../workspace/workspace.service';
declare var GlobalNavbar: any;

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit, AfterViewInit {

  get isLoggedIn() {
    return this._isLoggedIn;
  }

  get userName() {
    return this._userName;
  }

  get pullLeft() {
    return this._pullLeft;
  }

  get drawerHidden(): boolean {
    return this._hideDrawer;
  }

  get headerDrawerVisible(): boolean {
    return this._showNotification || this._showUserMenu || this._showHelp; // or others eventually
  }

  get showNotificationCenter() {
    return this._showNotification;
  }

  get showUserMenu() {
    return this._showUserMenu;
  }

  get showManagementLink() {
    return this._showManagementLink;
  }

  get showHelp() {
    return this._showHelp;
  }

  get notifications(): Notification[] {
    return this.notification.getNotifications();
  }

  get requestProgress() {
    if (this.lastCnt === 0 && this.onGoingRequestsCnt === 0) {
      return 0;
    } else if (this.lastCnt !== 0 && this.onGoingRequestsCnt === 0) {
      return 100;
    } else if (this.waitForATick) {
      // the progress bar is just initialized, we return 0 first thus when next tick it got the real value,
      // we can see the animation.
      observableTimer(0).pipe(take(1)).toPromise().then(() => this.waitForATick = false);
      return 0;
    } else {
      return 100 - 38.2 * this.onGoingRequestsCnt / this.highestCnt;
    }
  }

  get showOldUIGotoButton() {
    return environment.enableBEUI && !environment.enableTCEUI;
  }

  get productName() {
    if (environment.enableTCEUI) {
      return 'Cloud Events WebStudio';
    }
    return (environment.enableBEUI) ? 'BusinessEvents WebStudio' : 'Artifact Management Server';
  }

  get copyRightYear() {
    return (environment.enableBEUI) ? '2020' : '2016-2018';
  }

  get productImage() {
    return (environment.enableBEUI) ? 'assets/img/webstudio-logo@3x.png' : 'assets/img/ams-logo@3x.png';
  }

  get isTCEEnable() {
    return environment.enableTCEUI;
  }

  title = 'TIBCO AMS';

  onGoingRequestsCnt = 0;
  lastCnt = 0;
  highestCnt = 0;
  showProgress: boolean;
  waitForATick: boolean;
  clearCnt: Subscription;

  onGoingRequestThreshold = 2;
  private _whenLoggedIn = {
    'Logout': true,
    'Create Project': false,
    'Dashboard': true,
    'Workspace': true,
  };

  private _whenNotLoggedIn = {
    'Login': true,
  };

  private _pullLeft = [
    { text: 'Workspace', link: '/workspace', },
    { text: 'Dashboard', link: '/dashboard', },
  ];

  private _userName: string; // this should be managed outside of just the nav-bar
  private _isLoggedIn: boolean; // this should be managed outside of just the nav-bar

  //  private _mainBodyMaxHeight: number;
  //  private _headerHeight = 60;
  private _showNotification = false;
  private _showHelp = false;
  private _showUserMenu = false;
  private _hideDrawer = true;
  private _showManagementLink = false;

  constructor(
    private dashboard: DashboardService,
    private rest: RestService,
    private react: ReactService,
    private user: UserService,
    private notification: NotificationCenterService,
    private settings: SettingsService,
    private project: ProjectService,
    private artifact: ArtifactService,
    private workspace: WorkspaceService,
    private authState: AuthStateService,
    private operator: BEOperatorService,
    public i18n: I18n
  ) { }

  isVisible(s: string) {
    return this.isLoggedIn ? this._whenLoggedIn[s] : this._whenNotLoggedIn[s];
  }

  //  get bodyMaxHeight(): number {
  //    return this._mainBodyMaxHeight;
  //  }
  //
  //  @HostListener('window:resize', ['$event'])
  //  onResize(event) {
  //    let w: Window = event.target;
  //    this._mainBodyMaxHeight = w.innerHeight - this._headerHeight;
  //    this.ui.maxHeight = this._mainBodyMaxHeight;
  //  }

  onLogout() {
    this.react.terminate();
    this.rest.logout().toPromise();
  }

  onHelp() {
    // note: this will not work in dev mode, as it will get redirected to the workspace
    if (environment.enableBEUI) {
      window.open('https://docs.tibco.com/products/tibco-businessevents');
    } else {
      window.open(window.location.origin + '/doc');
    }
    this.toggleHelpMenu();
  }

  onAPIDoc() {
    // note: this will not work in dev mode, as it will get redirected to the workspace
    if (environment.enableBEUI) {
      window.open(window.location.origin + '/WebStudio/apidoc');
    } else {
      window.open(window.location.origin + '/apidoc');
    }
    this.toggleHelpMenu();
  }

  ngOnInit() {
    //    this._mainBodyMaxHeight = window.window.innerHeight - this._headerHeight;
    //    this.ui.maxHeight = this._mainBodyMaxHeight;

    this.rest.ongoingRequests.subscribe(cnt => {
      // wrap the update in a setTimeout async block to avoid change detection errors:
      // ERROR: 'Expression has changed after it was checked'
      //      this.updateCount(cnt);
      setTimeout(() => { this.updateCount(cnt); }, 0);
    });

    if (environment.enableTCEUI) {
      // check for auth/access via Troposphere
      this.rest.checkTroposphereToken()
        .then(resp => {
          this.checkAuthState();
        });
    } else {
      this.checkAuthState();
    }
  }

  checkAuthState() {
    // check if already logged in externally
    if (environment.enableBEUI &&
      (this.authState.currentState.action === 'INIT' || this.authState.currentState.action === 'RESUME')) {
      this.rest.checkForExternalLogin();
    }

    this.authState.state.subscribe(auth => {
      const protocol = window.location.protocol;
      const hostname = window.location.hostname;
      const homeLocation = protocol + '//' + hostname + '/index.html';

      switch (auth.action) {
        // RESUME is an intermediate state, we need to send our a request to verify whether the token is still valid.
        case 'RESUME':
          this.settings.init()
            .then(ok => {
              if (ok) {
                this.authState.resume();
              } else {
                this.authState.logout();
              }
            });
          break;
        case 'INIT':
          this._isLoggedIn = false;
          this._userName = '';
          this.workspace.clear();
          this.dashboard.clear();
          this.project.clear();
          this.artifact.clear();
          if (environment.enableTCEUI) {
            window.location.href = homeLocation;
          }
          break;
        case 'SUCCESS':
          this._isLoggedIn = true;
          if (environment.enableBEUI && (<BEUser>auth.userInfo).firstName) {
            this._userName = (<BEUser>auth.userInfo).firstName;
          } else { this._userName = auth.userInfo.userName; }
          this.settings.init()
            .then(() => {
              this.workspace.refresh();
              this.artifact.init();
              this.dashboard.init();
              this.react.init();
              this.user.hasPermission('manage_permissions:*').then(ok => this._showManagementLink = ok);
              if (this.operator && environment.enableBEUI) {
                this.operator.init();
              }
            });
          break;
        default:
          if (environment.enableTCEUI) {
            window.location.href = homeLocation;
          }
          throw new Error(this.i18n('Unrecognizable auth action: ') + auth.action);
      }
    });
  }

  ngAfterViewInit() {
    console.log('initializing');

    if (this.isTCEEnable) {
      console.log('initializing tce');
      const customizedComponents = [];
      // let notificationComponent = {
      //   name: 'notifications',
      //   template: '#ws-notifications'
      // };

      const helpComponent = {
        name: 'help',
        template: '#ws-help'
      };
      // customizedComponents.push(notificationComponent);
      customizedComponents.push(helpComponent);

      const navbar = new GlobalNavbar({
        container: '#globalNavBar',
        textAfterLogo: 'Events WebStudio',
        iconMenus: {

          search: {
            visible: false,
          },
          download: {
            visible: false,
          },
          help: {
            visible: true,
          },
          notifications: {
            visible: false,
          },
          profile: {
            visible: true
          },
        },
        customizedComponents: customizedComponents,
        idle: {
          keepAliveUrl: '/WebStudio'
        },
      });

      navbar.refreshRebrandingStyle({
        'customRightButton': {
          'backgroundColor': '#254787',
          'color': '#062e79',
        }
      });

      navbar.load();
    }
  }

  updateCount(cnt: number) {
    this.lastCnt = this.onGoingRequestsCnt;
    this.onGoingRequestsCnt = cnt;
    if (this.onGoingRequestsCnt > 0) {
      // enable the progress bar
      if (this.lastCnt === 0) {
        this.showProgress = true;
        this.waitForATick = true;
      }
      // cancel any ongoing hiding operation
      if (this.clearCnt) {
        this.clearCnt.unsubscribe();
        this.clearCnt = null;
      }
      // update the highest
      if (this.onGoingRequestsCnt > this.highestCnt) {
        this.highestCnt = this.onGoingRequestsCnt;
      }
    } else {
      // hiding the progress bar
      if (!this.clearCnt) {
        this.clearCnt = observableTimer(650).pipe(take(1)).subscribe(() => {
          this.lastCnt = 0;
          this.highestCnt = 0;
          this.showProgress = false;
          this.clearCnt.unsubscribe();
        });
      }
    }
  }

  @HostListener('document:keydown.escape', ['$event'])
  toggleNotificationCenter(e?: Event) {
    if (e instanceof Event) {
      e.stopPropagation();
      e.preventDefault();
      if (!this._showNotification && e.type === 'keydown') {
        return;
      }
    }
    //    if (!NotificationCenterService.notificationDialogOpen) {
    //      this._showNotification = !this._showNotification;
    //      this._showUserMenu = false;
    //    }
    if (this._showNotification) {
      this.hideDrawer();
    } else {
      this._hideDrawer = false;
      this._showNotification = true;
      this._showUserMenu = false;
      this._showHelp = false;
    }
  }

  toggleUserMenu(e?: Event) {
    if (this._showUserMenu) {
      this.hideDrawer();
    } else {
      this._hideDrawer = false;
      this._showNotification = false;
      this._showUserMenu = true;
      this._showHelp = false;
    }
    if (e instanceof Event) {
      e.preventDefault();
      e.stopPropagation();
    }
  }

  toggleHelpMenu(e?: Event) {
    if (e instanceof Event) {
      e.preventDefault();
      e.stopPropagation();
    }
    if (this._showHelp) {
      this.hideDrawer();
    } else {
      this._hideDrawer = false;
      this._showNotification = false;
      this._showUserMenu = false;
      this._showHelp = true;
    }
  }

  toggleMenu(e?: Event) {
    if (this.showUserMenu) {
      this.toggleUserMenu(e);
    } else if (this.showNotificationCenter) {
      this.toggleNotificationCenter(e);
    } else if (this.showHelp) {
      this.toggleHelpMenu(e);
    }
  }

  hideDrawer() {
    this._showNotification = false;
    this._showUserMenu = false;
    this._showHelp = false;
    setTimeout(() => { this._hideDrawer = true; }, 400); // 400ms is the time of the animation
  }

  loadOldUI() {
    window.location.href = '/WebStudio/WebStudio.html';
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

  navigateToOrchestrator(): void {
    const protocol = window.location.protocol;
    const hostname = window.location.hostname;
    const tceHomeLocation = protocol + '//' + hostname + '/index.html';
    window.location.href = tceHomeLocation;
  }
}
