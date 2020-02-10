/**
 * @Author: Rahil Khera
 * @Date:   2017-08-29T16:26:40+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-29T16:26:48+05:30
 */
import { I18n } from '@ngx-translate/i18n-polyfill';

import { BEActions } from './actions-be.modal';

export class BEPermissions {

  get text(): string {
    return this._text;
  }

  set text(value: string) {
    this._text = value;
  }

  get type(): string {
    return this._type;
  }

  set type(value: string) {
    this._type = value;
  }

  get resourceref(): string {
    return this._resourceref;
  }

  set resourceref(value: string) {
    this._resourceref = value;
  }

  set action(value: Array<BEActions>) {
    this._action = value;
  }

  get action(): BEActions[] {
    return this._action;
  }

  public static get resourceText(): Map<string, string> {
    const resourceTextMap = new Map<string, string>();
    resourceTextMap.set('PRJ', 'PROJECT');
    resourceTextMap.set('C', 'CONCEPT');
    resourceTextMap.set('SC', 'SCORECARD');
    resourceTextMap.set('DM', 'DOMAIN');
    resourceTextMap.set('RU', 'RULE');
    resourceTextMap.set('E', 'EVENT');
    resourceTextMap.set('CN', 'CHANNEL');
    resourceTextMap.set('RT', 'RULETEMPLATE');
    resourceTextMap.set('RTV', 'RULETEMPLATEVIEW');
    resourceTextMap.set('RTI', 'RULETEMPLATEINSTANCE');
    resourceTextMap.set('RF', 'RULEFUNCTION');
    resourceTextMap.set('TABLE', 'RULEFUNCTIONIMPL');
    return resourceTextMap;
  }

  public static get resoucePermissionMap(): Map<string, BEPermissions> {
    const resourceMap = new Map<string, BEPermissions>();

    const approveAction = new BEActions('approval', 'DENY', BEActions.i18n);
    const commitAction = new BEActions('commit', 'DENY', BEActions.i18n);
    const checkoutAction = new BEActions('checkout', 'DENY', BEActions.i18n);
    const genDeployAction = new BEActions('gen_deploy', 'DENY', BEActions.i18n);
    const manageLocksAction = new BEActions('manage_locks', 'DENY', BEActions.i18n);
    const addImplAction = new BEActions('add_impl', 'DENY', BEActions.i18n);
    const delImplAction = new BEActions('del_impl', 'DENY', BEActions.i18n);
    const addInstAction = new BEActions('add_inst', 'DENY', BEActions.i18n);
    const delInstAction = new BEActions('del_inst', 'DENY', BEActions.i18n);
    const readAction = new BEActions('read', 'DENY', BEActions.i18n);

    const project = new BEPermissions(BEActions.i18n);
    project.type = 'PRJ';
    project.text = BEActions.i18n('Project');
    project.action = [approveAction, commitAction, checkoutAction, genDeployAction, manageLocksAction];
    resourceMap.set(project.type, project);

    const concept = new BEPermissions(BEActions.i18n);
    concept.type = 'C';
    concept.text = BEActions.i18n('Concept');
    concept.action = [readAction];
    resourceMap.set(concept.type, concept);

    const scorecard = new BEPermissions(BEActions.i18n);
    scorecard.type = 'SC';
    scorecard.text = BEActions.i18n('Scorecard');
    scorecard.action = [readAction];
    resourceMap.set(scorecard.type, scorecard);

    const domain = new BEPermissions(BEActions.i18n);
    domain.type = 'DM';
    domain.text = BEActions.i18n('Domain');
    domain.action = [readAction];
    resourceMap.set(domain.type, domain);

    const rule = new BEPermissions(BEActions.i18n);
    rule.type = 'RU';
    rule.text = BEActions.i18n('Rule');
    rule.action = [readAction];
    resourceMap.set(rule.type, rule);

    const event = new BEPermissions(BEActions.i18n);
    event.type = 'E';
    event.text = BEActions.i18n('Event');
    event.action = [readAction];
    resourceMap.set(event.type, event);

    const channel = new BEPermissions(BEActions.i18n);
    channel.type = 'CN';
    channel.text = BEActions.i18n('Channel');
    channel.action = [readAction];
    resourceMap.set(channel.type, channel);

    const ruletemplate = new BEPermissions(BEActions.i18n);
    ruletemplate.type = 'RT';
    ruletemplate.text = BEActions.i18n('Rule Template');
    ruletemplate.action = [readAction, addInstAction, delInstAction];
    resourceMap.set(ruletemplate.type, ruletemplate);

    const ruletemplateview = new BEPermissions(BEActions.i18n);
    ruletemplateview.type = 'RTV';
    ruletemplateview.text = BEActions.i18n('Rule Template View');
    ruletemplateview.action = [readAction];
    resourceMap.set(ruletemplateview.type, ruletemplateview);

    const ruletemplateinstance = new BEPermissions(BEActions.i18n);
    ruletemplateinstance.type = 'RTI';
    ruletemplateinstance.text = BEActions.i18n('Rule Template Instance');
    ruletemplateinstance.action = [readAction];
    resourceMap.set(ruletemplateinstance.type, ruletemplateinstance);

    const rulefunctionimpl = new BEPermissions(BEActions.i18n);
    rulefunctionimpl.type = 'TABLE';
    rulefunctionimpl.text = BEActions.i18n('Rule Function Implementation');
    rulefunctionimpl.action = [readAction];
    resourceMap.set(rulefunctionimpl.type, rulefunctionimpl);

    const rulefunction = new BEPermissions(BEActions.i18n);
    rulefunction.type = 'RF';
    rulefunction.text = BEActions.i18n('Rule Function');
    rulefunction.action = [readAction, addImplAction, delImplAction];
    resourceMap.set(rulefunction.type, rulefunction);

    const property = new BEPermissions(BEActions.i18n);
    property.type = 'RF';
    property.text = BEActions.i18n('Rule Function');
    property.action = [readAction];
    resourceMap.set(property.type, property);

    return resourceMap;
  }
  public static i18n;

  private _type: string;
  private _resourceref: string;
  private _action: Array<BEActions>;
  private _text: string;

  constructor(public i18n: I18n) {
    this.type = '';
    this.resourceref = '';
    this.action = [];
    this.text = '';
    BEPermissions.i18n = i18n;
  }

  public static getSaveJson(projectName: string, projectPermissions: Map<string, Map<string, Array<BEActions>>>): any {
    const payload = {
      'request': {
        'data': {
          'aclSettings': {
            'aclSettingsItem': {
              'projectName': projectName,
              'aclData': {
                'entries': {
                  'entry': BEPermissions.getEntryJson(projectPermissions)
                },
                'resources': {
                  'resource': [
                    {
                      'rid': 'PRJ',
                      'type': 'PROJECT'
                    },
                    {
                      'rid': 'C',
                      'type': 'CONCEPT'
                    },
                    {
                      'rid': 'SC',
                      'type': 'SCORECARD'
                    },
                    {
                      'rid': 'DM',
                      'type': 'DOMAIN'
                    },
                    {
                      'rid': 'RU',
                      'type': 'RULE'
                    },
                    {
                      'rid': 'E',
                      'type': 'EVENT'
                    },
                    {
                      'rid': 'CN',
                      'type': 'CHANNEL'
                    },
                    {
                      'rid': 'RT',
                      'type': 'RULETEMPLATE'
                    },
                    {
                      'rid': 'RTV',
                      'type': 'RULETEMPLATEVIEW'
                    },
                    {
                      'rid': 'RTI',
                      'type': 'RULETEMPLATEINSTANCE'
                    },
                    {
                      'rid': 'TABLE',
                      'type': 'RULEFUNCTIONIMPL'
                    },
                    {
                      'rid': 'PR',
                      'type': 'PROPERTY'
                    },
                    {
                      'rid': 'RF',
                      'type': 'RULEFUNCTION'
                    },
                    {
                      'rid': 'CF',
                      'type': 'CATALOGFUNCTION'
                    },
                    {
                      'rid': 'WSL',
                      'type': 'WSDL'
                    },
                    {
                      'rid': 'BEP',
                      'type': 'BEPROCESS'
                    }
                  ]
                }
              }
            }
          }
        }
      }
    };
    return payload;
  }

  private static getPermissionJson(permissions: Map<string, Array<BEActions>>): any[] {
    const permissionJson = [];
    for (const resourceref of Array.from(permissions.keys())) {
      const actions: Array<BEActions> = permissions.get(resourceref);
      for (let i = 0; i < actions.length; i++) {
        const permission = {
          'action': {
            'type': actions[i].type,
            'value': actions[i].value === 'ALLOW' ? 'ALLOW' : 'DENY',
          },
          'resourceref': resourceref
        };
        permissionJson.push(permission);
      }
    }
    return permissionJson;
  }

  private static getEntryJson(projectPermissions: Map<string, Map<string, Array<BEActions>>>): any[] {
    const entryJson = [];
    for (const roleName of Array.from(projectPermissions.keys())) {
      const singleEntry = {
        'permissions': {
          'permission': BEPermissions.getPermissionJson(projectPermissions.get(roleName))
        },
        'role': {
          'name': roleName
        }
      };
      entryJson.push(singleEntry);
    }
    return entryJson;
  }
}
