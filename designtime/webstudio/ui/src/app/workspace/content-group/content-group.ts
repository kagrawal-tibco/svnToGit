import { Artifact } from '../../models/artifact';

export class ContentGroup {

  system: boolean;
  active = false;
  resources: Array<Artifact>;

  groupId: string;
  groupName: string;

  headerIcon: string;

  groupIcon: string;
  //  name: string;
  fileType: string;
  shared: boolean;

  constructor(system: boolean, groupId: string, name: string, headerIcon: string, groupIcon: string, fileType: string, shared: boolean) {
    this.system = system;
    this.groupId = groupId;
    //    this.id = groupId;
    this.groupName = name;
    this.headerIcon = headerIcon;
    this.groupIcon = groupIcon;
    this.fileType = fileType;
    this.shared = shared;
    this.resources = new Array<Artifact>();
  }
  addItem(item: Artifact) {
    const exItem = this.resources.find(it => item.id === it.id);
    if (!exItem) {
      this.resources.push(item);
    } else {
      this.resources.splice(this.resources.indexOf(exItem), 1, item);
    }
  }
  removeItem(item: Artifact) {
    const exItem = this.resources.find(it => item.id === it.id);
    if (exItem) {
      this.resources.splice(this.resources.indexOf(exItem), 1);
    }
  }
  replaceItem(item: Artifact, newItem: Artifact) {
    const exItem = this.resources.find(it => item.id === it.id);
    if (exItem) {
      this.resources.splice(this.resources.indexOf(exItem), 1, newItem);
    }
  }
  getItems(): Artifact[] {
    return this.resources;
  }
  getName() {
    return this.groupName;
  }
  clear() {
    this.resources = [];
  }

}
export class ProjectsGroup extends ContentGroup {
  projectToGroupMap: Map<string, Artifact> = new Map<string, Artifact>();
  idToArtifactMap: Map<string, Artifact> = new Map<string, Artifact>();

  constructor() {
    super(true, 'Projects', 'All Projects', 'fa ', 'fa', '', false);
  }
  addToGroups(projectId: string, resource: Artifact) {
    const projGroup = this.projectToGroupMap.get(projectId);
    if (!projGroup) {
      //      projGroup = manager.root(projectId);
      //      this.projectToGroupMap.set(projectId, projGroup);
    }
    if (resource.parentId) {
      this.idToArtifactMap.set(resource.parentId, resource);
    } else {
      this.idToArtifactMap.set(resource.id, resource);
    }
    this.addItem(resource);
  }
  getArtifactItemById(id: string) {
    return this.idToArtifactMap.get(id);
  }
  removeArtifactItemById(id: string) {
    const node = this.idToArtifactMap.get(id);
    if (node) {
      this.idToArtifactMap.delete(id);
      this.removeItem(node);
    }
  }
  getAllArtifacts() {
    return this.idToArtifactMap.values();
  }

}
