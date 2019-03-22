import { Grade } from './../../../data/Grade';
import { catchError } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/data/Student';
import { Router, ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent implements OnInit {
  student: Student = new Student()
  uuid: string
  grades: Grade[] = []

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("studentUUID")
    if (this.uuid === 'new') {
      this.student.uuid = 'Automatisch'
    } else {
      this.loadStudent()
      this.loadGrades()
    }
  }

  loadGrades() {
    console.log('loading grades...')
    this.http.get<Grade[]>('api/grades')
      .subscribe(res => {
        this.grades = res
      })
  }

  saveClick() {
    if (this.uuid === 'new') {
      this.createNewStudent()
    } else {
      this.updateStudent()
    }
  }

  updateStudent() {
    console.log('updating student...')
    this.http.patch<UuidResponse>(`api/student/${this.uuid}`, this.student)
      .subscribe(uuidResponse => {
        this.loadStudent()
      })
  }

  createNewStudent() {
    console.log('creating student...')
    this.http.post<UuidResponse>('api/student', this.student)
      .subscribe(uuidResponse => {
        this.uuid = uuidResponse.uuid
        this.loadStudent()
      })
  }

  loadStudent() {
    console.log('loading student...')
    this.http
      .get<Student>(`api/student/${this.uuid}`, { observe: 'body' })
      .subscribe(student => {
        this.student = student
      })
  }

  deleteClick() {
    console.log('deleting student...')
    this.http
      .delete<any>(`api/student/${this.uuid}`, { observe: 'body' })
      .subscribe(_ => {
        this.router.navigate(['management/students'])
      })
  }
}
