import { Component, OnInit } from '@angular/core';
import { Book } from "../../domain/Book";
import { BookService } from "../../service/book.service";
import { DatabaseService } from "../../service/database.service";
import { ActivatedRoute } from "@angular/router";

@Component({
    selector: 'app-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: [ './book-list.component.scss' ]
})
export class BookListComponent implements OnInit {

    public books: Book[] = [];

    constructor(private bookService: BookService, private databaseService: DatabaseService, private act: ActivatedRoute) {
        this.act.data.subscribe(value => {
            this.books = value.books;
        });
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
                this.databaseService.db.books.toArray().then((value: Book[]) => this.books = value);
            }
        )
    }
}
