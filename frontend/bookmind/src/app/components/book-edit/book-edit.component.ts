import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { defaultBook } from "../../domain/default/defaultBook";
import { BookService } from "../../service/book.service";
import { NotificationService } from "../../service/notification.service";

@Component({
    selector: 'app-book-edit',
    templateUrl: './book-edit.component.html',
    styleUrls: ['./book-edit.component.scss']
})
export class BookEditComponent implements OnInit {

    public book = defaultBook()

    constructor(private act: ActivatedRoute,
                private router: Router,
                private bookService: BookService,
                private notify: NotificationService) {
        this.act.data.subscribe(value => {
            this.book = value.book;
        });
    }

    ngOnInit(): void {
        console.log(this.book);
    }

    updateBook() {
        this.bookService.updateBook(this.book, this.book.id).subscribe(
            () => {
                this.notify.success('Success.book.update');
                this.router.navigateByUrl('/books');
            }
        )
    }
}
