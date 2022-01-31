import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { defaultBook } from "../../domain/default/defaultBook";

@Component({
    selector: 'app-book-edit',
    templateUrl: './book-edit.component.html',
    styleUrls: ['./book-edit.component.scss']
})
export class BookEditComponent implements OnInit {

    private book = defaultBook()

    constructor(private act: ActivatedRoute) {
        this.act.data.subscribe(value => {
            this.book = value.book;
        });
    }

    ngOnInit(): void {
        console.log(this.book);
    }

}
