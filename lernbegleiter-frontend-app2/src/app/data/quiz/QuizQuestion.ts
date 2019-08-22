import { QuizAnswer } from './QuizAnswer';

export class QuizQuestion {
  uuid = ''
  content = ''
  answers: QuizAnswer[] = []
}
