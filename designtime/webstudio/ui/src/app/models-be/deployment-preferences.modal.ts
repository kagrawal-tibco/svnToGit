export class DeploymentPreferences {
  enable: boolean;
  host: string;
  port: string;
  userName: string;
  password: string;
  clusterName: string;
  agentName: string;
  oldName: string;
  name: string;
  inMemory: boolean;
  isAdded: boolean;
  projectName: string;
  oldObject: Object;

  constructor() {
    this.enable = true;
    this.host = '';
    this.port = '';
    this.userName = '';
    this.password = '';
    this.clusterName = '';
    this.agentName = '';
    this.oldName = '';
    this.name = '';
    this.inMemory = true;
    this.isAdded = false;
    this.projectName = '';
    this.oldObject = {
      enable: true,
      host: '',
      port: '',
      userName: '',
      password: '',
      clusterName: '',
      agentName: '',
      projectName: '',
      inMemory: true,
    };
  }

  get displayName(): string {
    return this.name + ' | ' + this.projectName;
  }
}
