import JSEncrypt from 'jsencrypt';
import { adminApi } from '../api/admin';

let cachedPublicKeyPem: string | null = null;

/** 拉取并缓存后端 RSA 公钥（PEM） */
export async function getLoginPublicKeyPem(): Promise<string> {
  if (cachedPublicKeyPem) {
    return cachedPublicKeyPem;
  }
  const res = await adminApi.getPublicKey();
  const pem = res.data?.publicKeyPem;
  if (!pem) {
    throw new Error('无法获取登录加密公钥');
  }
  cachedPublicKeyPem = pem;
  return pem;
}

/** 使用后端公钥 RSA 加密明文密码，返回 Base64 密文 */
export async function encryptLoginPassword(plainPassword: string): Promise<string> {
  const pem = await getLoginPublicKeyPem();
  const encryptor = new JSEncrypt();
  encryptor.setPublicKey(pem);
  const cipher = encryptor.encrypt(plainPassword);
  if (!cipher) {
    throw new Error('密码加密失败');
  }
  return cipher;
}
