import { Book } from "../Book";

export function defaultBook(): Book {
    return {
        active: null,
        id: 0,
        version: 0,
        title: '',
        author: '',
        shelves: [],
        connectionVersion: 0,
        connectionVersionSignature: ''
    }
}
