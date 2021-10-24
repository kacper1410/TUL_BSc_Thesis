import { Component, OnInit } from '@angular/core';
import { Book } from "../domain/Book";
import { BookService } from "../service/book.service";

@Component({
    selector: 'app-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: [ './book-list.component.css' ]
})
export class BookListComponent implements OnInit {

    public books: Book[] = [];

    constructor(private bookService: BookService) {
    }

    ngOnInit(): void {
    }

    getBooks() {
        this.bookService.getBooks().subscribe(
            (books: Book[]) => {
                this.books = books;
            }
        )
    }
}
