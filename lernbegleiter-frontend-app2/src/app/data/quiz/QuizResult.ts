import { BaseDto } from './../BaseDto';
import { QuizResultEntry } from './QuizResultEntry';

export class QuizResult extends BaseDto {
  entries: QuizResultEntry[];
}
