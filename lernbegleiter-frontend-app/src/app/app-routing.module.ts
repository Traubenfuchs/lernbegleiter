import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LearningModulesComponent } from './components/admin/learning-modules/learning-modules.component';
import { HomeComponent } from './components/general/home/home.component';
import { LoginComponent } from './components/general/login/login.component';
import { LoginSuccessComponent } from './components/general/login-success/login-success.component';
import { ClassComponent } from './components/admin/class/class.component';
import { ClassesComponent } from './components/admin/classes/classes.component';
import { SubModulesComponent } from './components/admin/sub-modules/sub-modules.component';
import { SubModuleComponent } from './components/admin/sub-module/sub-module.component';
import { LearningModuleComponent } from './components/admin/learning-module/learning-module.component';
import { StudentComponent } from './components/admin/student/student.component';
import { GradeComponent } from './components/admin/grade/grade.component';
import { StudentsComponent } from './components/admin/students/students.component';
import { GradesComponent } from './components/admin/grades/grades.component';


const routes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'login-success', component: LoginSuccessComponent },

  { path: 'management/grades', component: GradesComponent }, // grade = Schulklasse / Schülerverband
  { path: 'management/grade/:gradeUUID', component: GradeComponent }, // grade = Schulklasse / Schülerverband

  { path: 'management/students', component: StudentsComponent },
  { path: 'management/student/:studentUUID', component: StudentComponent },

  { path: 'management/learning-modules', component: LearningModulesComponent },
  { path: 'management/learning-module/:learningModuleUUID', component: LearningModuleComponent },

  { path: 'management/learning-module/:learningModuleUUID/sub-modules', component: SubModulesComponent },
  { path: 'management/learning-module/:learningModuleUUID/sub-module/:subModuleUUID', component: SubModuleComponent },

  { path: 'management/classes', component: ClassesComponent }, // class = Fach
  { path: 'management/class/:classUUID', component: ClassComponent }, // class = Fach

  { path: 'student/:studentUUID/weekly-overview/:week', component: ClassesComponent }, // class = Fach
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
