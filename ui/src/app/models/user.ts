export class User {
  userName: string;
  password: string;
  tokenId: string;
  valid: boolean;
  message: string;

  constructor(userName: string, password: string, token: string, valid: boolean, msg: string) {
    this.userName = userName;
    this.password = password;
    this.tokenId = token;
    this.valid = valid;
    this.message = msg;
  }
}
