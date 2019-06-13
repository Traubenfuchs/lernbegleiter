import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { LearningModule } from "../../../data/LearningModule";
import { Class } from "../../../data/Class";

@Component({
  selector: 'app-learning-modules',
  templateUrl: './learning-modules.component.html',
  styleUrls: ['./learning-modules.component.scss']
})
export class LearningModulesComponent implements OnInit {

  learningModules: LearningModule[] = [];
  classes: Class[] = [];
  selectedClass: Class
  selectedClassUuid: string = undefined

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.loadLearningModules()
    this.loadClasses()
  }

  ///////////////////////////////////
  // REST read calls

  loadLearningModules() {
    console.log('Loading learningModules...')
    this.http.get<LearningModule[]>(`api/class/${this.selectedClassUuid}/learning-modules`)
      .subscribe(res => {
        console.log('LearningModules loaded.')
        this.learningModules = res
      })
  }

  loadClasses() {
    console.log("Loading Classes...")
    this.http
      .get<Class[]>(`api/classes`, { observe: 'body' })
      .subscribe(classes => {
        console.log('Classes loaded.')
        this.classes = classes
      });
  }

  //////////////////////////////
  // rest write calls

  deleteLearningModule(learningModuleUuid: string) {
    //TODO
  }

  ///////////////////////////////////
  // special UI interactions
  classChanged(className) {
    this.classes.forEach(clazz => {
      if (clazz.name === className) {
        this.selectedClassUuid = clazz.uuid
        this.loadLearningModules()
        return
      }
    })
  }
}
