import { LearningModuleStudent } from './LearningModuleStudent';

export class ClassCompletion {
  className = '';
  classUuid: string;
  deadline: Date;
  learningModulesStudent: LearningModuleStudent[] = [];
}
