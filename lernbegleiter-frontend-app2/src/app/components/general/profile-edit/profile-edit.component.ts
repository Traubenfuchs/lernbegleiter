import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/data/Student';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.component.html',
  styleUrls: ['./profile-edit.component.scss']
})
export class ProfileEditComponent implements OnInit {

  user: Student = new Student();
  uuid = 'new';

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
    this.uuid = this.route.snapshot.paramMap.get("userUUID");
    this.loadUser();
  }

  ngOnInit() {

  }

  loadUser() {
    console.log('Loading user...');

    this.http.get<Student>(`api/user/${this.uuid}`)
      .subscribe(res => {
        console.log(`Loaded user ${this.uuid}`);
        this.user = res;
        this.user.uuid = this.uuid;
      });
  }

  updateUser() {
    console.log('Updating user...');
    this.http.patch<UuidResponse>(`api/user/${this.uuid}`, this.user)
      .subscribe(uuidResponse => {
        console.log("updated user, reloading user...");
        this.loadUser();
      });
  }

}
