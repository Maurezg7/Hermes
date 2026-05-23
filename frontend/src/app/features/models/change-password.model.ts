export class ChangePassword{
    datauser!: String;
    oldpassword!: String;
    newpassword!: String;

    constructor(datauser: String, oldpassword: String, newpassword: String){
      this.datauser = datauser;
      this.oldpassword = oldpassword;
      this.newpassword = newpassword;
    }
}
