import { LoginService } from './../../services/login.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-weekly-overview-landing-page',
  templateUrl: './weekly-overview-landing-page.component.html',
  styleUrls: ['./weekly-overview-landing-page.component.scss']
})
export class WeeklyOverviewLandingPageComponent implements OnInit {

  constructor(
    private router: Router,
    private loginService: LoginService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    const currentWeek = new Date()['getWeekNumber' + '']();
    const year = new Date().getFullYear() + (currentWeek === 1 ? 1 : 0);

    this.router.navigate([`/student/${this.route.snapshot.paramMap.get("studentUUID")}/weekly-overview/${currentWeek}/${year}`]);
  }
}
