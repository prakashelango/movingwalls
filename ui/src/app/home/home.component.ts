import {Component} from '@angular/core';
import {first} from 'rxjs/operators';

import {User} from '@app/_models';
import {AuthenticationService, UserService} from '@app/_services';
import {Router} from '@angular/router';

@Component({templateUrl: 'home.component.html'})
export class HomeComponent {
  loading = false;
  users: User[];
  currentUser: User;

  constructor(private router: Router,
              private authenticationService: AuthenticationService, private userService: UserService) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit() {
    this.loading = true;
    this.userService.getAll().pipe(first()).subscribe(users => {
      this.loading = false;
      this.users = users;
    });
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
