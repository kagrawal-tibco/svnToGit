import { Injectable } from '@angular/core';

declare var Terminal;

@Injectable()
export class TerminalService {
  makeTerminal(rows?: number) {
    const xterm = new Terminal({
      cursorBlink: true,
      rows: rows,
    });
    return xterm;
  }
}
