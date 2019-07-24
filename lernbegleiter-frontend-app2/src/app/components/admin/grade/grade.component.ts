import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Grade } from './../../../data/Grade';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-grade',
  templateUrl: './grade.component.html',
  styleUrls: ['./grade.component.scss']
})
export class GradeComponent implements OnInit {
  public grade: Grade = new Grade()

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() { this.loadGrade() }

  loadGrade() {
    console.log(`Loading grade ${this.route.snapshot.paramMap.get("gradeUUID")}`)

    this.http.get<Grade>(`api/grade/${this.route.snapshot.paramMap.get("gradeUUID")}`)
      .subscribe(res => {
        console.log(`Loaded grade ${this.route.snapshot.paramMap.get("gradeUUID")}`)
        this.grade = res
      })
  }

  ngOnChanges(changes: SimpleChanges) {
    this.loadGrade()
  }

  deleteStudentFromGrade(studentUuid: string) {
    console.log(`Deleting student with uuid <${studentUuid}> from grade <${studentUuid}>.`)

    this.http.delete<any>(`api/grade/${this.route.snapshot.paramMap.get("gradeUUID")}/student/${studentUuid}`)
      .subscribe(res => {
        console.log(`Student with uuid <${studentUuid}> deleted from grade <${studentUuid}>.`)
        this.loadGrade()
      })
  }
  deleteClass(uuid: string) {
    console.log('Not implemented yet!')
  }
}
