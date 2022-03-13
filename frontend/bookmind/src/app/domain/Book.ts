import { Shelf } from "./Shelf";

export interface Book {
    id: number;
    version: number;
    title: string;
    author: string;
    shelves: Shelf[]
}
