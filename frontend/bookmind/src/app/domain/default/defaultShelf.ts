import { Shelf } from "../Shelf";

export function defaultShelf(): Shelf {
    return {
        id: 0,
        version: 0,
        name: '',
        username: '',
        books: [],
        new: false
    }
}
