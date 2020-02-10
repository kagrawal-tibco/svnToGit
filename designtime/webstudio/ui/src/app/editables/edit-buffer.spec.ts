import { EditBuffer } from './edit-buffer';

describe('EditBuffer', () => {
  let buffer: EditBuffer<string>;
  let content: string;
  describe('when init with read-only mode', () => {
    beforeEach(() => {
      buffer = EditBuffer.text();
      content = 'BUFFER';
      buffer.init(content, false);
    });
    it('shall not be editable', () => {
      expect(buffer.isEditable()).toBe(false);
      expect(buffer.getBuffer()).toBeFalsy();
      expect(buffer.getBase()).toBe(content);
      // and therefore it shall not be dirty;
      expect(buffer.isDirty()).toBe(false);
    });
  });
  describe('when init with read-write mode', () => {
    beforeEach(() => {
      buffer = EditBuffer.text();
      content = 'BUFFER';
      buffer.init(content, true);
    });
    it('shall be editable', () => {
      expect(buffer.isEditable()).toBe(true);
      expect(buffer.getBuffer()).toBe(content);
      expect(buffer.getBase()).toBe(content);
    });
    it('shall have base and buffer isolated', () => {
      buffer.replaceBuffer(content + content);
      expect(buffer.getBase()).toBe(content);
      expect(buffer.getBuffer()).not.toBe(content);
    });
    it('shall not be checked for dirtiness aggressively', () => {
      buffer.replaceBuffer(content + content);
      expect(buffer.isDirty()).toBe(false);
    });
    it('it shall be dirty after marked for dirty check', () => {
      buffer.replaceBuffer(content + content);
      buffer.markForDirtyCheck();
      expect(buffer.isDirty()).toBe(true);
    });
    describe('after it\'s modified', () => {
      beforeEach(() => {
        buffer.replaceBuffer(content + content);
        buffer.markForDirtyCheck();
        expect(buffer.isDirty()).toBe(true);
      });
      it('shall be clean after change the content back to original', () => {
        buffer.replaceBuffer(content);
        buffer.markForDirtyCheck();
        expect(buffer.isDirty()).toBe(false);
      });
      it('shall be clean after discarding changes and the buffer shall be the same as the base', () => {
        buffer.onDiscardChanges();
        expect(buffer.isDirty()).toBe(false);
        expect(buffer.getBase()).toBe(content);
        expect(buffer.getBuffer()).toBe(content);
      });
      it('shall be clean after saving and the base shall be the same as the buffer', () => {
        buffer.onSave();
        expect(buffer.isDirty()).toBe(false);
        expect(buffer.getBase()).toBe(content + content);
        expect(buffer.getBuffer()).toBe(content + content);
      });
      it('shall emit onRefresh event when discarding changes', (done) => {
        buffer.onRefresh().subscribe(ok => {
          expect(ok).toBe(true);
          done();
        });
        buffer.onDiscardChanges();
      });
      it('shall emit onRefresh event when markForRefresh', (done) => {
        buffer.onRefresh().subscribe(ok => {
          expect(ok).toBe(true);
          done();
        });
        buffer.markForRefresh();
      });
    });
  });

});
