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
  public year: number
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
    this.year = parseInt(this.route.snapshot.paramMap.get("year"))
    this.loadWeeklyOverview()
  }

  loadWeeklyOverview() {
    console.log('Loading weekly overview...')
    this.http
      .get<WeeklyOverview>(
        `api/student/${this.studentUuid}/weekly-overview/${this.week}/${this.year}`,
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

  goToWeekRelative(offset: number) { // student/:studentUUID/weekly-overview/:week/:year
    this.week += offset
    if (this.week > 52) {
      this.week = 1
      this.year = this.year + 1
    } else if (this.week < 1) {
      this.week = 52
      this.year = this.year - 1
    }
    this.router.navigate([`student//${this.studentUuid}/weekly-overview/${this.week + offset}/${this.year}`])
  }
}
