import { TreeNode } from './tree-node';

export enum EventType {
  RIGHT_CLICK,
  CLICK,
}

export class TreeViewEvent {
  constructor(
    private _type: EventType,
    private _node: TreeNode,
    public event?: MouseEvent
  ) { }

  get node(): TreeNode {
    return this._node;
  }

  get type(): EventType {
    return this._type;
  }

  static rightClick(node: TreeNode, e: MouseEvent) {
    return new TreeViewEvent(EventType.RIGHT_CLICK, node, e);
  }

  static singleClick(node: TreeNode, e: MouseEvent) {
    return new TreeViewEvent(EventType.CLICK, node, e);
  }

}
