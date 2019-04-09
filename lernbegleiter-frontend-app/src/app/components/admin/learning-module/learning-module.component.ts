import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-learning-module',
  templateUrl: './learning-module.component.html',
  styleUrls: ['./learning-module.component.scss']
})
export class LearningModuleComponent implements OnInit {

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
  }

}
