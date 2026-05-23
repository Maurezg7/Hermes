import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor, NgIf } from '@angular/common';
import { Server } from '../../models/server.model';
import { Channel } from '../../models/channel.model';

@Component({
  selector: 'app-modals',
  imports: [FormsModule, NgIf, NgFor],
  templateUrl: './modals.html',
  styleUrl: './modals.scss',
})
export class Modals {
  @Input() activeModal: string | null = null;
  @Input() userServers: any[] = [];
  @Input() server: Server = new Server();
  @Input() channel: Channel = new Channel();
  @Input() friendUsername: string = '';
  @Input() friendError: string = '';
  @Input() searchResults: any[] = [];
  @Input() inviteUsername: string = '';
  @Input() inviteError: string = '';
  @Input() inviteSuccess: boolean = false;
  @Input() members: String[] = [];

  @Output() onClose = new EventEmitter<void>();
  @Output() onSubmitServer = new EventEmitter<void>();
  @Output() onSubmitChannel = new EventEmitter<void>();
  @Output() onSubmitFriend = new EventEmitter<string>();
  @Output() onSubmitInvite = new EventEmitter<string>();

  @Output() friendUsernameChange = new EventEmitter<string>();
  @Output() inviteUsernameChange = new EventEmitter<string>();

  onFriendNameChange(name: string) {
    this.friendUsernameChange.emit(name);
  }

  onInviteNameChange(name: string) {
    this.inviteUsernameChange.emit(name);
    this.inviteSuccess = false;
  }

  selectSearchResult(user: any) {
    this.friendUsername = user.username;
    this.friendUsernameChange.emit(user.username);
    this.searchResults = [];
  }

  selectInviteResult(user: any) {
    this.inviteUsername = user.username;
    this.inviteUsernameChange.emit(user.username);
    this.searchResults = [];
  }

  onServerImageChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const reader = new FileReader();
      reader.onload = (e) => {
        this.server.image = e.target?.result as string;
      };
      reader.readAsDataURL(input.files[0]);
    }
  }

  onServerUserservers() {
    this.activeModal = 'userservers';
    this.onClose.emit();
  }
}
