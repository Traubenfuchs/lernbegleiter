import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'quizRunState'
})
export class QuizRunStatePipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    if (value === 'CREATED') {
      return "erzeugt"
    }

    if (value === 'WAITING_FOR_ANSWERS') {
      return "Warten auf Antwortwn"
    }

    if (value === 'WAITING_FOR_NEXT_QUESTION') {
      return "Warten auf nächste Frage"
    }

    if (value === 'DONE') {
      return "abgeschlossen"
    }

    return value;
  }

}
