import { CommitCandidate } from './commit-candidate';
import { User } from './user';

export class CommitStatus {
  static enumMap = new Map<string, CommitStatus>();

  static PENDING = new CommitStatus('Pending', 'PENDING');
  static APPROVED = new CommitStatus('Approved', 'APPROVED');
  static CANCELED = new CommitStatus('Canceled', 'CANCELLED');
  static REJECTED = new CommitStatus('Rejected', 'REJECTED');
  static STALE = new CommitStatus('Stale', 'STALE');

  /**
   * Shall not be called directly. Use CommitStatus.fromVal(val:string).
   */
  constructor(
    public name: string,
    public value: string
  ) {
    CommitStatus.enumMap.set(value, this);
  }

  static fromVal(val: string) {
    const found = CommitStatus.enumMap.get(val);
    if (found) {
      return found;
    } else {
      throw Error('unrecognizable commit status: ' + val);
    }
  }
}

export class Commit {
  id: string;
  committer: User;
  resolver?: User;
  resolutionComment?: string;
  message: string;
  projectName: string;
  changeList: CommitCandidate[];
  status: CommitStatus;
  commitTime: Date;
  resolveTime?: Date;
  version?: number;
}

export interface CommitResolution {
  status: boolean;
  comment?: string;
}
