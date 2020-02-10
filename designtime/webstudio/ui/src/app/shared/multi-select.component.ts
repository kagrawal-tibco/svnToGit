import { Component, Input, } from '@angular/core';

export interface MultiSelectOption {
  text: string;
  classes: ('warning' | 'danger' | 'success' | 'disposed')[];
  checkable: boolean;
  enableCheckbox?: boolean;
  checked?: boolean;
  enableBtn?: boolean;
  btnText?: boolean;
  noBtnText?: string;
  btnOnClickHandler?: () => void;
}

@Component({
  selector: 'multi-select',
  templateUrl: './multi-select.component.html',
  styleUrls: ['./multi-select.component.css'],
})
export class MultiSelectComponent {
  @Input()
  title: string;
  @Input()
  options: MultiSelectOption[];
  @Input()
  emptyMsg: string;

  setClasses(opt: MultiSelectOption) {
    const classes = {
      'list-group-item': true
    };
    if (opt.classes) {
      opt.classes.map(clazz => {
        if (clazz === 'disposed') {
          return clazz;
        } else {
          return 'list-group-item-' + clazz;
        }
      }).forEach(clazz => classes[clazz] = true);
    }
    return classes;
  }

  onCheck(opt: MultiSelectOption) {
    if (opt.enableCheckbox && opt.checkable) {
      opt.checked = !opt.checked;
    }
  }

  onBtnClick(opt: MultiSelectOption) {
    if (opt.enableBtn && opt.btnOnClickHandler) {
      opt.btnOnClickHandler();
    }
  }
}
