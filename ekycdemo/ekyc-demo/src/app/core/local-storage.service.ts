import {Inject, Injectable} from '@angular/core';
import * as crypto from 'crypto-js';
import EncUtf8 from 'crypto-js/enc-utf8';

const HASH_SALT = 'AOMH9FIYVVFSAL71QjjfNlu7SElVu4fY';

@Injectable()
export class LocalStorageService {
  constructor() {
  }

  get length(): number {
    return localStorage.length;
  }

  clear(): void {
    return localStorage.clear();
  }

  getItem(key: string): string | null {
    const value = localStorage.getItem(this.getKey(key));
    return value ? crypto.AES.decrypt(value, HASH_SALT).toString(EncUtf8) : value;
  }

  getKey(rawKey: string): string {
    return crypto.SHA256(HASH_SALT + rawKey).toString();
  }

  getObject<T>(key: string): T | null {
    const value = localStorage.getItem(this.getKey(key));
    return value ? JSON.parse(crypto.AES.decrypt(value, HASH_SALT).toString(EncUtf8)) : value;
  }

  key(index: number): string | null {
    return localStorage.key(index);
  }

  removeItem(key: string): void {
    return localStorage.removeItem(this.getKey(key));
  }

  setItem(key: string, value: string): void {
    return localStorage.setItem(this.getKey(key), crypto.AES.encrypt(value, HASH_SALT).toString());
  }

  setObject(key: string, objectValue: object) {
    const value = JSON.stringify(objectValue);
    return this.setItem(key, value);
  }
}
