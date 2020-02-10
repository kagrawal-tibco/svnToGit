export class ResizableModal {
  public maximized = false;

  constructor(protected context: any, protected baseClasses: string) {
  }

  public toggleMaximize() {
    this.maximized = !this.maximized;
    if (this.maximized) {
      this.context.dialogClass = 'modal-dialog modal-maximize';
    } else {
      this.context.dialogClass = this.baseClasses;
    }
  }

}
