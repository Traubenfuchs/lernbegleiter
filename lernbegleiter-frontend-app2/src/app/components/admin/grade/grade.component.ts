import { Severity } from './../../../data/Severity';
import { GrowlService } from './../../../services/growl.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Grade } from './../../../data/Grade';
import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { GrowlMessage } from 'src/app/data/GrowlMessage';

@Component({
  selector: 'app-grade',
  templateUrl: './grade.component.html',
  styleUrls: ['./grade.component.scss']
})
export class GradeComponent {
  public grade: Grade = new Grade();
  public uuid = 'new';
  public grades: Grade[] = [];
  public sourceGradeForImport = 'default';

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private growlService: GrowlService) {

    this.uuid = this.route.snapshot.paramMap.get("gradeUUID");
    this.loadGrade();
    this.loadGrades();
  }

  loadGrade() {
    this.http.get<Grade>(`api/grade/${this.uuid}`)
      .subscribe(res => {
        this.grade = res;
      });
  }

  loadGrades() {
    this.http.get<Grade[]>('api/grades').subscribe(
      res => this.grades = res,
      err => console.log(err)
    );
  }

  ngOnChanges(changes: SimpleChanges) {
    this.loadGrade();
  }

  deleteStudentFromGrade(studentUuid: string) {
    this.http.delete<any>(`api/grade/${this.uuid}/student/${studentUuid}`)
      .subscribe(res => {
        this.loadGrade();
        this.growlService.addMessage(new GrowlMessage("Schüler wurde von Klasse entfernt.", Severity.SUCCESS, 2000));
      });
  }
  deleteClass(uuid: string) {
    this.http.delete<any>(`api/class/${uuid}`)
      .subscribe(res => {
        this.loadGrade();
        this.loadGrades();
        this.growlService.addMessage(new GrowlMessage("Fach wurde gelöscht.", Severity.SUCCESS, 2000));
      });
  }
  import() {
    this.http.post<any>(`api/grade/${this.uuid}/import/${this.sourceGradeForImport}`, {})
      .subscribe(res => {
        this.loadGrade();
        this.growlService.addMessage(new GrowlMessage("Fächer wurden importiert.", Severity.SUCCESS, 2000));
      });
  }
}
