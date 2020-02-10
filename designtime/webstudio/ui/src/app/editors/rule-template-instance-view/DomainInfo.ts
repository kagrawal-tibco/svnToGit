/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:08:15+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-05-04T14:06:48+05:30
 */
import { SingleEntry } from './SingleEntry';

export class DomainInfo {

  private name: string = undefined;
  private folder: string = undefined;
  private description: string = undefined;
  private ownerProjectName: string = undefined;
  private type: string = undefined;
  private singleEntry: Array<SingleEntry> = new Array<SingleEntry>();

  constructor(type?: string) {
    if (type != null) {
      this.type = type;
    }
  }

  public getName(): string {
    return this.name;
  }

  public setName(value: string) {
    if (value != null) {
      this.name = value;
    }
  }
  public getFolder(): string {
    return this.folder;
  }

  public setFolder(value: string) {
    if (value != null) {
      this.folder = value;
    }
  }
  public getDescription(): string {
    return this.description;
  }

  public setDescription(value: string) {
    if (value != null) {
      this.description = value;
    }
  }

  public getOwnerProjectName(): string {
    return this.ownerProjectName;
  }

  public setOwnerProjectName(value: string) {
    if (value != null) {
      this.ownerProjectName = value;
    }
  }

  public getType(): string {
    return this.type;
  }

  public setType(type: string) {
    if (type != null) {
      this.type = type;
    }
  }

  public addValue(id: string, value: string): void {
    this.singleEntry.push(new SingleEntry(id, value));
  }

  public getValues(): Array<SingleEntry> {
    return this.singleEntry;
  }
}
