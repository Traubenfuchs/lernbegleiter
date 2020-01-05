import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'quizType'
})
export class QuizTypePipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    if (value === 'ONE_QUESTION_AT_A_TIME') {
      return 'Einzelne Frage mit Timelimit';
    }
    if (value === 'FREE_ANSWERING') {
      return 'freies Antworten / take home';
    }
    if (value === 'FINISH_SELF') {
      return 'selbst abschließbar';
    }
    return value;
  }

}
