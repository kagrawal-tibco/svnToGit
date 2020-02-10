export interface NavigableWizardContext {
  canNextStep: Promise<boolean>;
  canFinish: Promise<boolean>;
}
