
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { timer as observableTimer, Observable } from 'rxjs';

import { ArtifactStatusBarComponent } from './artifact-status-bar.component';

import { environment } from '../../environments/environment';
import { VerifyConfig } from '../verify/verify.service';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';
type PanelTab = 'Problems' | 'Verify' | 'Properties' | 'TestData';

@Component({
  selector: 'artifact-panel',
  templateUrl: './artifact-panel.component.html',
  styleUrls: ['./artifact-panel.component.css'],
})
export class ArtifactPanelComponent implements OnInit {
  @Input()
  public verifyConfig: VerifyConfig;

  @Input()
  public supportVerify: boolean;

  @Output()
  public close = new EventEmitter<boolean>();

  @Output()
  public startVerify = new EventEmitter<boolean>();

  public curr: string;
  public tabs: String[];

  tabMap: Map<PanelTab, string> = new Map<PanelTab, string>();

  constructor(private multiTabService: MultitabEditorService, public i18n: I18n) {
    this.tabMap.set('Problems', this.i18n('Problems'));
    this.tabMap.set('Verify', this.i18n('Verify'));
    this.tabMap.set('Properties', this.i18n('Properties'));
    this.tabMap.set('TestData', this.i18n('Test Data'));
    if (environment.enableBEUI) {
      this.multiTabService.activeTabSubject.subscribe((tab) => {
        if (tab) {
          const extension = tab.payload.type.extensions[0];
          if (extension === 'ruletemplateinstance') {
            let index = this.tabs.indexOf(this.tabMap.get('Properties'));
            if (index === -1) {
              this.tabs.push(this.tabMap.get('Properties'));
            }
            index = this.tabs.indexOf(this.tabMap.get('TestData'));
            if (index > -1) {
              this.tabs.splice(index, 1);
            }
          } else if (extension === 'rulefunctionimpl') {
            let index = this.tabs.indexOf(this.tabMap.get('Properties'));
            if (index === -1) {
              this.tabs.push(this.tabMap.get('Properties'));
            }
            index = this.tabs.indexOf(this.tabMap.get('TestData'));
            if (index === -1) {
              this.tabs.push(this.tabMap.get('TestData'));
            }
          }
        } else {
          this.curr = this.tabMap.get('Problems');
        }
      });
    }
  }

  public startVerifySession(config: VerifyConfig): Promise<boolean> {
    this.curr = this.tabMap.get('Verify');
    return observableTimer(0).toPromise().then(() => {
      this.verifyConfig = config;
      return true;
    });
  }

  onClose() {
    this.close.emit(true);
  }

  onVerify() {
    this.startVerify.emit(true);
  }

  get hasVerifyOnGoing() {
    return this.verifyConfig != null;
  }

  onSelect(tab: string) {
    this.curr = tab;
  }

  ngOnInit() {
    if (this.supportVerify) {
      this.tabs = [this.tabMap.get('Problems'), this.tabMap.get('Verify')];
    } else if (environment.enableBEUI) {
      if (this.multiTabService.tabs.length > 0) {
        if (this.multiTabService.isBEDTActive()) {
          this.tabs = [this.tabMap.get('Problems'), this.tabMap.get('Properties'), this.tabMap.get('TestData')];
        } else {
          this.tabs = [this.tabMap.get('Problems'), this.tabMap.get('Properties')];
        }
      } else {
        this.tabs = [this.tabMap.get('Problems')];
      }
    } else {
      this.tabs = [this.tabMap.get('Problems')];
    }
    if (this.verifyConfig && this.supportVerify) {
      this.curr = this.tabMap.get('Verify');
    } else {
      this.curr = ArtifactStatusBarComponent.selectedView;
    }
  }

  get showProperties(): boolean {
    const show = environment.enableBEUI && this.multiTabService.tabs.length > 0;
    if (!show && !this.multiTabService.isBEDTActive()) {
      const index = this.tabs.indexOf(this.tabMap.get('Properties'));
      if (index > -1) {
        this.tabs.splice(index, 1);
      }
    }
    return show;
  }

  get showTestData(): boolean {
    const show = environment.enableBEUI && this.multiTabService.isBEDTActive();
    if (!show) {
      const index = this.tabs.indexOf(this.tabMap.get('TestData'));
      if (index > -1) {
        this.tabs.splice(index, 1);
      }
    }
    return show;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
