import { LoginService } from '../services/login.service';
import {Injectable} from '@angular/core';
import { CanActivate, Router, Route,RouterStateSnapshot, ActivatedRouteSnapshot, UrlTree } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class AdminTeacherGuard implements CanActivate {
    private rights: string[] = ['ADMIN','TEACHER'];
    constructor(private loginService: LoginService){}

    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
      ): boolean{
          const loginResponse = this.loginService.getLoginResponse();
        return !!loginResponse && loginResponse.rights.some(x=>this.rights.indexOf(x) >= 0);
      }
}