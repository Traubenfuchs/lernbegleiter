import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CompetitiveQuizStudentComponent } from './competitive-quiz-student.component';

describe('CompetitiveQuizStudentComponent', () => {
  let component: CompetitiveQuizStudentComponent;
  let fixture: ComponentFixture<CompetitiveQuizStudentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CompetitiveQuizStudentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CompetitiveQuizStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
