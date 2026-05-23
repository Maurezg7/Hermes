import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Server } from '../models/server.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ServerService {
  private urlBase = 'http://localhost:8080/api/servers';
  private clientHttp = inject(HttpClient);

  getAllUserServer(id: Number): Observable<any> {
    return this.clientHttp.get(`${this.urlBase}/user/${id}`);
  }

  getServer(name: String): Observable<any> {
    return this.clientHttp.get(`${this.urlBase}/${name}`);
  }

  postServer(server: Server, idUser: Number): Observable<Object> {
    return this.clientHttp.post(`${this.urlBase}/user/${idUser}`, server);
  }

  putServer(server: Server, idServer: Number): Observable<Object> {
    return this.clientHttp.put(`${this.urlBase}/${idServer}`, server);
  }

  deleteServer(name: String) {
    return this.clientHttp.delete(`${this.urlBase}/${name}`);
  }

  getMembersServer(idServer: Number): Observable<any>{
    return this.clientHttp.get(`${this.urlBase}/user/members/${idServer}`);
  }
}
