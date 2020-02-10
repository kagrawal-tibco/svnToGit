import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Router } from '@angular/router';

import { NgDragDropModule } from 'ng-drag-drop';
import { TooltipModule } from 'ngx-bootstrap';

import { ContentGroupComponent } from './content-group.component';
import { ContentGroupModule } from './content-group.module';
import { ContentPaneModule } from './content-pane/content-pane-module';

import { AppModule } from '../../app.module';
import { CoreModule } from '../../core/core.module';

describe('ContentGroupComponent', () => {

  let component: ContentGroupComponent;
  let fixture: ComponentFixture<ContentGroupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [ContentPaneModule, ContentGroupModule, CoreModule, AppModule, TooltipModule.forRoot(), NgDragDropModule.forRoot()],
      providers: [
        { provide: Router, useClass: class { navigate = jasmine.createSpy('navigate'); } }
      ],
      //      declarations: [ Draggable, Droppable ]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ContentGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
