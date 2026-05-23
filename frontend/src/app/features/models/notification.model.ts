export interface Notification {
  id: number;
  userId: number;
  username: string;
  type: 'FRIEND_REQUEST' | 'FRIEND_ACCEPTED' | 'SERVER_JOIN_REQUEST' | 'SERVER_JOIN_APPROVED' | 'SERVER_JOIN_REJECTED' | 'SERVER_INVITE_ACCEPTED' | 'SERVER_INVITE_REJECTED';
  message: string;
  isRead: boolean;
  relatedUserId: number | null;
  relatedUsername: string | null;
  relatedServerId: number | null;
  relatedChannelId: number | null;
  relatedFriendshipId: number | null;
  createdAt: Date;
}

export type NotificationType = Notification['type'];
