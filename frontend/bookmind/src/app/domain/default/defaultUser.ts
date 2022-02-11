import { User } from "../User";

export function defaultUser(): User {
    return {
        id: 0,
        version: 0,
        username: '',
        email: '',
        enabled: false,
        authorities: [],
    }
}
