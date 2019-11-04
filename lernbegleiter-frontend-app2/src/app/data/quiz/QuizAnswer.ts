export class QuizAnswer {
  uuid: string
  content = ''
  internalId = new Date().getTime() * Math.random()
  correct = false
  tickedCorrectly = false
  position: number
}
