export interface ChannelRequest {
  requestId: number;
  userId: number;
  username: string;
  channel: {
    channelId: number;
    name: string;
    description: string;
  };
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  requestedAt: string;
}
