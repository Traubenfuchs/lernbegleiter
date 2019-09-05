import { BaseDto } from './../../../data/BaseDto';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Quiz } from './../../../data/quiz/Quiz';

@Component({
  selector: 'app-quizes',
  templateUrl: './quizes.component.html',
  styleUrls: ['./quizes.component.scss']
})
export class QuizesComponent implements OnInit {
  quizzes: Quiz[] = []
  filterWord = ''
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.loadQuizzes()
  }

  loadQuizzes() {
    console.log('Loading Quizzes...')

    this.http.get<Quiz[]>(`api/quizzes`)
      .subscribe(res => {
        console.log('Quizzes loaded.')
        res.sort((l, r) => BaseDto.getTsUpdateAsNumber(l) > BaseDto.getTsUpdateAsNumber(r) ? -1 : 1)
        this.quizzes = res
      })
  }
}
