import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/data/Student';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.scss']
})
export class StudentsComponent implements OnInit {

  public students: Student[]

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.loadStudents()
  }

  loadStudents() {
    this.http
      .get<Student[]>(`api/students`, { observe: 'body' })
      .subscribe(students => {
        this.students = students
      })
  }

}
