import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';
import { AuthUser } from '../models/auth-user.model';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private urlBase = `${environment.apiUrl}/users`;
  private clientHttp = inject(HttpClient);

  getUser(userData: String): Observable<any> {
    return this.clientHttp.get(`${this.urlBase}/${userData}`);
  }

  putUser(user: User, userData: AuthUser): Observable<Object> {
    return this.clientHttp.put(`${this.urlBase}/${userData}`, user);
  }

  deleteUser(userData: String) {
    return this.clientHttp.delete(`${this.urlBase}/${userData}`);
  }

  getUserId(userData: String): Observable<any> {
    return this.clientHttp.get(`${this.urlBase}/getId/${userData}`);
  }

  searchUsers(query: String): Observable<any[]> {
    return this.clientHttp.get<any[]>(`${this.urlBase}/search/${query}`);
  }
}
