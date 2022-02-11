import { Component, OnInit } from '@angular/core';
import { BookService } from "../../../service/book.service";
import { defaultBook } from "../../../domain/default/defaultBook";
import { Router } from "@angular/router";

@Component({
    selector: 'app-book-add',
    templateUrl: './book-add.component.html',
    styleUrls: [ './book-add.component.scss' ]
})
export class BookAddComponent implements OnInit {

    book = defaultBook();

    constructor(private bookService: BookService, private router: Router) {
    }

    ngOnInit(): void {
    }

    addBook() {
        this.bookService.addBook(this.book).subscribe(
            () => this.router.navigateByUrl("/books")
        )
    }
}
