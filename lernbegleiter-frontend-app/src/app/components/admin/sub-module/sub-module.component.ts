import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { SubModule } from 'src/app/data/SubModule';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-sub-module',
  templateUrl: './sub-module.component.html',
  styleUrls: ['./sub-module.component.scss']
})
export class SubModuleComponent implements OnInit {
  uuid: string
  learningModuleUuid: string
  subModule: SubModule = new SubModule()
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("subModuleUUID")
    this.learningModuleUuid = this.route.snapshot.paramMap.get("learningModuleUUID")

    if (this.uuid === 'new') {
      this.subModule.uuid = 'Automatisch'
    } else {
      this.loadSubModule()
    }
  }

  saveClick() {
    if (this.uuid === 'new') {
      this.createSubModule()
    } else {
      this.updateSubModule()
    }
  }

  updateSubModule() {
    console.log('Updating subModule...')
    this.http.patch<UuidResponse>(`api/sub-module/${this.uuid}`, this.subModule)
      .subscribe(uuidResponse => {
        console.log('Updated subModule.')
        //this.router.navigate([`management/class/${this.classUuid}`])
      })
  }

  createSubModule() {
    console.log('Creating subModule...')
    this.http.post<UuidResponse>(`api/learning-module/${this.learningModuleUuid}/sub-module`, this.subModule)
      .subscribe(uuidResponse => {
        console.log('Created subModule.')
        //this.router.navigate([`management/class/${this.classUuid}`])
      })
  }

  loadSubModule() {
    console.log('Loading subModule...')
    this.http
      .get<SubModule>(`api/sub-module/${this.uuid}`, { observe: 'body' })
      .subscribe(subModule => {
        console.log('Loaded subModule.')
        this.subModule = subModule
      })
  }

}
