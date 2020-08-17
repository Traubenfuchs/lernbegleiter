import { LobGeneric } from './LobGeneric';
import { BaseDto } from './BaseDto';

export class LearningModule extends BaseDto {
  name = '';
  start: string;
  deadline: string;
  description = "";
  color = "#ffffff";
  lobs: LobGeneric[] = [];
}
