import { Severity } from './../../../data/Severity';
import { GrowlService } from './../../../services/growl.service';
import { Teacher } from 'src/app/data/Teacher';
import { Router, ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UuidResponse } from 'src/app/data/UuidResponse';
import { GrowlMessage } from 'src/app/data/GrowlMessage';

@Component({
  selector: 'app-teacher',
  templateUrl: './teacher.component.html',
  styleUrls: ['./teacher.component.scss']
})
export class TeacherComponent {
  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private growlService: GrowlService) {
    this.uuid = this.route.snapshot.paramMap.get("teacherUUID");
    if (this.isNew()) {
      this.teacher.uuid = 'Automatisch';
    } else {
      this.loadTeacher();
    }
  }

  uuid: string;
  teacher = new Teacher();

  loadTeacher() {
    this.http
      .get<Teacher>(`api/teacher/${this.uuid}`, { observe: 'body' })
      .subscribe(teacher => {
        console.log('Loaded teacher.');
        this.teacher = teacher;
      });
  }
  updateTecher() {
    this.http.patch<UuidResponse>(`api/teacher/${this.uuid}`, this.teacher)
      .subscribe(uuidResponse => {
        this.loadTeacher();
        this.growlService.addMessage(new GrowlMessage("Lehrer wurde geupdated.", Severity.SUCCESS, 2000));
      });
  }
  createTeacher() {
    this.http.post<UuidResponse>('api/teacher', this.teacher)
      .subscribe(uuidResponse => {
        console.log("Created teacher.");
        this.router.navigate([`management/teacher/${uuidResponse.uuid}`]);
        this.growlService.addMessage(new GrowlMessage("Lehrer wurde erstellt.", Severity.SUCCESS, 2000));

      });
  }
  deleteTeacher() {
    this.http
      .delete<any>(`api/teacher/${this.uuid}`, { observe: 'body' })
      .subscribe(_ => {
        console.log('Deleted teacher.');
        this.router.navigate(['management/teachers']);
        this.growlService.addMessage(new GrowlMessage("Lehrer wurde gelöscht.", Severity.SUCCESS, 2000));
      });
  }

  isNew = () => this.uuid === 'new';
}
