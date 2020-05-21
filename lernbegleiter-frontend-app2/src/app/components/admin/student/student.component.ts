import { Severity } from './../../../data/Severity';
import { GrowlService } from './../../../services/growl.service';
import { Grade } from './../../../data/Grade';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/data/Student';
import { ActivatedRoute, Router } from '@angular/router';
import { UuidResponse } from 'src/app/data/UuidResponse';
import { Breadcrumb } from "../../../data/Breadcrumb";
import { GrowlMessage } from 'src/app/data/GrowlMessage';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.scss']
})
export class StudentComponent {
  student: Student = new Student();
  uuid: string;
  grades: Grade[] = [];
  selectedClass = '';

  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    private growlService: GrowlService) {
    this.route.params.subscribe(params => {
      this.refresh();
    });
  }

  refresh() {
    this.uuid = this.route.snapshot.paramMap.get("studentUUID");
    this.loadGrades();
    if (this.uuid === 'new') {
      this.student.uuid = 'Automatisch';
    } else {
      this.loadStudent();
    }
  }

  loadGrades() {
    this.http.get<Grade[]>('api/grades')
      .subscribe(res => {
        this.grades = res;
      });
  }

  saveClick() {
    if (this.uuid === 'new') {
      this.createNewStudent();
    } else {
      this.updateStudent();
    }
  }

  updateStudent() {
    this.http.patch<UuidResponse>(`api/student/${this.uuid}`, this.student)
      .subscribe(uuidResponse => {
        this.loadStudent();
        this.growlService.addMessage(new GrowlMessage("Schüler wurde upgedated.", Severity.SUCCESS, 2000));
      });
  }

  createNewStudent() {
    this.http.post<UuidResponse>('api/student', this.student)
      .subscribe(uuidResponse => {
        this.router.navigate([`management/student/${uuidResponse.uuid}`]);
        this.growlService.addMessage(new GrowlMessage("Schüler wurde angelegt.", Severity.SUCCESS, 2000));
      });
  }

  loadStudent() {
    this.http
      .get<Student>(`api/student/${this.uuid}`)
      .subscribe(student => {
        this.student = student;
      });
  }

  deleteClick() {
    this.http
      .delete<any>(`api/student/${this.uuid}`)
      .subscribe(_ => {
        this.router.navigate(['management/students;']);
        this.growlService.addMessage(new GrowlMessage("Schüler wurde gelöscht.", Severity.SUCCESS, 2000));
      });
  }
}
