import { QuizQuestion } from './QuizQuestion';
import { BaseDto } from './../BaseDto';
import { QuizRunState } from './QuizRunState';

export class QuizRun extends BaseDto {
  nextTimeLimit: string;
  nextTimeLimitForInput: string;
  currentQuestions: QuizQuestion[] = [];
  state: string;
  quizRunType: string;
  questionCount: number;
}
