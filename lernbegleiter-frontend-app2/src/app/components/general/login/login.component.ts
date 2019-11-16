
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
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
  errorMessage: string = null;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private route: ActivatedRoute,
    private formbuilder: FormBuilder) {
  }


  ngOnInit() {
    this.loginFormgroup = this.formbuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  login() {
    this.errorMessage = null;
    this.loginService.loginWithUnPw(
      this.loginFormgroup.get('username').value,
      this.loginFormgroup.get('password').value,
      res => {
        this.response = JSON.stringify(res);
        const originalTarget = this.route.snapshot.queryParamMap.get("originalTarget");
        if (originalTarget && originalTarget.length !== 0) {
          window.location.href = originalTarget;
        } else {
          this.router.navigate(['/login-success']);
        }

      },
      err => {
        this.errorMessage = err.error.message;
      });
  }
}
