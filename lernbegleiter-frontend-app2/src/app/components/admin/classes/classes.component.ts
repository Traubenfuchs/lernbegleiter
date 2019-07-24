import {HttpClient} from '@angular/common/http';
import {Class} from './../../../data/Class';
import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {

  public classes: Class[]

  isLoadingClasses: boolean = true;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.loadClasses()
  }

  loadClasses() {
    this.isLoadingClasses = true;
    this.http
    .get<Class[]>(`api/classes`, {observe: 'body'})
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
    console.log("Deleting class...")
    //TODO
  }

}
