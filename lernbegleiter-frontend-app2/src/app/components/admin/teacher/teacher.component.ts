import { Teacher } from 'src/app/data/Teacher';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-teacher',
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.scss']
})
export class TeacherComponent implements OnInit {
  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) { }

  uuid: string
  teacher = new Teacher()

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("teacherUUID")
    if (this.isNew()) {
      this.teacher.uuid = 'Automatisch'
    } else {
      this.loadTeacher()
    }
  }

  loadTeacher() {
    console.info("Loading teacher...")
    this.http
    .get<Teacher>(`api/teacher/${this.uuid}`, {observe: 'body'})
    .subscribe(teacher => {
      console.log('Loaded teacher.')
      this.teacher = teacher
    })
  }
  updateTecher() {
    console.info("Updating teacher...")
    this.http.patch<UuidResponse>(`api/teacher/${this.uuid}`, this.teacher)
      .subscribe(uuidResponse => {
        console.info("Updated teacher.")
        this.loadTeacher()
      })
  }
  createTeacher() {
    console.info("Creating teacher...")
    this.http.post<UuidResponse>('api/teacher', this.teacher)
      .subscribe(uuidResponse => {
        console.log("Created teacher.")
        this.router.navigate([`management/teacher/${uuidResponse.uuid}`])
      })
  }
  deleteTeacher() {
    console.info("Deleting teacher...")
    this.http
      .delete<any>(`api/teacher/${this.uuid}`, { observe: 'body' })
      .subscribe(_ => {
        console.log('Deleted teacher.')
        this.router.navigate(['management/teachers'])
      })
  }

  isNew = () => this.uuid === 'new'

}
