import { QuizResultEntry } from './../../../data/quiz/QuizResultEntry';
import { QuizAttempt } from './../../../data/quiz/QuizAttempt';
import { Subject } from 'rxjs';
import { QuizQrCodeResponse } from './../../../data/quiz/QuizQrCodeResponse';
import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UuidResponse } from 'src/app/data/UuidResponse';

import { QuizResult } from './../../../data/quiz/QuizResult';
import { QuizRun } from './../../../data/quiz/QuizRun';
import { QuizRunState } from './../../../data/quiz/QuizRunState';
import { LoginService } from './../../../services/login.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { BehaviorSubject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

@Component({
  selector: 'app-quiz-run',
  templateUrl: './quiz-run.component.html',
  styleUrls: ['./quiz-run.component.scss']
})
export class QuizRunComponent implements OnDestroy {

  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    public loginService: LoginService,
    private sanitizer: DomSanitizer) {
    this.route.params.subscribe(params => {
      this.refresh();
    });

    this.audio.src = "/assets/Greek_Dance.mp3";
    this.audio.load();

    this.loadQuizRun();
    this.loadQuizResultInternal();

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

      const timeLeftSecs = Math.round(diff / 100) / 10;

      if (timeLeftSecs < 0) {
        this.timeLeft = undefined;
      } else if (Math.round(timeLeftSecs) < 120) {
        let strR = timeLeftSecs.toString();
        if (Number.isInteger(timeLeftSecs)) {
          strR += '.0';
        }
        this.timeLeft = strR;
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
  globalImageQuizPictureUUID = '';
  readonly audio = new Audio();
  destroyed = false;
  loadingQuizRun = false;
  loadingQuizResult = false;
  safeQr = new BehaviorSubject<SafeUrl>(null);
  fancyResults = !false;
  quizRunUuid = '';
  quizUuid = '';
  quizAttemptUuid = '';
  quizResult = new QuizResult();
  quizRun = new QuizRun();
  _QuizRunState = QuizRunState;
  timeLeftIntervalId: any;
  timeLeft = '0';
  quizAttempt = new QuizAttempt();
  quizRunInterval = 500;

  answerChangeDebouncers = new Map<string, Subject<string>>();

  questionCount = 0;
  questionsCorrect = 0;

  ngOnDestroy(): void {
    this.destroyed = true;
    this.stopMusic();
  }

  refresh() {
    this.quizRunUuid = this.route.snapshot.paramMap.get("quizRunUUID");
    this.quizUuid = this.route.snapshot.paramMap.get("quizUUID");
    this.quizResult = new QuizResult();
    this.quizRun = new QuizRun();
    if (this.quizRunUuid && this.quizRunUuid.length !== 0) {
      this.loadQrCode();
    }

    if (this.quizRunUuid === 'new') {
      this.quizRun.quizRunType = 'ONE_QUESTION_AT_A_TIME';
      this.quizRun.uuid = 'Automatisch';
    } else if (this.loginService.loggedInAndStudent()) {
      this.http
        .post<UuidResponse>(`api/quiz-run/${this.quizRunUuid}/quiz-attempt:create-if-not-exists`, {})
        .subscribe(uuidResponse => {
          console.log('Loaded quizAttemptUuid.');
          this.quizAttemptUuid = uuidResponse.uuid;
        });
    }
  }

  saveClick() {
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
        this.router.navigate([`management/quiz/${this.quizUuid}/quiz-run/${uuidResponse.uuid}`]);
      });
  }

  loadQuizResultInternal() {
    if (this.destroyed || this.loadingQuizResult) {
      return;
    }
    this.loadQuizResult();
  }

  loadQuizResult() {
    if (!this.quizRunUuid || this.quizRunUuid === '' || this.quizRunUuid === 'new') {
      setTimeout(() => this.loadQuizResultInternal(), 3000);
      return;
    }
    if (!this.loginService.loggedInAndTeacherOrAdmin()) {
      return;
    }
    this.loadingQuizResult = true;
    const result = this.http.get<QuizResult>(`api/quiz/${this.quizUuid}/quiz-run/${this.quizRunUuid}/quiz-result`);
    result.subscribe(res => {
      res.entries = res.entries.sort((l, r) => {
        if (l.points === r.points) {
          return l.weightedPoints > r.weightedPoints ? -1 : 1;
        }

        if (l.points > r.points) {
          return -1;
        } else {
          return 1;
        }
      });

      const max = res.entries.map(v => v.weightedPoints).reduce((previous, current) => current > previous ? current : previous, 0);

      res.entries.forEach(v => v.heightPerc = "" + (v.weightedPoints * 250 / max) + 'px');

      this.quizResult = res;
    },
      err => {

      }, () => {
        this.loadingQuizResult = false;
        setTimeout(() => this.loadQuizResultInternal(), 3000);
      });

    return result;
  }

  loadQuizRun() {
    if (this.destroyed || this.loadingQuizRun) {
      return;
    }
    if (!this.quizRunUuid || this.quizRunUuid === '' || this.quizRunUuid === 'new') {
      setTimeout(() => this.loadQuizRun(), this.quizRunInterval);
      return;
    }
    this.loadingQuizRun = true;
    const result = this.http.get<QuizRun>(`api/quiz-run-${this.loginService.loggedInAndAdmin() ? 'admin' : 'student'}/${this.quizRunUuid}`);
    result.subscribe(res => {
      this.quizRun = res;

      this.questionCount = 0;
      this.questionsCorrect = 0;
      res.currentQuestions.forEach(q => {
        this.questionCount += 1;
        if (q.answers.every(a => a.tickedCorrectly)) {
          this.questionsCorrect += 1;
        }
      });

      if (res && res.nextTimeLimit && res.nextTimeLimit.length > 3) {
        this.quizRun.nextTimeLimitForInput = res.nextTimeLimit.substring(0, res.nextTimeLimit.length - 1);
      }

      setTimeout(() => {
        if (!this.loginService.loggedInAndTeacherOrAdmin()) {
          return;
        }
        if (res.quizRunType != 'ONE_QUESTION_AT_A_TIME') {
          return;
        }

        if (this._QuizRunState[res.state] == this._QuizRunState.WAITING_FOR_ANSWERS) {
          this.startMusicIfNotPlaying();
        } else {
          this.stopMusic();
        }

      }, 0);


      // if (this.quizRun.nextTimeLimit && this.quizRun.nextTimeLimit.length > 15) {
      //   this.quizRun.nextTimeLimit = this.quizRun.nextTimeLimit.substring(0, 16)
      // }
    },
      err => {

      },
      () => {
        this.loadingQuizRun = false;
        setTimeout(() => this.loadQuizRun(), this.quizRunInterval);
        if (this.quizRun.quizRunType === 'FREE_ANSWERING' || this.quizRun.quizRunType === 'FINISH_SELF') {
          this.quizRunInterval = 1000;
        }
        if (this.quizRun.quizRunType === 'FINISH_SELF') {
          this.loadAttempt();
        }
      });
  }

  loadQrCode() {
    this.http.get<QuizQrCodeResponse>(`api/quiz/${this.quizRunUuid}/qr`).subscribe(r => {
      this.safeQr.next(this.sanitizer.bypassSecurityTrustUrl("data:image/png;base64, " + r.qrCodeImageAsBase64));
    });
  }

  advance() {
    this.http.post<QuizRun>(`api/quiz-run/${this.quizRunUuid}:advance`, {})
      .subscribe(res => {
        this.quizRun = res;
        this.loadingQuizRun = false;
        this.loadQuizRun();
      });
  }

  answerFreeTextQuestion(quizQuestionAttemptUuid: string, freeText: string) {
    this.http.post<QuizRun>(`api/quiz-run/${this.quizRunUuid}/quiz-attempt/${this.quizAttemptUuid}:answer`, {
      freeText
    }).subscribe(res => {
      this.quizRun = res;
    });
  }

  flipAnswerTo(quizAnswerUuid: string, correct: boolean) {
    this.http.post<QuizRun>(`api/quiz-run/${this.quizRunUuid}/quiz-attempt/${this.quizAttemptUuid}:tick`, {
      quizAnswerUuid,
      correct
    }).subscribe(res => {
      this.quizRun = res;
    });
  }

  freeTextChange(quizQuestionUuid: string, freeText: string) {
    let debouncerSubject = this.answerChangeDebouncers.get(quizQuestionUuid);

    if (!debouncerSubject) {
      debouncerSubject = new Subject<string>();
      this.answerChangeDebouncers.set(quizQuestionUuid, debouncerSubject);
      debouncerSubject.pipe(
        debounceTime(400),
        distinctUntilChanged())
        .subscribe(value =>
          this.freeTextChangeInternal(quizQuestionUuid, value)
        );
    }

    debouncerSubject.next(freeText);
  }

  freeTextChangeInternal(quizQuestionUuid: string, freeText: string) {
    this.http.post<QuizRun>(`api/quiz-run/${this.quizRunUuid}/quiz-attempt/${this.quizAttemptUuid}:answer`, {
      quizQuestionUuid,
      freeText
    }).subscribe(res => {
      this.quizRun = res;
    });
  }

  finishSelf() {
    this.http.post<any>(`api/quiz-run/${this.quizRunUuid}/quiz-attempt:finish`, {})
      .subscribe(res => {
        this.loadAttempt();
      });
  }

  loadAttempt() {
    if (!this.quizRun || !this.loginService.loggedInAndStudent()) {
      return;
    }
    this.http.get<QuizAttempt>(`api/quiz-run/${this.quizRunUuid}/quiz-attempt`)
      .subscribe(res => {
        this.quizAttempt = res;
      });
  }

  stopMusic() {
    this.audio.pause();
    this.audio.currentTime = 0;
  }
  startMusicIfNotPlaying() {
    if (this.audio.paused) {
      this.audio.play();
    }
  }

  trackQuizResultLines(index, item: QuizResultEntry) {
    return item.name + item.weightedPoints;
  }

  trackByFn(index, item) {
    return item.uuid; // or item.id
  }

  selectGlobalImage(uuid: string) {
    this.globalImageQuizPictureUUID = uuid;
  }
  clearGlobalImage() {
    this.globalImageQuizPictureUUID = null;
  }
}
