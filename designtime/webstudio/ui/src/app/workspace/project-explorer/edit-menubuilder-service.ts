import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as FileSaver from 'file-saver';
import * as JSZip from 'jszip';
import { of as observableOf, Observable } from 'rxjs';
import { map, mergeMap, toArray } from 'rxjs/operators';

import { AuditTrailMode } from 'app/audit-trail/audit-trail.component';
import { AuditTrailContext, AuditTrailModal } from 'app/audit-trail/audit-trail.modal';

import { DashboardService } from './../../dashboard/dashboard.service';
import { Context, ItemMap } from './context-menu.service';
import { LockArtifactModal, LockArtifactModalContext } from './lock-artifact.modal';
import { ProjectExplorerService } from './project-explorer.service';

import { environment } from '../../../environments/environment';
import { DeploymentCreationModal, DeploymentCreationModalContext } from '../../artifact-deployment/deployment-creation.modal';
import { DeploymentHistoryContext, DeploymentHistoryModal } from '../../artifact-deployment/deployment-history.modal';
import { DescriptorManageModal, DescriptorManageModalContext } from '../../artifact-deployment/descriptor-manage.modal';
import { ArtifactEditorService } from '../../artifact-editor/artifact-editor.service';
import { ArtifactProblemsService } from '../../artifact-editor/artifact-problems.service';
import { BERenameContext, BERenameModal } from '../../artifact-editor/rename-artifact.modal';
import { ArtifactImporterModal, ArtifactImporterModalContext } from '../../artifact-importer/artifact-importer.modal';
import { ArtifactInfoModal, ArtifactInfoModalContext } from '../../artifact-importer/artifact-info.modal';
import { ArtifactRevisionSelectorModal } from '../../artifact-revision-selector/artifact-revision-selector.modal';
import { CheckoutCommitComponent, CheckoutCommitContext } from '../../checkout-lifecycle/checkout-commit.component';
import { CheckoutExternalSyncComponent, CheckoutExternalSyncContext } from '../../checkout-lifecycle/checkout-external-sync.component';
import { CheckoutRevertComponent, CheckoutRevertContext } from '../../checkout-lifecycle/checkout-revert.component';
import { CheckoutSyncComponent, CheckoutSyncContext } from '../../checkout-lifecycle/checkout-sync.component';
import { AlertService, AlertType } from '../../core/alert.service';
import { ArtifactService, SynchronizeStrategy } from '../../core/artifact.service';
import { GroupService } from '../../core/group.service';
import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { RecordService } from '../../core/record.service';
import { RestService } from '../../core/rest.service';
import { SCMService } from '../../core/scm.service';
import { SettingsService } from '../../core/settings.service';
import { GenerateDeployableContext, GenerateDeployableModal } from '../../deployment/generate-deployable.modal';
import { ImportArtifactContext, ImportArtifactModal } from '../../editors/decision-table/import/import.modal';
import { EditorInterface } from '../../editors/editor-interface';
import { ProjectSummary } from '../../editors/project-summary/project-summary';
import { ArtifactsExportModal, ArtifactsExportModalContext } from '../../export/artifacts-export.modal';
import { ArtifactHistoryContext, ArtifactHistoryModal } from '../../history/artifact-history.modal';
import { ProjectHistoryContext, ProjectHistoryModal } from '../../history/project-history.modal';
import { DecisionTableSave } from '../../models-be/decision-table-be/decision-table-be-save';
import { BESettings } from '../../models-be/settings-be';
import { Artifact, ArtifactType } from '../../models/artifact';
import { Result } from '../../models/dto';
import { ProjectMeta } from '../../models/project-meta';
import { ProjectInfoModal, ProjectInfoModalContext } from '../../project-importer/project-info.modal';
import { SyncExternalModal } from '../../sync-external/sync-external.modal';
import { SynchronizeEditorContext, SynchronizeEditorModal } from '../../synchronize-editor/synchronize-editor.modal';
import { NodeType, TreeNode } from '../../widgets/tree-view/tree-node';
import { MultitabEditorService } from '../multitab-editor/multitab-editor.service';
import { WorkspaceService } from '../workspace.service';

@Injectable()
export class EditMenubuilderService {
  constructor(
    private project: ProjectService,
    private artifact: ArtifactService,
    private artifactEditorService: ArtifactEditorService,
    private modal: ModalService,
    private matDialog: MatDialog,
    private service: ProjectExplorerService,
    private settings: SettingsService,
    private alert: AlertService,
    private workspace: WorkspaceService,
    private dashboard: DashboardService,
    private rest: RestService,
    private multiTab: MultitabEditorService,
    private group1: GroupService,
    private record: RecordService,
    private scm: SCMService,
    private problems: ArtifactProblemsService,
    public i18n: I18n
  ) {

  }

  public buildItems(node?: any, editorContext?: boolean): Map<string, any> {
    const items = new Map<string, any>();
    if (node) {
      switch (node.type) {
        case NodeType.ROOT:
          if (!environment.enableBEUI) {
            if (!editorContext) {
              items.set('create', {
                name: this.i18n('New Artifact...'),
                accesskey: 'New',
                icon: 'fa-plus-square-o',
                callback: this.getOnNewArtifactHandler(node, ArtifactType.SB_DECISION_TABLE)
              });
            }
          }

          if (environment.enableBEUI) {
            items.set('delete', {
              name: this.i18n('Delete'),
              accesskey: 'Delete',
              icon: 'fa-trash-o',
              callback: this.getOnDeleteArtifactHandler(node),
            });
          }

          items.set('commit', {
            name: this.i18n('Commit...'),
            accesskey: 'Commit',
            icon: 'fa-share-alt',
            callback: this.getOnCommitHandler(node),
          });

          if (node.root.payload.type !== 'NATIVE' && !environment.enableBEUI) {
            items.set('pull', {
              name: this.i18n('Force pull'),
              accesskey: 'Force Pull',
              icon: 'fa-arrow-circle-down',
              callback: this.getOnForcePullHandler(node),
            });
          }
          items.set('sync', {
            name: this.i18n('Synchronize...'),
            accesskey: 'Synchronize',
            icon: 'fa-compress',
            callback: this.getOnSyncCheckoutHandler(node),
          });
          const menuItemName = (environment.enableBEUI) ? this.i18n('Revert') : this.i18n('Discard Local Changes');
          items.set('discard', {
            name: menuItemName,
            accesskey: 'Discard',
            callback: this.getOnRevertCheckoutHandler(node),
            icon: 'fa-times-circle-o',
          });

          if (environment.enableBEUI) {
            items.set('validate', {
              name: this.i18n('Validate'),
              accesskey: 'Validate',
              icon: 'fa-check',
              callback: environment.enableBEUI ? this.getOnBeProjectValidateHandler(node)
                : this.getOnValidateHandler(node),
            });
            items.set('audit-trail', {
              name: this.i18n('Audit Trail'),
              accesskey: 'AuditTrail',
              icon: 'fa-bars',
              callback: this.getAuditTrailHandler(node),
            });
            if (!environment.enableTCEUI) {
              items.set('sync-external', {
                name: this.i18n('Sync To Repository'),
                accesskey: 'SyncExternal',
                icon: 'fa-compress',
                callback: this.getOnSyncExternalCheckoutHandler(node),
              });
            }
            items.set('generate-deployable', {
              name: this.i18n('Generate Deployable '),
              accesskey: 'GenerateDeployable',
              icon: 'fa-server',
              callback: this.getOnGenerateDeployableHandler(node),
            });
            items.set('export', {
              name: this.i18n('Export'),
              accesskey: 'export',
              icon: 'fa-download',
              callback: this.getOnExportProjectHandler(node),
            });
            items.set('details', {
              name: this.i18n('Details'),
              accesskey: 'details',
              icon: 'fa-info-circle',
              callback: this.getOnProjectDetailsHandler(node),
            });
          } else {
            items.set('project-history', {
              name: this.i18n('History'),
              accesskey: 'History',
              icon: 'fa-history',
              callback: this.getOnProjectHistoryHandler(node),
            });
            items.set('more', {
              name: this.i18n('More'),
              accesskey: 'more',
              icon: 'fa-cogs',
              items: {
                'info': {
                  name: this.i18n('Info'),
                  accesskey: 'i',
                  icon: 'fa-info-circle',
                  callback: this.getOnProjectInfoHandler(node),
                },
                'download': {
                  name: this.i18n('Download'),
                  accesskey: 'download',
                  icon: 'fa-download',
                  callback: this.getOnDownloadProjectHandler(node),
                },
                'project-hide': {
                  name: this.i18n('Hide'),
                  accesskey: 'hide',
                  icon: 'fa-minus-square-o',
                  callback: this.getOnHideProjectHandler(node),
                }
              }
            });
          }
          break;
        case NodeType.INTERNAL:
          if (!editorContext) {
            items.set('create', {
              name: this.i18n('New Artifact...'),
              accesskey: 'New',
              icon: 'fa-plus-square-o',
              disabled: this.getDisableValue(),
              callback: this.getOnNewArtifactHandler(node, ArtifactType.SB_DECISION_TABLE)
            });
          }
          if (environment.enableBEUI) {
            items.set('delete', {
              name: this.i18n('Delete'),
              accesskey: 'Delete',
              icon: 'fa-trash-o',
              callback: this.getOnDeleteArtifactHandler(node),
            });
            items.set('commit', {
              name: this.i18n('Commit...'),
              accesskey: 'Commit',
              icon: 'fa-share-alt',
              callback: this.getOnCommitHandler(node)
            });
            items.set('sync', {
              name: this.i18n('Synchronize...'),
              accesskey: 'Synchronize',
              icon: 'fa-compress',
              callback: this.getOnSyncCheckoutHandler(node),
            });
            items.set('revert', {
              name: this.i18n('Revert'),
              accesskey: 'Revert',
              icon: 'fa-times-circle-o',
              callback: this.getOnRevertCheckoutHandler(node)
            });
          }
          break;
        case NodeType.LEAF:
          const artifact: Artifact = this.artifact.syncWithCache(node.payload);
          const checkIfAlreadyOpen = (editorInterface: EditorInterface) => {
            const buffer = this.artifactEditorService.getBuffer(artifact);
            return (buffer && buffer.editorInterface === editorInterface);
          };

          const showOptions = this.isManagableArtifact(artifact.type);

          if (artifact.type === ArtifactType.RULE_TEMPLATE) {
            items.set('create', {
              name: this.i18n('New Business Rule'),
              accesskey: 'New',
              icon: 'fa-plus-square-o',
              callback: this.getOnNewRTIHandler(node, ArtifactType.RULE_TEMPLATE_INSTANCE, true)
            });
          } else if (artifact.type === ArtifactType.RULE_FUNCTION) {
            items.set('create', {
              name: this.i18n('New Decision Table'),
              accesskey: 'New',
              icon: 'fa-plus-square-o',
              callback: this.getOnNewDTHandler(node, ArtifactType.BE_DECISION_TABLE, true)
            });
          } else {
            const openWithOptions: ItemMap = {
              'text-editor': {
                name: this.i18n('Text Editor'),
                accesskey: 'texteditor',
                icon: 'fa-file-text-o',
                disabled: checkIfAlreadyOpen(EditorInterface.TEXT),
                callback: this.getOnOpenWithHandler(node, EditorInterface.TEXT)
              },
              'metadata-editor': {
                name: this.i18n('Metadata Editor'),
                accesskey: 'metadataeditor',
                icon: 'fa-file-text-o',
                disabled: checkIfAlreadyOpen(EditorInterface.METADATA) || !artifact.type.supportMetadata,
                callback: this.getOnOpenWithHandler(node, EditorInterface.METADATA)
              }
            };
            switch (artifact.type) {
              case ArtifactType.SB_DECISION_TABLE:
                openWithOptions['sb-decision-table-editor'] = {
                  name: this.i18n('StreamBase Decision Table Editor'),
                  accesskey: 'sbdecisiontable',
                  icon: 'fa-table',
                  disabled: checkIfAlreadyOpen(EditorInterface.SB_DECISION_TABLE),
                  callback: this.getOnOpenWithHandler(node, EditorInterface.SB_DECISION_TABLE)
                };
                break;
            }
            if (editorContext && !environment.enableBEUI) {
              items.set('open-with', {
                name: this.i18n('Switch to'),
                accesskey: 'open',
                icon: 'fa-pencil-square-o',
                items: openWithOptions
              });
              // need this one since context menu items can't be empty
            } else if (!showOptions) {
              items.set('open-with', {
                name: this.i18n('Open with'),
                accesskey: 'open',
                icon: 'fa-pencil-square-o',
                disabled: !showOptions
              });
            }
          }

          if (artifact.isCheckedOutArtifact && showOptions) {
            items.set('delete', {
              name: this.i18n('Delete'),
              accesskey: 'Delete',
              icon: 'fa-trash-o',
              callback: this.getOnDeleteArtifactHandler(node),
            });
            if (!environment.enableBEUI) {
              items.set('discard', {
                name: this.i18n('Discard Local Changes'),
                accesskey: 'Discard',
                icon: 'fa-times-circle-o',
                callback: this.getOnDiscardCheckoutHandler(node),
              });
            }
            items.set('commit', {
              name: this.i18n('Commit...'),
              accesskey: 'Commit',
              icon: 'fa-share-alt',
              callback: this.getOnCommitHandler(node),
            });

            if (artifact.locked) {
              items.set('unlock', {
                name: this.i18n('Unlock'),
                accesskey: 'Unlock',
                icon: 'fa-unlock',
                callback: this.getOnUnlockHandler(node),
              });
            } else {
              if (environment.enableBEUI) {
                if (!environment.enableTCEUI) {
                  items.set('rename', {
                    name: this.i18n('Rename'),
                    accesskey: 'Rename',
                    icon: 'fa-times-circle-o',
                    callback: this.getOnRenameHandler(node),
                  });
                }
                items.set('sync', {
                  name: this.i18n('Synchronize...'),
                  accesskey: 'Synchronize',
                  icon: 'fa-compress',
                  callback: this.getOnSyncCheckoutHandler(node),
                });
                items.set('lock', {
                  name: this.i18n('Lock...'),
                  accesskey: 'Lock',
                  icon: 'fa-lock',
                  callback: this.getOnLockHandler(node),
                });
                items.set('revert', {
                  name: this.i18n('Revert'),
                  accesskey: 'Revert',
                  icon: 'fa-times-circle-o',
                  callback: this.getOnRevertCheckoutHandler(node),
                });
              } else {
                items.set('lock', {
                  name: this.i18n('Lock...'),
                  accesskey: 'Lock',
                  icon: 'fa-lock',
                  callback: this.getOnLockHandler(node),
                });
              }
            }
            if (!environment.enableBEUI) {
              items.set('replace', {
                name: this.i18n('Replace with'),
                accesskey: 'replace',
                icon: 'fa-files-o',
                items: {
                  'replace-with-file': {
                    name: this.i18n('Upload File...'),
                    icon: 'fa-upload',
                    accesskey: 'upload',
                    callback: this.getOnReplaceWithUploadFileHandler(node),
                  },
                  'replace-with-latest-revision': {
                    name: this.i18n('Latest Revision'),
                    icon: 'fa-undo',
                    accesskey: 'latest revision',
                    callback: this.getOnReplaceWithLatestRevisionHandler(node),
                  },
                  'replace-with-older-revision': {
                    name: this.i18n('Older Revision...'),
                    icon: 'fa-archive',
                    accesskey: 'older revision',
                    callback: this.getOnReplaceWithRevisionHandler(node),
                  }
                }
              });
            }
          } else {
            if (!environment.enableBEUI) {
              items.set('checkout', {
                name: this.i18n('Checkout'),
                accesskey: 'Checkout',
                icon: 'fa-code-fork',
                callback: this.getOnCheckoutHandler(node),
              });
              items.set('delete', {
                name: this.i18n('Delete'),
                accesskey: 'Delete',
                icon: 'fa-trash-o',
                callback: this.getOnDeleteArtifactHandler(node),
              });
            }
          }

          if (showOptions) {
            items.set('artifact-history', {
              name: this.i18n('History'),
              accesskey: 'History',
              icon: 'fa-history',
              callback: this.getOnArtifactHistoryHandler(node),
            });
          }

          if (environment.enableBEUI) {
            if (showOptions) {
              items.set('audit-trail', {
                name: this.i18n('Audit Trail'),
                accesskey: 'AuditTrail',
                icon: 'fa-history',
                callback: this.getAuditTrailHandler(node),
              });
            }
            if (artifact.type === ArtifactType.BE_DECISION_TABLE || artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE) {
              items.set('export', {
                name: this.i18n('Export'),
                accesskey: 'Export',
                icon: 'fa-download',
                callback: this.exportArtifact(node)
              });
            } else if (artifact.type === ArtifactType.RULE_FUNCTION) {
              items.set('import', {
                name: this.i18n('Import'),
                accesskey: 'Import',
                icon: 'fa-upload',
                callback: this.importArtifact(node)
              });
            }
          }

          if ((!artifact.isCheckedOutArtifact || artifact.parentId) && !environment.enableBEUI) {
            items.set('deployment', {
              name: this.i18n('Deployment'),
              accesskey: 'd',
              icon: 'fa-server',
              items: {
                'deploy': {
                  name: this.i18n('Deploy'),
                  accesskey: 'e',
                  icon: 'fa-rocket',
                  callback: this.getOnNewDeploymentHandler(node),
                },
                'deploy_history': {
                  name: this.i18n('Deployment History'),
                  accesskey: 't',
                  icon: 'fa-history',
                  callback: this.getOnDeploymentHistoryHandler(node),
                },
                'deploy_descriptor': {
                  name: this.i18n('Manage Descriptors'),
                  accesskey: 'Manage',
                  icon: 'fa-wrench',
                  callback: this.getOnManageDescriptorsHandler(node),
                }
              }
            });
          }

          if (artifact.needSync) {
            items.set('synchronize', {
              name: this.i18n('Synchronize...'),
              accesskey: 'Synchronize',
              icon: 'fa-compress',
              callback: this.getOnSynchronizeHandler(node),
            });
          }
          
          if (!environment.enableBEUI) {
            items.set('more', {
              name: this.i18n('More'),
              accesskey: 'more',
              icon: 'fa-cogs',
              items: {
                'info': {
                  name: this.i18n('Info'),
                  accesskey: 'i',
                  icon: 'fa-info-circle',
                  callback: this.getOnArtifactInfoHandler(node),
                },
                'download': {
                  name: this.i18n('Download'),
                  accesskey: 'download',
                  icon: 'fa-download',
                  callback: this.getOnDownloadArtifactHandler(node),
                }
              }
            });
          }
          break;
      }
    }
    return items;
  }

  // make public so that the content group view can re-use this context menu
  public getMenuBuilder(editorContext?: boolean) {
    return (ctx: Context): any => {
      let node = ctx.data;
      editorContext = false;
      if (node instanceof Artifact) {
        // get the corresponding TreeNode
        editorContext = true;
        let newNode = this.service.manager.getNodeByKey(node.id);
        if (!newNode) {
          // just dynamically create it so we can figure out what this artifact is
          newNode = this.service.manager.leaf(node.id);
          newNode.payload = ctx.data;
        }
        node = newNode;
      }

      // build the menu based on which kind of node was clicked
      const items = this.buildItems(node, editorContext);
      const itemMap: ItemMap = {};
      items.forEach((value: any, key: string) => {
        itemMap[key] = {
          name: value.name,
          accesskey: value.accesskey,
          callback: value.callback,
          disabled: value.disabled,
          icon: value.icon,
          className: value.className,
          items: value.items,
        };
      });
      return itemMap;
    };
  }

  getDisableValue(): boolean {
    if (environment.enableBEUI) {
      return true;
    } else {
      return false;
    }
  }

  isManagableArtifact(aType: ArtifactType): boolean {
    if (environment.enableBEUI) {
      if (aType === ArtifactType.BE_DECISION_TABLE ||
        aType === ArtifactType.RULE_TEMPLATE_INSTANCE ||
        aType === ArtifactType.DOMAIN_MODEL) {
        return true;
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

  public performUnlock(artifact: Artifact, force: boolean) {
    this.artifact.unlockArtifact(artifact, force)
      .then(resp => {
        const alertType: AlertType = (resp.success) ? 'success' : 'error';
        if (resp.success) {
          artifact.locked = false;
          const syncArt = this.artifact.syncWithCache(artifact);
          syncArt.locked = false;
          if (!environment.enableBEUI) { this.artifact.markAsModified(artifact, syncArt); }
        }
        this.alert.flash(resp.message, alertType);
        console.log(resp.message);
      });
  }

  createProjectSummary(record: any, project: string): string {
    const name = project;
    const description = '';
    const size = record.size;
    const artifactsCount = record.artifactsCount;
    const lastCheckoutBy = record.lastCheckoutBy;
    const lastCheckoutTime = record.lastCheckoutTime;
    const lastcommitBy = record.lastCommitBy;
    const lastCommitTime = record.lastCommitTime;
    const lastGenerateDeployableBy = record.lastGenerateDeployableBy;
    const lastGenerateDeployableTime = record.lastGenerateDeployableTime;
    const lastSyncBy = record.lastSyncBy;
    const lastSyncTime = record.lastSyncTime;
    const lastValidateBy = record.lastValidateBy;
    const lastValidateTime = record.lastValidateTime;
    const totalCommits = record.totalCommits;
    const totalApprovals = record.totalApprovals;
    const totalDeployments = record.totalDeployments;
    const totalRejections = record.totalRejections;
    const pSummary = new ProjectSummary(name, description, size, artifactsCount, lastCheckoutBy,
      lastCheckoutTime, lastcommitBy, lastCommitTime, lastGenerateDeployableBy, lastGenerateDeployableTime, lastSyncBy,
      lastSyncTime, lastValidateBy, lastValidateTime, totalCommits, totalApprovals, totalDeployments, totalRejections);
    return JSON.stringify(pSummary);
  }

  getOnRenameHandler(node: TreeNode) {
    return (key, opt) => {
      let artifact: Artifact;
      artifact = node.payload;

      Promise.resolve()
        .then(() => { return this.artifact.getCheckedOutArtifactWithContent(artifact.id); })
        .then((currentArtifact) => {
          if (currentArtifact) {
            return currentArtifact.content;
          }
        }).then((content) => {
          let implementPath = '';
          if (content) {
            const artifactContent = JSON.parse(content);
            if (artifactContent) {
              implementPath = artifactContent.implementsPath;
            }
            this.modal
              .open(BERenameModal, new BERenameContext(artifact, implementPath))
              .then((data: any) => {
                if (data.artifact) { artifact = data.artifact; }
              }, err => { if (err) { throw err; } });
          }
        });
    };
  }

  private getOnNewArtifactHandler(node: TreeNode, artifactType: ArtifactType, changeNode?: boolean) {
    return (key, opt) => {
      if (changeNode) {
        // let content = Promise.resolve().then(() => {
        //   return this.artifact.getCheckedOutArtifactWithContent(node.payload.id)
        // }).then(currentArtifact => {
        //     currentArtifact.content;
        //     if (currentArtifact.content)  {
        //       artifactType.isRTIView = true;
        //     }
        // });

        node = node.parent;
      }
      const root = node.root;
      const project: ProjectMeta = root.payload;
      const projectId = project.projectId;
      const projectName = project.projectName;

      const baseDir: string = node.path;
      this.project.getAllThenMerge(projectId)
        .then(artifacts => artifacts.map(a => a.path))
        .then(paths => this.modal.open(ArtifactImporterModal,
          new ArtifactImporterModalContext(projectId, projectName, baseDir, paths, artifactType)))
        .then((real: Artifact) => {
          if (real) {
            //            this.service.addArtifactToRoot(root, real);
            //            this.workspace.activateAll(real);
          }
        }, () => { });
    };
  }

  private getOnNewRTIHandler(node: TreeNode, artifactType: ArtifactType, changeNode?: boolean) {
    return (key, opt) => {
      Promise.resolve()
        .then(() => { if (node.payload) { return this.artifact.getCheckedOutArtifactWithContent(node.payload.id); } })
        .then((currentArtifact) => {
          if (currentArtifact) {
            return currentArtifact.content;
          }
        })
        .then((content) => {
          if (content && node.payload) {
            // parse, set missing items and stringify again
            const artifactContent = JSON.parse(content);
            let validRTView = true;
            if (artifactContent) {
              // common for both
              artifactContent.implementsPath = node.payload.id.split('@')[2];
              artifactContent.isSyncMerge = false;

              // specific for RTI builder -> newly created
              if (artifactContent.symbols) {
                const firstSymbol = artifactContent.symbols.symbolInfo[0];
                artifactContent.conditions = this.getTemplateFilterCondition(firstSymbol.symbolAlias, firstSymbol.type);
              } else if (artifactContent.view) {
                validRTView = !(artifactContent.view.htmlText === undefined
                  || artifactContent.view.htmlText === null
                  || artifactContent.view.htmlText.length === 0);
                if (validRTView) {
                  const html: string = artifactContent.view.htmlText;
                  if (html.trim().length === 0) {
                    validRTView = false;
                  }
                }
              }

              content = JSON.stringify(artifactContent);
            }
            const baseArtifactPath = (<Artifact>node.payload).path;
            if (changeNode) {
              node = node.parent;
            }

            if (validRTView) {
              const root = node.root;
              const project: ProjectMeta = root.payload;
              const projectId = project.projectId;
              const projectName = project.projectName;
              const baseDir: string = node.path;
              this.project.getCheckedOutArtifacts(projectId, true).toPromise()
                .then(artifacts => artifacts.map(a => a.path))
                .then(paths => this.modal.open(ArtifactImporterModal,
                  new ArtifactImporterModalContext(projectId, projectName, baseDir, paths, artifactType, content, baseArtifactPath)))
                .then((real: Artifact) => {
                  if (real) {
                    //                this.service.addArtifactToRoot(root, real);
                    //                this.workspace.activateAll(real);
                    if (real.locked == undefined) {
                      real.locked = false;
                    }
                    this.multiTab.activateTab(real);
                  }
                }, () => { });
            } else {
              this.alert.flash(this.i18n('Cannot create Rule Template Instance. No view defined in RT'), 'error');
            }
          }

        });
    };
  }

  private getOnNewDTHandler(node: TreeNode, artifactType: ArtifactType, changeNode?: boolean) {
    return (key, opt) => {
      Promise.resolve()
        .then(() => { if (node.payload) { return this.artifact.getCheckedOutArtifactWithContent(node.payload.id); } })
        .then((currentArtifact) => {
          if (currentArtifact) {
            return currentArtifact.content;
          }
        })
        .then((content) => {
          if (content && node.payload) {
            // parse, set missing items and stringify again
            const artifactContent = JSON.parse(content);
            if (artifactContent.isVirtual) {
              if (artifactContent) {
                artifactContent.implementsPath = node.payload.id.split('@')[2];
                artifactContent.isSyncMerge = false;
                content = DecisionTableSave.defaultDtSave(artifactContent);
              }

              if (changeNode) {
                node = node.parent;
              }

              const root = node.root;
              const project: ProjectMeta = root.payload;
              const projectId = project.projectId;
              const projectName = project.projectName;
              const baseDir: string = node.path;
              this.project.getCheckedOutArtifacts(projectId, true).toPromise()
                .then(artifacts => artifacts.map(a => a.path))
                .then(paths => this.modal.open(ArtifactImporterModal,
                  new ArtifactImporterModalContext(projectId, projectName, baseDir, paths, artifactType, content)))
                .then((real: Artifact) => {
                  if (real) {
                    //                this.service.addArtifactToRoot(root, real);
                    //                this.workspace.activateAll(real);
                    this.multiTab.activateTab(real);
                  }
                }, () => { });
            } else {
              this.alert.flash(this.i18n('{{name}}.rulefunction is not an virtual rule function.', { name: artifactContent.name }), 'error', true);
            }
          }
        });
    };
  }

  private getTemplateFilterCondition(symbolAlias: string, symbolType: string): any {
    // TODO - will need to provide configurative values for matchType and operator
    const defaultMatchType = (<BESettings>this.settings.latestSettings).defaultRTIFilterType;
    const conditionfilter = {
      filter: [
        {
          filterId: this.getUniqueId(),
          matchType: defaultMatchType,
          filter: [
            {
              link: [
                {
                  name: symbolAlias,
                  type: symbolType
                }
              ],
              filterId: this.getUniqueId(),
              operator: 'equals',
              value: {
                simple: ''
              }
            }
          ]
        }
      ],
      filterId: this.getUniqueId()
    };

    return conditionfilter;
  }

  private getUniqueId(): string {
    const dt = new Date();
    return '' + dt.getTime() + Math.round(Math.random() * 100) + Math.round(Math.random() * 100) + Math.round(Math.random() * 100);
  }

  private getOnProjectHistoryHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      this.project.getProjectCommits(project.projectId)
        .then(cmts => this.modal.open(ProjectHistoryModal, new ProjectHistoryContext(cmts))
          .then(() => { }, () => { }));
    };
  }

  private getOnGenerateDeployableHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      this.modal
        .open(GenerateDeployableModal, new GenerateDeployableContext(project.projectName))
        .then(() => { }, () => { });
    };
  }

  private getOnExportProjectHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      this.modal
        .open(ArtifactsExportModal, new ArtifactsExportModalContext(project.projectName, project.projectId))
        .then(() => { }, () => { });
    };
  }

  private getOnProjectDetailsHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      const artifact = new Artifact();
      artifact.id = 'Project@' + project.projectName + '@Summary';
      artifact.path = '/' + project.projectId;
      artifact.projectId = project.projectId;
      artifact.type = ArtifactType.PROJECT_SUMMARY;

      this.rest.get('projects/' + project.projectName + '/summary.json').pipe(
        map(res => {
          if (res.ok()) {
            artifact.content = this.createProjectSummary(res.record[0], project.projectName);
            this.multiTab.activateTab(artifact, EditorInterface.PROJECT_SUMMARY);
          }
        }))
        .toPromise();
    };
  }

  private getOnArtifactHistoryHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      this.artifact
        .getArtifactHistory(artifact)
        .then(res => this.modal.open(ArtifactHistoryModal, new ArtifactHistoryContext(res))
          .then(() => { }, () => { }));
    };
  }

  private getOnSynchronizeHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      if (artifact.type.supportSync) {
        this.artifactEditorService.promptIfDirty(artifact)
          .then(() => this.artifact.getCheckedOutArtifactWithContent(artifact.id))
          .then(updated => this.artifact.getArtifactRevisionWithContent(updated.parentId, updated.checkedOutFromRevision)
            .then(base => this.artifact.getArtifactRevisionWithContent(updated.parentId, updated.latestRevision)
              .then(lhs => this.modal.open(SynchronizeEditorModal, new SynchronizeEditorContext(base, lhs, updated)))))
          .then(() => { }, () => { });
      } else {
        this.modal.confirm()
          .message(this.i18n('This artifact\'s type does not support synchronization. \
          Would you like to discard your local changes and replace the local artifact with the latest revision from the server?'))
          .okBtn('Replace')
          .showClose(true)
          .open().result
          .then(
            (confirmed) => this.artifact.getArtifactLatest(artifact.parentId)
          ).then(
            (parent) => this.artifact.synchronize(parent, artifact, SynchronizeStrategy.LATEST, null)
          ).then(
            updated => {
              return updated;
            }
          ).catch(
            error => {
              if (error) {
                this.alert.flash(error.message, 'error', true);
              }
            }
          );
      }
    };
  }

  private getOnReplaceWithUploadFileHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      Promise.resolve()
        .then(() => {
          if (artifact.isCheckedOutArtifact) {
            return this.artifact.getCheckedOutArtifactWithContent(artifact.id);
          } else {
            throw Error(this.i18n('Cannot sync external to non-checked-out artifact.'));
          }
        })
        .then(updated => this.modal.open(SyncExternalModal, SyncExternalModal.context(updated)).then(() => { }, () => { }));
    };
  }

  private getOnReplaceWithRevisionHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      return Promise.resolve()
        .then(() => {
          if (artifact.status !== 'ADDED') {
            return this.artifact.getCheckedOutArtifactWithContent(artifact.id);
          } else {
            throw this.i18n('This newly added artifact does not have revision in the repository yet.');
          }
        })
        .then(
          updated => this.artifact.getArtifactHistory(updated)
            .then(history => this.modal.open(ArtifactRevisionSelectorModal,
              ArtifactRevisionSelectorModal.context(artifact.name, history)))
            .then((revision: number) => this.artifact.getArtifactRevisionWithContent(artifact.parentId, revision))
            .then(ar => {
              if (ar) {
                updated.metadata = ar.metadata;
                updated.content = ar.content;
                return this.artifact.updateCheckoutArtifact(updated);
              }
            })
            .then(() => this.artifact.markCheckoutAsRerender(updated), err => { if (err) { throw err; } }),
          err => this.alert.flash(err, 'warning'));
    };
  }

  private getOnReplaceWithLatestRevisionHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      Promise.resolve()
        .then(() => {
          if (artifact.status !== 'ADDED') {
            return this.artifact.getCheckedOutArtifactWithContent(artifact.id);
          } else {
            throw this.i18n('This newly added artifact does not have revision in the repository yet.');
          }
        })
        .then(updated => {
          this.artifact.getArtifactLatest(updated.parentId)
            .then(latest => {
              updated.content = latest.content;
              updated.metadata = latest.metadata;
              return this.artifact.updateCheckoutArtifact(updated);
            })
            .then(() => {
              this.artifact.markCheckoutAsRerender(updated);
            });
        }, err => this.alert.flash(err, 'warning'));
    };
  }

  private getOnDeleteArtifactHandler(node: TreeNode) {
    return (key, opt) => {
      let project: ProjectMeta = undefined;
      let artifact: Artifact = undefined;

      if (node.type === NodeType.ROOT) {
        project = node.payload;
      } else if (node.type === NodeType.INTERNAL) {
        artifact = new Artifact();
        artifact.path = node.id.substring(node.id.indexOf('/'), node.id.length);
        artifact.type = ArtifactType.FOLDER;
        artifact.projectId = this.getRootProjectNode(node).projectId;
      } else if (node.type === NodeType.LEAF) {
        artifact = node.payload;
      }

      const artifactOrProjectLabel = (project) ? 'project' : 'artifact';
      const artifactOrProjectName = (project) ? project.projectName : artifact.name;
      this.modal.confirm()
        .message(this.i18n('Click Confirm to delete {{0}} \'{{1}}\' from the workspace. You can recover the deletion if this {{2}} is already in the repository.', { 0: artifactOrProjectLabel, 1: artifactOrProjectName, 2: artifactOrProjectLabel }))
        .okBtn(this.i18n('Confirm'))
        .cancelBtn(this.i18n('Cancel'))
        .open().result
        .then(
          () => (project) ? this.project.deleteProject(project) : this.artifact.deleteArtifact([artifact]),
          () => {/*noop*/ }
        )
        .then(deleted => {
          if (deleted) {
            if (project) {
              this.artifact.markProjectAsDeleted(project);
              this.workspace.refresh();
              this.dashboard.refresh();
            } else {
              if (node.type === NodeType.INTERNAL) {
                node.children.forEach(element => {
                  this.artifact.markAsDeleted(element.payload);
                });
              } else {
                this.artifact.markAsDeleted(artifact);
              }
            }
          }
        }, err => this.alert.flash(err, 'warning'));
    };
  }

  private getOnRevertHandler(node: TreeNode) {
    return (key, opt) => {
      let artifact: Artifact = node.payload;
      this.artifact.getCheckedOutArtifactWithContent(artifact.id)
        .then(updated => artifact = updated)
        .then(updated =>
          this.modal.confirm()
            .message(this.i18n('You are reverting your local changes to artifact \'{{name}}\'. Click Confirm to proceed.', { name: artifact.name }))
            .open().result
            .then(
              () => this.artifact.revertArtifact([artifact.id]),
              () => { /* noop */ }))
        .then(ok => {
          if (ok) {
            // this.workspace.removeFromAll(artifact);
          }
          return ok;
        })
        .then(ok => {
          if (ok) {
            this.artifact.getCheckedOutArtifactWithContent(artifact.id)
              .then(parent => {
                this.artifact.markAsModified(artifact, parent);
                //                this.workspace.addArtifactToExplorer(parent);
              });
          }
        }, err => this.alert.flash(err, 'warning'));
    };
  }

  private getOnDiscardCheckoutHandler(node: TreeNode) {
    return (key, opt) => {
      let artifact: Artifact = node.payload;

      this.artifact.getCheckedOutArtifactWithContent(artifact.id)
        .then(updated => artifact = updated)
        .then(updated =>
          this.modal.confirm()
            .message(this.i18n('You are discarding your local changes to artifact {{name}}. Click Confirm to proceed.', { name: artifact.name }))
            .open().result
            .then(
              () => this.artifact.unCheckoutArtifact([artifact.id]),
              () => { /* noop */ }))
        .then(ok => {
          if (ok) {
            return new Promise((resolve, reject) => {
              if (artifact.checkedOutFromRevision) {
                this.artifact.getArtifactLatest(artifact.parentId)
                  .then(parent => {
                    if (parent.status !== 'DELETED') {
                      this.artifact.markAsModified(artifact, parent);
                    }
                    resolve(parent);
                  });
              } else {
                // artifact was not checked out from any revision, this effectively deletes the artifact
                this.artifact.markAsDeleted(artifact);
                resolve(artifact);
              }
            });
          }
          return undefined;
        }, err => {
          this.alert.flash(err, 'warning');
          return undefined;
        })
        .then(art => {
          if (art) {
            //            this.workspace.removeFromAll(artifact);
            if (art.locked) {
              // if this user owns the lock, prompt them to release the lock during discard
              if (environment.enableBEUI) {
                // unlock in BE context?
              } else {
                this.artifact.getUserLocks(true)
                  .then(res => {
                    const lockedArt = res.find(rec => rec.artifactId === artifact.parentId);
                    if (lockedArt) {
                      if (this.rest.userName === lockedArt.user) {
                        // this user owns the lock
                        this.modal.confirm()
                          .message(this.i18n('The discarded artifact {{name}} is currently locked by you. Do you also want to release the lock?', { name: artifact.name }))
                          .open().result
                          .then(
                            () => this.performUnlock(art, false),
                            () => { /* noop */ });
                      }
                    }
                  });
              }
            }
          }
        });
    };
  }

  private getOnHideProjectHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      this.settings.latestSettings.hideInExplorer[project.projectId] = true;
      this.settings.onSaveUISettings()
        .then(() => {
          this.multiTab.markToRemove(tab => {
            const a = tab.payload;
            return a.projectId === project.projectId;
          });
        });
    };
  }

  private importArtifact(node: TreeNode) {
    return (key, opt) => {
      Promise.resolve()
        .then(() => { return this.artifact.getCheckedOutArtifactWithContent(node.payload.id); })
        .then((currentArtifact) => {
          if (currentArtifact) {
            return currentArtifact.content;
          }
        })
        .then((content) => {
          if (content) {
            // parse, set missing items and stringify again
            const artifactContent = JSON.parse(content);
            if (artifactContent.isVirtual) {

              const artifact: Artifact = node.payload;
              const artifactItems = artifact.id.split('@');
              const a = this.group1.getGroupArtifacts('Projects', artifactItems[1]).toPromise()
                .then((checkedOutArtifactList: Artifact[]) => {
                  this.modal.open(ImportArtifactModal, new ImportArtifactContext(artifact.id, checkedOutArtifactList))
                    .then(() => { }, err => { if (err) { throw err; } });
                });

            } else {
              this.alert.flash(this.i18n('{{name}}.rulefunction is not an virtual rule function.', { name: artifactContent.name }), 'error', true);
            }
          }
        });
    };
  }

  private exportArtifact(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      this.artifact.export(artifact);
    };
  }

  private getOnRevertCheckoutHandler(node: TreeNode) {
    return (key, opt) => {
      let project: ProjectMeta = undefined;
      let artifactPath: string = undefined;

      if (node.type === NodeType.ROOT) {
        project = node.payload;
      } else if (node.type === NodeType.INTERNAL) {
        project = this.getRootProjectNode(node);
        artifactPath = node.id.substring(node.id.indexOf('/'), node.id.length);
      } else if (node.type === NodeType.LEAF) {
        project = this.project.getProjectMeta(node.payload.projectId);
        artifactPath = node.payload.path;
      }
      this.modal
        .open(CheckoutRevertComponent, new CheckoutRevertContext(project, artifactPath))
        .then(() => { }, () => { });
    };
  }

  private getRootProjectNode(node: TreeNode): ProjectMeta {
    while (!(node.payload instanceof ProjectMeta)) {
      node = node.parent;
    }
    return node.payload;
  }

  private getOnForcePullHandler(node: TreeNode) {
    return (key, opt) => {
      this.scm.forcePull((<ProjectMeta>node.root.payload).projectName);
    };
  }

  private getOnSyncCheckoutHandler(node: TreeNode) {
    return (key, opt) => {
      let project: ProjectMeta = undefined;
      let artifactPath: string = undefined;
      let artifactType: string = undefined;

      if (node.type === NodeType.ROOT) {
        project = node.payload;
      } else if (node.type === NodeType.INTERNAL) {
        project = this.getRootProjectNode(node);
        artifactPath = node.id.substring(node.id.indexOf('/'), node.id.length);
      } else if (node.type === NodeType.LEAF) {
        project = this.project.getProjectMeta(node.payload.projectId);
        artifactPath = node.payload.path;
        artifactType = (<ArtifactType>node.payload.type).defaultExtension;
      }
      this.modal
        .open(CheckoutSyncComponent, new CheckoutSyncContext(project, artifactPath, artifactType))
        .then(() => { }, () => { });
    };
  }

  private getOnValidateHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      this.project.validateProject(project.projectId)
        .then(validationResponse => {
          if (validationResponse) {
            let errorCnt = 0, warnCnt = 0;
            let errorMsgs = `<b>Errors,</b><ul>`, warnMsgs = `<b>Warnings,</b><ul>`;
            validationResponse.forEach(validationRecord => {
              this.record.recordToAnayzeResult(validationRecord.analyzeResult).forEach(problem => {
                if (problem.error) {
                  errorCnt++;
                  errorMsgs += '<li>' + problem.message + `</li>`;
                } else {
                  warnCnt++;
                  warnMsgs += '<li>' + problem.message + `</li>`;
                }
              });
            });
            errorMsgs += '</ul>';
            warnMsgs += '</ul>';

            let validationMsg;
            if (errorCnt > 0 || warnCnt > 0) {
              validationMsg = `Found <b>'${errorCnt}'</b> error's and <b>'${warnCnt}'</b> warning's.<br/><br/>`;
              if (errorCnt > 0) { validationMsg += errorMsgs; }
              if (warnCnt > 0) { validationMsg += warnMsgs; }
            } else {
              validationMsg = this.i18n('No errors or warning found.');
            }

            this.modal.alert()
              .message(validationMsg).open().result;
          }
        });
    };
  }

  private getOnBeProjectValidateHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      this.project.validateProject(project.projectId)
        .then(validationResponse => {
          if (validationResponse) {
            let errorCnt = 0, warnCnt = 0;
            const problems: Array<Result> = new Array<Result>();
            validationResponse.forEach(validationRecord => {
              this.record.recordToAnayzeResult(validationRecord.analyzeResult).forEach(problem => {
                problems.push(problem);
                if (problem.error) {
                  errorCnt++;
                } else {
                  warnCnt++;
                }
              });
            });
            if (problems) {
              const artifact: Artifact = new Artifact();
              artifact.projectId = project.projectId;
              artifact.analyzeResult = problems;
              this.problems.refresh(artifact, 'project');
              if (problems.length === 0) {
                this.alert.flash(this.i18n('No errors/warning found.'), 'success');
              } else {
                let errorCount = 0;
                let warningCount = 0;
                problems.forEach(r => {
                  if (r.error) {
                    errorCount++;
                  } else {
                    warningCount++;
                  }
                });

                this.alert.flash(this.i18n('There are {{errorCount}}  error(s) and {{warningCount}}  warning(s) found.', { errorCount: errorCount, warningCount: warningCount }), 'error', true);

              }
            }
          }
        });
    };
  }

  private getOnSyncExternalCheckoutHandler(node: TreeNode) {
    return (key, opt) => {
      const project: ProjectMeta = node.payload;
      this.modal
        .open(CheckoutExternalSyncComponent, new CheckoutExternalSyncContext(project))
        .then(() => { }, () => { });
    };
  }

  private getOnCommitHandler(node: TreeNode) {
    return (key, opt) => {
      let project: ProjectMeta = undefined;
      let artifactPath: string = undefined;

      if (node.type === NodeType.ROOT) {
        project = node.payload;
      } else if (node.type === NodeType.INTERNAL) {
        project = this.getRootProjectNode(node);
        artifactPath = node.id.substring(node.id.indexOf('/'), node.id.length);
      } else if (node.type === NodeType.LEAF) {
        project = this.project.getProjectMeta(node.payload.projectId);
        artifactPath = node.payload.path;
      }
      this.modal
        .open(CheckoutCommitComponent, new CheckoutCommitContext(project, artifactPath))
        .then(() => { }, () => { });
    };
  }

  private getOnLockHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      if (artifact) {
        if (!environment.enableBEUI) {
          this.modal.open(LockArtifactModal, new LockArtifactModalContext(artifact))
            .then(() => { }, err => { if (err) { throw err; } });
        } else {
          this.artifact.lockArtifact(artifact)
            .then(resp => {
              const alertType: AlertType = (resp.success) ? 'success' : 'error';
              if (resp.success) {
                artifact.locked = true;
                this.artifact.syncWithCache(artifact);
              }
              this.alert.flash(resp.message, alertType);
              console.log(resp.message);
            });
        }
      }
    };
  }

  private getOnUnlockHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      if (artifact) {
        if (environment.enableBEUI) {
          this.performUnlock(artifact, false);
        } else {
          this.artifact.getUserLocks(true)
            .then(res => {
              const lockedArt = res.find(rec => rec.artifactId === artifact.parentId);
              if (lockedArt) {
                if (this.rest.userName === lockedArt.user) {
                  // this user owns the lock
                  this.performUnlock(artifact, false);
                } else {
                  this.modal.confirm()
                    .message(this.i18n('This artifact is currently locked by user \'') + lockedArt.user + '. ' +
                      this.i18n('Do you want to force the release of the lock?'))
                    .open().result
                    .then(
                      () => this.performUnlock(artifact, true),  // user does not own lock
                      () => { /* noop */ })
                    .then(ok => {
                      return ok;
                    })
                    ;
                }
              } else {
                // couldn't find the lock, for now just try to unlock anyway
                this.performUnlock(artifact, false);
              }
            })
            .catch(err => this.alert.flash(err.message, 'error'));
        }
      }
    };
  }

  private getOnCheckoutHandler(node: TreeNode) {
    return (key, opt) => {
      const artifact: Artifact = node.payload;
      if (artifact && !artifact.isCheckedOutArtifact) {
        this.artifact.checkoutArtifact(artifact.id)
          .then(a => {
            this.artifact.markAsModified(artifact, a);
            this.workspace.activateAll(a);
            //            this.workspace.replaceAll(artifact, a)
            //              .then(() => this.workspace.activateAll(a));
          }).catch(
            error => { /* this.alert.flash(error.errorMessage, 'warning') */ }
          );
      } else {
        this.alert.flash(this.i18n('Artifact {{name}} is already checked-out, unable to checkout again.', { name: artifact.name }), 'warning');
      }
    };
  }

  private getOnNewDeploymentHandler(node: TreeNode) {
    const artifact: Artifact = this.artifact.syncWithCache(node.payload as Artifact);
    const project: ProjectMeta = node.root.payload;
    return (key, opt) => {
      this.modal.open(DeploymentCreationModal, new DeploymentCreationModalContext(project.projectName, artifact, this.i18n))
        .then(() => { }, err => {
          if (err) {
            throw err;
          }
        });
    };
  }

  private getOnDeploymentHistoryHandler(node: TreeNode) {
    const artifact: Artifact = this.artifact.syncWithCache(node.payload);
    return (key, opt) => {
      this.modal.open(DeploymentHistoryModal, new DeploymentHistoryContext(artifact.id))
        .then(() => { }, err => { if (err) { throw err; } });
    };
  }

  private getOnManageDescriptorsHandler(node: TreeNode) {
    const artifact: Artifact = this.artifact.syncWithCache(node.payload);
    const project: ProjectMeta = node.root.payload;
    return (key, opt) => {
      this.modal.open(DescriptorManageModal, new DescriptorManageModalContext(project.projectName, artifact))
        .then(() => { }, err => { if (err) { throw err; } });
    };
  }

  private getOnArtifactInfoHandler(node: TreeNode) {
    const artifact: Artifact = this.artifact.syncWithCache(node.payload);
    const project: ProjectMeta = node.root.payload;
    return (key, opt) => {
      this.modal.open(ArtifactInfoModal, new ArtifactInfoModalContext(project.projectName, artifact))
        .then(() => { }, err => { if (err) { throw err; } });
    };
  }

  private getOnDownloadArtifactHandler(node: TreeNode) {
    const artifact: Artifact = this.artifact.syncWithCache(node.payload);
    return (key, opt) => {
      Promise.resolve()
        .then(() => {
          if (artifact.isCheckedOutArtifact) {
            return this.artifact.getCheckedOutArtifactWithContent(artifact.id);
          } else {
            return this.artifact.getArtifactLatest(artifact.id);
          }
        })
        .then(updated => {
          const blob = updated.contentAsBlob;
          FileSaver.saveAs(blob, updated.name);
        });
    };
  }

  private getOnOpenWithHandler(node: TreeNode, editorInterface: EditorInterface) {
    const artifact: Artifact = this.artifact.syncWithCache(node.payload);
    return (key, opt) => {
      this.multiTab.activateTab(artifact, editorInterface)
        .then(() => this.workspace.activateAll(artifact));
    };
  }

  private getOnProjectInfoHandler(node: TreeNode) {
    const project: ProjectMeta = node.payload;
    return (key, opt) => {
      this.modal.open(ProjectInfoModal, new ProjectInfoModalContext(project))
        .then(() => { }, err => { if (err) { throw err; } });
    };
  }

  private getOnDownloadProjectHandler(node: TreeNode) {
    const project: ProjectMeta = node.payload;
    return (key, opt) => {
      this.project.getAllThenMerge(project.projectId)
        .then(artifacts => observableOf(...artifacts).pipe(mergeMap(a => {
          if (a.isCheckedOutArtifact) {
            return this.artifact.getCheckedOutArtifactWithContent(a.id);
          } else {
            return this.artifact.getArtifactLatest(a.id);
          }
        }), toArray()).toPromise())
        .then(artifacts => {
          const zip = new JSZip();
          artifacts.forEach(artifact => zip.file(
            artifact.path,
            artifact.content,
            {
              binary: artifact.isBinary, base64: artifact.isBinary
            }));
          return zip.generateAsync({ type: 'blob' });
        })
        .then(content => FileSaver.saveAs(content, `${project.projectName}.zip`));
    };
  }

  private getAuditTrailHandler(node: TreeNode) {
    return (key, opt) => {
      let project: string = undefined;
      let artifactPath: string = undefined;
      let artifactType: string = undefined;
      let mode: AuditTrailMode;

      if (node.type === NodeType.ROOT) {
        project = node.payload.projectName;
        mode = 'PROJECT';
      } else if (node.type === NodeType.LEAF) {
        project = this.project.getProjectMeta(node.payload.projectId).projectName;
        artifactPath = node.payload.path;
        artifactType = (<ArtifactType>node.payload.type).defaultExtension;
        mode = 'ARTIFACT';
      }

      const dialogRef = this.matDialog.open(AuditTrailModal, {
        width: '60vw',
        height: '60vh',
        panelClass: 'audit-trail-model',
        data: <AuditTrailContext>{
          projectName: project,
          artifactPath: artifactPath,
          artifactType: artifactType,
          mode: mode
        }
      });

      dialogRef.afterClosed().subscribe(result => {
        // this.animal = result;
      });
      // this.modal.open(AuditTrailModal, new AuditTrailContext(project, artifactPath, artifactType, mode))
      //   .then(() => {}, err => {if (err) {throw err; } });
    };
  }
}
