import {Severity, SeverityHandler} from "./Severity";

export class GrowlMessage {
  message: string;
  severity: Severity;
  msToDisappear: number;

  constructor(message: string, severity: Severity, msToDisappear: number) {
    this.message = message;
    this.severity = severity;
    this.msToDisappear = msToDisappear;

    setTimeout(() => {
      this.severity = SeverityHandler.fadeOut(this.severity);
    }, msToDisappear);
  }
}
