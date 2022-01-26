import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './components/book-list/book-list.component';
import { BookAddComponent } from "./components/book-add/book-add.component";
import { LoginComponent } from "./components/login/login.component";
import { MainComponent } from "./components/main/main.component";

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
