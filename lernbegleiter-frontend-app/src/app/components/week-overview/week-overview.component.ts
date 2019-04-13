import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { WeeklyOverview } from './../../data/weekly-overview/WeeklyOverview';
import { Component, OnInit } from '@angular/core';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-week-overview',
  templateUrl: './week-overview.component.html',
  styleUrls: ['./week-overview.component.scss']
})
export class WeekOverviewComponent implements OnInit {

  public week: number
  public studentUuid
  public weeklyOverview: WeeklyOverview = new WeeklyOverview()

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
    this.router.routeReuseStrategy.shouldReuseRoute = function () {
      return false;
    };


  }

  ngOnInit() {
    this.studentUuid = this.route.snapshot.paramMap.get("studentUUID")
    this.week = parseInt(this.route.snapshot.paramMap.get("week"))
    this.loadWeeklyOverview()
  }

  loadWeeklyOverview() {
    console.log('Loading weekly overview...')
    this.http
      .get<WeeklyOverview>(
        `api/student/${this.studentUuid}/weekly-overview/${this.week}`,
        { observe: 'body' })
      .subscribe(weeklyOverview => {
        console.log('Loaded weekly overview.')
        this.weeklyOverview = weeklyOverview
      })
  }

  saveWeeklyOverview() {
    console.log('Saving weekly overview...')

    this.http.patch<UuidResponse>(`api/student/${this.studentUuid}/weekly-overview`, this.weeklyOverview)
      .subscribe(uuidResponse => {
        console.log('Saved weekly overview...')
        this.loadWeeklyOverview()
      })
  }

  goToWeekRelative(offset: number) { // student/:studentUUID/weekly-overview/:week
    this.router.navigate([`student//${this.studentUuid}/weekly-overview/${this.week + offset}`])
  }
}
