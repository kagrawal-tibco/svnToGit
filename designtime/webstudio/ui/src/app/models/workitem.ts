import { Commit } from './commit';
export class WorkListEntryStatus {
  static CONSTANTS = new Map<string, WorkListEntryStatus>();
  static RESOLVED = new WorkListEntryStatus('Resolved', 'RESOLVED');
  static UNRESOLVED = new WorkListEntryStatus('Unresolved', 'UNRESOLVED');

  constructor(
    public name: string,
    public value: string
  ) {
    WorkListEntryStatus.CONSTANTS.set(value, this);
  }

  static fromVal(val: string) {
    return WorkListEntryStatus.CONSTANTS.get(val);
  }

}

export interface WorkListEntry {
  id: string;
  title?: string;
  status?: WorkListEntryStatus;
  commit: Commit;
}

// Wrapper for pending commits to be displayed correctly in the commit-item-list.
export class CommitWorkListEntry implements WorkListEntry {
  id: string;
  commit: Commit;
}
