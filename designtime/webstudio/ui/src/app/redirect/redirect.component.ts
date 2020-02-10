
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { filter, take } from 'rxjs/operators';

import { AlertService, AlertType } from '../core/alert.service';
import { AuthStateService } from '../core/auth-state.service';
import { Logger } from '../core/logger.service';

@Component({
  selector: 'app-redirect',
  templateUrl: './redirect.component.html',
  styleUrls: ['./redirect.component.css']
})
export class RedirectComponent implements OnInit {

  public url: string;
  private msg: string;
  private msgType: string;

  constructor(
    private log: Logger,
    private authState: AuthStateService,
    private router: Router,
    private alert: AlertService,
    private route: ActivatedRoute
  ) {

  }

  ngOnInit() {
    this.url = this.route.snapshot.params['url'];
    this.msg = this.route.snapshot.queryParams['msg'];
    this.msgType = this.route.snapshot.queryParams['msgType'];
    if (this.msg && this.msgType) {
      this.alert.flash(this.msg, this.msgType as AlertType);
      console.log(this.msg);
    }
    this.authState.state.pipe(
      filter(state => state.action !== 'RESUME'),
      take(1))
      .toPromise()
      .then(state => {
        if (state.action === 'INIT') {
          if (this.url === 'workspace') {
            this.router.navigate(['/login']);
          } else {
            this.router.navigate(['/login/redirect/', this.url]);
          }
        } else if (state.action === 'SUCCESS') {
          this.router.navigateByUrl(this.url);
        }
      });
  }
}
