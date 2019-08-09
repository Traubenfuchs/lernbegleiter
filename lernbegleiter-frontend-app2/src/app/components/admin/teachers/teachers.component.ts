import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Teacher } from 'src/app/data/Teacher';

@Component({
  selector: 'app-teachers',
  templateUrl: './teachers.component.html',
  styleUrls: ['./teachers.component.scss']
})
export class TeachersComponent implements OnInit {
  public teachers: Teacher[]
  public isLoadingTeachers = true

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  ngOnInit() {
    this.loadTeachers()
  }

  loadTeachers() {
    console.info("Loading teachers...")

    this.http
      .get<Teacher[]>(`api/teachers`, { observe: 'body' })
      .subscribe(
        teachers => this.teachers = teachers,
        err => {
          console.log(err)
          this.isLoadingTeachers = false
        },
        () => {
          console.log("Loaded teachers.")
          this.isLoadingTeachers = false
        }
      )
  }
}
