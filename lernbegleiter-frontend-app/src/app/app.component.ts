import { Router } from '@angular/router';
import { LoginService } from './services/login.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styles: []
})
export class AppComponent {
  constructor(public loginService: LoginService, public router: Router) { }
  title = 'lernbegleiter-frontend-app';
}
