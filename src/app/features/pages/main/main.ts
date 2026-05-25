import { ChangeDetectorRef, Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, debounceTime, distinctUntilChanged, switchMap } from 'rxjs';
import { Sidebar } from '../../components/sidebar/sidebar';
import { Modals } from '../../components/modals/modals';
import { ChannelList } from '../../components/channel-list/channel-list';
import { ChatArea } from '../../components/chat-area/chat-area';
import { UserService } from '../../services/user.service';
import { ServerService } from '../../services/server.service';
import { ChannelService } from '../../services/channel.service';
import { ChatboxService } from '../../services/chatbox.service';
import { FriendshipService } from '../../services/friendship.service';
import { MessageService } from '../../services/message.service';
import { NotificationService } from '../../services/notification.service';
import { ChannelRequestService } from '../../services/channel-request.service';
import { StateUserService } from '../../services/userstate.service';
import { Server } from '../../models/server.model';
import { Channel } from '../../models/channel.model';
import { FriendDTO } from '../../models/friendship.model';
import { Notification } from '../../models/notification.model';
import { ServerRequest } from '../../models/server-request.model';

@Component({
  selector: 'app-main',
  standalone: true,
  imports: [Sidebar, Modals, ChannelList, ChatArea],
  templateUrl: './main.html',
  styleUrl: './main.scss',
  providers: [StateUserService],
})
export class Main implements OnInit {
  private router = inject(Router);
  private userService = inject(UserService);
  private serverService = inject(ServerService);
  private channelService = inject(ChannelService);
  private chatboxService = inject(ChatboxService);
  private friendshipService = inject(FriendshipService);
  private messageService = inject(MessageService);
  private notificationService = inject(NotificationService);
  private channelRequestService = inject(ChannelRequestService);
  private userStateService = inject(StateUserService);
  private cdr = inject(ChangeDetectorRef);

  userState: string = 'LOADING';

  isFriends = false;
  idUser: number | null = null;
  idServerSeleccionado: number | null = null;
  idChannelSeleccionado: number | null = null;
  idFriendSeleccionado: number | null = null;

  isModalOpen = false;
  isModalChannel = false;
  isModalFriend = false;
  isModalInvite = false;
  isModalUserServers = false;

  allUserServers: Server[] = [];
  allMembersService: String[] = [];
  allChannelsServer: Channel[] = [];
  allMessages: any[] = [];
  pendingRequests: FriendDTO[] = [];
  acceptedFriends: FriendDTO[] = [];
  notifications: Notification[] = [];
  serverPendingRequests: ServerRequest[] = [];

  server: Server = new Server();
  channel: Channel = new Channel();
  newMessage: string = '';
  currentChannelName: string = 'Selecciona un canal';
  friendUsername: string = '';
  friendError: string = '';
  inviteUsername: string = '';
  inviteError: string = '';
  username: string = '';
  inviteSuccess: boolean = false;
  userServers: any[] = [];
  searchResults: any[] = [];
  private searchSubject = new Subject<string>();

  ngOnInit() {
    this.checkSession();
    this.username = localStorage.getItem('currentUser') || 'Usuario';
    this.searchSubject
      .pipe(
        debounceTime(300),
        distinctUntilChanged(),
        switchMap((query) => this.userService.searchUsers(query)),
      )
      .subscribe((results) => {
        this.searchResults = results;
        this.cdr.detectChanges();
      });
  }

  private checkSession() {
    const savedUsername = localStorage.getItem('currentUser');
    if (savedUsername) this.loadUserData(savedUsername);
    else this.router.navigate(['/login']);
  }

  private loadUserData(username: string) {
    this.userService.getUserId(username).subscribe({
      next: (id) => {
        this.idUser = Number(id);
        this.loadUserState();
        this.refreshServers();
        this.refreshFriends();
      },
      error: () => this.router.navigate(['/login']),
    });
  }

  loadUserState() {
    if (!this.idUser) return;
    this.userStateService.getStateUser(this.idUser).subscribe({
      next: (state) => {
        this.userState = state;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error al cargar estado del usuario:', err);
        this.userState = 'ERROR';
        this.cdr.detectChanges();
      },
    });
  }

  updateUserState(state: string) {
    if (!this.idUser) return;
    this.userState = state;
    this.userStateService.putStateUser(state, this.idUser).subscribe({
      next: () => {
        
      },
      error: (err) => console.error('Error al actualizar estado:', err),
    });
  }

  refreshServers() {
    if (!this.idUser) return;
    this.serverService.getAllUserServer(this.idUser).subscribe((data) => {
      this.allUserServers = [...data];
      this.cdr.detectChanges();
    });
  }

  refreshMembersServer() {
    if (!this.idServerSeleccionado) return;
    this.serverService.getMembersServer(this.idServerSeleccionado).subscribe((data) => {
      this.allMembersService = [...data];
      this.cdr.detectChanges();
    });
  }

  refreshFriends() {
    if (!this.idUser) return;
    this.friendshipService.getAcceptedFriends(this.idUser).subscribe((data) => {
      this.acceptedFriends = data;
      this.cdr.detectChanges();
    });
    this.friendshipService.getPendingRequests(this.idUser).subscribe((data) => {
      this.pendingRequests = data;
      this.cdr.detectChanges();
    });
    this.loadNotifications();
  }

  loadNotifications() {
    if (!this.idUser) return;
    this.notificationService.getNotifications(this.idUser).subscribe((data) => {
      this.notifications = data;
      this.cdr.detectChanges();
    });
  }

  loadUnreadCount() {
    if (!this.idUser) return 0;
    let count = 0;
    this.notificationService.getUnreadCount(this.idUser).subscribe((data) => {
      count = data;
      this.cdr.detectChanges();
    });
    return count;
  }

  returnHome() {
    this.idChannelSeleccionado = null;
    this.idFriendSeleccionado = null;
    this.currentChannelName = 'Inicio';
    this.allMessages = [];
    this.allChannelsServer = [];
    this.isFriends = false;
    this.cdr.detectChanges();
  }

  loadChannels(idServer: number) {
    this.idServerSeleccionado = idServer;
    this.channelService.getAllServerChannels(Number(idServer)).subscribe((data) => {
      this.allChannelsServer = data;
      this.allMessages = [];
      this.idChannelSeleccionado = null;
      this.currentChannelName = 'Selecciona un canal';
      this.loadServerPendingRequests(idServer);
      this.cdr.detectChanges();
    });
  }

  loadMembers(idServer: number) {
    this.serverService.getMembersServer(Number(idServer)).subscribe((data) => {
      this.allMembersService = data;
      this.cdr.detectChanges();
    });
  }

  loadServerPendingRequests(serverId: number) {
    this.channelRequestService.getPending(serverId).subscribe({
      next: (data) => {
        this.serverPendingRequests = data;
        this.cdr.detectChanges();
      },
      error: () => {
        this.serverPendingRequests = [];
        this.cdr.detectChanges();
      },
    });
  }

  approveServerRequest(requestId: number) {
    if (!this.idUser || !this.idServerSeleccionado) return;
    this.channelRequestService.processRequest(requestId, this.idUser, 'APPROVED').subscribe({
      next: () => {
        this.loadServerPendingRequests(this.idServerSeleccionado!);
        this.loadNotifications();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al aprobar solicitud', err),
    });
  }

  rejectServerRequest(requestId: number) {
    if (!this.idUser || !this.idServerSeleccionado) return;
    this.channelRequestService.processRequest(requestId, this.idUser, 'REJECTED').subscribe({
      next: () => {
        this.loadServerPendingRequests(this.idServerSeleccionado!);
        this.loadNotifications();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al rechazar solicitud', err),
    });
  }

  loadChatbox(idChannel: number, name: string) {
    this.idChannelSeleccionado = idChannel;
    this.idFriendSeleccionado = null;
    this.currentChannelName = name;
    this.chatboxService.getPagedMessagesChatbox(idChannel, 0, 50).subscribe((data) => {
      this.allMessages = data.sort(
        (a: any, b: any) => new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime(),
      );
      this.scrollToBottom();
      this.cdr.detectChanges();
    });
  }

  loadPrivateMessages(friendId: number, username: string) {
    if (!this.isFriends) {
      this.isFriends = true;
      this.refreshFriends();
    }
    this.idChannelSeleccionado = null;
    this.idFriendSeleccionado = friendId;
    this.currentChannelName = username;
    this.messageService.getChatHistory(this.idUser!, friendId, 0, 50).subscribe((data) => {
      this.allMessages = data
        .map((m) => ({
          content: m.content,
          createdAt: m.createdAt,
          creator_id: m.emisorId,
          username: m.emisorUsername,
        }))
        .reverse();
      this.scrollToBottom();
      this.cdr.detectChanges();
    });
  }

  handleMessageSubmit(text: string) {
    this.newMessage = text;
    this.sendMessage();
  }

  sendMessage() {
    if (!this.newMessage.trim() || !this.idUser) return;

    if (this.idChannelSeleccionado) {
      this.chatboxService
        .postMessageChatbox({ content: this.newMessage }, this.idChannelSeleccionado, this.idUser)
        .subscribe((message) => {
          this.allMessages.push(message);
          this.newMessage = '';
          this.scrollToBottom();
          this.cdr.detectChanges();
        });
    } else if (this.idFriendSeleccionado) {
      this.messageService
        .createMessage({ content: this.newMessage }, this.idUser, this.idFriendSeleccionado)
        .subscribe((res) => {
          this.allMessages.push({
            content: res.content,
            createdAt: res.createdAt,
            creator_id: res.emisorId,
            username: res.emisorUsername,
          });
          this.newMessage = '';
          this.scrollToBottom();
          this.cdr.detectChanges();
        });
    }
  }

  onSubmitServer() {
    if (this.idUser && this.server.name) {
      this.serverService.postServer(this.server, this.idUser).subscribe(() => {
        this.isModalOpen = false;
        this.server = new Server();
        this.refreshServers();
      });
    }
  }

  onSubmitChannel() {
    if (this.idServerSeleccionado && this.channel.name && this.idUser) {
      this.channelService
        .postChannel(this.channel, this.idServerSeleccionado, this.idUser)
        .subscribe({
          next: () => {
            this.isModalChannel = false;
            this.channel = new Channel();
            this.loadChannels(this.idServerSeleccionado!);
            this.cdr.detectChanges();
          },
          error: (err) => console.error('Error al crear canal', err),
        });
    }
  }

  onSubmitFriendRequest() {
    if (this.idUser && this.friendUsername.trim()) {
      this.userService.getUserId(this.friendUsername).subscribe({
        next: (targetId) => {
          this.friendshipService.sendFriendRequest(this.idUser!, Number(targetId)).subscribe({
            next: () => {
              console.log('Solicitud enviada con éxito');
              this.toggleFriendModal(false);
            },
            error: (err) => {
              this.friendError = 'Ya existe una solicitud o relación con este usuario.';
            },
          });
        },
        error: () => {
          this.friendError = 'No se encontró ningún usuario con ese nombre.';
        },
      });
    }
    this.isModalFriend = false;
    this.friendUsername = '';
  }

  private scrollToBottom() {
    setTimeout(() => {
      const chatContainer = document.querySelector('.bodychat');
      if (chatContainer) chatContainer.scrollTop = chatContainer.scrollHeight;
    }, 100);
  }

  toggleFriendModal(open: boolean) {
    this.isModalFriend = open;
    if (!open) {
      this.friendUsername = '';
      this.friendError = '';
      this.searchResults = [];
    }
    this.cdr.detectChanges();
  }

  onFriendNameChange(name: string) {
    this.friendUsername = name;
    if (name.trim().length >= 2) {
      this.searchSubject.next(name);
    } else {
      this.searchResults = [];
    }
  }

  selectSearchResult(user: any) {
    this.friendUsername = user.username;
    this.searchResults = [];
  }

  sendFriendRequest(username: string) {
    if (!username.trim() || !this.idUser) return;

    this.userService.getUserId(username).subscribe({
      next: (targetId) => {
        this.friendshipService.sendFriendRequest(this.idUser!, Number(targetId)).subscribe({
          next: () => {
            this.isModalFriend = false;
            this.friendUsername = '';
            this.friendError = '';
            this.cdr.detectChanges();
          },
          error: () => {
            this.friendError = 'Error al enviar la solicitud.';
            this.cdr.detectChanges();
          },
        });
      },
      error: () => {
        this.friendError = 'El usuario no existe.';
        this.cdr.detectChanges();
      },
    });
  }

  acceptFriend(friendId: number) {
    if (!this.idUser) return;
    this.friendshipService.acceptFriendRequest(this.idUser, friendId).subscribe({
      next: () => {
        this.refreshFriends();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al aceptar solicitud', err),
    });
  }

  rejectFriend(friendId: number) {
    if (!this.idUser) return;
    this.friendshipService.blockUser(this.idUser, friendId).subscribe({
      next: () => {
        this.refreshFriends();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al rechazar solicitud', err),
    });
  }

  toggleInviteModal(open: boolean) {
    this.isModalInvite = open;
    if (!open) {
      this.inviteUsername = '';
      this.inviteError = '';
      this.inviteSuccess = false;
      this.searchResults = [];
    }
    this.cdr.detectChanges();
  }

  onInviteNameChange(name: string) {
    this.inviteUsername = name;
    if (name.trim().length >= 2) {
      this.searchSubject.next(name);
    } else {
      this.searchResults = [];
    }
  }

  selectInviteResult(user: any) {
    this.inviteUsername = user.username;
    this.searchResults = [];
  }

  sendServerInvite(username: string) {
    if (!username.trim() || !this.idServerSeleccionado) return;

    this.userService.getUserId(username).subscribe({
      next: (targetId) => {
        const targetUserId = Number(targetId);

        if (!this.idServerSeleccionado) return;

        this.channelService
          .inviteUserToServer(targetUserId, this.idServerSeleccionado, this.idUser!)
          .subscribe({
            next: () => {
              this.inviteSuccess = true;
              this.inviteError = '';
              setTimeout(() => {
                this.toggleInviteModal(false);
              }, 1500);
              this.cdr.detectChanges();
            },
            error: (err) => {
              this.inviteError = err.error?.message || 'Ya tiene una solicitud pendiente';
              this.inviteSuccess = false;
              this.cdr.detectChanges();
            },
          });
      },
      error: () => {
        this.inviteError = 'Usuario no encontrado';
        this.inviteSuccess = false;
        this.cdr.detectChanges();
      },
    });
  }

  acceptServerInvite(requestId: number | null | undefined) {
    if (!requestId || !this.idUser) return;

    this.channelRequestService.userRespondToInvite(requestId, this.idUser, 'APPROVED').subscribe({
      next: () => {
        this.refreshServers();
        this.loadNotifications();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error al aceptar invitación', err),
    });
  }

  rejectServerInvite(notificationId: number) {
    if (!this.idUser) return;

    this.channelRequestService
      .userRespondToInvite(notificationId, this.idUser, 'REJECTED')
      .subscribe({
        next: () => {
          this.loadNotifications();
          this.cdr.detectChanges();
        },
        error: (err) => {
          this.notificationService.deleteNotification(notificationId).subscribe({
            next: () => {
              this.loadNotifications();
              this.cdr.detectChanges();
            },
          });
        },
      });
  }

  openMembersModal() {
    this.isModalUserServers = true;
  }
}
