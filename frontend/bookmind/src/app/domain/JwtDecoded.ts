export interface JwtDecoded {
    sub: string,
    iat: number,
    exp: number,
    authorities: string
}
