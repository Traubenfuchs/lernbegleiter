import { Severity } from './../../../data/Severity';
import { GrowlService } from './../../../services/growl.service';
import { Router } from '@angular/router';
import { Grade } from './../../../data/Grade';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { UuidResponse } from 'src/app/data/UuidResponse';
import { GrowlMessage } from 'src/app/data/GrowlMessage';

@Component({
  selector: 'app-grades',
  templateUrl: './grades.component.html',
  styleUrls: ['./grades.component.scss']
})
export class GradesComponent {
  grades: Grade[];
  displayedGrades: Grade[];
  newGradeName: string;
  isLoadingGrades = true;
  _filterWord = '';

  constructor(
    private http: HttpClient,
    public router: Router,
    private growlService: GrowlService) {
    this.loadGrades();
  }

  get filterWord(): string {
    return this._filterWord;
  }

  set filterWord(newFilterWord: string) {
    // this.valueChanged(this._filterWord, newFilterWord)
    this._filterWord = newFilterWord;
    this.filterGrades();
  }

  filterGrades() {
    const filterWords = this._filterWord.toUpperCase().split(" ");

    this.displayedGrades =
      this.grades.filter(g =>
        filterWords.every(filterWord =>
          [g.name]
            .some(candidate => candidate.toUpperCase().indexOf(filterWord) >= 0)
        )
      );
  }


  loadGrades() {
    this.isLoadingGrades = true;
    this.http.get<Grade[]>('api/grades').subscribe(
      res => {
        this.grades = res;
        this.grades.sort((l, r) => l.name.localeCompare(r.name));
        this.filterGrades();
      },
      err => console.log(err),
      () => this.isLoadingGrades = false
    );
  }

  createGrade() {
    this.http.post<UuidResponse>('api/grade', { name: this.newGradeName })
      .subscribe(res => {
        this.newGradeName = "";
        this.loadGrades();
        this.growlService.addMessage(new GrowlMessage("Klasse wurde erstellt.", Severity.SUCCESS, 2000));
      });
  }

  deleteGrade(uuid: string) {
    this.http.delete<any>('api/grade/' + uuid)
      .subscribe(() => {
        this.loadGrades();
        this.growlService.addMessage(new GrowlMessage("Klasse wurde gelöscht.", Severity.SUCCESS, 2000));
      });
  }
}




