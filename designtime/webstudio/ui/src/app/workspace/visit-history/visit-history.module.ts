import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { VisitHistoryComponent } from './visit-history.component';
import { VisitHistoryService } from './visit-history.service';

@NgModule({
  imports: [CommonModule],
  declarations: [VisitHistoryComponent],
  exports: [VisitHistoryComponent],
  providers: [VisitHistoryService]
})
export class VisitHistoryModule {

}
