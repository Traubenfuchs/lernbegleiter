import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Quiz } from './../../../data/quiz/Quiz';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {
  uuid = ''
  quiz = new Quiz()
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("quizUUID")
    if (this.uuid === 'new') {

    } else {
      this.loadQuiz()
    }

  }

  loadQuiz() {
    console.log('Loading Quiz...')

    this.http.get<Quiz>(`api/quiz/${this.uuid}`)
      .subscribe(res => {
        console.log('Qiz loaded.')
        this.quiz = res
      })
  }

}
