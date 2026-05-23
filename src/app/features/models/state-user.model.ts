export class UserState{
  state!: string;

  constructor(state: string = "") {
    this.state = state;
  }

  getState(): string {
    return this.state;
  }

  setState(state: string): void {
    this.state = state;
  }
}
