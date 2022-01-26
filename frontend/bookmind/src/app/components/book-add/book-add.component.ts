import { Component, OnInit } from '@angular/core';
import { BookService } from "../../service/book.service";

@Component({
    selector: 'app-book-add',
    templateUrl: './book-add.component.html',
    styleUrls: [ './book-add.component.scss' ]
})
export class BookAddComponent implements OnInit {

    constructor(private bookService: BookService) {
    }

    ngOnInit(): void {
    }

    addBook(title: string) {
        this.bookService.addBook({title: title}).subscribe(
            () =>  console.log("Super")
        )
    }
}
