import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {toPromise} from "rxjs/operator/toPromise";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'unts';

  constructor(private http: HttpClient) {
      // http.get('http://localhost:8080/api/users')
      //   .toPromise()
      //   .then(data => this.users = data)
  }

  login(user: string, pass: string) {
    var data = JSON.stringify({ userName:user, password:pass});
    console.log(data);

    this.http.post('http://localhost:8080/api/login', data)
      .toPromise()
      .then(function(data){
        console.log("Sisselogimine: " +data );
      })
      .catch(e => console.log(e));
  }

  logout() {
    this.http.post('http://localhost:8080/api/logout','')
      .toPromise()
      .then(() => console.log('Logout'))
      .catch(e => console.log(e));
  }

  getUnits() {
    this.http.get('http://localhost:8080/api/units')
       .toPromise()
       .then(data => this.units = data)
  }

  units: any = [];
}
