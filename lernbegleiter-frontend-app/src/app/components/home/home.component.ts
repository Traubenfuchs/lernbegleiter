import { Router } from '@angular/router';
import { LoginService } from './../../services/login.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(public loginService: LoginService, private router: Router) { }

  ngOnInit() {
  }
  public  logout() {
    console.log("LOGOUT");
  }

}
