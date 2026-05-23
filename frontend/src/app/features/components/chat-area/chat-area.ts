import { DatePipe } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-chat-area',
  imports: [DatePipe, FormsModule],
  templateUrl: './chat-area.html',
  styleUrl: './chat-area.scss',
})
export class ChatArea {
  @Input() messages: any[] = [];
  @Input() channelName: string = '';
  @Input() idUser: number | null = null;
  @Input() isChannelSelected: boolean = false;
  @Input() idServer: number | null = null;

  @Output() onSendMessage = new EventEmitter<string>();
  @Output() onViewServerUsers = new EventEmitter<void>();

  newMessage: string = '';

  submit() {
    if (this.newMessage.trim()) {
      this.onSendMessage.emit(this.newMessage);
      this.newMessage = '';
    }
  }
}
