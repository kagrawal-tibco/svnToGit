import {
  AfterViewInit,
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnDestroy,
  Output,
  SimpleChanges,
  ViewChild,
} from '@angular/core';

import { TerminalService } from './terminal.service';

export const PROMPT = '>>> ';
export const LINE_HEIGHT = 20;
export const HISTORY_SIZE = 63;

@Component({
  selector: 'terminal',
  templateUrl: './terminal.component.html'
})
export class TerminalComponent implements AfterViewInit, OnDestroy, OnChanges {
  @Input()
  printPrompt = true;

  @Input()
  rows: number;

  @Output()
  promptln = new EventEmitter<string>();

  @ViewChild('xterm', { static: false })
  private terminal: ElementRef;

  private xterm: any;

  private promptEnabled = false;
  private promptBuffer: string[] = [];
  private promptPos = 0;
  private history: string[] = [];
  private historyPointer = -1;

  constructor(
    private service: TerminalService
  ) {

  }

  public println(s: string) {
    const gap = this.promptBuffer.length - this.promptPos;
    if (this.promptEnabled) {
      this.terminalRight(gap);
      this.terminalDeleteMany(this.promptBuffer.length + PROMPT.length);
    }
    this.terminalWrite(s);
    this.terminalWrite('\r\n');
    if (this.promptEnabled) {
      this.terminalWrite(PROMPT + this.promptBuffer.join(''));
      this.terminalLeft(gap);
    }
  }

  public enablePrompt() {
    if (!this.promptEnabled) {
      this.xterm.setOption('cursorBlink', true);
      this.promptEnabled = true;
      this.promptBuffer = [];
      this.promptPos = 0;
      this.xterm.write(PROMPT);
    }
  }

  public disablePrompt() {
    if (this.promptEnabled) {
      this.xterm.setOption('cursorBlink', false);
      this.terminalDeleteMany(this.promptBuffer.length + PROMPT.length);
      this.promptBuffer = [];
      this.promptPos = 0;
      this.promptEnabled = false;
    }
  }

  public clearPrompt() {
    if (this.promptEnabled) {
      const n = this.promptBuffer.length;
      const gap = this.promptBuffer.length - this.promptPos;
      this.terminalRight(gap);
      this.terminalDeleteMany(n);
      this.promptBuffer = [];
      this.promptPos = 0;
    }
  }

  public clear() {
    this.xterm.clear();
  }

  ngOnDestroy() {
    if (this.xterm) {
      this.xterm.destroy();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['rows']) {
      if (this.xterm) {
        this.xterm.resize(this.xterm.cols, this.rows);
        this.xterm.fit();
      }
    }
  }

  ngAfterViewInit() {
    this.xterm = this.service.makeTerminal(this.rows);

    this.xterm.on('key', (key: string, ev: KeyboardEvent) => {
      this.onKey(key, ev);
    });

    this.xterm.open(this.terminal.nativeElement, true);
    this.xterm.linkify();
    this.xterm.fit();

    this.xterm.resize(this.xterm.cols, this.rows);

    /**
     * FIXME: This is a necessary hack until
     * https://github.com/sourcelair/xterm.js/issues/344 got fixed.
     * By then we shall use the xterm builtin paste listener
     * 
     */
    const ele: Element = this.xterm.textarea;
    ele.addEventListener('paste', (ev: ClipboardEvent) => {
      ev.preventDefault();
      ev.stopPropagation();
      const data = (ev.clipboardData.getData('text/plain'));
      this.syncWrite(data);
    });
  }

  private onKey(key: string, ev: KeyboardEvent) {
    ev.preventDefault();
    ev.stopPropagation();
    const printable = (
      !ev.altKey && !ev.ctrlKey && !ev.metaKey
    );
    if (this.promptEnabled) {
      if (ev.keyCode === 13) {
        this.newline();
      } else if (ev.keyCode === 8) {
        // Do not delete the prompt
        if (this.promptPos > 0) {
          const gap = this.promptBuffer.length - this.promptPos;
          this.terminalLeft(1);
          this.terminalWrite(this.promptBuffer.slice(this.promptPos, this.promptBuffer.length).join(''));
          this.terminalWrite(' ');
          this.terminalLeft(gap + 1);
          this.bufferDeleteOne();
        }
      } else if (printable) {
        if (ev.charCode === 0) {
          switch (ev.keyCode) {
            // left
            case 37:
              if (this.promptPos > 0) {
                this.promptPos--;
                this.terminalWrite(key);
              }
              return;
            // up
            case 38:
              if (this.history.length - 1 - this.historyPointer > 0) {
                this.clearPrompt();
                this.historyPointer++;
                const line = this.history[this.history.length - 1 - this.historyPointer];
                this.bufferWrite(line);
                this.terminalWrite(line);
              }
              return;
            // right
            case 39:
              if (this.promptPos < this.promptBuffer.length) {
                this.promptPos++;
                this.terminalWrite(key);
              }
              return;
            // down
            case 40:
              if (this.historyPointer > 0 && this.historyPointer <= this.history.length - 1) {
                this.clearPrompt();
                this.historyPointer--;
                const line = this.history[this.history.length - 1 - this.historyPointer];
                this.bufferWrite(line);
                this.terminalWrite(line);
              } else if (this.historyPointer === 0) {
                this.clearPrompt();
                this.historyPointer--;
              }
              return;
            default:
              // ignore others
              return;
          }
        } else {
          this.historyPointer = -1;
          this.syncWrite(key);
          return;
        }
      }
    }
  }

  private newline() {
    const line = this.promptBuffer.join('');
    const valid = line.trim() !== '';
    if (valid) {
      const idx = this.history.indexOf(line);
      if (idx >= 0) {
        // if found the same command, remove it from history
        // and then the history can no longer be full
        this.history.splice(idx, 1);
      } else if (this.history.length >= HISTORY_SIZE) {
        // otherwise if it's full, remove the oldest one
        this.history.shift();
      }
      // record the history
      this.history.push(line);
      this.historyPointer = -1;
    }
    // always print the new prompt and reset the buffer
    this.terminalWrite('\r\n' + PROMPT);
    this.promptBuffer = [];
    this.promptPos = 0;
    if (valid) {
      this.promptln.emit(line);
    }
  }

  private bufferDeleteOne() {
    if (this.promptPos > 0) {
      this.promptBuffer.splice(this.promptPos - 1, 1);
      this.promptPos--;
    }
  }

  private terminalDeleteMany(n: number) {
    this.terminalLeft(n);
    this.terminalWrite(' '.repeat(n));
    this.terminalLeft(n);
  }

  private terminalLeft(n) {
    if (n > 0) {
      this.xterm.write(`\x1B[${n}D`);
    }
  }

  private terminalRight(n) {
    if (n > 0) {
      this.xterm.write(`\x1B[${n}C`);
    }
  }

  private terminalWrite(s: string) {
    this.xterm.write(s);
  }

  private bufferWrite(s: string) {
    const chars = s.split('');
    this.promptBuffer.splice(this.promptPos, 0, ...chars);

    this.promptPos += chars.length;
  }

  private syncWrite(s: string) {
    this.bufferWrite(s);
    const gap = this.promptBuffer.length - this.promptPos;
    const toWrite = this.promptBuffer.slice(this.promptPos - s.length, this.promptBuffer.length).join('');
    this.terminalWrite(toWrite);
    this.terminalLeft(gap);
  }
}
