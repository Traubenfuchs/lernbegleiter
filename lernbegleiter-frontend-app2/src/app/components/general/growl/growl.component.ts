import { Component, OnInit } from '@angular/core';
import { GrowlService } from "../../../services/growl.service";
import { GrowlMessage } from "../../../data/GrowlMessage";

@Component({
  selector: 'app-growl',
  templateUrl: './growl.component.html',
  styleUrls: ['./growl.component.scss']
})
export class GrowlComponent {

  messages: GrowlMessage[] = [];

  constructor(private growlService: GrowlService) {
    let timer;

    this.growlService.messages$.subscribe(
      (message) => {
        clearTimeout(timer);
        this.messages = [];
        this.messages.push(message);
        timer = setTimeout(() => this.messages = [], this.messages[0].msToDisappear + 500);
      });
  }
}
