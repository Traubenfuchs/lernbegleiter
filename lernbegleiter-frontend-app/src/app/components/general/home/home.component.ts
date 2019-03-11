import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public loginService: LoginService, private router: Router) {
  }

  ngOnInit() {
  }

  public logout() {
    console.log("LOGOUT");
  }

}
