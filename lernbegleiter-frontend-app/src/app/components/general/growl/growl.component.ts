import {Component, OnInit} from '@angular/core';
import {GrowlService} from "../../../services/growl.service";
import {GrowlMessage} from "../../../data/GrowlMessage";

@Component({
  selector: 'app-growl',
  templateUrl: './growl.component.html',
  styleUrls: ['./growl.component.scss']
})
export class GrowlComponent implements OnInit {

  messages: GrowlMessage[] = [];

  constructor(private growlService: GrowlService) {
  }

  ngOnInit() {
    this.growlService.messages$.subscribe(
        (message) => {
          this.messages = [];
          this.messages.push(message);
          setTimeout(() => this.messages = [], this.messages[0].msToDisappear + 500);
        });
  }
}
