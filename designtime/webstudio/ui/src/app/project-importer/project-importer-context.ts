import { Artifact } from '../models/artifact';
import { ScmRepositoryRecord } from '../models/dto';
import { ProjectMeta } from '../models/project-meta';
import { NavigableWizardContext } from '../navigable-wizard/navigable-wizard-context';
export interface ProjectImporterContext extends NavigableWizardContext {
  projectInfo: ProjectMeta;
  artifacts: Artifact[];
  existingProjectNames: string[];
  repos: ScmRepositoryRecord[];
}
