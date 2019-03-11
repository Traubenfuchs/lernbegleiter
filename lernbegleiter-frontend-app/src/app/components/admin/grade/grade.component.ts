import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Grade } from './../../../data/Grade';
import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/data/Student';

@Component({
  selector: 'app-grade',
  templateUrl: './grade.component.html',
  styleUrls: ['./grade.component.scss']
})
export class GradeComponent implements OnInit {
  public grade: Grade = new Grade()

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() { }

  loadGrade() {
    this.http.get<Grade>(`api/grade/${this.route.snapshot.paramMap.get("gradeUUID")}`)
      .subscribe(res => {
        console.log("Loaded grade")
        this.grade = res
      })
  }

  deleteFromGrade(uuid: string) {
    this.http.delete<any>(`api/grade/${uuid}/student/${this.route.snapshot.paramMap.get("gradeUUID")}`)
      .subscribe(res => {
        console.log(`Student with uuid <${uuid}> deleted.`)
        this.loadGrade()
      })
  }
}
