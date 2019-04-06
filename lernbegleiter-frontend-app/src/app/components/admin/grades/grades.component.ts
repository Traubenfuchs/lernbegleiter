import {Grade} from './../../../data/Grade';
import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {UuidResponse} from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.scss']
})
export class GradesComponent implements OnInit {
  grades: Grade[];
  selectedGrade: Grade = undefined;
  newGradeName: string;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.loadGrades()
  }

  loadGrades() {
    this.http.get<Grade[]>('api/grades')
    .subscribe(res => {
      this.grades = res
    })
  }

  createGrade() {
    this.http.post<UuidResponse>('api/grade', {name: this.newGradeName})
    .subscribe(res => {
      console.log("created grade, reloading grades, response " + res.uuid)
      this.loadGrades()
    })
  }

  deleteGrade(uuid: string) {
    this.http.delete<any>('api/grade/' + uuid)
    .subscribe(() => {
      this.loadGrades()
    })
  }

  setSelectedGrade(grade: Grade) {
    this.selectedGrade = grade;
  }
}




