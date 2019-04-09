import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-learning-modules',
  templateUrl: './learning-modules.component.html',
  styleUrls: ['./learning-modules.component.scss']
})
export class LearningModulesComponent implements OnInit {

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }


  ngOnInit() {
    this.loadLearningModules()
  }

  loadLearningModules() {

  }

}
