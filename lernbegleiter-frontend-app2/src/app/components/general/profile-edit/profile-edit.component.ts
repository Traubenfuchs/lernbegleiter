import { Severity } from './../../../data/Severity';
import { GrowlService } from './../../../services/growl.service';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/data/Student';
import { UuidResponse } from 'src/app/data/UuidResponse';
import { GrowlMessage } from 'src/app/data/GrowlMessage';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent {
  user: Student = new Student();
  uuid = 'new';

  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    private growlService: GrowlService) {
    this.uuid = this.route.snapshot.paramMap.get("userUUID");
    this.loadUser();
  }

  loadUser = () =>
    this.http.get<Student>(`api/user/${this.uuid}`)
      .subscribe(res => {
        this.user = res;
        this.user.uuid = this.uuid;
      })

  updateUser =
    () => this.http.patch<UuidResponse>(`api/user/${this.uuid}`, this.user)
      .subscribe(uuidResponse => {
        this.loadUser();
        this.growlService.addMessage(new GrowlMessage("User wurde upgedated.", Severity.SUCCESS, 2000));
      })
}
