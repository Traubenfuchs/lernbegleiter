import {LoginService} from './../../services/login.service';
import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public username = '';
  public password = '';
  public response = 'default text';

  constructor(private loginService: LoginService, private router: Router) {
  }


  ngOnInit() {
  }

  login() {
    this.loginService.loginWithUnPw(
        this.username,
        this.password,
        res => {
          this.response = JSON.stringify(res);
          this.router.navigate(['/login-success'])
        },
        err => {
        });
  }
}
