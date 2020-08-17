import { LobGeneric } from './LobGeneric';
import { LearningModule } from './LearningModule';

export class Class {
  uuid = '';
  name = '';
  gradeName = '';
  students: LearningModule[] = [];
  color = "#ffffff";
  lobs: LobGeneric[] = [];
}
