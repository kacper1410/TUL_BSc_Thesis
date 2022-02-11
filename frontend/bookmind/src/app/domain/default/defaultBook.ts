import { Book } from "../Book";

export function defaultBook(): Book {
    return {
        id: 0,
        version: 0,
        title: '',
        author: ''
    }
}
