import { Book } from "./Book";

export interface Shelf {
    active: any;
    id: number;
    version: number;
    name: string;
    username: string;
    books: Book[],
    new: boolean
}
