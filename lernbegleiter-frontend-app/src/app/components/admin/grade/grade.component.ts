import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient} from '@angular/common/http';
import {Grade} from './../../../data/Grade';
import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-grade',
  templateUrl: './grade.component.html',
  styleUrls: ['./grade.component.scss']
})
export class GradeComponent implements OnInit {
  public grade: Grade = new Grade()

  @Input() gradeUUID;

  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.loadGrade()
  }

  // loadClasses() {
  //   this.http.get<Class>(`api/grade/${this.route.snapshot.paramMap.get("gradeUUID")}`)
  //     .subscribe(res => {
  //       console.log("Loaded grade")
  //       this.grade = res
  //     })
  // }

  loadGrade() {
    this.http.get<Grade>(`api/grade/${this.gradeUUID}`)
    .subscribe(res => {
      console.log("Loaded grade")
      this.grade = res
    })
  }

  deleteFromGrade(uuid: string) {
    this.http.delete<any>(`api/grade/${this.gradeUUID}/student/${uuid}`)
    .subscribe(res => {
      console.log(`Student with uuid <${uuid}> deleted.`)
      this.loadGrade()
    })
  }
}
