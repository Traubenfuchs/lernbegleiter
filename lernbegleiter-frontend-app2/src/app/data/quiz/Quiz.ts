import { BaseDto } from './../BaseDto';
import { Teacher } from './../Teacher';
import { QuizQuestion } from './QuizQuestion';
import { QuizRun } from './QuizRun';

export class Quiz extends BaseDto {
  name = ''
  description = ''
  questions: QuizQuestion[] = []
  author = new Teacher()
  quizType: string
  quizRuns: QuizRun[] = []
}
