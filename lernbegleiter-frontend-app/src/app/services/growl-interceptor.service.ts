import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from "@angular/common/http";
import { Observable, throwError } from "rxjs";
import { catchError } from "rxjs/internal/operators/catchError";
import { GrowlService } from "./growl.service";
import { Severity } from "../data/Severity";
import { GrowlMessage } from "../data/GrowlMessage";
import { tap } from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class GrowlInterceptor implements HttpInterceptor {

  constructor(private growlService: GrowlService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(
      tap((response: HttpResponse<any>) => {
        if (response.body && response.status.toString()[0] === '2') {
          //this.growlService.addMessage(new GrowlMessage('Hier könnte Ihre Werbung stehen!', Severity.SUCCESS, 6000));
        }
      }),
      catchError((error: HttpErrorResponse) => {
        // this.growlService.addMessage(new GrowlMessage('Hier könnte Ihre Werbung stehen!', Severity.ERROR, 6000));
        return throwError(error);
      })
    )
  }
}
