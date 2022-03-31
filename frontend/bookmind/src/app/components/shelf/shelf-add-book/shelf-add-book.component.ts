import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { ConfirmService } from "../../../service/confirm.service";
import { ShelfService } from "../../../service/shelf.service";
import { defaultBook } from "../../../domain/default/defaultBook";
import { Shelf } from "../../../domain/Shelf";
import { BookService } from "../../../service/book.service";

@Component({
    selector: 'app-shelf-add-book',
    templateUrl: './shelf-add-book.component.html',
    styleUrls: ['./shelf-add-book.component.scss']
})
export class ShelfAddBookComponent implements OnInit {
    public shelves: Array<Shelf> = [];
    public book = defaultBook();

    constructor(private act: ActivatedRoute,
                private confirm: ConfirmService,
                private shelfService: ShelfService,
                private bookService: BookService,
                private router: Router) {
        this.act.data.subscribe(value => {
            this.shelves = value.shelves;
            this.book = value.book;
        });
    }

    ngOnInit(): void {
    }


    getShelvesAndBook() {
        this.bookService.getBookWithShelves(this.book.id).subscribe(
            (book) => this.book = book
        )
        this.shelfService.getMyShelves().subscribe(
            (shelves) => this.shelves = shelves
        )
    }

    viewShelf(id: number) {
        this.router.navigateByUrl(`/shelves/details/${id}`)
    }

    isOnShelf(shelfId: number) {
        if (this.book.shelves)
            return this.book.shelves.some(shelf => shelf.id === shelfId);
        return false;
    }

    addBookToShelf(shelfId: number, bookId: number) {
        this.shelfService.addBookToShelf(bookId, shelfId).subscribe(
            () => this.getShelvesAndBook()
        )
    }

    removeBookFromShelf(shelfId: number, bookId: number) {
        this.confirm.confirm('Are you sure you want to remove this book from shelf?', () =>
            this.shelfService.removeBookFromShelf(bookId, shelfId).subscribe(
                () => this.getShelvesAndBook()
            )
        )
    }
}
