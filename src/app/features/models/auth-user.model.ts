export class AuthUser{
  dataUser: string = '';
  dataPassword: string = '';

  constructor(){}

  setDataUser(dataUser: string){
    this.dataUser = dataUser;
  }

  setDataPassword(dataPassword: string){
    this.dataPassword = dataPassword;
  }

  getDataUser(){
    return this.dataUser;
  }

  getDataPassword(){
    return this.dataPassword;
  }
}
