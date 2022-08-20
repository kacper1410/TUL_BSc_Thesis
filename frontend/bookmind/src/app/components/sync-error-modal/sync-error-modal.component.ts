import { Component, OnInit } from '@angular/core';
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";
import { ADD_BOOK, REMOVE_BOOK, UPDATE } from "../../domain/actionTypes";
import { Shelf } from "../../domain/Shelf";
import { DatabaseService } from "../../service/database.service";
import { AuthService } from "../../service/auth.service";

@Component({
    selector: 'app-sync-error-modal',
    templateUrl: './sync-error-modal.component.html',
    styleUrls: ['./sync-error-modal.component.scss']
})
export class SyncErrorModalComponent implements OnInit {

    errorNotifications: any = [];

    constructor(public activeModal: NgbActiveModal,
                private dbService: DatabaseService,
                private authService: AuthService) {
    }

    ngOnInit(): void {
    }

    public initialize(errorsByShelfId: any) {
        const username = this.authService.getUsername();
        for (const shelfId in errorsByShelfId) {
            this.dbService.getShelfByUsername(Number(shelfId), username).subscribe(
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
        this.dbService.getBook(bookId).subscribe(
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
