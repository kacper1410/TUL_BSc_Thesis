import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './book-list/book-list.component';
import { BookAddComponent } from "./book-add/book-add.component";
import { LoginComponent } from "./login/login.component";
import { MainComponent } from "./main/main.component";

const routes: Routes = [
    {path: '', redirectTo: '/home', pathMatch: 'full'},
    {path: 'home', component: MainComponent},
    {path: 'login', component: LoginComponent},
    {path: 'books', component: BookListComponent},
    {path: 'books/add', component: BookAddComponent},
    {path: '**', redirectTo: '/home'},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
