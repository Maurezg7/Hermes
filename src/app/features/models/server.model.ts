export class Server {
  id!: number;
  name!: String;
  description!: String;
  image!: String;
  members!: { username: string; email: string }[];

  constructor() {}

  setName(name: String) {
    this.name = name;
  }

  setDescription(description: String) {
    this.description = description;
  }

  setImage(image: String) {
    this.image = image;
  }
}
