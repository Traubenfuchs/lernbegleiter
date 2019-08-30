import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { QuizRun } from './../../../data/quiz/QuizRun';

@Component({
  selector: 'app-quiz-run',
  templateUrl: './quiz-run.component.html',
  styleUrls: ['./quiz-run.component.scss']
})
export class QuizRunComponent implements OnInit {
  uuid = ''
  quizRun = new QuizRun()
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("quizRunUUID")
    if (this.uuid === 'new') {

    } else {
      this.loadQuizRun()
    }
  }

  loadQuizRun() {
    console.log('Loading QuizRun...')

    this.http.get<QuizRun>(`api/class/${this.uuid}/learning-modules`)
      .subscribe(res => {
        console.log('QuizRun loaded.')
        this.quizRun = res
      })
  }
}
