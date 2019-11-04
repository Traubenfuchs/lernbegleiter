import { SaneDateTimePipe } from './pipes/sane-date-time.pipe';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClassComponent } from './components/admin/class/class.component';
import { ClassesComponent } from './components/admin/classes/classes.component';
import { GradeComponent } from './components/admin/grade/grade.component';
import { GradesComponent } from './components/admin/grades/grades.component';
import { LearningModuleComponent } from './components/admin/learning-module/learning-module.component';
import { StudentComponent } from './components/admin/student/student.component';
import { StudentsComponent } from './components/admin/students/students.component';
import { SubModuleComponent } from './components/admin/sub-module/sub-module.component';
import { HomeComponent } from './components/general/home/home.component';
import { LoginSuccessComponent } from './components/general/login-success/login-success.component';
import { LoginComponent } from './components/general/login/login.component';
import { SecurityInterceptor } from './services/security-interceptor.service';
import { WeekOverviewComponent } from './components/week-overview/week-overview.component';
import { LoaderComponent } from './components/general/loader/loader.component';
import { BreadcrumbComponent } from './components/general/breadcrumb/breadcrumb.component';
import { GrowlComponent } from './components/general/growl/growl.component';
import { GrowlInterceptor } from "./services/growl-interceptor.service";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProfileEditComponent } from './components/general/profile-edit/profile-edit.component';
import { SaneDatePipe } from './pipes/sane-date.pipe';
import { TeachersComponent } from './components/admin/teachers/teachers.component';
import { TeacherComponent } from './components/admin/teacher/teacher.component';
import { QuizesComponent } from './components/quiz/quizes/quizes.component';
import { QuizComponent } from './components/quiz/quiz/quiz.component';
import { QuizRunsComponent } from './components/quiz/quiz-runs/quiz-runs.component';
import { QuizRunComponent } from './components/quiz/quiz-run/quiz-run.component';
import { QuizRunStatePipe } from './pipes/quiz-run-state.pipe';
import { QuizTypePipe } from './pipes/quiz-type.pipe';

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
    SubModuleComponent,
    ClassComponent,
    ClassesComponent,
    WeekOverviewComponent,
    LoaderComponent,
    BreadcrumbComponent,
    GrowlComponent,
    ProfileEditComponent,
    SaneDatePipe,
    SaneDateTimePipe,
    TeachersComponent,
    TeacherComponent,
    QuizesComponent,
    QuizComponent,
    QuizRunsComponent,
    QuizRunComponent,
    QuizRunStatePipe,
    QuizTypePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: SecurityInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: GrowlInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}
