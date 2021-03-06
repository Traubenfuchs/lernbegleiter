import { DisplayModuleComponent } from './components/display-module/display-module.component';
import { DisplayClassComponent } from './components/display-class/display-class.component';
import { MyQuizzesComponent } from './components/quiz/my-quizzes/my-quizzes.component';
import { MassRegistrationComponent } from './components/admin/mass-registration/mass-registration.component';
import { MassRegistrationsComponent } from './components/admin/mass-registrations/mass-registrations.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { WeeklyOverviewLandingPageComponent } from './components/weekly-overview-landing-page/weekly-overview-landing-page.component';
import { QuizRunsComponent } from './components/quiz/quiz-runs/quiz-runs.component';
import { QuizRunComponent } from './components/quiz/quiz-run/quiz-run.component';
import { QuizComponent } from './components/quiz/quiz/quiz.component';
import { QuizesComponent } from './components/quiz/quizes/quizes.component';

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/general/login/login.component';
import { LoginSuccessComponent } from './components/general/login-success/login-success.component';
import { ClassComponent } from './components/admin/class/class.component';
import { LearningModuleComponent } from './components/admin/learning-module/learning-module.component';
import { StudentComponent } from './components/admin/student/student.component';
import { GradeComponent } from './components/admin/grade/grade.component';
import { StudentsComponent } from './components/admin/students/students.component';
import { GradesComponent } from './components/admin/grades/grades.component';
import { WeekOverviewComponent } from './components/week-overview/week-overview.component';
import { ProfileEditComponent } from './components/general/profile-edit/profile-edit.component';
import { TeachersComponent } from './components/admin/teachers/teachers.component';
import { TeacherComponent } from './components/admin/teacher/teacher.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'login', component: LoginComponent },
  { path: 'login-success', component: LoginSuccessComponent },

  { path: 'management/grades', component: GradesComponent }, // grade = Schulklasse / Schülerverband
  { path: 'management/grade/:gradeUUID', component: GradeComponent }, // grade = Schulklasse / Schülerverband

  { path: 'management/teachers', component: TeachersComponent },
  { path: 'management/teacher/:teacherUUID', component: TeacherComponent },

  { path: 'management/students', component: StudentsComponent },
  { path: 'management/student/:studentUUID', component: StudentComponent },

  { path: 'management/class/:classUUID/learning-module/:learningModuleUUID', component: LearningModuleComponent },

  { path: 'management/class/:classUUID', component: ClassComponent }, // class = Fach

  { path: 'student/:studentUUID/weekly-overview', component: WeeklyOverviewLandingPageComponent }, // class = Fach
  { path: 'student/:studentUUID/weekly-overview/:week/:year', component: WeekOverviewComponent }, // class = Fach

  { path: 'management/quizzes', component: QuizesComponent },
  { path: 'management/quiz/:quizUUID', component: QuizComponent },
  { path: 'management/quiz/:quizUUID/quiz-runs', component: QuizRunsComponent },
  { path: 'management/quiz/:quizUUID/quiz-run/:quizRunUUID', component: QuizRunComponent },
  { path: 'my-quizzes', component: MyQuizzesComponent },

  { path: 'management/mass-registrations', component: MassRegistrationsComponent },
  { path: 'management/mass-registration/:massRegistrationUUID', component: MassRegistrationComponent },

  { path: 'display-module/:moduleUUID', component: DisplayModuleComponent },
  { path: 'display-class/:classUUID', component: DisplayClassComponent },

  { path: 'user/:userUUID/profile', component: ProfileEditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {
    onSameUrlNavigation: 'reload'
  })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
