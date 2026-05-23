import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { MessageDTO, MessageRequest } from '../models/message.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/messages`;

  createMessage(content: MessageRequest, idEmisor: number, idReceptor: number): Observable<MessageDTO> {
    return this.http.post<MessageDTO>(`${this.apiUrl}/emisor/${idEmisor}/receptor/${idReceptor}`, content);
  }

  getChatHistory(u1: number, u2: number, page: number = 0, size: number = 20): Observable<MessageDTO[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<MessageDTO[]>(`${this.apiUrl}/history/${u1}/${u2}`, { params });
  }

  updateMessage(idMessage: number, idUser: number, content: MessageRequest): Observable<MessageDTO> {
    return this.http.put<MessageDTO>(`${this.apiUrl}/${idMessage}/user/${idUser}`, content);
  }

  deleteMessage(idMessage: number, idUser: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idMessage}/user/${idUser}`);
  }
}
