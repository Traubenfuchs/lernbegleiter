import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ClassComponent} from './components/admin/class/class.component';
import {ClassesComponent} from './components/admin/classes/classes.component';
import {GradeComponent} from './components/admin/grade/grade.component';
import {GradesComponent} from './components/admin/grades/grades.component';
import {LearningModuleComponent} from './components/admin/learning-module/learning-module.component';
import {LearningModulesComponent} from './components/admin/learning-modules/learning-modules.component';
import {StudentComponent} from './components/admin/student/student.component';
import {StudentsComponent} from './components/admin/students/students.component';
import {SubModuleComponent} from './components/admin/sub-module/sub-module.component';
import {SubModulesComponent} from './components/admin/sub-modules/sub-modules.component';
import {HomeComponent} from './components/general/home/home.component';
import {LoginSuccessComponent} from './components/general/login-success/login-success.component';
import {LoginComponent} from './components/general/login/login.component';
import {SecurityInterceptor} from './services/security-interceptor.service';
import {WeekOverviewComponent} from './components/week-overview/week-overview.component';
import { LoaderComponent } from './components/general/loader/loader.component';
import { BreadcrumbComponent } from './components/general/breadcrumb/breadcrumb.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    LoginSuccessComponent,
    StudentsComponent,
    GradesComponent,
    GradeComponent,
    StudentComponent,
    LearningModuleComponent,
    LearningModulesComponent,
    SubModuleComponent,
    SubModulesComponent,
    ClassComponent,
    ClassesComponent,
    WeekOverviewComponent,
    LoaderComponent,
    BreadcrumbComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: SecurityInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
