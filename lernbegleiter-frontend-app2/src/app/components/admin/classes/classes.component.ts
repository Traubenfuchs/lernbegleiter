import { HttpClient } from '@angular/common/http';
import { Class } from './../../../data/Class';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {

  public classes: Class[];

  isLoadingClasses = true;

  constructor(private http: HttpClient) {
    this.loadClasses();
  }

  ngOnInit() {

  }

  loadClasses() {
    this.isLoadingClasses = true;
    this.http
      .get<Class[]>(`api/classes`)
      .subscribe(
        classes => this.classes = classes,
        (err) => console.log(err),
        () => this.isLoadingClasses = false
      );
  }

  deleteClass(classUuid
    :
    string
  ) {
    console.log("Deleting class...");
    // TODO
  }

}
