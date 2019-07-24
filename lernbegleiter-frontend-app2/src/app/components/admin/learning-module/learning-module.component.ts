import {LearningModule} from './../../../data/LearningModule';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Component, OnInit} from '@angular/core';
import {UuidResponse} from 'src/app/data/UuidResponse';
import {SubModule} from 'src/app/data/SubModule';
import {Breadcrumb} from "../../../data/Breadcrumb";

@Component({
  selector: 'app-learning-module',
  templateUrl: './learning-module.component.html',
  styleUrls: ['./learning-module.component.scss']
})
export class LearningModuleComponent implements OnInit {
  learningModule: LearningModule;
  uuid: string;
  classUuid: string;
  subModules: SubModule[] = [];
  breadcrumbs: Breadcrumb[] = [];

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
    this.learningModule = {
      uuid: '',
      name: '',
      subModules: [],
      start:'',
      deadline: '',
      description: ''
    }
  }

  ngOnInit() {
    this.breadcrumbs = [
      Breadcrumb.inactiveOf('/management/learning-modules', 'Übersicht'),
      Breadcrumb.activeOf('Modul anlegen')
    ];

    this.uuid = this.route.snapshot.paramMap.get("learningModuleUUID")
    this.classUuid = this.route.snapshot.paramMap.get("classUUID")

    if (this.isModuleNew()) {
      this.learningModule.uuid = 'Automatisch'
    } else {
      this.loadLearningModule()
      this.loadSubModules()
    }
  }

  isModuleNew() {
    return this.uuid === 'new';
  }

  saveClick() {
    if (this.isModuleNew()) {
      this.createLearningModule()
    } else {
      this.updateLearningModule()
    }
  }

  deleteSubModule(uuid: string) {
    //TODO
  }

  loadSubModules() {
    console.log("Loading subModules...")
    this.http.get<SubModule[]>(`api/learning-module/${this.uuid}/sub-modules`)
    .subscribe(subModules => {
      console.log("Loaded subModules...")
      this.subModules = subModules
    })
  }

  updateLearningModule() {
    console.log('Updating learningModule...')
    this.http.patch<UuidResponse>(`api/class/${this.classUuid}/learning-module`, this.learningModule)
    .subscribe(uuidResponse => {
      console.log('Updated learningModule.')
      this.router.navigate([`management/class/${this.classUuid}`])
    })
  }

  createLearningModule() {
    console.log('Creating learningModule...')
    this.http.post<UuidResponse>(`api/class/${this.classUuid}/learning-module`, this.learningModule)
    .subscribe(uuidResponse => {
      console.log('Created learningModule.')
      this.router.navigate([`management/class/${this.classUuid}`])
    })
  }

  loadLearningModule() {
    console.log('Loading learningModule...')
    this.http
    .get<LearningModule>(`api/learning-module/${this.uuid}`, {observe: 'body'})
    .subscribe(learningModule => {
      console.log('Loaded learningModule.')
      this.learningModule = learningModule
    })
  }

  getCardHeaderForModule() {
    return this.isModuleNew() ? 'Modul anlegen' : 'Modul bearbeiten';
  }

  getCardDescriptionForModule() {
    return this.isModuleNew() ? 'Hier können Sie ein neues Modul anlegen.' : 'Hier können Sie ein Modul bearbeiten.';
  }
}
