import { HttpClient } from '@angular/common/http';
import { WeeklyOverview } from './../../data/weekly-overview/WeeklyOverview';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-week-overview',
  templateUrl: './week-overview.component.html',
  styleUrls: ['./week-overview.component.scss']
})
export class WeekOverviewComponent implements OnInit {

  public weeklyOverview: WeeklyOverview

  constructor(private http: HttpClient) { }

  ngOnInit() {
  }

  loadWeeklyOverview() {

  }

}
