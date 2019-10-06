import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Class } from 'src/app/data/Class';
import { LearningModule } from 'src/app/data/LearningModule';
import { UuidResponse } from 'src/app/data/UuidResponse';

import { Breadcrumb } from '../../../data/Breadcrumb';
import { Grade } from '../../../data/Grade';

@Component({
  selector: 'app-class',
  templateUrl: './class.component.html',
  styleUrls: ['./class.component.scss']
})
export class ClassComponent implements OnInit {
  class: Class = new Class()
  uuid: string
  grades: Grade[] = []
  learningModules: LearningModule[] = []

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      this.ngOnInit()
    });
   }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("classUUID")
    this.loadGrades()
    this.loadLearningModules()
    if (this.isClassNew()) {
      this.class.uuid = 'Automatisch'
      this.class.gradeName = sessionStorage.getItem('preferedGradeName')
    } else {
      this.loadClass()
    }
  }

  isClassNew = () => this.uuid === 'new'

  loadLearningModules = () => {
    console.log('Loading LearningModules...')

    this.http.get<LearningModule[]>(`api/class/${this.uuid}/learning-modules`)
      .subscribe(res => {
        console.log('LearningModules loaded.')
        this.learningModules = res
      })
  }

  loadGrades() {
    console.log('Loading grades...')
    this.http.get<Grade[]>('api/grades')
      .subscribe(res => {
        console.log('Grades loaded.')
        this.grades = res
      })
  }

  loadClass() {
    console.log('Loading class...')
    this.http.get<Class>(`api/class/${this.uuid}`)
      .subscribe(c => {
        console.log('Class loaded.')
        this.class = c
      })
  }

  saveClick() {
    if (this.uuid === 'new') {
      this.createNewClass()
    } else {
      this.updateClass()
    }
  }

  updateClass() {
    console.log('updating class...')
    this.http.patch<UuidResponse>(`api/class/${this.uuid}`, this.class)
      .subscribe(() => {
        this.loadClass()
        console.log("udated class")
        this.router.navigate([`management/classes/`])
      });
  }

  createNewClass() {
    console.log('creating class...')
    this.http.post<UuidResponse>('api/class', this.class)
      .subscribe(uuidResponse => {
        console.log("created class.")
        this.router.navigate([`management/class/${uuidResponse.uuid}`])
      })
  }

  deleteClick() {
    console.log('deleting class...')
    this.http
      .delete<any>(`api/class/${this.uuid}`)
      .subscribe(_ => {
        this.router.navigate(['management/classes'])
      })
  }

  getCardHeaderForClass = () => this.isClassNew() ? 'Neues Fach anlegen' : ' Fach bearbeiten'
  getCardDescriptionForClass = () => this.isClassNew() ? 'Hier kannst du ein neues Fach anlegen.' : 'Hier kannst du das ausgewählte Fach bearbeiten.';

  deleteLearningModule(uuid: string) {
    console.log('deleting learning module...')
    this.http
      .delete<any>(`api/learning-module/${uuid}`)
      .subscribe(_ => {
        this.ngOnInit()
      })
  }
}
