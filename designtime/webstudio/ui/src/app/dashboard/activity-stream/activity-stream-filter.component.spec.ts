import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActivityStreamFilterComponent } from './activity-stream-filter.component';

describe('ActivityStreamFilterComponent', () => {
  let component: ActivityStreamFilterComponent;
  let fixture: ComponentFixture<ActivityStreamFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ActivityStreamFilterComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActivityStreamFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
