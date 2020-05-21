import { Severity } from './../../../data/Severity';
import { GrowlMessage } from './../../../data/GrowlMessage';
import { GrowlService } from './../../../services/growl.service';
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
export class LearningModuleComponent {
  uuid: string;
  classUuid: string;
  isLoadingSubModules = true;

  deadlineFormControl: FormControl;
  learningModuleFormGroup: FormGroup;

  constructor(
    public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute,
    private formbuilder: FormBuilder,
    private growlService: GrowlService) {
    this.uuid = this.route.snapshot.paramMap.get("learningModuleUUID");
    this.classUuid = this.route.snapshot.paramMap.get("classUUID");

    this.deadlineFormControl = new FormControl(undefined, [Validators.required]);
    this.learningModuleFormGroup = this.formbuilder.group({
      name: [undefined, [Validators.required]],
      deadline: this.deadlineFormControl,
      description: [undefined, []],
      start: [undefined, [Validators.required]],
      color: ['#ffffff', []]
    }, {
      validator: fg => {
        const startC = fg.controls.start;
        const deadlineC = fg.controls.deadline;
        const startV = startC.value;
        const deadlineV = deadlineC.value;

        if (deadlineC.errors && !deadlineC.errors.mustBeBefore) {
          return;
        }

        if (!startV || !deadlineV) {
          deadlineC.setErrors(null);
          return;
        }

        if (new Date(deadlineV).getTime() < new Date(startV).getTime()) {
          deadlineC.setErrors({ mustBeBefore: true });
        }
      }
    });

    if (this.uuid !== 'new') {
      this.loadLearningModule();
    }
  }

  saveClick() {
    if (this.uuid === 'new') {
      this.createLearningModule();
    } else {
      this.updateLearningModule();
    }
  }

  updateLearningModule() {
    this.http.put<UuidResponse>(`api/learning-module`, this.formToData())
      .subscribe(uuidResponse => {
        this.router.navigate([`management/class/${this.classUuid}`]);
        this.growlService.addMessage(new GrowlMessage("Modul wurde upgedated.", Severity.SUCCESS, 2000));
      });
  }

  createLearningModule() {
    this.http.post<UuidResponse>(`api/class/${this.classUuid}/learning-module`, this.formToData())
      .subscribe(uuidResponse => {
        this.router.navigate([`management/class/${this.classUuid}`]);
        this.growlService.addMessage(new GrowlMessage("Modul wurde erstellt.", Severity.SUCCESS, 2000));
      });
  }

  loadLearningModule() {
    this.http
      .get<LearningModule>(`api/learning-module/${this.uuid}`, { observe: 'body' })
      .subscribe(learningModule => {
        const c = this.learningModuleFormGroup.controls;
        c.name.setValue(learningModule.name);
        c.description.setValue(learningModule.description);
        c.deadline.setValue(learningModule.deadline);
        c.start.setValue(learningModule.start);
        c.color.setValue(learningModule.color);
      });
  }

  formToData() {
    const result = new LearningModule();
    const c = this.learningModuleFormGroup.controls;
    result.uuid = this.uuid;
    result.name = c.name.value;
    result.description = c.description.value;
    result.deadline = c.deadline.value;
    result.start = c.start.value;
    result.color = c.color.value;

    return result;
  }

  getCardHeaderForModule() {
    return this.uuid === 'new' ? 'Modul anlegen' : 'Modul bearbeiten';
  }

  getCardDescriptionForModule() {
    return this.uuid === 'new' ? 'Hier können Sie ein neues Modul anlegen.' : 'Hier können Sie ein Modul bearbeiten.';
  }
  valueMissing() {
    const c = this.learningModuleFormGroup.controls;
    return [c.deadline, c.start, c.name].some(v => !v.value || v.value.length === 0);
  }
}
