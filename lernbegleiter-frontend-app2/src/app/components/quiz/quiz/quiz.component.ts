import { Lob } from './../../../data/Lob';
import { Severity } from './../../../data/Severity';
import { GrowlMessage } from './../../../data/GrowlMessage';
import { GrowlService } from './../../../services/growl.service';
import { QuizAnswer } from './../../../data/quiz/QuizAnswer';
import { QuizQuestion } from './../../../data/quiz/QuizQuestion';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Quiz } from './../../../data/quiz/Quiz';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  styleUrls: ['./quiz.component.scss']
})
export class QuizComponent implements OnInit {
  uuid = ''
  quiz = new Quiz()

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute, private g: GrowlService) {
    this.route.params.subscribe(params => {
      this.ngOnInit()
    });
   }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("quizUUID")
    if (this.uuid === 'new') {
      this.newQuestion()
    } else {
      this.loadQuiz()
    }
  }

  loadQuiz() {
    console.log('Loading Quiz...')

    this.http.get<Quiz>(`api/quiz/${this.uuid}`)
      .subscribe(res => {
        console.log('Quiz loaded.')
        this.quiz = res
        if (this.quiz.questions.length === 0) {
          this.newQuestion()
        }
        this.quiz.questions.forEach(q => {
          q.internalId = new Date().getTime() * Math.random()
          if (!q.lob) {
            q.lob = new Lob()
          }

          q.answers.forEach(a => a.internalId = q.internalId = new Date().getTime() * Math.random())
          q.answers.sort((l, r) => l.position < r.position ? -1 : 1)
        })
        this.quiz.questions.sort((l, r) => l.position < r.position ? -1 : 1)
      })
  }

  newQuestion() {
    const question = new QuizQuestion()
    let answer = new QuizAnswer()
    answer.position = 0
    question.answers.push(answer)
    answer = new QuizAnswer()
    answer.position = 1
    question.answers.push(answer)
    this.quiz.questions.push(question)
  }

  newAnswer(question: QuizQuestion) {
    question.answers.push(new QuizAnswer())
  }

  deleteAnswer(internalId: number, question: QuizQuestion) {
    question.answers = question.answers.filter(a => a.internalId !== internalId)
  }

  deleteQuestion(internalId: number) {
    this.quiz.questions = this.quiz.questions.filter(q => q.internalId !== internalId)
  }

  swap(answer: QuizAnswer) {
    answer.correct = !answer.correct
  }

  orderize() {
    let i = 0
    for (i; i < this.quiz.questions.length; i++) {
      this.quiz.questions[i].position = i
    }
    for (const question of this.quiz.questions) {
      i = 0
      question.answers.forEach(a => a.position = i++)
    }
  }

  save() {
    this.orderize()

    this.http.post<UuidResponse>('api/quiz', this.quiz)
      .subscribe(uuidResponse => {
        this.router.navigate([`management/quiz/${uuidResponse.uuid}`])
        this.g.addMessage(new GrowlMessage("Neues Quiz erstellt!", Severity.SUCCESS, 3000))
      })
  }
  update() {
    this.orderize()

    this.http.put<string>(`api/quiz/${this.uuid}`, this.quiz)
      .subscribe(() => {
        this.router.navigate([`management/quiz/${this.uuid}`])
        this.g.addMessage(new GrowlMessage("Quiz wurde aktualisiert!", Severity.SUCCESS, 3000))
        this.loadQuiz()
      })
  }

  deleteImageFromQuestion(question: QuizQuestion) {
    question.lob.quizPictureFileName = undefined
    question.lob.quizPictureUUID = undefined
  }

  onQuestionImageChange(ev: any, qq: QuizQuestion) {
    qq.lob.quizPictureUUID = undefined
    qq.lob.quizPictureFileName = ev.target.files[0].name
    qq.lob.quizPictureBase64 = ''

    const reader = new FileReader()

    reader.onloadend = () => {
      const b64 = reader.result['replace'](/^data:.+;base64,/, '')
      qq.lob.quizPictureBase64 = b64
    };
    reader.readAsDataURL(ev.target.files[0])
  }

  delete() {
    this.http.delete<string>(`api/quiz/${this.uuid}`)
      .subscribe(() => {
        this.router.navigate([`management/quizzes`])
      })
  }
}
