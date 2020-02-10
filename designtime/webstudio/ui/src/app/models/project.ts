import { Artifact } from './artifact';
import { ProjectMeta } from './project-meta';

export class Project {

  constructor(
    private meta: ProjectMeta,
    private artifacts: Artifact[]
  ) {
  }

  getMeta() {
    return this.meta;
  }

  getArtifacts() {
    return this.artifacts;
  }
}
