/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:00:58+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:02:29+05:30
 */

import { CommandImpl } from './CommandImpl';

export class ActionsImpl {

  private actions: Array<CommandImpl>;

  getActions(): Array<CommandImpl> {
    if (this.actions == null) {
      this.actions = new Array<CommandImpl>();
    }
    return this.actions;
  }

  setActions(value: Array<CommandImpl>) {
    if (value != null) {
      this.actions = value;
    }
  }

  addCommand(command: CommandImpl): void {
    if (this.actions.indexOf(command) === -1) {
      this.actions.push(command);
    }
  }

  removeCommand(command: CommandImpl): void {
    const index: number = this.actions.indexOf(command);
    if (index > -1) {
      this.actions.splice(index, 1);
    }
  }
}
