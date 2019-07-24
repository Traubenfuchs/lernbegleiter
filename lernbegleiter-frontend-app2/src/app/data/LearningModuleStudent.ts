import { SubModuleStudent } from './SubModuleStudent';

export class LearningModuleStudent {
	uuid: string
	name: string
	dueDate: Date
	finishedAt: Date
	subModules: SubModuleStudent[] = []
}