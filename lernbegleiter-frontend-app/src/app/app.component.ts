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


  constructor(public loginService: LoginService, public router: Router) {
    this.weekInYear = this.getWeekNumber()
    const loginResponse = loginService.getLoginResponse()
    if (!!loginResponse)
      loginResponse.uuid
  }
  title = 'lernbegleiter-frontend-app';


  getWeekNumber(): any {
    const d = new Date();
    d.setUTCDate(d.getUTCDate() + 4 - (d.getUTCDay() || 7))
    var yearStart = new Date(Date.UTC(d.getUTCFullYear(), 0, 1))
    var weekNo = Math.ceil((((d.getTime() - yearStart.getTime()) / 86400000) + 1) / 7)
    return weekNo
  }
}
