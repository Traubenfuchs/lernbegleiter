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
  public uuid: string = 'new'

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("gradeUUID")
    this.loadGrade()
  }

  loadGrade() {
    console.log(`Loading grade ${this.uuid}`)

    this.http.get<Grade>(`api/grade/${this.uuid}`)
      .subscribe(res => {
        console.log(`Loaded grade ${this.uuid}`)
        this.grade = res
      })
  }

  ngOnChanges(changes: SimpleChanges) {
    this.loadGrade()
  }

  deleteStudentFromGrade(studentUuid: string) {
    console.log(`Deleting student with uuid <${studentUuid}> from grade <${studentUuid}>.`)

    this.http.delete<any>(`api/grade/${this.uuid}/student/${studentUuid}`)
      .subscribe(res => {
        console.log(`Student with uuid <${studentUuid}> deleted from grade <${studentUuid}>.`)
        this.loadGrade()
      })
  }
  deleteClass(uuid: string) {
    console.log('Not implemented yet!')
  }
}
