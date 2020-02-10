import { OnDestroy } from '@angular/core';

import { Observable, Subject } from 'rxjs';

export abstract class TakeUntilDestroy implements OnDestroy {
  private subject = new Subject();
  get whenDestroyed(): Observable<{}> {
    return this.subject.asObservable();
  }
  ngOnDestroy() {
    this.subject.next(true);
  }
}
