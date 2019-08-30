import { QuizAnswer } from './QuizAnswer';

export class QuizQuestion {
  uuid: string
  content = ''
  answers: QuizAnswer[] = []
  internalId = new Date().getTime() * Math.random()
  position: number
}
