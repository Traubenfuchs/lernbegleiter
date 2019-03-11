
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  response = 'default text';
  loginFormgroup: FormGroup;

  constructor(private loginService: LoginService,
    private router: Router,
    private formbuilder: FormBuilder) {
  }


  ngOnInit() {
    this.loginFormgroup = this.formbuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    }
    )
  }

  login() {
    this.loginService.loginWithUnPw(
      this.loginFormgroup.get('username').value,
      this.loginFormgroup.get('password').value,
      res => {
        this.response = JSON.stringify(res);
        this.router.navigate(['/login-success'])
      },
      err => {
      });
  }
}
