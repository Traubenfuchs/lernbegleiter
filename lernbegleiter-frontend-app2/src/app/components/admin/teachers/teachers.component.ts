import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Teacher } from 'src/app/data/Teacher';

@Component({
  selector: 'app-teachers',
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent {
  public teachers: Teacher[];
  public isLoadingTeachers = true;

  constructor(private http: HttpClient) {
    this.loadTeachers();
  }

  loadTeachers() {
    this.http
      .get<Teacher[]>(`api/teachers`, { observe: 'body' })
      .subscribe(
        teachers => this.teachers = teachers,
        err => {
          this.isLoadingTeachers = false;
        },
        () => {
          this.isLoadingTeachers = false;
        }
      );
  }
}
