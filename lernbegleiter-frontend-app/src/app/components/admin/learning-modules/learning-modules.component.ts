import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Component, OnInit} from '@angular/core';
import {LearningModule} from "../../../data/LearningModule";
import {Class} from "../../../data/Class";

@Component({
  selector: 'app-learning-modules',
  templateUrl: './learning-modules.component.html',
  styleUrls: ['./learning-modules.component.scss']
})
export class LearningModulesComponent implements OnInit {

  learningModules: LearningModule[] = [];
  classes: Class[] = [];
  selectedClass: Class;

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
    this.learningModules = [
      new LearningModule('1', 'Modul A', [], '1.1.2000', 'Das ist ein Modul'),
      new LearningModule('2', 'Modul B', [], '1.1.2000', 'Das ist ein Modul'),
      new LearningModule('3', 'Modul C', [], '1.1.2000', 'Das ist ein Modul')
    ]
  }

  ngOnInit() {
    this.loadLearningModules();
    this.loadClasses();
  }

  loadLearningModules() {
    console.log('Loading learningModules...')
    this.http.get<LearningModule[]>(`api/class/${this.selectedClass}/learning-modules`)
    .subscribe(res => {
      console.log('LearningModules loaded.')
      this.learningModules = res
    })
  }

  loadClasses() {
    this.http
    .get<Class[]>(`api/classes`, {observe: 'body'})
    .subscribe(classes => this.classes = classes,
    );
  }

  deleteLearningModule(learningModuleUuid: string) {
    //TODO
  }

  isClassSelected() {
    return this.selectedClass !== undefined || this.selectedClass != null;
  }

  setSelection(selectedClass: Class) { // TODO : weiter
    this.selectedClass = selectedClass;
    console.log(selectedClass)
  }

}
