import { HttpClient } from '@angular/common/http';
import { Class } from './../../../data/Class';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {

  public classes: Class[]

  constructor(private http: HttpClient) { }

  ngOnInit() {
    this.loadClasses()
  }

  loadClasses() {
    console.log("Loading Classes...")
    this.http
      .get<Class[]>(`api/classes`, { observe: 'body' })
      .subscribe(classes => {
        console.log("Loaded classes.")
        this.classes = classes
      })
  }

  deleteClass(classUuid: string) {
    console.log("Deleting class...")
    //TODO
  }

}
