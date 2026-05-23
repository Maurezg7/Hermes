import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { FriendDTO } from '../models/friendship.model';
import { MessageDTO } from '../models/message.model';


@Injectable({
  providedIn: 'root'
})
export class FriendshipService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/friends`;

  sendFriendRequest(userId: number, friendId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/request/${userId}/${friendId}`, {});
  }

  getPrivateMessages(userId: number, friendId: number, page: number, size: number): Observable<MessageDTO[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<MessageDTO[]>(`${this.apiUrl}/messages/${userId}/${friendId}`, { params });
  }

  getPendingRequests(userId: number): Observable<FriendDTO[]> {
    return this.http.get<FriendDTO[]>(`${this.apiUrl}/pending/${userId}`);
  }

  getAcceptedFriends(userId: number): Observable<FriendDTO[]> {
    return this.http.get<FriendDTO[]>(`${this.apiUrl}/accepted/${userId}`);
  }

  blockUser(userId: number, targetId: number): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/block/${userId}/${targetId}`, {});
  }

  acceptFriendRequest(userId: number, friendId: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/accept/${userId}/${friendId}`, {});
  }

}
