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
  selectedClass: string = ''

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("studentUUID")
    this.loadGrades()
    if (this.uuid === 'new') {
      this.student.uuid = 'Automatisch'
    } else {
      this.loadStudent()
    }
  }

  loadGrades() {
    console.log('Loading grades...')
    this.http.get<Grade[]>('api/grades')
      .subscribe(res => {
        console.log('Grades loaded.')
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
    console.log('Updating student...')
    this.http.patch<UuidResponse>(`api/student/${this.uuid}`, this.student)
      .subscribe(uuidResponse => {
        this.loadStudent()
      })
  }

  createNewStudent() {
    console.log('Creating student...')
    this.http.post<UuidResponse>('api/student', this.student)
      .subscribe(uuidResponse => {
        this.router.navigate([`management/student/${uuidResponse.uuid}`])
      })
  }

  loadStudent() {
    console.log('Loading student...')
    this.http
      .get<Student>(`api/student/${this.uuid}`, { observe: 'body' })
      .subscribe(student => {
        console.log('Loaded student.')
        this.student = student
      })
  }

  deleteClick() {
    console.log('Deleting student...')
    this.http
      .delete<any>(`api/student/${this.uuid}`, { observe: 'body' })
      .subscribe(_ => {
        console.log('Deleted student.')
        this.router.navigate(['management/students'])
      })
  }
}
