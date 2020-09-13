import { Class } from 'src/app/data/Class';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-display-class',
  templateUrl: './display-class.component.html',
  styleUrls: ['./display-class.component.scss']
})
export class DisplayClassComponent {
  uuid: string;
  class = new Class();

  constructor(public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute
  ) {
    this.uuid = this.route.snapshot.paramMap.get("classUUID");

    this.http.get<Class>(`api/class/${this.uuid}`).subscribe(x => {
      this.class = x;
    });
  }
}
