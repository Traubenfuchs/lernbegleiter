import { UuidResponse } from 'src/app/data/UuidResponse';
import { Severity } from './../../../data/Severity';
import { MassRegistration } from './../../../data/MassRegistration';
import { HttpClient } from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { GrowlService } from './../../../services/growl.service';
import { Component, OnInit } from '@angular/core';
import { GrowlMessage } from 'src/app/data/GrowlMessage';

@Component({
  selector: 'app-mass-registration',
  templateUrl: './mass-registration.component.html',
  styleUrls: ['./mass-registration.component.scss']
})
export class MassRegistrationComponent {
  uuid = 'new';
  massRegistration: MassRegistration;
  deletionTimeHtml: any;
  constructor(
    private http: HttpClient,
    public router: Router,
    private route: ActivatedRoute,
    private growlService: GrowlService) {
    this.init();
    this.route.params.subscribe(params => {
      this.init();
    });
  }
  init() {
    this.uuid = this.route.snapshot.paramMap.get("massRegistrationUUID");
    if (this.uuid === 'new') {
      this.massRegistration = new MassRegistration();
      this.massRegistration.uuid = 'Automatisch';
    } else {
      this.loadMassRegistration();
    }
  }
  loadMassRegistration() {
    this.http
      .get<MassRegistration>(`api/mass-registration/${this.uuid}`)
      .subscribe(res => {
        this.massRegistration = res;
      });

  }
  saveClick() {
    if (this.uuid === 'new') {
      this.createNewMassRegistration();
    } else {
      this.updateMassRegistration();
    }
  }
  createNewMassRegistration() {
    this.http.post<UuidResponse>('api/mass-registration', this.massRegistration)
      .subscribe(uuidResponse => {
        this.router.navigate([`management/mass-registration/${uuidResponse.uuid}`]);
        this.growlService.addMessage(new GrowlMessage("Massen-Registrierung wurde angelegt.", Severity.SUCCESS, 2000));
      });
  }
  updateMassRegistration() {
    this.http.patch<UuidResponse>(`api/mass-registration/${this.uuid}`, this.massRegistration)
      .subscribe(uuidResponse => {
        this.loadMassRegistration();
        this.growlService.addMessage(new GrowlMessage("Massen-Registrierung wurde upgedated.", Severity.SUCCESS, 2000));
      });
  }
  deleteClick() {
    this.http
      .delete<any>(`api/mass-registration/${this.uuid}`)
      .subscribe(_ => {
        this.router.navigate(['management/mass-registrations']);
        this.growlService.addMessage(new GrowlMessage("Massen-Registrierung wurde gelöscht.", Severity.SUCCESS, 2000));
      });
  }
}
