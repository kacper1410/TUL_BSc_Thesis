import { Book } from "./Book";

export interface Shelf {
    id: number;
    version: number;
    name: string;
    username: string;
    books: Book[],
    new: boolean
}
