import { JwtService } from './jwt.service';
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
  private loginResponse: LoginResponse;

  constructor(private http: HttpClient, private jwtService: JwtService, private router: Router) {
    jwtService.requestJwt(jwt => {
      this.loginResponse = JSON.parse(jwt)
    })
  }

  public logout() {
    this.loginResponse = null
    this.jwtService.deleteJwt()
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
        this.jwtService.deleteJwt()
        error(res);
        return throwError(res.error || 'Server error');
      }));
    result.subscribe(v => {
      this.loginResponse = v
      this.jwtService.setJwt(JSON.stringify(this.loginResponse))
      success(v);
    })
    return result;
  }
  public loggedInAndStudent() {
    return !!this.loginResponse && this.loginResponse.rights.indexOf('STUDENT') > -1;
  }
  public loggedInAndAuthority() {
    return !!this.loginResponse && (
      this.loginResponse.rights.indexOf('STUDENT') > -1 ||
      this.loginResponse.rights.indexOf('ADMIN') > -1);
  }
}

export type LoginSuccessCallback = (loginResponse: LoginResponse) => void;
export type LoginErrorCallback = (loginResponse: HttpErrorResponse) => void;
