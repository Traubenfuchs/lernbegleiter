import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { SubModule } from 'src/app/data/SubModule';
import { UuidResponse } from 'src/app/data/UuidResponse';
import { Location } from '@angular/common';

@Component({
  selector: 'app-sub-module',
  templateUrl: './sub-module.component.html',
  styleUrls: ['./sub-module.component.scss']
})
export class SubModuleComponent implements OnInit {
  uuid: string
  learningModuleUuid: string
  subModule: SubModule = new SubModule()
  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute, private _location: Location) {
    this.route.params.subscribe(params => {
      this.ngOnInit()
    });
  }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("subModuleUUID")
    this.learningModuleUuid = this.route.snapshot.paramMap.get("learningModuleUUID")

    if (this.isNew()) {
      this.subModule.uuid = 'Automatisch'
    } else {
      this.loadSubModule()
    }
  }

  isNew=()=>this.uuid === 'new'

  updateSubModule() {
    console.log('Updating subModule...')
    this.http.patch<UuidResponse>(`api/sub-module/${this.uuid}`, this.subModule)
      .subscribe(uuidResponse => {
        console.log('Updated subModule.')
        this._location.back()
      })
  }

  createSubModule() {
    console.log('Creating subModule...')
    this.http.post<UuidResponse>(`api/learning-module/${this.learningModuleUuid}/sub-module`, this.subModule)
      .subscribe(uuidResponse => {
        console.log('Created subModule.')
        this._location.back()
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
