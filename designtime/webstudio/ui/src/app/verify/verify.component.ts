
import { AfterViewInit, Component, DoCheck, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { timer as observableTimer, BehaviorSubject, Observable } from 'rxjs';
import { filter, map, take, takeUntil } from 'rxjs/operators';

import { VerifyConfig, VerifyService } from './verify.service';

import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { UIService } from '../core/ui.service';
import { WebSocketService } from '../core/websocket.service';
import { VerifyResponseRecord } from '../models/dto';
import { LINE_HEIGHT, TerminalComponent } from '../terminal/terminal.component';

type SessionStatus = 'INIT' | 'SUCCESS' | 'TERMINATING' | 'TERMINATED';

@Component({
  selector: 'verify',
  templateUrl: './verify.component.html'
})
export class VerifyComponent implements OnInit, OnDestroy, AfterViewInit, DoCheck {

  get isActive(): boolean {
    return this.status.value !== 'TERMINATED';
  }

  get callOutClass() {
    const val = this.status.value;
    return {
      'callout': true,
      'callout-warning': val === 'INIT' || val === 'TERMINATING',
      'callout-success': val === 'SUCCESS',
      'callout-danger': val === 'TERMINATED',
    };
  }
  @Input()
  config: VerifyConfig;

  rows: number;
  verifyRecordId: string;
  status: BehaviorSubject<SessionStatus>;

  @ViewChild('term', { static: false })
  private terminal: TerminalComponent;

  @ViewChild('wrapper', { static: false })
  private wrapper: ElementRef;

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    private service: VerifyService,
    private websocket: WebSocketService,
    private modal: ModalService,
    private ui: UIService,
    private route: ActivatedRoute,
    private router: Router,
    public i18n: I18n
  ) {
  }

  ngOnInit() {
    this.status = new BehaviorSubject<SessionStatus>('INIT');
  }

  ngDoCheck() {
    if (this.wrapper.nativeElement.offsetHeight !== 0) {
      this.rows = Math.floor((this.wrapper.nativeElement.offsetHeight) / LINE_HEIGHT);
    }
  }

  ngAfterViewInit() {
    if (this.websocket.isReady.value) {
      observableTimer(1000).toPromise().then(() => this.startSession());
    } else {
      this.terminal.println(this.i18n('No WebSocket connection established, please refresh browser and try again.'));
    }
  }

  onNewCommand(cmd: string) {
    this.processCommand(cmd);
  }

  processCommand(cmd: string) {
    cmd = cmd.trim();
    if (cmd.startsWith('help')) {
      this.printHelp();
    } else if (cmd.startsWith('enq ')) {
      if (this.status.value === 'SUCCESS') {
        this.service.execute(this.verifyRecordId, cmd.slice(4).trim());
      } else {
        this.terminal.println(this.i18n('Unable to enqueue at this moment.'));
      }
    } else if (cmd === 'clear') {
      this.terminal.clear();
    } else if (cmd === 'stop') {
      this.service.disconnect(this.verifyRecordId);
    } else if (cmd === 'reload') {
      this.stopSession().then(() => this.startSession());
    } else {
      this.terminal.println(this.i18n('Invalid command: {{cmd}}', { cmd: cmd }));
      this.printHelp();
    }
  }

  printHelp() {
    this.terminal.println('Usage:');
    this.terminal.println('\tenq <tuple>  - enqueue a CSV format tuple.');
    this.terminal.println('\tclear        - clear the terminal.');
    this.terminal.println('\tstop         - disconnect current session and stop the container.');
    this.terminal.println('\treload       - restart a new session and reload the artifact.');
    this.terminal.println('\thelp         - print the usage infomation.');
  }

  startSession() {
    Promise.resolve()
      .then(() => {
        this.status.next('INIT');
        this.terminal.disablePrompt();
        return this.service.connect(this.config);
      })
      .then(record => {
        if (record) {
          this.verifyRecordId = record.verifyRecordId;
          this.terminal.println(record.responseMessage);
          this.websocket.messages().pipe(
            map(msg => <VerifyResponseRecord>JSON.parse(msg.payload)),
            filter(response => response.verifyRecordId === this.verifyRecordId),
            takeUntil(this.status.pipe(filter(status => status === 'TERMINATED'))))
            .subscribe(response => {
              this.terminal.println(response.responseMessage);
              if (response.responseMessage.endsWith('All resources are loaded. The server is ready to accept inputs.')) {
                this.status.next('SUCCESS');
                this.terminal.enablePrompt();
              } else if (response.responseMessage.endsWith('Cleanup completed.')) {
                this.status.next('TERMINATED');
                this.terminal.enablePrompt();
              } else if (response.responseMessage.endsWith('Cleanup starts.')) {
                this.status.next('TERMINATING');
                this.terminal.disablePrompt();
              }
            });
        } else {
          this.status.next('TERMINATED');
          this.terminal.println(this.i18n('Fail to create session.'));
          this.terminal.enablePrompt();
        }
      });
  }

  stopSession(): Promise<any> {
    const val = this.status.value;
    if (val !== 'TERMINATED') {
      this.service.disconnect(this.verifyRecordId);
      return this.status.pipe(filter(status => status === 'TERMINATED'), take(1)).toPromise();
    } else {
      return Promise.resolve();
    }
  }

  ngOnDestroy() {
    if (this.verifyRecordId) {
      // this has to happen in next tick otherwise we see angular error from progress bar.
      observableTimer(0).toPromise().then(() => this.stopSession());
    }
  }
}
