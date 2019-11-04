import { QuizQuestion } from './QuizQuestion'
import { BaseDto } from './../BaseDto'
import { QuizRunState } from './QuizRunState'

export class QuizRun extends BaseDto {
  nextTimeLimit: string
  currentQuestions: QuizQuestion[] = []
  state: QuizRunState
  quizRunType: string
}
