import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class hourService {

  public DepartTime = [
    { hour: '09:00' },
    { hour: '09:30' },
    { hour: '10:00' },
    { hour: '10:30' },
    { hour: '11:00' },
    { hour: '11:30' },
    { hour: '12:00' },
    { hour: '12:30' },
    { hour: '13:00' },
    { hour: '13:30' },
    { hour: '14:00' },
    { hour: '14:30' },
    { hour: '15:00' },
    { hour: '15:30' },
    { hour: '16:00' },
    { hour: '16:30' },
    { hour: '17:00' },
    { hour: '17:30' },
    { hour: '18:00' },
    { hour: '18:30' },
    { hour: '19:00' },
    { hour: '19:30' },
    { hour: '20:00' },
    { hour: '20:30' }];


  constructor() { }

  getHour() {
    return this.DepartTime;
  }

}