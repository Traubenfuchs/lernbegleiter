import { BaseDto } from './../../../data/BaseDto';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuizRun } from 'src/app/data/quiz/QuizRun';

@Component({
  selector: 'app-quiz-runs',
  templateUrl: './quiz-runs.component.html',
  styleUrls: ['./quiz-runs.component.scss']
})
export class QuizRunsComponent {
  quizRuns: QuizRun[] = [];
  quizUUID = '';
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
    this.quizUUID = this.route.snapshot.paramMap.get("quizUUID");
    this.loadQuizRuns();
  }

  loadQuizRuns() {
    this.http.get<QuizRun[]>(`api/quiz/${this.quizUUID}/quiz-runs`)
      .subscribe(res => {
        res = res.sort((l, r) => BaseDto.getTsCreationAsNumber(l) > BaseDto.getTsCreationAsNumber(r) ? -1 : 1);
        this.quizRuns = res;
      });
  }
}
