import { EditorInterface } from './editor-interface';
describe('EditorInterface', () => {
  it('shall have 2 interfaces in registry', () => {
    expect(EditorInterface.REGISTRY.length).toBe(7);
  });

  it('shall have DecisionTableEditor registered', () => {
    expect(EditorInterface.REGISTRY.indexOf(EditorInterface.SB_DECISION_TABLE)).not.toBe(-1);
  });

  it('shall have TextEditor registered', () => {
    expect(EditorInterface.REGISTRY.indexOf(EditorInterface.TEXT)).not.toBe(-1);
  });
});
