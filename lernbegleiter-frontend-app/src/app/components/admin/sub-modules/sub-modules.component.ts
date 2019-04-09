import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sub-modules',
  templateUrl: './sub-modules.component.html',
  styleUrls: ['./sub-modules.component.scss']
})
export class SubModulesComponent implements OnInit {

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.loadSubModules()
  }
  loadSubModules() {

  }
}
