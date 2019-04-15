import {Observable} from 'rxjs';
import {LoginService} from './login.service';

import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SecurityInterceptor implements HttpInterceptor {

  constructor(public loginService: LoginService) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.loginService.getLoginResponse()) {
      return next.handle(request);
    }

    request = request.clone({
      setHeaders: {
        Authorization: this.loginService.getLoginResponse().secret
      }
    });

    return next.handle(request)
  }
}
