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

  @Input() gradeUUID;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.loadGrade()
  }

  loadGrade() {
    console.log(`Loading grade ${this.gradeUUID}`)

    this.http.get<Grade>(`api/grade/${this.gradeUUID}`)
      .subscribe(res => {
        console.log(`Loaded grade ${this.gradeUUID}`)
        this.grade = res
      })
  }

  ngOnChanges(changes: SimpleChanges) {
    this.loadGrade()
  }

  deleteClass(uuid: string) {
    //TODO
  }

  deleteFromGrade(uuid: string) {
    console.log(`Deleting student with uuid <${uuid}> from grade <${uuid}>.`)

    this.http.delete<any>(`api/grade/${this.gradeUUID}/student/${uuid}`)
      .subscribe(res => {
        console.log(`Student with uuid <${uuid}> deleted from grade <${uuid}>.`)
        this.loadGrade()
      })
  }
}
