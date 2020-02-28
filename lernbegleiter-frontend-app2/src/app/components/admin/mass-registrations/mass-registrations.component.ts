import { MassRegistration } from './../../../data/MassRegistration';
import { GrowlService } from './../../../services/growl.service';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-mass-registrations',
  templateUrl: './mass-registrations.component.html',
  styleUrls: ['./mass-registrations.component.scss']
})
export class MassRegistrationsComponent {
  massRegistrations: MassRegistration[];
  isLoadingMassRegistrations = true;
  constructor(
    private http: HttpClient,
    public router: Router,
    private growlService: GrowlService) {
    this.loadMassRegistrations();
  }

  deleteRegistration(uuid: string) {
    this.http.delete<any>(`api/mass-registration/${uuid}`)
      .subscribe(res => {
        this.loadMassRegistrations();
        // this.growlService.addMessage(new GrowlMessage("Klasse wurde erstellt.", Severity.SUCCESS, 2000));
      });
  }

  loadMassRegistrations() {
    this.isLoadingMassRegistrations = true;
    this.http.get<MassRegistration[]>('api/mass-registrations')
      .subscribe(res => {
        this.massRegistrations = res;
        this.isLoadingMassRegistrations = false;
        // this.growlService.addMessage(new GrowlMessage("Klasse wurde erstellt.", Severity.SUCCESS, 2000));
      });
  }
}
