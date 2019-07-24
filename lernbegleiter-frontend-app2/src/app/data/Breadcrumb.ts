export class Breadcrumb {
  isActive: boolean;
  routerUrl: string;
  pagename: string;

  private constructor(isActive: boolean, routerUrl: string, pagename: string) {
    this.isActive = isActive;
    this.routerUrl = routerUrl;
    this.pagename = pagename;
  }

  static activeOf(pagename: string): Breadcrumb {
    return new Breadcrumb(true, '', pagename);
  }

  static inactiveOf(routerUrl: string, pageName: string) {
    return new Breadcrumb(false, routerUrl, pageName);
  }
}
