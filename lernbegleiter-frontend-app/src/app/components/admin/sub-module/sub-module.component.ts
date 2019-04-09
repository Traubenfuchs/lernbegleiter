import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sub-module',
  templateUrl: './sub-module.component.html',
  styleUrls: ['./sub-module.component.scss']
})
export class SubModuleComponent implements OnInit {

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
  }

}
