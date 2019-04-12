export enum Severity {
  SUCCESS = 'ease-slide-in-top alert alert-success',
  INFO = 'ease-slide-in-top alert alert-info',
  WARNING = 'ease-slide-in-top alert alert-warning',
  ERROR = 'ease-slide-in-top alert alert-danger',

  SUCCESS_FADE_OUT = 'ease-slide-out-top alert alert-success',
  INFO_FADE_OUT = 'ease-slide-out-top alert alert-info',
  WARNING_FADE_OUT = 'ease-slide-out-top alert alert-warning',
  ERROR_FADE_OUT = 'ease-slide-out-top alert alert-danger',
}

export class SeverityHandler {
  static fadeOut(severity: Severity): Severity {
    switch (severity) {
      case Severity.INFO:
        return Severity.INFO_FADE_OUT;
      case Severity.ERROR:
        return Severity.ERROR_FADE_OUT;
      case Severity.WARNING:
        return Severity.WARNING_FADE_OUT;
      case Severity.SUCCESS:
        return Severity.SUCCESS_FADE_OUT;
    }
  }
}

