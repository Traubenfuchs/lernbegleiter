import { Teacher } from './../Teacher';
import { QuizQuestion } from './QuizQuestion';
import { QuizRun } from './QuizRun';

export class Quiz {
  uuid: string
  name = ''
  description = ''
  questions: QuizQuestion[] = []
  author = new Teacher()
  quizType: string
  quizRuns: QuizRun[] = []

}
