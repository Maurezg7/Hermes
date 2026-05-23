import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Server } from '../models/server.model';
import { Observable } from 'rxjs';
import { Channel } from '../models/channel.model';

@Injectable({
  providedIn: 'root',
})
export class ChannelService {
  private urlBase = 'http://localhost:8080/api/channels';
  private requestBase = 'http://localhost:8080/api/server-requests';
  private clientHttp = inject(HttpClient);

  getAllServerChannels(id: number): Observable<any> {
    return this.clientHttp.get(`${this.urlBase}/server/${id}`);
  }

  postChannel(channel: Channel, idServer: number, idUser: number): Observable<Object> {
    return this.clientHttp.post(`${this.urlBase}/server/${idServer}/user/${idUser}`, channel);
  }

  inviteUserToServer(userId: number, serverId: number, invitedBy?: number): Observable<any> {
    const url = invitedBy 
      ? `${this.requestBase}/user/${userId}/server/${serverId}?invitedBy=${invitedBy}`
      : `${this.requestBase}/user/${userId}/server/${serverId}`;
    return this.clientHttp.post(url, {});
  }
}
