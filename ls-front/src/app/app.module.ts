import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './components/home/home.component';
import { ListMemoComponent } from './modules/memo-set/list-memo/list-memo.component';
import { CardMemoComponent } from './modules/memo-set/card-memo/one-card-memo/card-memo.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MemoRoutingModule } from './modules/memo-routing.module';
import { AllCardMemosComponent } from './modules/memo-set/card-memo/all-card-memos/all-card-memos.component';
import { LeashaButtonComponent } from './components/shared/leasha-button/leasha-button.component';
import { LeashaInputComponent } from './components/shared/leasha-input/leasha-input.component';
import { AuthService } from './services/auth.service';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ListMemoComponent,
    CardMemoComponent,
    NavbarComponent,
    FooterComponent,
    LoginComponent,
    SidebarComponent,
    AllCardMemosComponent,
    LeashaButtonComponent,
    LeashaInputComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MemoRoutingModule,
    ReactiveFormsModule,
  ],
  providers: [
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
