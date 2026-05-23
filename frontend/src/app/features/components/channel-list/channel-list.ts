import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Channel } from '../../models/channel.model';
import { ServerRequest } from '../../models/server-request.model';

@Component({
  selector: 'app-channel-list',
  imports: [],
  templateUrl: './channel-list.html',
  styleUrl: './channel-list.scss',
})
export class ChannelList {
  @Input() username: string = '';
  @Input() loading: boolean = false;
  @Input() channels: Channel[] = [];
  @Input() serverId: number | null = null;
  @Input() isAdmin: boolean = false;
  @Input() pendingRequests: ServerRequest[] = [];
  @Input() currentUserId: number | null = null;
  @Input() userState: string = 'LOADING';

  @Output() onSelectChannel = new EventEmitter<Channel>();
  @Output() onCreateChannel = new EventEmitter<void>();
  @Output() onInviteUser = new EventEmitter<void>();
  @Output() onApproveRequest = new EventEmitter<number>();
  @Output() onRejectRequest = new EventEmitter<number>();
  @Output() onViewServerUsers = new EventEmitter<void>();
  @Output() onStateChange = new EventEmitter<string>();

  isModalState: boolean = false;

  toggleModalState() {
    this.isModalState = !this.isModalState;
  }

  changeState(state: string) {
    this.onStateChange.emit(state);
  }
}
