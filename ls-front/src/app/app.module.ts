import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { ListMemoComponent } from './modules/memo-set/list-memo/list-memo.component';
import { CardMemoComponent } from './modules/memo-set/card-memo/card-memo.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { MemoSetComponent } from './modules/memo-set/memo-set.component';
import { LoginComponent } from './components/login/login.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    MemoSetComponent,
    ListMemoComponent,
    CardMemoComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    SidebarComponent,
  ],
  imports: [BrowserModule, AppRoutingModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
