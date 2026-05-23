import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Chatbox } from '../models/chatbox.model';

@Injectable({
  providedIn: 'root',
})
export class ChatboxService {
  private urlBase = 'http://localhost:8080/api/chatbox';
  private clientHttp = inject(HttpClient);

  getPagedMessagesChatbox(
    idChannel: number,
    page: number = 0,
    size: number = 50,
  ): Observable<any[]> {
    const params = new HttpParams().set('page', page.toString()).set('size', size.toString());

    return this.clientHttp.get<any[]>(`${this.urlBase}/channel/${idChannel}/paged`, { params });
  }

  postMessageChatbox(chatbox: any, idChannel: number, idUser: number): Observable<any> {
    return this.clientHttp.post<any>(
      `${this.urlBase}/channel/${idChannel}/user/${idUser}`,
      chatbox,
    );
  }

  getAllChannelChatbox(idChannel: number): Observable<any[]> {
    return this.clientHttp.get<any[]>(`${this.urlBase}/channel/${idChannel}`);
  }
}
