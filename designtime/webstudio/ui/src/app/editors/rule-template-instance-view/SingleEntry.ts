export class SingleEntry {
  private value = '';
  private description = '';

  constructor(val: string, desc: string) {
    this.value = val;
    this.description = desc;
  }

  public getValue(): string {
    return this.value;
  }

  public setValue(value: string) {
    if (value) {
      this.value = value;
    }
  }

  public getDescription(): string {
    return this.description;
  }

  public setDescription(value: string) {
    if (value) {
      this.description = value;
    }
  }
}
