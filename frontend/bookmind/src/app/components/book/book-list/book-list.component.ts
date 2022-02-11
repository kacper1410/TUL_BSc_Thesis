import { Component, OnInit } from '@angular/core';
import { Book } from "../../../domain/Book";
import { BookService } from "../../../service/book.service";
import { DatabaseService } from "../../../service/database.service";
import { ActivatedRoute, Router } from "@angular/router";
import { ConfirmService } from "../../../service/confirm.service";
import { AuthService } from "../../../service/auth.service";

@Component({
    selector: 'app-book-list',
    templateUrl: './book-list.component.html',
    styleUrls: ['./book-list.component.scss']
})
export class BookListComponent implements OnInit {

    public books: Book[] = [];

    constructor(private bookService: BookService,
                private databaseService: DatabaseService,
                private act: ActivatedRoute,
                private confirm: ConfirmService,
                public authService: AuthService,
                private router: Router) {
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

    delete(id: number) {
        this.confirm.confirm('Are you sure you want to delete this book?', () =>
            this.bookService.remove(id).subscribe(
                () => this.getBooks()
            )
        )
    }

    update(id: number) {
        this.router.navigateByUrl('/books/edit/' + id);
    }
}
