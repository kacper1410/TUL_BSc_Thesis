import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ADD_BOOK, REMOVE_BOOK, UPDATE } from "../../domain/actionTypes";
import { ShelfService } from "../../service/shelf.service";
import { BookService } from "../../service/book.service";
import { Shelf } from "../../domain/Shelf";

@Component({
    selector: 'app-sync-error-modal',
    templateUrl: './sync-error-modal.component.html',
    styleUrls: ['./sync-error-modal.component.scss']
})
export class SyncErrorModalComponent implements OnInit {

    errorNotifications: any = [];

    constructor(public activeModal: NgbActiveModal,
                private shelfService: ShelfService,
                private bookService: BookService) {
    }

    ngOnInit(): void {
    }

    public initialize(errorsByShelfId: any) {
        for (const shelfId in errorsByShelfId) {
            this.shelfService.getShelf(Number(shelfId)).subscribe(
                (shelf) => {
                    errorsByShelfId[shelfId].forEach(
                        (error: any) => error.bookId
                            ? this.resolveBook(error.bookId, shelf, error.shelfActionType)
                            : this.resolveWithoutBook(shelf, error.shelfActionType)
                    )
                }
            )
        }
    }

    private resolveBook(bookId: number, shelf: Shelf, actionType: string) {
        this.bookService.getBookDetails(bookId).subscribe(
            (book)=> this.errorNotifications.push({
                shelf: shelf.name,
                type: this.translateActionType(actionType),
                book: book.title
            })
        )
    }

    private resolveWithoutBook(shelf: Shelf, actionType: string) {
        this.errorNotifications.push({
            shelf: shelf.name,
            type: this.translateActionType(actionType),
        })
    }

    private translateActionType(shelfActionType: string) {
        switch (shelfActionType) {
            case REMOVE_BOOK:
                return "Remove book";
            case ADD_BOOK:
                return "Add book";
            case UPDATE:
                return "Edit shelf";
            default:
                return "UKNOWN";
        }
    }

}
