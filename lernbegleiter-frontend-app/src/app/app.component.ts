import { Router } from '@angular/router';
import { LoginService } from './services/login.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styles: []
})
export class AppComponent {
  public weekInYear: number

  welcomeHeader = 'Lernbegleiter';
  contextMessage = 'Dein Wochenplaner';

  gradesUrl = '/management/grades';
  studentsUrl = '/management/students';
  newStudentUrl = '/management/student/new';
  classesUrl = '/management/classes';
  learningModuledUrl = '/management/learning-modules';

  constructor(public loginService: LoginService, public router: Router) {
    this.weekInYear = AppComponent.getWeekNumber();
    const loginResponse = loginService.getLoginResponse();
    if (!!loginResponse)
      loginResponse.uuid
  }


  static getWeekNumber(): any {
    const d = new Date();
    d.setUTCDate(d.getUTCDate() + 4 - (d.getUTCDay() || 7));
    const yearStart = new Date(Date.UTC(d.getUTCFullYear(), 0, 1));
    return Math.ceil((((d.getTime() - yearStart.getTime()) / 86400000) + 1) / 7)
  }

  isLoggedInWithAnyAuthority(): boolean {
    return this.loginService.loggedInAndAuthority()
  }

  isLoggedInWithStudentAuthority(): boolean {
    return this.loginService.loggedInAndStudent();
  }

  isRouterSetTo(pages: string[]): boolean {
    let isRouterSetTo = false;

    pages.forEach(p => {
      if (this.router.url === p) {
        isRouterSetTo = true;
      }
    });

    return isRouterSetTo;
  }

  updateContextMessage(url: string) {
    this.contextMessage = "DEFAULT MESSAGE -> CHANGEME!"
    console.log("HI  " + url)
    switch (url) {
      case this.gradesUrl:
        this.contextMessage = `Hier kannst du die Klassen verwalten.`;
        break;
      case this.studentsUrl:
        this.contextMessage = `Hier kannst du die Schüler verwalten.`;
        break;
      case this.classesUrl:
        this.contextMessage = `Hier kannst du die Fächer verwalten.`;
        break;
    }

  }
}
