declare module 'jsencrypt' {
  export class JSEncrypt {
    constructor(options?: { default_key_size?: string });
    setPublicKey(pubkey: string): void;
    setPrivateKey(privkey: string): void;
    encrypt(text: string): string | false;
    decrypt(text: string): string | false;
    getPublicKey(): string;
    getPrivateKey(): string;
  }

  export default JSEncrypt;
}
