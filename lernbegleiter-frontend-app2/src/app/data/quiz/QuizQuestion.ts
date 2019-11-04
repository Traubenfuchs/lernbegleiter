import { QuizAnswer } from './QuizAnswer';
import { Lob } from '../Lob';

export class QuizQuestion {
  uuid: string
  content = ''
  answers: QuizAnswer[] = []
  internalId = new Date().getTime() * Math.random()
  position: number
  lob: Lob = new Lob()
  timeLimit = 40
  answeredCorrectly: boolean = undefined
  answerCount: number
}
