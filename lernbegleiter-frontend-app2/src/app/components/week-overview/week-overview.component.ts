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
export class WeekOverviewComponent {

  public week: number;
  public year: number;
  public studentUuid;
  public weeklyOverview: WeeklyOverview = new WeeklyOverview();
  public classCompletions: ClassCompletion[] = [];

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute, public loginService: LoginService) {
    // this.router.routeReuseStrategy.shouldReuseRoute = () => false
    this.studentUuid = this.route.snapshot.paramMap.get("studentUUID");

    this.route.params.subscribe(params =>
      this.refresh()
    );
  }

  refresh() {
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
        if(!!ccs) {
          ccs.forEach(x=>{
            if(!x.learningModulesStudent) {
              return;
            }

            x.learningModulesStudent= x.learningModulesStudent.sort((l,r)=>{
              return l.dueDate < r.dueDate ? -1:1
            })
            
          })
        }
        
        this.classCompletions = ccs;
      });
  }

  unfinishLearningModule(uuid: string) {
    this.http.post<UuidResponse>(`api/learningModuleStudent/${uuid}?complete=false`, undefined)
      .subscribe(uuidResponse => {
        this.refresh();
      });
  }

  finishLearningModule(uuid: string) {
    this.http.post<UuidResponse>(`api/learningModuleStudent/${uuid}`, undefined)
      .subscribe(uuidResponse => {
        this.refresh();
      });
  }
  finishSubModule(uuid: string) {
    this.http.post<UuidResponse>(`api/subModuleStudent/${uuid}`, undefined)
      .subscribe(uuidResponse => {
        this.refresh();
      });
  }

  loadWeeklyOverview() {
    this.http
      .get<WeeklyOverview>(
        `api/student/${this.studentUuid}/weekly-overview/${this.week}/${this.year}`)
      .subscribe(weeklyOverview => {
        this.weeklyOverview = weeklyOverview;
      });
  }

  saveWeeklyOverview() {
    this.http.patch<UuidResponse>(`api/student/${this.studentUuid}/weekly-overview`, this.weeklyOverview)
      .subscribe(uuidResponse => {
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
