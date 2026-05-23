import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable, tap } from 'rxjs';
import { AuthUser } from '../models/auth-user.model';
import { ChangePassword } from '../models/change-password.model';
import { User } from '../models/user.model';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private urlBase = `${environment.apiUrl}/auth`;
  private clientHttp = inject(HttpClient);
  private userSubject = new BehaviorSubject<string | null>(localStorage.getItem('currentUser'));
  public currentUser$ = this.userSubject.asObservable();
  router: any;

  setSession(username: string) {
    localStorage.setItem('currentUser', username);
    this.userSubject.next(username);
  }

  postRegister(user: User): Observable<Object> {
    return this.clientHttp.post(`${this.urlBase}/register`, user);
  }

  loginRequest(user: AuthUser): Observable<Object> {
    return this.clientHttp.post(`${this.urlBase}/login`, user);
  }

  verifyUser(authUser: AuthUser, code: string): Observable<any> {
    const payload = { dataUser: authUser.getDataUser(), dataPassword: authUser.getDataPassword() };
    return this.clientHttp.post<any>('api/auth/verify?code=' + code, authUser).pipe(
      tap((res) => {
        if (res.token) {
          localStorage.setItem('token', res.token);
          this.router.navigate(['/perfil']);
        }
      })
    );
  }

  changePassword(user: ChangePassword): Observable<Object> {
    return this.clientHttp.post(`${this.urlBase}/forgot-password`, user);
  }
}
