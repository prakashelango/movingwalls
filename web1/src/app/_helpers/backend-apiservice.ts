import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from "rxjs/index";
import {User} from "../_models";

@Injectable()
export class BackendApiservice {

  baseUrl: string = 'http://localhost:8071/movingwalls/oauth/';

  constructor(private http: HttpClient) {
  }

  login(loginPayload): Observable<BackendApiservice> {
    return this.http.post<BackendApiservice>(this.baseUrl + 'token/login', loginPayload);
  }

  createUser(user: User): Observable<BackendApiservice> {
    return this.http.post<BackendApiservice>(this.baseUrl, user);
  }
}