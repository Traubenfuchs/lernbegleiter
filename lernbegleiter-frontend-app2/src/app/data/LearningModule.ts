import { SubModule } from './SubModule';

export class LearningModule {
  uuid: string = ''
  name: string = ''
  subModules: SubModule[]
  start: string
  deadline: string
  description: string

  constructor(uuid: string, name: string, subModules: SubModule[], deadline: string, description: string) {
    this.uuid = uuid;
    this.name = name;
    this.subModules = subModules;
    this.deadline = deadline;
    this.description = description;
  }
}
