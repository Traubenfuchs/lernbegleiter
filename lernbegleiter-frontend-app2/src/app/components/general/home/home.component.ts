import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { GrowlService } from "../../../services/growl.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(
    public loginService: LoginService,
    public router: Router,
    private route: ActivatedRoute,
    private growlService: GrowlService) {

  }
  public weekInYear = HomeComponent.getWeekNumber();
  public year: number = new Date().getFullYear();

  contextMessage = 'Dein Wochenplaner';

  gradesUrl = '/management/grades';
  studentsUrl = '/management/students';
  newStudentUrl = '/management/student/new';
  classesUrl = '/management/classes';
  learningModuledUrl = '/management/learning-modules';
  teachersUrl = '/management/teachers';
  quizzesUrl = 'management/quizzes';

  static getWeekNumber(): any {
    const d = new Date();
    d.setUTCDate(d.getUTCDate() + 4 - (d.getUTCDay() || 7));
    const yearStart = new Date(Date.UTC(d.getUTCFullYear(), 0, 1));
    return Math.ceil((((d.getTime() - yearStart.getTime()) / 86400000) + 1) / 7);
  }

  ngOnInit(): void {
  }

  getProfileUrl = () => `user/${this.loginService.getUserUuid()}/profile`;
  getWeeklyOverviewUrl = () => `/student/${this.loginService.getUserUuid()}/weekly-overview/${this.weekInYear}/${this.year}`;


  getOriginalTaget = () => {
    const originalTarget = new URL(window.location.href).searchParams.get('originalTarget');
    console.log("ooo " + originalTarget + " " + window.location.href);
    if (!originalTarget || originalTarget.length === 0) {
      console.log("X");
      return window.location.href;
    } else {
      console.log("y");
      return originalTarget;
    }
  }

}
