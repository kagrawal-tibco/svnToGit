import { Artifact } from '../models/artifact';
import { NavigableWizardContext } from '../navigable-wizard/navigable-wizard-context';
export interface ArtifactImporterContext extends NavigableWizardContext {
  method: 'CREATE' | 'IMPORT';
  result: Artifact;
  existingArtifactPaths: string[];
  projectName: string;
  baseDir: string;
}
