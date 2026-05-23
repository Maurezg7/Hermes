import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { ServerRequest } from '../models/server-request.model';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ChannelRequestService {
  private apiUrl = `${environment.apiUrl}/server-requests`;

  constructor(private http: HttpClient) {}

  requestAccess(userId: number, serverId: number): Observable<ServerRequest> {
    return this.http.post<ServerRequest>(`${this.apiUrl}/user/${userId}/server/${serverId}`, {});
  }

  getPending(serverId: number): Observable<ServerRequest[]> {
    return this.http.get<ServerRequest[]>(`${this.apiUrl}/server/${serverId}/pending`);
  }

  processRequest(requestId: number, adminId: number, status: string): Observable<ServerRequest> {
    return this.http.patch<ServerRequest>(
      `${this.apiUrl}/${requestId}/process?status=${status}&adminId=${adminId}`, {}
    );
  }

  userRespondToInvite(requestId: number, userId: number, response: string): Observable<ServerRequest> {
    return this.http.patch<ServerRequest>(
      `${this.apiUrl}/${requestId}/respond?userId=${userId}&response=${response}`, {}
    );
  }
}
