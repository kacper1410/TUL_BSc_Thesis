import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookListComponent } from './book-list/book-list.component';
import { BookAddComponent } from "./book-add/book-add.component";
import { MainComponent } from "./main/main.component";

const routes: Routes = [
    {path: '', component: MainComponent},
    {path: 'books', component: BookListComponent},
    {path: 'books/add', component: BookAddComponent}
];

@NgModule({
    imports: [ RouterModule.forRoot(routes) ],
    exports: [ RouterModule ]
})
export class AppRoutingModule {
}
