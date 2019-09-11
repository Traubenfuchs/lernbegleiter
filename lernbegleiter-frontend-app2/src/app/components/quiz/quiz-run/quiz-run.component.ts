import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { QuizRun } from './../../../data/quiz/QuizRun';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-quiz-run',
  templateUrl: './quiz-run.component.html',
  styleUrls: ['./quiz-run.component.scss']
})
export class QuizRunComponent implements OnInit {
  uuid = ''
  quizUuid = ''
  quizRun = new QuizRun()
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("quizRunUUID")
    this.quizUuid = this.route.snapshot.paramMap.get("quizUUID")

    if (this.uuid === 'new') {

    } else {
      this.loadQuizRun()
    }
  }

  saveClick() {
    console.log('Creating Quiz Run...')
    this.http.post<UuidResponse>(`api/quiz/${this.quizUuid}/quiz-run`, {})
      .subscribe(uuidResponse => {
        console.log('Created Quiz Run. Refreshing route.')
        this.router.navigate([`management/quiz/${this.quizUuid}/quiz-run/${uuidResponse.uuid}`])
      });
  }

  loadQuizRun() {
    console.log('Loading QuizRun...')
    this.http.get<QuizRun>(`api/quiz-run/${this.uuid}`)
      .subscribe(res => {
        console.log('QuizRun loaded.')
        this.quizRun = res
      })
  }
}
