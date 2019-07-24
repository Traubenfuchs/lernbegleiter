import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";
import {GrowlMessage} from "../data/GrowlMessage";

@Injectable({
  providedIn: 'root'
})
export class GrowlService {

  messageSubject: Subject<GrowlMessage>;
  messages$: Observable<GrowlMessage>;

  constructor() {
    this.messageSubject = new Subject();
    this.messages$ = this.messageSubject.asObservable();
  }

  addMessage(message: GrowlMessage) {
    this.messageSubject.next(message);
  }
}
