import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { LoginRequest } from '../data/LoginRequest';
import { LoginResponse } from './../data/LoginResponse';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private loginUrl = 'api/login';
  private loginCheckUrl = 'api/login/check';
  private loginResponse: LoginResponse;

  constructor(private http: HttpClient, private router: Router) {
    const loginResponseS = localStorage.getItem('loginResponse')
    if (!!loginResponseS) {
      this.loginResponse = JSON.parse(loginResponseS)
    }
    setTimeout(() => {
      console.log("doing login check")
      this.http
        .post<any>(this.loginCheckUrl, null)
        .pipe(catchError(res => {
          console.error('login check not ok')
          this.logout()
          return throwError(res.error || 'Server error');
        })).subscribe(v => {
          console.info('login check ok')
        })
    }, 0)
  }

  public getUserUuid(): string {
    if (!!this.loginResponse) {
      return this.loginResponse.uuid
    }
  }

  public logout() {
    this.loginResponse = null
    localStorage.removeItem('loginResponse')
    this.router.navigate(['/'])
  }

  public getLoginResponse(): LoginResponse {
    return this.loginResponse;
  }

  public loginWithUnPw(email: string, password: string, success: LoginSuccessCallback, error: LoginErrorCallback): Observable<LoginResponse> {
    return this.loginWithRequest({ email, password }, success, error);
  }

  public loginWithRequest(loginRequest: LoginRequest, success: LoginSuccessCallback, error: LoginErrorCallback): Observable<LoginResponse> {
    const result = this.http
      .post<LoginResponse>(this.loginUrl, loginRequest)
      .pipe(catchError(res => {
        this.logout()
        error(res);
        return throwError(res.error || 'Server error');
      }));
    result.subscribe(v => {
      this.loginResponse = v
      localStorage.setItem('loginResponse', JSON.stringify(this.loginResponse))
      success(v);
    })
    return result;
  }

  public loggedInAndStudent() {
    return !!this.loginResponse && this.loginResponse.rights.indexOf('STUDENT') > -1;
  }
  public loggedInAndTeacherOrAdmin() {
    return !!this.loginResponse && (this.loginResponse.rights.indexOf('TEACHER') > -1 || this.loginResponse.rights.indexOf('ADMIN') > -1);
  }
  public loggedInAndAdmin() {
    return !!this.loginResponse && this.loginResponse.rights.indexOf('ADMIN') > -1;
  }

  public loggedInAndAuthority() {
    return !!this.loginResponse && (
      this.loginResponse.rights.indexOf('STUDENT') > -1 ||
      this.loginResponse.rights.indexOf('ADMIN') > -1);
  }
}

export type LoginSuccessCallback = (loginResponse: LoginResponse) => void;
export type LoginErrorCallback = (loginResponse: HttpErrorResponse) => void;
