import { MyQuizzes } from './../../../data/MyQuizzes';
import { LoginService } from 'src/app/services/login.service';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-quizzes',
  templateUrl: './my-quizzes.component.html',
  styleUrls: ['./my-quizzes.component.scss']
})
export class MyQuizzesComponent {
  myQuizzes = new MyQuizzes;
  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    public loginService: LoginService) {
    this.loadMyQuizzes();
  }

  loadMyQuizzes() {
    this.http.get<MyQuizzes>(`api/my-quizzes`).subscribe(res => {
      this.myQuizzes = res;
    });
  }
}
