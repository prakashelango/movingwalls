import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {User} from "../models";
import {environment} from "../../environments/environment";

@Injectable({providedIn: 'root'})
export class AuthenticationService {
    public currentUser: Observable<User>;
    private currentUserSubject: BehaviorSubject<User>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(user: User) {
    return this.http.post<any>(`${environment.serviceApiUrl}/auth/login`, {
      userName: user.userName,
      password: user.password, tokenId: user.tokenId, valid: user.valid, message: user.message
    })
      .pipe(map(user => {
        // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify({
          userName: user.userName,
          password: user.password, tokenId: user.tokenId, valid: user.valid, message: user.message
        }));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  authenticate() {
    return this.http.post<any>(`${environment.serviceApiUrl}/auth/validateToken`, localStorage.getItem('currentUser'))
      .pipe(map(user => {
        // store user details and basic auth credentials in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify({
          userName: user.userName,
          password: user.password, tokenId: user.tokenId, valid: user.valid, message: user.message
        }));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  isLoggedin() {
    return this.currentUserValue.valid;
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
