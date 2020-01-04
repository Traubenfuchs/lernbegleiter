import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UuidResponse } from 'src/app/data/UuidResponse';

import { ClassCompletion } from './../../data/ClassCompletion';
import { WeeklyOverview } from './../../data/weekly-overview/WeeklyOverview';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-week-overview',
  templateUrl: './week-overview.component.html',
  styleUrls: ['./week-overview.component.scss']
})
export class WeekOverviewComponent implements OnInit {

  public week: number;
  public year: number;
  public studentUuid;
  public weeklyOverview: WeeklyOverview = new WeeklyOverview();
  public classCompletions: ClassCompletion[] = [];

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute, public loginService: LoginService) {
    // this.router.routeReuseStrategy.shouldReuseRoute = () => false
    this.studentUuid = this.route.snapshot.paramMap.get("studentUUID");

    this.route.params.subscribe(params =>
      this.ngOnInit()
    );

    // setTimeout(() => this.route.params.subscribe(this.ngOnInit), 100);
  }

  ngOnInit() {
    this.week = parseInt(this.route.snapshot.paramMap.get("week"), 10);
    this.year = parseInt(this.route.snapshot.paramMap.get("year"), 10);
    this.loadLearningModuleStudents();
    this.loadWeeklyOverview();
  }

  loadLearningModuleStudents() {
    console.log('Loading loadLearningModuleStudents...');
    this.http
      .get<ClassCompletion[]>(
        `api/student/${this.studentUuid}/learningModuleStudent`)
      .subscribe(ccs => {
        console.log('Loaded loadLearningModuleStudents.');
        this.classCompletions = ccs;
      });
  }

  unfinishLearningModule(uuid: string) {
    console.log('Unfinishing learningModule...');

    this.http.post<UuidResponse>(`api/learningModuleStudent/${uuid}?complete=false`, undefined)
      .subscribe(uuidResponse => {
        this.loadLearningModuleStudents();
        console.log('Unfinished learningModule.');
      });
  }

  finishLearningModule(uuid: string) {
    console.log('Finishing learningModule...');

    this.http.post<UuidResponse>(`api/learningModuleStudent/${uuid}`, undefined)
      .subscribe(uuidResponse => {
        this.loadLearningModuleStudents();
        console.log('Finished learningModule.');
        this.ngOnInit();
      });
  }
  finishSubModule(uuid: string) {
    console.log('Finishing subModule...');

    this.http.post<UuidResponse>(`api/subModuleStudent/${uuid}`, undefined)
      .subscribe(uuidResponse => {
        this.loadLearningModuleStudents();
        console.log('Finished subModule.');
        this.ngOnInit();
      });
  }

  loadWeeklyOverview() {
    console.log('Loading weekly overview...');
    this.http
      .get<WeeklyOverview>(
        `api/student/${this.studentUuid}/weekly-overview/${this.week}/${this.year}`)
      .subscribe(weeklyOverview => {
        console.log('Loaded weekly overview.');
        this.weeklyOverview = weeklyOverview;
      });
  }

  saveWeeklyOverview() {
    console.log('Saving weekly overview...');

    this.http.patch<UuidResponse>(`api/student/${this.studentUuid}/weekly-overview`, this.weeklyOverview)
      .subscribe(uuidResponse => {
        console.log('Saved weekly overview...');
        this.loadWeeklyOverview();
      });
  }

  goToWeekRelative(offset: number) { // student/:studentUUID/weekly-overview/:week/:year
    this.week += offset;
    if (this.week > 52) {
      this.week = 1;
      this.year = this.year + 1;
    } else if (this.week < 1) {
      this.week = 52;
      this.year = this.year - 1;
    }
    this.router.navigate([`student/${this.studentUuid}/weekly-overview/${this.week}/${this.year}`]);
  }
}
