export interface ServerRequest {
  requestId: number;
  userId: number;
  username: string;
  server: {
    serverId: number;
    name: string;
    description: string;
  };
  status: 'PENDING' | 'APPROVED' | 'REJECTED';
  requestedAt: string;
}
