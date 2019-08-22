import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuizRun } from 'src/app/data/quiz/QuizRun';

@Component({
  selector: 'app-quiz-runs',
  templateUrl: './quiz-runs.component.html',
  styleUrls: ['./quiz-runs.component.scss']
})
export class QuizRunsComponent implements OnInit {
  quizRuns: QuizRun[] = []
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.loadQuizRuns()
  }

  loadQuizRuns() {
    console.log('Loading QuizRuns...')

    this.http.get<QuizRun[]>(`api/class/${this.uuid}/learning-modules`)
      .subscribe(res => {
        console.log('QuizRuns loaded.')
        this.quizRuns = res
      })
  }

}
