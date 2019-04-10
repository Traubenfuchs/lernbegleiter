import { SubModule } from './SubModule';

export class LearningModule {
	uuid: string = ''
	name: string = ''
	subModules: SubModule[]
	deadline: string
	description: string
}