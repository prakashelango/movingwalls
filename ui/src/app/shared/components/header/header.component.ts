import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {SharedService} from "../../shared.service";
import {Router} from "@angular/router";

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

  constructor(private sharedService: SharedService, private router: Router) {
    //this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
    this.isLoggedin = this.sharedService.isLoggedin();
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
    console.log("Logout");
    this.sharedService.logout();// TODO Logout call to service
    this.router.navigate(['/login']);
  }
}
