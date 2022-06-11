import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './components/book/book-list/book-list.component';
import { BookAddComponent } from "./components/book/book-add/book-add.component";
import { LoginComponent } from "./components/login/login.component";
import { MainComponent } from "./components/main/main.component";
import { BookResolver } from "./resolvers/BookResolver";
import { BookEditComponent } from "./components/book/book-edit/book-edit.component";
import { BookDetailsResolver } from "./resolvers/BookDetailsResolver";
import { BookWithShelvesResolver } from "./resolvers/BookWithShelvesResolver";
import { ProfileComponent } from "./components/user/profile/profile.component";
import { ProfileResolver } from "./resolvers/ProfileResolver";
import { UserListComponent } from "./components/user/user-list/user-list.component";
import { UserResolver } from "./resolvers/UserResolver";
import { ShelfListComponent } from "./components/shelf/shelf-list/shelf-list.component";
import { ShelfResolver } from "./resolvers/ShelfResolver";
import { ShelfAddComponent } from "./components/shelf/shelf-add/shelf-add.component";
import { ShelfDetailsComponent } from "./components/shelf/shelf-details/shelf-details.component";
import { ShelfDetailsResolver } from "./resolvers/ShelfDetailsResolver";
import { ShelfAddBookComponent } from "./components/shelf/shelf-add-book/shelf-add-book.component";
import { ShelfEditComponent } from "./components/shelf/shelf-edit/shelf-edit.component";
import { RegisterFormComponent } from "./components/register/register-form/register-form.component";
import { ConfirmAccountComponent } from "./components/register/confirm-account/confirm-account.component";
import { ConfirmResolver } from "./resolvers/ConfirmResolver";

const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: MainComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register', component: RegisterFormComponent},
    {path: 'confirm/:code', component: ConfirmAccountComponent, resolve: {status: ConfirmResolver}},
    {path: 'profile', component: ProfileComponent, resolve: {user: ProfileResolver}},
    {path: 'users', component: UserListComponent, resolve: {users: UserResolver}},
    {path: 'shelves', component: ShelfListComponent, resolve: {shelves: ShelfResolver}},
    {path: 'shelves/add', component: ShelfAddComponent},
    {path: 'shelves/edit/:id', component: ShelfEditComponent, resolve: {shelf: ShelfDetailsResolver}},
    {path: 'shelves/book/:id', component: ShelfAddBookComponent, resolve: {shelves: ShelfResolver, book: BookWithShelvesResolver}},
    {path: 'shelves/details/:id', component: ShelfDetailsComponent, resolve: {shelf: ShelfDetailsResolver}},
    {path: 'books', component: BookListComponent, resolve: {books: BookResolver}},
    {path: 'books/add', component: BookAddComponent},
    {path: 'books/edit/:id', component: BookEditComponent, resolve: {book: BookDetailsResolver}},
    {path: '**', redirectTo: '/home'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
