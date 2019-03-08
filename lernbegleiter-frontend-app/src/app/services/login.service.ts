import {LoginResponse} from './../data/LoginResponse';
import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from "rxjs";
import {LoginRequest} from "../data/LoginRequest";
import {catchError} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private loginUrl = 'api/login';
  private loginResponse: LoginResponse;

  constructor(private http: HttpClient) {
  }

  public logout() {
    this.loginResponse = null
  }

  public getLoginResponse(): LoginResponse {
    console.log("GETTING LOGIN RESPONSE")
    return this.loginResponse;
  }

  public loginWithUnPw(email: string, password: string, success: LoginSuccessCallback, error: LoginErrorCallback): Observable<LoginResponse> {
    return this.loginWithRequest({email, password}, success, error);
  }

  public loginWithRequest(loginRequest: LoginRequest, success: LoginSuccessCallback, error: LoginErrorCallback): Observable<LoginResponse> {
    const result = this.http
    .post<LoginResponse>(this.loginUrl, loginRequest)
    .pipe(catchError(res => {
      error(res);
      return throwError(res.error || 'Server error');
    }));
    result.subscribe(v => {
      this.loginResponse = v;
      success(v);
    })
    return result;
  }
}

export type LoginSuccessCallback = (loginResponse: LoginResponse) => void;
export type LoginErrorCallback = (loginResponse: HttpErrorResponse) => void;
