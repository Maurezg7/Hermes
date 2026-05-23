import { inject } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

export class StateUserService {
  private urlBase = 'http://localhost:8080/api/statesuser';
  private clientHttp = inject(HttpClient);

  getStateUser(idUser: number): Observable<string> {
    return this.clientHttp.get(`${this.urlBase}/userstate/${idUser}`, { responseType: 'text' });
  }


  putStateUser(state: string, idUser: number) {
    return this.clientHttp.put(`${this.urlBase}/userstate/${idUser}/${state}`, null);
  }
}
