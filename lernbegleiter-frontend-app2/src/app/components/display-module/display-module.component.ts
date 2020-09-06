import { LearningModule } from 'src/app/data/LearningModule';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-display-module',
  templateUrl: './display-module.component.html',
  styleUrls: ['./display-module.component.scss']
})
export class DisplayModuleComponent {
  uuid: string;
  learningModule = new LearningModule();

  constructor(public router: Router,
    public http: HttpClient,
    private route: ActivatedRoute
  ) {
    this.uuid = this.route.snapshot.paramMap.get("moduleUUID");
    this.http.get<LearningModule>(`api/learning-module/${this.uuid}`).subscribe(x => {
      this.learningModule = x;
      console.log(this.learningModule.description);
    });
  }
}
