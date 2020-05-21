import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { BaseDto } from './../../../data/BaseDto';
import { Quiz } from './../../../data/quiz/Quiz';

@Component({
  selector: 'app-quizes',
  templateUrl: './quizes.component.html',
  styleUrls: ['./quizes.component.scss']
})
export class QuizesComponent {
  quizzes: Quiz[] = [];
  displayedQuizzes: Quiz[] = [];
  _filterWord = '';
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
    this.loadQuizzes();
  }

  get filterWord(): string {
    return this._filterWord;
  }
  set filterWord(newFilterWord: string) {
    // this.valueChanged(this._filterWord, newFilterWord)
    this._filterWord = newFilterWord;
    this.filterQuizzes();
  }

  filterQuizzes() {
    const filterWords = this._filterWord.toUpperCase().split(" ");

    this.displayedQuizzes = this.quizzes.filter(q =>
      filterWords
        .every(ufw =>
          [q.author.familyName,
          q.author.firstName,
          q.author.email,
          q.name,
          q.description
          ].some(candidate => candidate.toUpperCase().indexOf(ufw) >= 0)
        )
    );
  }

  delete(quizUuid: string) {
    console.log('Quiz deleting.');
    this.http.delete<any>(`api/quiz/${quizUuid}`)
      .subscribe(res => {
        console.log('Quiz deleted.');
        this.loadQuizzes();
      });
  }

  loadQuizzes() {
    console.log('Loading Quizzes...');

    this.http.get<Quiz[]>(`api/quizzes`)
      .subscribe(res => {
        console.log('Quizzes loaded.');
        res.sort((l, r) => BaseDto.getTsUpdateAsNumber(l) > BaseDto.getTsUpdateAsNumber(r) ? -1 : 1);
        this.quizzes = res;
        this.filterQuizzes();
      });
  }
}
