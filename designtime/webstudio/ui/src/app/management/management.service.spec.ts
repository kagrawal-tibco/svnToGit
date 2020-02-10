import { TestBed } from '@angular/core/testing';

import { ManagementService } from './management.service';

import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';

describe('ManagementService', () => {
  let service: ManagementService;

  beforeEach(() => {
    const log = {
      log: (any) => { }
    };
    const rest = {
      get: (any) => { },
      post: (any) => { }
    };
    TestBed.configureTestingModule({
      providers: [
        {
          provide: Logger,
          useValue: log
        },
        {
          provide: RestService,
          useValue: rest
        },
        ManagementService
      ]
    });
    service = TestBed.get(ManagementService);
  });

  it('can parse normal permission strings into Permission', () => {
    const perms = [
      'project:import',
      'project:import:ABC',
      'project:import:EDF',
      'artifact:read',
      'artifact:read:ABC',
      'artifact:read:EDF',
      'artifact:write:ABC',
      'artifact:write:EDF',
      'group:*',
    ];
    const permission = service.parseToPermission(perms);

    expect(permission['project']['import'].length).toBe(3);
    expect(permission['project']['import']).toEqual(['*', 'ABC', 'EDF']);

    expect(permission['artifact']['read'].length).toBe(3);
    expect(permission['artifact']['read']).toEqual(['*', 'ABC', 'EDF']);

    expect(permission['artifact']['write'].length).toBe(2);
    expect(permission['artifact']['write']).toEqual(['ABC', 'EDF']);

    Object.keys(service.getActionsForPermissionType('group')).forEach((act: string) => {
      expect(permission['group'][act]).toEqual(['*']);
    });

  });

  it('can parse wildcard permission strings into permission', () => {
    const perms = [
      '*'
    ];
    const permission = service.parseToPermission(perms);

    service.getAllPermissionTypes().forEach(type => {
      Object.keys(service.getActionsForPermissionType(type)).forEach(act => {
        expect(permission[type][act]).toEqual(['*']);
      });
    });
  });

  it('can parse partial wildcard permission strings into permission', () => {
    const perms = [
      'group'
    ];
    const permission = service.parseToPermission(perms);

    Object.keys(service.getActionsForPermissionType('group')).forEach((act: string) => {
      expect(permission['group'][act]).toEqual(['*']);
    });
  });

});
