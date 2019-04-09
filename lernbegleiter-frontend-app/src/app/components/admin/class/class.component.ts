import { Grade } from './../../../data/Grade';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Class } from 'src/app/data/Class';
import { UuidResponse } from 'src/app/data/UuidResponse';

@Component({
  selector: 'app-class',
  templateUrl: './class.component.html',
  styleUrls: ['./class.component.scss']
})
export class ClassComponent implements OnInit {
  class: Class = new Class()
  uuid: string
  grades: Grade[] = []


  constructor(public router: Router, public http: HttpClient, private route: ActivatedRoute) { }

  ngOnInit() {
    this.uuid = this.route.snapshot.paramMap.get("classUUID")
    this.loadGrades()
    if (this.uuid === 'new') {
      this.class.uuid = 'Automatisch'
      this.class.gradeName = sessionStorage.getItem('preferedGradeName')
    } else {
      this.loadClass()
    }
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
      .subscribe(uuidResponse => {
        this.loadClass()
      })
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
      .delete<any>(`api/class/${this.uuid}`, { observe: 'body' })
      .subscribe(_ => {
        this.router.navigate(['management/classes'])
      })
  }

}
