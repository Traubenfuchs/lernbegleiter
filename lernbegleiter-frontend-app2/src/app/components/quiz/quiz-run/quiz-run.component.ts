import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UuidResponse } from 'src/app/data/UuidResponse';

import { Quiz } from './../../../data/quiz/Quiz';
import { QuizResult } from './../../../data/quiz/QuizResult';
import { QuizRun } from './../../../data/quiz/QuizRun';
import { QuizRunState } from './../../../data/quiz/QuizRunState';
import { LoginService } from './../../../services/login.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-quiz-run',
  templateUrl: './quiz-run.component.html',
  styleUrls: ['./quiz-run.component.scss']
})
export class QuizRunComponent implements OnInit, OnDestroy {
  destroyed = false;
  loadingQuizRun = false;
  loadingQuizResult = false;
  safeQr = new BehaviorSubject<SafeUrl>(null);
  fancyResults = !false;
  uuid = '';
  quizUuid = '';
  // quiz = new Quiz()
  quizAttemptUuid = '';
  quizResult = new QuizResult();
  quizRun = new QuizRun();
  _QuizRunState = QuizRunState;
  timeLeftIntervalId: any;
  timeLeft = '0';

  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    public loginService: LoginService,
    private sanitizer: DomSanitizer) {
    this.route.params.subscribe(params => {
      this.ngOnInit();
    });

    const loadQuizRun = () => {
      if (this.destroyed) {
        return;
      }
      const promise = this.loadQuizRun();
      if (promise) {
        promise.subscribe(null, null, () => setTimeout(loadQuizRun, 100));
      } else { setTimeout(loadQuizRun, 500); }
    };
    loadQuizRun();

    const loadQuizResult = () => {
      if (this.destroyed) {
        return;
      }
      const promise = this.loadQuizResult();
      if (promise) {
        promise.subscribe(null, null, () => setTimeout(loadQuizResult, 100));
      } else { setTimeout(loadQuizResult, 500); }
    };
    loadQuizResult();

    const updateTimer = () => {
      if (this.destroyed) {
        return;
      }
      setTimeout(updateTimer, 100);

      if (!this.quizRun || !this.quizRun.nextTimeLimit) {
        this.timeLeft = undefined;
        return;
      }
      const diff = new Date(this.quizRun.nextTimeLimit.toString()).getTime() - new Date().getTime();
      if (diff < 0) {
        this.timeLeft = undefined;
        return;
      }

      const timeLeftSecs = Math.round(diff * 100) / 100000;

      if (timeLeftSecs < 0) {
        this.timeLeft = undefined;
      } else if (Math.round(timeLeftSecs) < 120) {
        this.timeLeft = timeLeftSecs.toString();
      } else if (timeLeftSecs < 60 * 60) {
        this.timeLeft = `mehr als ${Math.floor(timeLeftSecs / 60)} Minuten`;
      } else if (timeLeftSecs < 60 * 60 * 24) {
        this.timeLeft = `mehr als ${Math.floor(timeLeftSecs / 60 / 60)} Stunden`;
      } else {
        this.timeLeft = `mehr als ${Math.floor(timeLeftSecs / 60 / 60 / 24)} Tage`;
      }
    };
    updateTimer();
  }

  ngOnDestroy(): void {
    console.log("quiz-run-component ngOnDestroy called...");
    this.destroyed = true;
  }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("quizRunUUID");
    this.quizUuid = this.route.snapshot.paramMap.get("quizUUID");
    this.quizResult = new QuizResult();
    this.quizRun = new QuizRun();


    if (this.uuid === 'new') {
      this.quizRun.quizRunType = 'FREE_ANSWERING';
      this.quizRun.uuid = 'Automatisch';
    } else {
      if (this.loginService.loggedInAndStudent()) {
        console.log('Loading quizAttemptUuid...');
        this.http
          .post<UuidResponse>(`api/quiz-run/${this.uuid}/quiz-attempt:create-if-not-exists`, {})
          .subscribe(uuidResponse => {
            console.log('Loaded quizAttemptUuid.');
            this.quizAttemptUuid = uuidResponse.uuid;
          });
      }
    }
  }

  /* loadQuiz() {
    console.log('Loading Quiz...')
    if (!this.quizUuid || this.quizUuid === '' || this.quizUuid === 'new') {
      console.log(`Not loading quiz because id is <${this.quizUuid}>...`)
      return
    }

    this.http.get<Quiz>(`api/quiz/${this.quizUuid}`)
      .subscribe(res => {
        console.log('Quiz loaded.')
        this.quiz = res
      })
  } */

  saveClick() {
    console.log('Creating Quiz Run...');

    let nextTimeLimit: string;
    try {
      nextTimeLimit = new Date(this.quizRun.nextTimeLimit).toISOString();
    } catch {
      // ignore
    }

    this.http.post<UuidResponse>(`api/quiz/${this.quizUuid}/quiz-run`, {
      quizRunType: this.quizRun.quizRunType,
      nextTimeLimit
    })
      .subscribe(uuidResponse => {
        console.log('Created Quiz Run. Refreshing route.');
        this.router.navigate([`management/quiz/${this.quizUuid}/quiz-run/${uuidResponse.uuid}`]);
      });
  }

  loadQuizResult() {
    if (!this.uuid || this.uuid === '' || this.uuid === 'new') {
      return;
    }
    if (!this.loginService.loggedInAndTeacherOrAdmin()) {
      return;
    }
    this.loadingQuizResult = true;
    console.log('Loading QuizResult...');
    const result = this.http.get<QuizResult>(`api/quiz/${this.quizUuid}/quiz-run/${this.uuid}/quiz-result`);
    result.subscribe(res => {
      res.entries = res.entries.sort((l, r) => l.points < r.points ? -1 : 1);

      const max = res.entries.map(v => v.weightedPoints).reduce((previous, current) => current > previous ? current : previous, 0);

      res.entries.forEach(v => v.heightPerc = "" + (v.weightedPoints * 250 / max) + 'px');
      res.entries.push(res.entries[0]);
      res.entries.push(res.entries[0]);
      res.entries.push(res.entries[0]);
      // res.entries.forEach(v => console.log('xxx ' + v.heightPerc * 10000));

      console.log('Loaded QuizResult.');
      this.quizResult = res;
    },
      err => {

      }, () => {
        this.loadingQuizResult = false;
      });

    return result;
  }

  loadQuizRun() {
    if (!this.uuid || this.uuid === '' || this.uuid === 'new') {
      return;
    }
    console.log('Loading QuizRun...');
    this.loadingQuizRun = true;
    const result = this.http.get<QuizRun>(`api/quiz-run-${this.loginService.loggedInAndAdmin() ? 'admin' : 'student'}/${this.uuid}`);
    result.subscribe(res => {
      console.log('QuizRun loaded.');
      this.quizRun = res;
      this.safeQr.next(this.sanitizer.bypassSecurityTrustUrl("data:image/png;base64, " + res.qrCodeAsBase64));
      // if (this.quizRun.nextTimeLimit && this.quizRun.nextTimeLimit.length > 15) {
      //   this.quizRun.nextTimeLimit = this.quizRun.nextTimeLimit.substring(0, 16)
      // }
    },
      err => {

      },
      () => {
        this.loadingQuizRun = false;
      });
    return result;
  }

  advance() {
    console.log('Advancing quiz run...');
    this.http.post<QuizRun>(`api/quiz-run/${this.uuid}:advance`, {})
      .subscribe(res => {
        console.log('Advanced quiz run.');
        this.quizRun = res;
      });
  }

  flipAnswerTo(quizAnswerUuid: string, correct: boolean) {
    console.log('Setting quiz answer...');
    this.http.post<QuizRun>(`api/quiz-run/${this.uuid}/quiz-attempt/${this.quizAttemptUuid}:answer`, {
      quizAnswerUuid,
      correct
    }).subscribe(res => {
      this.quizRun = res;
    },
      () => {
        this.loadQuizRun();
      });
  }

  trackQuizResultLines(index, item) {
    return item.name + item.points;
  }

  trackByFn(index, item) {
    return item.uuid; // or item.id
  }
}
