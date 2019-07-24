import {Component, Input, OnInit} from '@angular/core';
import {Breadcrumb} from "../../../data/Breadcrumb";

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss']
})
export class BreadcrumbComponent implements OnInit {

  @Input() breadcrumbs: Breadcrumb[];

  constructor() {
  }

  ngOnInit() {
  }

}
