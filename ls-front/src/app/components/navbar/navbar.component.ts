import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent implements OnInit {
  currentUser: any;
  constructor(private authService: AuthService, private router: Router) {}
  ngOnInit(): void {
    this.authService.currentUser.subscribe((user) => {
      this.currentUser = user;
      console.log('Utilisateur actuel: ', this.currentUser);
    });
  }

  logout() {
    this.authService.logout();
    console.log(
      'Déconnexion effectuée. Utilisateur actuel: ',
      this.currentUser
    );
  }
}
