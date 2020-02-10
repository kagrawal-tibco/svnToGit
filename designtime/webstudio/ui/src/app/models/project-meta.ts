import { ScmRepositoryRecord } from '../models/dto';
import { TreeNode } from '../widgets/tree-view/tree-node';
export class ProjectMeta {
  type: 'NATIVE' | 'SUBVERSION' | 'GIT';
  projectName: string;
  projectId: string;
  checkedOutArtifactIds: string[];
  checkoutId: string;
  scmParams?: {
    repo: ScmRepositoryRecord,
    root: TreeNode
  };

  get hasCheckedOutArtifact() {
    return this.checkedOutArtifactIds && this.checkedOutArtifactIds.length > 0;
  }
}
