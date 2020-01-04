import { Router } from '@angular/router';
import { Grade } from './../../../data/Grade';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.scss']
})
export class GradesComponent implements OnInit {
  grades: Grade[];
  newGradeName: string;
  isLoadingGrades = true;

  constructor(private http: HttpClient, public router: Router) {
    this.loadGrades();
  }

  ngOnInit() { }

  loadGrades() {
    console.log("Loading grades...");
    this.isLoadingGrades = true;
    this.http.get<Grade[]>('api/grades').subscribe(
      res => this.grades = res,
      err => console.log(err),
      () => this.isLoadingGrades = false
    );
  }

  createGrade() {
    this.http.post<UuidResponse>('api/grade', { name: this.newGradeName })
      .subscribe(res => {
        console.log("created grade, reloading grades, new grade uuid " + res.uuid);
        this.loadGrades();
      });
  }

  deleteGrade(uuid: string) {
    console.log("Deleting grade...");
    this.http.delete<any>('api/grade/' + uuid)
      .subscribe(() => {
        console.log("Deleted grade.");
        this.loadGrades();
      });
  }
}




