import {
  async,
  TestBed
} from '@angular/core/testing';

import { TreeManager } from './tree-manager';
import { NodeType } from './tree-node';
import { TreeViewModule } from './tree-view.module';

describe('TreeManager', () => {
  let manager: TreeManager;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [TreeViewModule],
      providers: [TreeManager],
    });
    manager = TestBed.get(TreeManager);
  });

  it('should be able to create root nodes', () => {
    const root = manager.root('r1');
    expect(root.type).toBe(NodeType.ROOT);
  });

  it('should be able to create internal nodes', () => {
    const i = manager.internal('i1');
    expect(i.type).toBe(NodeType.INTERNAL);
  });

  it('should be able to create leaf nodes', () => {
    const l = manager.leaf('l1');
    expect(l.type).toBe(NodeType.LEAF);
  });

  it('should override previous node if create node with same id', () => {
    const l1 = manager.root('l1');
    const l2 = manager.root('l1');
    expect(l2).not.toBe(l1);
  });

  it('should be able to return the node by given the id', () => {
    const l1 = manager.root('l1');
    expect(manager.getNodeByKey('l1')).toBe(l1);
  });

  it('should be able to activate a node and keep a record', async(() => {
    const i = manager.internal('i1');
    manager.activateByKey(i.id).then(() => {
      expect(i.selected).toBe(true);
      expect(manager.getActivatedNode()).toBe(i);
    });
  }));

  it('should be able to activate the node by given its key', async(() => {
    const i = manager.internal('i1');
    manager.activateByKey('i1').then(() => {
      expect(i.selected).toBe(true);
      expect(manager.getActivatedNode()).toBe(i);
    });
  }));

  it('should be able to maintain the uniqueness of activeness', async(() => {
    const i = manager.internal('i1');
    manager.activateByKey('i1').then(() => {
      expect(i.selected).toBe(true);
      expect(manager.getActivatedNode()).toBe(i);
      const l = manager.internal('l1');
      return manager.activateByKey(l.id);
    }).then(() => {
      expect(manager.getActivatedNode().id).toBe('l1');
      expect(i.selected).toBe(false);
    });
  }));

  it('should be able to de-activate all nodes', async(() => {
    const i = manager.internal('i1');
    manager.activateByKey('i1').then(() => {
      expect(i.selected).toBe(true);
      expect(manager.getActivatedNode()).toBe(i);
      return manager.activateByKey(null);
    }).then(() => {
      expect(manager.getActivatedNode()).toBeFalsy();
      return manager.activateByKey('non-existent');
    }).then(() => {
      expect(manager.getActivatedNode()).toBeFalsy();
    });
  }));
});
