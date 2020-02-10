/**
 * @Author: Rahil Khera
 * @Date:   2017-08-29T13:46:46+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-29T13:46:55+05:30
 */

export class BEUsers {
  get userName(): string {
    return this._userName;
  }

  set userName(value: string) {
    this._userName = value;
  }

  get userPassword(): string {
    return this._userPassword;
  }

  set userPassword(value: string) {
    this._userPassword = value;
  }

  get roleName(): Array<string> {
    return this._roleName;
  }

  set roleName(value: Array<string>) {
    this._roleName = value;
  }

  get actionType(): string {
    return this._actionType;
  }

  set actionType(value: string) {
    this._actionType = value;
  }

  get newUser(): boolean {
    return this._newUser;
  }

  set newUser(value: boolean) {
    this._newUser = value;
  }
  private _userName: string;
  private _userPassword: string;
  private _roleName: Array<string>;
  private _actionType: string;
  private _newUser: boolean;

  constructor() {
    this.userName = '';
    this.userPassword = '';
    this.roleName = [];
  }

  public static getSaveJson(userList: Array<BEUsers>): any {
    const payload = {
      'request': {
        'data': {
          'aclSettings': {
            'aclSettingsItem': {
              'authEntries': {
                'authType': 'file',
                'authEntry': BEUsers.getAuthEntryJson(userList),
              }
            }
          }
        }
      }
    };
    return payload;
  }

  private static getRoleNameJson(roleNames: Array<string>): string {
    let roleName = '';
    for (let i = 0; i < roleNames.length; i++) {
      if ((i + 1) !== roleNames.length) {
        roleName += roleNames[i] + ',';
      } else {
        roleName += roleNames[i];
      }
    }
    return roleName;
  }

  private static getAuthEntryJson(userList: Array<BEUsers>): any[] {
    const authEntries = [];
    for (let i = 0; i < userList.length; i++) {
      const authEntry = {
        'userName': userList[i].userName,
        'userPassword': userList[i].userPassword,
        'roleName': BEUsers.getRoleNameJson(userList[i].roleName),
        'actionType': userList[i].actionType ? userList[i].actionType : undefined
      };
      authEntries.push(authEntry);
    }
    return authEntries;
  }
}
