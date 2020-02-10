import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NgDragDropModule } from 'ng-drag-drop';
import { TooltipModule } from 'ngx-bootstrap';

import { ContentPaneModule } from './content-pane-module';
import { ContentPaneComponent } from './content-pane.component';

describe('ContentPaneComponent', () => {
  let component: ContentPaneComponent;
  let fixture: ComponentFixture<ContentPaneComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ContentPaneModule, TooltipModule.forRoot(), NgDragDropModule.forRoot()],
      //      declarations: [ ContentPaneComponent, ContentTreeComponent ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentPaneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
