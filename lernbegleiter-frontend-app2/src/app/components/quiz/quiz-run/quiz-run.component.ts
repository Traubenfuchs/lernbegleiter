import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UuidResponse } from 'src/app/data/UuidResponse';

import { Quiz } from './../../../data/quiz/Quiz';
import { QuizResult } from './../../../data/quiz/QuizResult';
import { QuizRun } from './../../../data/quiz/QuizRun';
import { QuizRunState } from './../../../data/quiz/QuizRunState';
import { LoginService } from './../../../services/login.service';

@Component({
  selector: 'app-quiz-run',
  templateUrl: './quiz-run.component.html',
  styleUrls: ['./quiz-run.component.scss']
})
export class QuizRunComponent implements OnInit, OnDestroy {
  uuid = ''
  quizUuid = ''
  quiz = new Quiz()
  quizAttemptUuid = ''
  quizResult = new QuizResult()
  quizRun = new QuizRun()
  _QuizRunState = QuizRunState
  intervalId: any
  timeLeftIntervalId: any
  timeLeft = 0

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute, public loginService: LoginService) {
    this.route.params.subscribe(params => {
      this.ngOnInit()
    });
  }

  ngOnDestroy(): void {
    console.log(`Clearing quiz-run interval<${this.intervalId}> ...`)
    clearInterval(this.intervalId)
    clearInterval(this.timeLeftIntervalId)
  }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("quizRunUUID")
    this.quizUuid = this.route.snapshot.paramMap.get("quizUUID")

    this.timeLeftIntervalId = setInterval(() => {
      if (!this.quizRun || !this.quizRun.nextTimeLimit) {
        this.timeLeft = 0
        return
      }
      const diff = Date.parse(this.quizRun.nextTimeLimit.toString()) - new Date().getTime()
      if (diff < 0) {
        this.timeLeft = 0
        return
      }

      this.timeLeft = Math.round(diff * 100) / 100000
    }, 100)

    if (this.uuid === 'new') {
      this.quizRun.quizRunType = 'FREE_ANSWERING'
      this.quizRun.uuid='Automatisch'
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
        this.intervalId = setInterval(() => {
          this.loadQuizRun()
          this.loadQuizResult()
        }, 1000);
      }

      this.loadQuizRun()
      this.loadQuizResult()
    }
  }

  loadQuiz() {
    console.log('Loading Quiz...')

    this.http.get<Quiz>(`api/quiz/${this.uuid}`)
      .subscribe(res => {
        console.log('Quiz loaded.')
        this.quiz = res
      })
  }

  saveClick() {
    console.log('Creating Quiz Run...')
    this.http.post<UuidResponse>(`api/quiz/${this.quizUuid}/quiz-run`, {})
      .subscribe(uuidResponse => {
        console.log('Created Quiz Run. Refreshing route.')
        this.router.navigate([`management/quiz/${this.quizUuid}/quiz-run/${uuidResponse.uuid}`])
      });
  }

  loadQuizResult() {
    if (!this.loginService.loggedInAndTeacherOrAdmin()) {
      return
    }
    console.log('Loading QuizResult...')
    this.http.get<QuizResult>(`api/quiz/${this.quizUuid}/quiz-run/${this.uuid}/quiz-result`)
      .subscribe(res => {
        res.entries = res.entries.sort((l, r) => l.points < r.points ? -1 : 1)
        console.log('Loaded QuizResult.')
        this.quizResult = res
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
    this.http.post<QuizRun>(`api/quiz-run/${this.uuid}/quiz-attempt/${this.quizAttemptUuid}:answer`, {
      quizAnswerUuid,
      correct
    }).subscribe(res => {
      this.quizRun = res
    })
  }
}
