import { Component, OnInit } from '@angular/core';
import { Book } from "../domain/Book";
import { BookService } from "../service/book.service";
import { DatabaseService } from "../service/database.service";

@Component({
    selector: 'app-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: [ './book-list.component.css' ]
})
export class BookListComponent implements OnInit {

    public books: Book[] = [];

    constructor(private bookService: BookService, private databaseService: DatabaseService) {
    }

    ngOnInit(): void {
    }

    getBooks() {
        this.bookService.getBooks().subscribe(
            (books: Book[]) => {
                this.books = books;
                books.forEach(book => {
                    this.databaseService.db.books.put(book);
                });
            },
            (error) => {
                console.log(this.databaseService.db.books.toArray());
                this.databaseService.db.books.toArray().then((value: Book[]) => this.books = value);
            }
        )
    }
}
