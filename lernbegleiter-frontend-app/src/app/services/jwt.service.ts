import { JwtCallback } from './jwt.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class JwtService {

  private jwtCallback: JwtCallback = () => {
    console.log("jwt callback dummy called")
  }
  private storageEventListener

  constructor() {
    this.storageEventListener = {
      requestJwt: (ev) => {
        if (ev.newValue === 'request' && !!sessionStorage.jwt) {
          const v = sessionStorage.jwt
          console.log('jwt was requested, sending: ' + v)
          this.setJwt(v)
        }
      },
      jwt: (ev) => {
        if (!!ev.newValue) {
          console.log('received new jwt: ' + ev.newValue)
          this.jwtCallback(ev.newValue)
          sessionStorage.jwt = ev.newValue
        }
      },
      deleteJwt: (ev) => {
        if (ev.newValue === 'delete') {
          sessionStorage.removeItem('jwt')
          this.jwtCallback(null)
        }
      }
    }
    window.addEventListener('storage', ev => {
      console.log("storage callback..." + ev.key)
      const sel = this.storageEventListener[ev.key]
      if (!!sel) {
        sel(ev)
      }
    })
  }

  public setJwt(jwt: string) {
    sessionStorage.setItem('jwt', jwt)
    localStorage.jwt = jwt
    localStorage.removeItem('jwt')
  }

  public deleteJwt() {
    localStorage.deleteJwt = 'delete'
    localStorage.removeItem('deleteJwt')
  }

  public requestJwt(jwtCallback: JwtCallback) {
    this.jwtCallback = jwtCallback
    localStorage.requestJwt = 'request'
    localStorage.removeItem('requestJwt')
  }
}

export type JwtCallback = (jwt: string) => void;