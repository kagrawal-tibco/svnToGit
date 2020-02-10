import { Injectable } from '@angular/core';

@Injectable()
export class UIService {
  private _maxHeight: number;
  constructor() {

  }

  set maxHeight(h: number) {
    this._maxHeight = h;
  }

  get maxHeight() {
    return this._maxHeight;
  }
}
