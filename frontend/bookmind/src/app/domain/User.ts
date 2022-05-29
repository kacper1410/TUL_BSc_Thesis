import { Authority } from "./Authority";

export interface User {
    id: number;
    version: number;
    username: string
    email: string
    enabled: boolean
    authorities: Authority[]
    password: string
}
