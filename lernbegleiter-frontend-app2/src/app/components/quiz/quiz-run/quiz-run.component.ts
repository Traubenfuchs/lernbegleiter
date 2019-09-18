import { browser } from 'protractor';
import { QuizRunState } from './../../../data/quiz/QuizRunState';
import { QuizAnswer } from './../../../data/quiz/QuizAnswer';

import { QuizQuestion } from './../../../data/quiz/QuizQuestion';
import { LoginService } from './../../../services/login.service';

import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

import { QuizRun } from './../../../data/quiz/QuizRun';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-quiz-run',
  templateUrl: './quiz-run.component.html',
  styleUrls: ['./quiz-run.component.scss']
})
export class QuizRunComponent implements OnInit, OnDestroy {

  uuid = ''
  quizUuid = ''
  quizAttemptUuid = ''
  quizRun = new QuizRun()
  _QuizRunState = QuizRunState
  intervalId: any
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute, public loginService: LoginService) {
    this.route.params.subscribe(params => {
      this.ngOnInit()
    });

  }

  ngOnDestroy(): void {
    console.log(`Clearing interval<${this.intervalId}> ...`)
    clearInterval(this.intervalId)
  }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("quizRunUUID")
    this.quizUuid = this.route.snapshot.paramMap.get("quizUUID")

    if (this.uuid === 'new') {

    } else {
      if (this.loginService.loggedInAndStudent()) {
        console.log('Loading quizAttemptUuid...')
        this.http
          .post<UuidResponse>(`api/quiz-run/${this.uuid}/quiz-attempt:create-if-not-exists`, {})
          .subscribe(uuidResponse => {
            console.log('Loaded quizAttemptUuid.')
            this.quizAttemptUuid = uuidResponse.uuid;
          })
      }
      if (!this.intervalId) {
        this.intervalId = setInterval(() => this.loadQuizRun(), 1000);
      }

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
    this.http.get<QuizRun>(`api/quiz-run-${this.loginService.loggedInAndAdmin() ? 'admin' : 'student'}/${this.uuid}`)
      .subscribe(res => {
        console.log('QuizRun loaded.')
        this.quizRun = res

        if (QuizRunState.DONE.toString() === QuizRunState[this.quizRun.state].toString()) {
          clearInterval(this.intervalId)
        }
      })
  }

  advance() {
    console.log('Advancing quiz run...')
    this.http.post<QuizRun>(`api/quiz-run/${this.uuid}:advance`, {})
      .subscribe(res => {
        console.log('Advanced quiz run.')
        this.quizRun = res
      })
  }

  flipAnswerTo(quizAnswerUuid: string, correct: boolean) {
    console.log('Setting quiz answer...')
    this.http.post<any>(`api/quiz-attempt/${this.quizAttemptUuid}:answer`, {
      quizAnswerUuid,
      correct
    })
      .subscribe(res => {
        this.loadQuizRun()
      })
  }

}
