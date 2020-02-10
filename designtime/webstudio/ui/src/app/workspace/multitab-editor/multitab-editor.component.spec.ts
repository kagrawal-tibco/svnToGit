import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MultitabEditorComponent } from './multitab-editor.component';
import { MultitabEditorService } from './multitab-editor.service';

import { CoreModule } from '../../core/core.module';
import { SharedModule } from '../../shared/shared.module';

describe('Multitab Editor', () => {
  let fixture: ComponentFixture<MultitabEditorComponent>;
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [CoreModule, SharedModule],
      providers: [MultitabEditorService],
      declarations: [MultitabEditorComponent]
    });
  });
});
