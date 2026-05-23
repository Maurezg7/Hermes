export class Chatbox {
  content!: string;
  createdAt?: string;
  creator_id!: number;
  username!: string;

  constructor(content: string = "", creator_id: number = 0, username: string = "") {
    this.content = content;
    this.creator_id = creator_id;
    this.username = username;
  }
}
