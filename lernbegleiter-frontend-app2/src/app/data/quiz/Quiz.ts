import { Teacher } from './../Teacher';
import { QuizQuestion } from './QuizQuestion';
import { QuizRun } from './QuizRun';

export class Quiz {
  uuid = ''
  name = ''
  description = ''
  questions: QuizQuestion[] = []
  author = new Teacher()
  quizType = ''
  expirationDate: Date
  quizRuns: QuizRun[] = []
}
