import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { EventEmitter } from '@angular/core';
import { Output } from '@angular/core';

@Component({
  selector: 'sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css', './nav-bar.component.css'],
  animations: [

    trigger('sidebarState', [
      state('restored', style({
        // paddingLeft: 12,
        opacity: 1,
        //    transform: 'translateX(0)',
      })),
      state('minimized', style({
        // paddingLeft: 8,
        opacity: 1,
        //  transform: 'translateX(-90%)',
      })),
      //      transition('minimized => restored', [
      //        animate('.75s', keyframes([
      //          style({
      //            visibility: 'restored',
      //            opacity: .8,
      //            transform: 'translateX(-95%)',
      //            offset: 0
      //          }),
      //          style({
      //            transform: 'translateX(0)',
      //            opacity: 1,
      //            offset: 1
      //          })
      //        ]))
      //      ]),
      //      transition('restored => minimized', [
      //        animate('.75s ease-out', keyframes([
      //          style({
      //            transform: 'translateX(0)',
      //            offset: 0
      //          }),
      //          // style({opacity: 1, transform: 'translateX(25px)', offset: .70}),
      //          style({
      //            opacity: .8,
      //            transform: 'translateX(-95%)',
      //            offset: 1
      //          })
      //        ]))
      //      ]),
    ]),
  ]
})
export class SidebarComponent implements OnInit {
  sidebarState = 'restored';
  @Output()
  minimized = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit() {
  }

  public toggleSidebarState() {
    this.sidebarState = this.sidebarState === 'minimized' ? 'restored' : 'minimized';
    this.minimized.emit(this.sidebarState === 'minimized');
  }

  get minimizedState() {
    return this.sidebarState === 'minimized';
  }

}
