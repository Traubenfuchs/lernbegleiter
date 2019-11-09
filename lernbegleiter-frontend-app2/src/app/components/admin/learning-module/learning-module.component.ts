import { Validators, FormControl } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { LearningModule } from './../../../data/LearningModule';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-learning-module',
  templateUrl: './learning-module.component.html',
  styleUrls: ['./learning-module.component.scss']
})
export class LearningModuleComponent implements OnInit {
  learningModule = new LearningModule()
  uuid: string
  classUuid: string
  isLoadingSubModules = true

  deadlineFormControl: FormControl;
  learningModuleFormGroup: FormGroup;

  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    private formbuilder: FormBuilder) {
  }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("learningModuleUUID")
    this.classUuid = this.route.snapshot.paramMap.get("classUUID")

    this.deadlineFormControl = new FormControl('', [Validators.required])
    this.learningModuleFormGroup = this.formbuilder.group({
      name: ['', [Validators.required]],
      deadline: this.deadlineFormControl,
      description: ['', []],
      start: ['', [Validators.required]]
    }, {
      validator: fg => {
        const startC = fg.controls.start;
        const deadlineC = fg.controls.deadline;
        const startV = startC.value
        const deadlineV = deadlineC.value

        if (deadlineC.errors && !deadlineC.errors.mustBeBefore) {
          return;
        }

        if (!startV || !deadlineV) {
          deadlineC.setErrors(null);
          return
        }

        if (new Date(deadlineV).getTime() < new Date(startV).getTime()) {
          deadlineC.setErrors({ mustBeBefore: true });
        }
      }
    })

    if (this.isModuleNew()) {
      this.learningModule.uuid = 'Automatisch'
    } else {
      this.loadLearningModule()
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

  updateLearningModule() {
    console.log('Updating learningModule...')
    this.http.put<UuidResponse>(`api/learning-module`, this.learningModule)
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
      .get<LearningModule>(`api/learning-module/${this.uuid}`, { observe: 'body' })
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

  valueMissing() {
    return Object.values(this.learningModuleFormGroup.controls).some(c => !!c.errors && c.errors.required)
  }
}
