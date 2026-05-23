export interface MessageDTO {
  id: number;
  content: string;
  createdAt: string | Date;
  emisorId: number;
  emisorUsername: string;
  receptorId: number;
  receptorUsername: string;
}

export class MessageRequest {
  content: string;
  constructor(content: string = "") {
    this.content = content;
  }
}
