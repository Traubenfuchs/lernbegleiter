import {Grade} from '../../../data/Grade';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Component, OnInit} from '@angular/core';
import {Class} from 'src/app/data/Class';
import {UuidResponse} from 'src/app/data/UuidResponse';
import {Breadcrumb} from "../../../data/Breadcrumb";

@Component({
  selector: 'app-class',
  templateUrl: './class.component.html',
  styleUrls: ['./class.component.scss']
})
export class ClassComponent implements OnInit {
  class: Class = new Class();
  uuid: string;
  grades: Grade[] = [];
  breadcrumbs: Breadcrumb[] = [];

  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.breadcrumbs = [
      Breadcrumb.inactiveOf('/management/classes', 'Übersicht'),
      Breadcrumb.activeOf(this.isClassNew() ? 'Neues Fach' : 'Fach bearbeiten')
    ];

    this.uuid = this.route.snapshot.paramMap.get("classUUID");
    this.loadGrades()
    if (this.isClassNew()) {
      this.class.uuid = 'Automatisch'
      this.class.gradeName = sessionStorage.getItem('preferedGradeName')
    } else {
      this.loadClass()
    }
  }

  isClassNew() {
    return this.uuid === 'new';
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
    .subscribe(() => this.loadClass(),
    );

  }

  createNewClass() {
    console.log('creating class...')
    this.http.post<UuidResponse>('api/class', this.class)
    .subscribe(uuidResponse => {
      this.router.navigate([`management/class/${uuidResponse.uuid}`])
    })
  }

  deleteClick() {
    console.log('deleting class...')
    this.http
    .delete<any>(`api/class/${this.uuid}`, {observe: 'body'})
    .subscribe(_ => {
      this.router.navigate(['management/classes'])
    })
  }

  getCardHeaderForClass() {
    return this.isClassNew() ? 'Neues Fach anlegen' : ' Fach bearbeiten';
  }

  getCardDescriptionForClass() {
    return this.isClassNew() ? 'Hier kannst du ein neues Fach anlegen.' : 'Hier kannst du das ausgewählte Fach bearbeiten.';
  }
}
