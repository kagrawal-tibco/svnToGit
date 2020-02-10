import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Observable } from 'rxjs/Rx';

import { TerminalComponent } from './terminal.component';
import { TerminalService } from './terminal.service';

import { SharedModule } from '../shared/shared.module';

describe('Terminal Component', () => {
  let fixture: ComponentFixture<TerminalComponent>;
  let xterm: any;
  let keyIn: (p: { key: string, e: KeyboardEvent }) => void;
  let service: TerminalService;

  const keyPress = (s: string): number => {
    s.split('').map(c => {
      return {
        key: c,
        e: <KeyboardEvent>{
          preventDefault: () => { },
          stopPropagation: () => { },
          charCode: c.charCodeAt(0),
          keyCode: c.charCodeAt(0)
        }
      };
    }).forEach(v => keyIn(v));
    return s.length;
  };

  const backspace = (t: number): number => {
    Array(t).fill({}).map((_, i) => {
      return {
        key: 'we do not actually print the delete key',
        e: <KeyboardEvent>{
          preventDefault: () => { },
          stopPropagation: () => { },
          charCode: 8,
          keyCode: 8
        }
      };
    }).forEach(v => keyIn(v));
    return t;
  };

  const enter = (): number => {
    keyIn({
      key: '\r\n',
      e: <KeyboardEvent>{
        keyCode: 13,
        preventDefault: () => { },
        stopPropagation: () => { }
      }
    });
    return 1;
  };

  const move = (direction: string, t: number) => {
    let code: number;
    let key: string;
    switch (direction) {
      case 'left':
        key = '\x1B[D';
        code = 37;
        break;
      case 'up':
        key = '\x1B[A';
        code = 38;
        break;
      case 'right':
        key = '\x1B[C';
        code = 39;
        break;
      case 'down':
        key = '\x1B[B';
        code = 40;
        break;
      default:
        throw Error('Cannot recognize the direction: ' + direction);
    }
    Array(t).fill({}).map((_, i) => {
      return {
        key: key,
        e: <KeyboardEvent>{
          keyCode: code,
          charCode: 0,
          preventDefault: () => { },
          stopPropagation: () => { }
        }
      };
    }).forEach(v => keyIn(v));
    return t;
  };

  const raw = (s: string) => {
    // JSON helps us to escape invisible characters
    return JSON.stringify(s);
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TerminalComponent],
      imports: [SharedModule],
      providers: [
        TerminalService
      ]
    });

    service = TestBed.get(TerminalService);
    xterm = service.makeTerminal();

    spyOn(service, 'makeTerminal').and.returnValue(xterm);

    // extract the callBack to emulate key strokes.
    spyOn(xterm, 'on').and.callThrough().and.callFake((event, cb: (key: string, e: KeyboardEvent) => void) => {
      if (event === 'key') {
        keyIn = (pair: { key: string, e: KeyboardEvent }) => {
          cb(pair.key, pair.e);
        };
      }
    });

    fixture = TestBed.createComponent(TerminalComponent);
  });

  it('shall be able to init', () => {
    const openSpy = spyOn(xterm, 'open').and.callThrough();
    const linkifySpy = spyOn(xterm, 'linkify').and.callThrough();
    const fitSpy = spyOn(xterm, 'fit').and.callThrough();
    fixture.detectChanges();

    expect(openSpy.calls.count()).toBe(1);
    expect(linkifySpy.calls.count()).toBe(1);
    expect(fitSpy.calls.count()).toBe(1);
  });

  it('shall be able to enable prompt', () => {
    const writeSpy = spyOn(xterm, 'write');
    const setOptionSpy = spyOn(xterm, 'setOption');

    fixture.detectChanges();

    fixture.componentInstance.enablePrompt();
    expect(writeSpy.calls.count()).toBe(1);
    expect(setOptionSpy.calls.count()).toBe(1);
  });

  it('shall be able to disable prompt', () => {
    // extract the callBack to emulate key strokes.
    const writeSpy = spyOn(xterm, 'write');
    const setOptionSpy = spyOn(xterm, 'setOption');

    fixture.detectChanges();

    fixture.componentInstance.enablePrompt();
    expect(writeSpy.calls.count()).toBe(1);
    expect(setOptionSpy.calls.count()).toBe(1);

    fixture.componentInstance.disablePrompt();
    expect(writeSpy.calls.count()).toBe(4); // 1 initial + 3 for deletion
    expect(setOptionSpy.calls.count()).toBe(2);
  });

  describe('when prompt is enabled', () => {
    let writeSpy: jasmine.Spy;
    let promptln: Observable<string>;
    let writeBuffer: string;
    beforeEach(() => {
      writeBuffer = '';
      writeSpy = spyOn(xterm, 'write').and.callFake((v) => {
        writeBuffer += v;
      });
      fixture.detectChanges();
      promptln = fixture.componentInstance.promptln;
      fixture.componentInstance.enablePrompt();
    });

    it('shall be able to edit prompt buffer', (done) => {
      const hello = 'hello world';
      const n = Math.ceil(Math.random() * 100);
      let keyInCnt = 1; // the very first prompt
      promptln.take(n).toArray().toPromise().then(lines => {
        lines.forEach((l, i) => {
          expect(l).toBe(hello + i);
        });
        expect(writeSpy.calls.count()).toBe(keyInCnt);
        done();
      });
      Observable.range(0, n).delay(10).subscribe(i => {
        keyInCnt += keyPress(hello + i);
        keyInCnt += enter();
      });
    });

    it('shall be able to edit when the cursor is not trailing', (done) => {
      const hello = 'hello world';
      const gap = 1;
      promptln.take(1).toArray().toPromise().then(lines => {
        expect(lines[0]).toBe('hello worl1d');
        const e = '>>> hello world\x1B[D1d\x1B[1D\r\n>>> ';
        expect(raw(writeBuffer)).toBe(raw(e));
        done();
      });
      keyPress(hello);
      move('left', gap);
      keyPress('1');
      enter();
    });

    it('shall be able to delete when the cursor is not trailing', (done) => {
      const hello = 'hello world';
      const gap = 1;
      promptln.take(1).toArray().toPromise().then(lines => {
        expect(lines[0]).toBe('hello word');
        const e = '>>> hello world\x1B[D\x1B[1Dd \x1B[2D\r\n>>> ';
        expect(raw(writeBuffer)).toBe(raw(e));
        done();
      });
      keyPress(hello);
      move('left', gap);
      backspace(1);
      enter();
    });

    it('shall be able to clear the prompt', () => {
      const hello = 'hello';
      keyPress(hello);
      fixture.componentInstance.clearPrompt();
      const e = '>>> hello\x1B[5D     \x1B[5D';
      expect(raw(writeBuffer)).toBe(raw(e));
    });

    it('shall be able to clear the prompt when the cursor is not trailing', () => {
      const hello = 'hello';
      keyPress(hello);
      move('left', 1);
      fixture.componentInstance.clearPrompt();
      const e = '>>> hello\x1B[D\x1B[1C\x1B[5D     \x1B[5D';
      expect(raw(writeBuffer)).toBe(raw(e));
    });

    it('shall not delete the prompt when extra backspaces are pressed down', () => {
      const hello = 'hello';
      keyPress(hello);
      backspace(10);
      fixture.componentInstance.clearPrompt();
      const e = '>>> hello\x1B[1D \x1B[1D\x1B[1D \x1B[1D\x1B[1D \x1B[1D\x1B[1D \x1B[1D\x1B[1D \x1B[1D';
      expect(raw(writeBuffer)).toBe(raw(e));
    });

    it('shall always show the prompt at the bottom', (done) => {
      const hello = 'h';
      const world = 'w';
      keyPress(hello);
      fixture.componentInstance.println(world);
      const e = '>>> h\x1B[5D     \x1B[5Dw\r\n>>> h';
      expect(raw(writeBuffer)).toBe(raw(e));

      promptln.take(1).toArray().toPromise().then(lines => {
        const curr = '>>> h\x1B[5D     \x1B[5Dw\r\n>>> hw\r\n>>> ';
        expect(raw(writeBuffer)).toBe(raw(curr));
        expect(lines[0]).toBe(hello + world);
        done();
      });
      keyPress(world);
      enter();
    });

    it('shall be able to navigate input history', (done) => {
      const n = 100;
      const hello = 'hello world';
      Observable.range(0, n)
        .map(i => {
          keyPress(hello + i);
          enter();
        })
        .toPromise()
        .then(() => {
          move('up', 10);
          move('down', 5);
          enter();
        });
      promptln.take(n + 1).subscribe(last => {
        expect(last).toBe(hello + (n - 5));
        done();
      });
    });

    it('shall be able to clear prompt when move down to the head of input history', (done) => {
      const n = 100;
      const hello = 'hello world';
      // there shall only be n entries
      promptln.bufferTime(2000)
        .take(1)
        .subscribe(lines => {
          expect(lines.length).toBe(n);
          done();
        });

      Observable.range(0, n)
        .map(i => {
          keyPress(hello + i);
          enter();
        })
        .take(n)
        .toPromise()
        .then(() => {
          move('up', 10);
          // this shall clear the buffer
          move('down', 11);
          enter();
        });
    });
  });
});
