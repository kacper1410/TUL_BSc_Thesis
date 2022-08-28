import { Shelf } from "./Shelf";

export interface Book {
    active: any;
    id: number;
    version: number;
    title: string;
    author: string;
    shelves: Shelf[],
    connectionVersion: number,
    connectionVersionSignature: string
}
