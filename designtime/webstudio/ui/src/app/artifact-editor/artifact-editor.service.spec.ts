import { async, TestBed } from '@angular/core/testing';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Subject } from 'rxjs/Rx';

import { ArtifactEditorModule } from './artifact-editor.module';
import { ArtifactEditorService } from './artifact-editor.service';

import { ArtifactService, StateChange } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { EditBuffer } from '../editables/edit-buffer';
import { EditorInterface } from '../editors/editor-interface';
import { Artifact, ArtifactType } from '../models/artifact';
import { APPLICANT_SIMPLE } from '../tests/data.spec';
describe('ArtifactEditorService', () => {
  const testSuite = (editorInterface: EditorInterface) => {
    describe(`when work with ${editorInterface.title}`, () => {
      let service: ArtifactEditorService;
      let modal: ModalService;
      let artifactService: ArtifactService;
      let artifact: Artifact;
      let stateChanges: Subject<StateChange>;
      const bufferModifier = (editBuffer: EditBuffer<any>) => {
        switch (editorInterface) {
          case EditorInterface.SB_DECISION_TABLE:
            editBuffer.getBuffer().createRuleWithAutoId();
            break;
          case EditorInterface.TEXT:
            editBuffer.replaceBuffer(editBuffer.getContent() + '-MODIFIED');
            break;
        }
        editBuffer.markForDirtyCheck();
      };

      beforeEach(async(() => {
        TestBed.configureTestingModule({
          imports: [ArtifactEditorModule],
          providers: [
            Logger,
            {
              provide: ArtifactService, useValue: {
                stateChanges: () => { },
                updateCheckoutArtifact: () => { },
              }
            },
            {
              provide: ModalService, useValue: {
                open: () => { }
              }
            }
          ]
        });

        stateChanges = new Subject<StateChange>();
        artifactService = TestBed.get(ArtifactService);
        expect(artifactService).toBeTruthy();
        spyOn(artifactService, 'stateChanges').and.returnValue(stateChanges);
        modal = TestBed.get(ModalService);
        service = TestBed.get(ArtifactEditorService);
        artifact = new Artifact();
        artifact.type = ArtifactType.SB_DECISION_TABLE;
        artifact.path = '';
        artifact.content = APPLICANT_SIMPLE;
      }));

      it('can make buffer with different mode', () => {
        const b1 = service.makeBuffer(artifact, editorInterface, true);
        const b2 = service.makeBuffer(artifact, editorInterface, false);
        expect(b1.editorInterface).toBe(b2.editorInterface);
        expect(b2.editorInterface).toBe(editorInterface);
        expect(b1.isEditable()).toBe(true);
        expect(b2.isEditable()).toBe(false);
        expect(b1.serialize()).toBe(artifact.content);
        expect(b2.serialize()).toBe(artifact.content);
        expect(b1.serialize()).not.toBe('');
      });

      it('can cache, retrieve, and clean the buffer for an artifact', () => {
        expect(service.getBuffer(artifact)).toBeFalsy();
        const b = service.makeBuffer(artifact, EditorInterface.SB_DECISION_TABLE, true);
        service.setBuffer(artifact, b);
        expect(service.getBuffer(artifact)).toBe(b);
        service.clearBuffer(artifact);
        expect(service.getBuffer(artifact)).toBeFalsy();
      });

      it('can override existing cached buffer', () => {
        expect(service.getBuffer(artifact)).toBeFalsy();
        const b = service.makeBuffer(artifact, EditorInterface.SB_DECISION_TABLE, true);
        service.setBuffer(artifact, b);
        expect(service.getBuffer(artifact)).toBe(b);
        const b1 = service.makeBuffer(artifact, EditorInterface.TEXT, true);
        service.setBuffer(artifact, b1);
        expect(service.getBuffer(artifact)).toBe(b1);
      });

      describe('shall be able to prompt...', () => {
        let buffer: EditBuffer<string>;

        beforeEach(() => {
          buffer = service.makeBuffer(artifact, editorInterface, true);
        });

        it('however, when cache is not dirty, it shall not prompt', () => {
          spyOn(modal, 'open').and.callFake(() => Promise.resolve(true));
          service.setBuffer(artifact, buffer);
          service.promptIfDirty(artifact);
          expect(modal.open).not.toHaveBeenCalled();
        });

        it('however, when cache is dirty but not cached, it shall not prompt', () => {
          spyOn(modal, 'open').and.callFake(() => Promise.resolve(true));
          bufferModifier(buffer);
          service.promptIfDirty(artifact);
          expect(modal.open).not.toHaveBeenCalled();
        });

        describe('when buffer is dirty and cached', () => {
          beforeEach(() => {
            service.setBuffer(artifact, buffer);
            bufferModifier(buffer);
          });

          it('shall prompt properly', () => {
            spyOn(modal, 'open').and.callFake(() => Promise.resolve(true));
            spyOn(artifactService, 'updateCheckoutArtifact').and.callFake(() => Promise.resolve(true));
            service.promptIfDirty(artifact);
            expect(modal.open).toHaveBeenCalledTimes(1);
          });

          it('shall do nothing when user cancel', async(() => {
            spyOn(modal, 'open').and.callFake(() => Promise.reject('cancelled'));
            spyOn(artifactService, 'updateCheckoutArtifact').and.callFake(() => Promise.resolve(true));
            service.promptIfDirty(artifact).then(saved => fail('shall not reach here'), cancelled => expect(cancelled).toBe('cancelled'));
            expect(modal.open).toHaveBeenCalledTimes(1);
            expect(artifactService.updateCheckoutArtifact).not.toHaveBeenCalled();
            expect(buffer.isDirty()).toBe(true);
          }));

          it('shall discard changes of the buffer when user choose to not save', async(() => {
            const base = buffer.getBase();
            expect(buffer.isDirty()).toBe(true);
            expect(base).not.toBe(buffer.getContent());
            spyOn(modal, 'open').and.callFake(() => Promise.resolve(false));
            spyOn(buffer, 'onDiscardChanges').and.callThrough();
            spyOn(artifactService, 'updateCheckoutArtifact').and.returnValue(Promise.resolve(true));
            service.promptIfDirty(artifact).then(saved => {
              expect(modal.open).toHaveBeenCalledTimes(1);
              expect(artifactService.updateCheckoutArtifact).not.toHaveBeenCalled();
              expect(buffer.onDiscardChanges).toHaveBeenCalledTimes(1);
              expect(buffer.isDirty()).toBe(false);
              expect(buffer.getContent()).toEqual(base);
              expect(saved).toBe(false);
            });
          }));

          describe('when user choose to save', () => {
            beforeEach(() => {
              spyOn(modal, 'open').and.returnValue(Promise.resolve(true));
            });

            it('shall update the buffer when the update operation succeeded in the server', async(() => {
              spyOn(artifactService, 'updateCheckoutArtifact').and.returnValue(Promise.resolve(true));
              spyOn(buffer, 'onSave').and.callThrough();
              const content = buffer.getContent();
              expect(buffer.getBase()).not.toBe(content);
              expect(artifact.content).not.toBe(content);
              service.promptIfDirty(artifact).then(saved => {
                expect(saved).toBe(true);
                expect(artifactService.updateCheckoutArtifact).toHaveBeenCalledTimes(1);
                expect(buffer.onSave).toHaveBeenCalledTimes(1);
                expect(buffer.isDirty()).toBe(false);
                expect(buffer.getBase()).toEqual(content);
                expect(artifact.content).toBe(buffer.serialize());
              });
            }));

            it('shall not update the buffer when the update operation failed in the server', async(() => {
              spyOn(artifactService, 'updateCheckoutArtifact').and.returnValue(Promise.resolve(false));
              spyOn(buffer, 'onSave').and.callThrough();
              const content = buffer.getContent();
              expect(buffer.getBase()).not.toBe(content);
              service.promptIfDirty(artifact).then(saved => {
                expect(saved).toBe(false);
                expect(artifactService.updateCheckoutArtifact).toHaveBeenCalledTimes(1);
                expect(buffer.onSave).not.toHaveBeenCalled();
                expect(buffer.isDirty()).toBe(true);
                expect(buffer.getBase()).not.toBe(content);
                expect(artifact.content).not.toBe(content);
                expect(buffer.getContent()).toBe(content);
              });
            }));
          });
        });
      });
    });
  };

  testSuite(EditorInterface.SB_DECISION_TABLE);
  testSuite(EditorInterface.TEXT);
});
