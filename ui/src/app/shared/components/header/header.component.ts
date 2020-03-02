import {Component, EventEmitter, OnInit, Output} from '@angular/core';

import {Router} from "@angular/router";
import {AuthenticationService} from "../../../services";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  loading = false;
  /*users: User[];
  currentUser: User;*/
  isLoggedin: boolean;

  @Output() toggleSideBarForMe: EventEmitter<any> = new EventEmitter();

  constructor(private authenticationService: AuthenticationService, private router: Router) {
    //this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    this.isLoggedin = this.authenticationService.isLoggedin();
  }

  ngOnInit() {
    this.loading = true;
    /*this.userService.getAll().pipe(first()).subscribe(users => {
      this.loading = false;
      this.users = users;
    });*/
  }

  toggleSideBar() {
    this.toggleSideBarForMe.emit();
    setTimeout(() => {
      window.dispatchEvent(
          new Event('resize')
      );
    }, 300);
  }

  logout() {
    this.authenticationService.logout();// TODO Logout call to service
    this.router.navigate(['/login']);
  }
}
