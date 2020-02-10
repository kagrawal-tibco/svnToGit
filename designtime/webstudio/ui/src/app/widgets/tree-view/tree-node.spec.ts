import {
  async,
  TestBed
} from '@angular/core/testing';
import { Router, RouterModule } from '@angular/router';

import { TreeManager } from './tree-manager';
import { TreeViewModule } from './tree-view.module';

import { ArtifactService } from '../../core/artifact.service';
import { CoreModule } from '../../core/core.module';
import { RestService } from '../../core/rest.service';
let manager: TreeManager; // limitted use, just for node creation
let artService: ArtifactService;
describe('TreeNode', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [CoreModule, RouterModule, TreeViewModule],
      providers: [ArtifactService, RestService, TreeManager,
        { provide: Router, useClass: class { navigate = jasmine.createSpy('navigate'); } }],
    });
    artService = TestBed.get(ArtifactService);
    manager = TestBed.get(TreeManager);
  }));

  it('should be able to add children', () => {
    const root = manager.root('1');
    const i = manager.internal('i1');
    root.addChild(i);
    expect(root.children.length).toBe(1);

    const l = manager.internal('l1');
    root.addChild(l);
    expect(root.children.length).toBe(2);
  });

  it('should be able to reset children', () => {
    const root = manager.root('1');
    const i = manager.internal('i1');
    root.addChild(i);
    expect(root.children.length).toBe(1);

    const l = manager.internal('l1');
    root.addChild(l);
    expect(root.children.length).toBe(2);

    root.resetChildren();
    expect(root.children.length).toBe(0);

  });

  it('should be able to throw error when node with same text are siblings', () => {
    const root = manager.root('1');
    const i = manager.internal('i1');
    i.text = 'aaaaa';
    const l = manager.leaf('l1');
    l.text = 'aaaaa';
    try {
      root.addChild(i);
      root.addChild(l);
      fail('shall not reach here');
    } catch (e) {
      expect(e.message).toBe(`Error adding 'aaaaa'.  A duplicate node with the same name already exists in the folder '1'.  You might continue to encounter this error until the duplicate file has been removed/renamed`);
    }
  });

  it('should be able to order internal nodes before leaf nodes', () => {
    const root = manager.root('1');
    const i = manager.internal('i1');
    i.text = 'zzzzz';
    const l = manager.leaf('l1');
    l.text = 'aaaaa';
    // add i first then l
    root.addChild(i);
    root.addChild(l);
    expect(root.children[0]).toBe(i);
    expect(root.children[1]).toBe(l);
    // add l first then i
    root.resetChildren();
    root.addChild(l);
    root.addChild(i);
    expect(root.children[0]).toBe(i);
    expect(root.children[1]).toBe(l);
  });

  it('should be able to order nodes with same type alphabetically based on text', () => {
    const root = manager.root('1');
    const i1 = manager.internal('i1');
    const l1 = manager.leaf('l1');
    // add i first then l
    root.addChild(i1);
    root.addChild(l1);

    const i0 = manager.internal('i0');
    const i2 = manager.internal('i2');
    root.addChild(i2);
    root.addChild(i0);

    const l2 = manager.leaf('l2');
    const l0 = manager.leaf('l0');
    root.addChild(l0);
    root.addChild(l2);

    const expected = [i0, i1, i2, l0, l1, l2];
    expect(root.children.length).toBe(expected.length);
    root.children.forEach((n, i) => {
      expect(n).toBe(expected[i]);
    });
  });
});
