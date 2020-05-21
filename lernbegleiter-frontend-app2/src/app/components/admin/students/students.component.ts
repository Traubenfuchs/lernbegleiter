import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Student } from 'src/app/data/Student';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.scss']
})
export class StudentsComponent {
  public students: Student[];
  public displayedStudents: Student[];
  public currentWeek: number;
  public year: number;
  public _filterWord = '';
  isLoadingStudent = true;

  constructor(private http: HttpClient) {
    this.currentWeek = new Date()['getWeekNumber' + '']();
    this.year = new Date().getFullYear() + (this.currentWeek === 1 ? 1 : 0);
    this.loadStudents();
  }

  get filterWord(): string {
    return this._filterWord;
  }

  set filterWord(newFilterWord: string) {
    // this.valueChanged(this._filterWord, newFilterWord)
    this._filterWord = newFilterWord;
    this.filterStudents();
  }

  filterStudents() {
    const filterWords = this._filterWord.toUpperCase().split(" ");

    this.displayedStudents =
      this.students.filter(s =>
        filterWords.every(filterWord =>
          [s.email,
          s.firstName,
          s.familyName,
          s.familyName,
          s.gradeName]
            .some(candidate => candidate.toUpperCase().indexOf(filterWord) >= 0)
        )
      );
  }

  loadStudents() {
    this.isLoadingStudent = true;
    this.http
      .get<Student[]>(`api/students`, { observe: 'body' })
      .subscribe(
        students => {
          this.students = students;
          this.filterStudents();
        },
        err => console.log(err),
        () => this.isLoadingStudent = false
      );
  }
}
