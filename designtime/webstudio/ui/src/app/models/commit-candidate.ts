import { ArtifactType } from './artifact';
export type CandidateStatus = 'ADDED' | 'MODIFIED' | 'DELETED' | 'CLEAN';

/**
 * Represents an entry in a commit record
 * If it's from committable API, then
 * id means the id of the CheckedOutArtifact which is about to be submitted for commit.
 *
 * Otherwise, if it's from the commit info API,
 * id means the committed ArtifactRevision id, mostly not useful
 * committedFrom represents the id of the CheckedOutArtifact which generated the
 * ArtifactRevision
 */
export interface CommitCandidate {
  id: string;
  name: string;
  parentId: string;
  parentRevision: number;
  stale: boolean;
  committedFrom: string;
  committed: boolean;
  status: CandidateStatus;
  type: ArtifactType;
  content: string;
  metadata: string;
  path: string;
  msg: string;
  selected: boolean;
  disposed?: boolean;
}
