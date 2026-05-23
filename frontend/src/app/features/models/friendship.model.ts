export enum FriendshipStatus {
  PENDING = 'PENDING',
  ACCEPTED = 'ACCEPTED',
  BLOCKED = 'BLOCKED'
}

export interface FriendDTO {
  id: number;
  username: string;
  email: string;
  status: FriendshipStatus;
}
