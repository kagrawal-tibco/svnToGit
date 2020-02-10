import { Artifact } from '../models/artifact';
import { DeploymentDescriptorItem, DeploymentDescriptorRecord } from '../models/dto';
import { NavigableWizardContext } from '../navigable-wizard/navigable-wizard-context';
export interface DeploymentCreationContext extends NavigableWizardContext {
  mode: 'CREATE' | 'SELECT';
  descriptor: DeploymentDescriptorItem;
  descriptors: DeploymentDescriptorRecord[];
  projectName: string;
  artifact: Artifact;
  revisions: number[];
  deployTime: Date;
  futureDeploy: boolean;
  showCalendar: boolean;
}
