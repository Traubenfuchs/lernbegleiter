import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitiveQuizTeacherComponent } from './competitive-quiz-teacher.component';

describe('CompetitiveQuizTeacherComponent', () => {
  let component: CompetitiveQuizTeacherComponent;
  let fixture: ComponentFixture<CompetitiveQuizTeacherComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompetitiveQuizTeacherComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitiveQuizTeacherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
