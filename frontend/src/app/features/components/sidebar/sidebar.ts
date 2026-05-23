import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FriendDTO } from '../../models/friendship.model';
import { Server } from '../../models/server.model';
import { Notification } from '../../models/notification.model';

@Component({
  selector: 'app-sidebar',
  imports: [],
  templateUrl: './sidebar.html',
  styleUrl: './sidebar.scss',
})
export class Sidebar {
  @Input() servers: Server[] = [];
  @Input() friends: FriendDTO[] = [];
  @Input() pendingRequests: FriendDTO[] = [];
  @Input() notifications: Notification[] = [];

  @Input() isFriendsMode: boolean = false;
  @Input() serverId: number | null = null;

  @Output() onHome = new EventEmitter<void>();
  @Output() onToggleMode = new EventEmitter<void>();
  @Output() onServerSelect = new EventEmitter<number>();
  @Output() onFriendSelect = new EventEmitter<FriendDTO>();
  @Output() onAcceptFriend = new EventEmitter<number>();
  @Output() onRejectFriend = new EventEmitter<number>();
  @Output() onAddFriend = new EventEmitter<void>();
  @Output() onCreateServer = new EventEmitter<void>();
  @Output() onAcceptServerInvite = new EventEmitter<number | null>();
  @Output() onRejectServerInvite = new EventEmitter<number>();

  isNotificationOpen: boolean = false;

  toggleNotifications() {
    this.isNotificationOpen = !this.isNotificationOpen;
  }

  selectServer(id: number) {
    this.onServerSelect.emit(id);
  }

  selectFriend(friend: FriendDTO) {
    this.onFriendSelect.emit(friend);
  }

  get unreadCount(): number {
    return this.notifications.filter(n => !n.isRead).length + this.pendingRequests.length;
  }

  get serverInvites(): Notification[] {
    return this.notifications.filter(n =>
      n.type === 'SERVER_JOIN_APPROVED' || n.type === 'SERVER_JOIN_REQUEST'
    );
  }
}
