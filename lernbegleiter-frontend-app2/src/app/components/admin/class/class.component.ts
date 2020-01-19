import { Severity } from './../../../data/Severity';
import { GrowlService } from './../../../services/growl.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Class } from 'src/app/data/Class';
import { LearningModule } from 'src/app/data/LearningModule';
import { UuidResponse } from 'src/app/data/UuidResponse';

import { Grade } from '../../../data/Grade';
import { GrowlMessage } from 'src/app/data/GrowlMessage';

@Component({
  selector: 'app-class',
  templateUrl: './class.component.html',
  styleUrls: ['./class.component.scss']
})
export class ClassComponent {
  class: Class = new Class();
  uuid: string;
  grades: Grade[] = [];
  learningModules: LearningModule[] = [];

  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    private growlService: GrowlService) {
    this.route.params.subscribe(() => this.load());
  }

  load() {
    this.uuid = this.route.snapshot.paramMap.get("classUUID");
    this.loadGrades();
    this.loadLearningModules();
    if (this.isClassNew()) {
      this.class.uuid = 'Automatisch';
      this.class.gradeName = sessionStorage.getItem('preferedGradeName');
    } else {
      this.loadClass();
    }
  }

  isClassNew = () => this.uuid === 'new';

  loadLearningModules = () => {
    this.http.get<LearningModule[]>(`api/class/${this.uuid}/learning-modules`)
      .subscribe(res => {
        this.learningModules = res;
      });
  }

  loadGrades() {
    this.http.get<Grade[]>('api/grades')
      .subscribe(res => {
        this.grades = res;
      });
  }

  loadClass() {
    this.http.get<Class>(`api/class/${this.uuid}`)
      .subscribe(c => {
        this.class = c;
      });
  }

  saveClick() {
    if (this.uuid === 'new') {
      this.createNewClass();
    } else {
      this.updateClass();
    }
  }

  updateClass() {
    this.http.patch<UuidResponse>(`api/class/${this.uuid}`, this.class)
      .subscribe(() => {
        this.loadClass();
        this.router.navigate([`management/classes/`]);
        this.growlService.addMessage(new GrowlMessage("Fach wurde upgedated.", Severity.SUCCESS, 2000));
      });
  }

  createNewClass() {
    this.http.post<UuidResponse>('api/class', this.class)
      .subscribe(uuidResponse => {
        this.router.navigate([`management/class/${uuidResponse.uuid}`]);
        this.growlService.addMessage(new GrowlMessage("Fach wurde erstellt.", Severity.SUCCESS, 2000));
      });
  }

  deleteClick() {
    this.http
      .delete<any>(`api/class/${this.uuid}`)
      .subscribe(_ => {
        this.router.navigate(['management/classes']);
        this.growlService.addMessage(new GrowlMessage("Fach wurde gelöscht.", Severity.SUCCESS, 2000));
      });
  }

  getCardHeaderForClass = () => this.isClassNew() ? 'Neues Fach anlegen' : ' Fach bearbeiten';
  getCardDescriptionForClass = () => this.isClassNew() ? 'Hier kannst du ein neues Fach anlegen.' : 'Hier kannst du das ausgewählte Fach bearbeiten.';

  deleteLearningModule(uuid: string) {
    this.http
      .delete<any>(`api/learning-module/${uuid}`)
      .subscribe(_ => {
        this.load();
        this.growlService.addMessage(new GrowlMessage("Modul wurde gelöscht.", Severity.SUCCESS, 2000));
      });
  }
}
