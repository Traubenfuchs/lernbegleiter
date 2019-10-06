import { SubModuleStudent } from './SubModuleStudent';

export class LearningModuleStudent {
	uuid = ''
	name = ''
	dueDate: Date
  finishedAt: Date
  late = false
  subModules: SubModuleStudent[] = []
  className = ''
}
