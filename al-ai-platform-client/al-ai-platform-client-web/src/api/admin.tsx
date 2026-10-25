import request from '../utils/request';
import { ApiResponse, LoginResponse, PageResult } from '../types';

export const adminApi = {
  /** 登录密码 RSA 公钥（PEM） */
  getPublicKey: (): Promise<ApiResponse<{ publicKeyPem: string }>> =>
    request.post('/admin/crypto/public-key', {}),
  /** password 为 RSA 加密后的 Base64 密文，禁止传明文 */
  login: (data: { username: string; password: string }): Promise<ApiResponse<LoginResponse>> =>
    request.post('/admin/login', data),
  logout: (): Promise<ApiResponse<void>> => request.post('/admin/logout', {}),
  dashboard: (): Promise<ApiResponse<any>> => request.post('/admin/dashboard', {}),

  modelList: (data: any): Promise<ApiResponse<PageResult>> => request.post('/admin/model/list', data),
  modelCreate: (data: any): Promise<ApiResponse<void>> => request.post('/admin/model/create', data),
  modelUpdate: (data: any): Promise<ApiResponse<void>> => request.post('/admin/model/update', data),
  modelDelete: (data: { id: number }): Promise<ApiResponse<void>> => request.post('/admin/model/delete', data),

  knowledgeList: (data: any): Promise<ApiResponse<PageResult>> => request.post('/admin/knowledge/list', data),
  knowledgeCreate: (data: any): Promise<ApiResponse<void>> => request.post('/admin/knowledge/create', data),
  knowledgeUpdate: (data: any): Promise<ApiResponse<void>> => request.post('/admin/knowledge/update', data),
  knowledgeDelete: (data: { id: number }): Promise<ApiResponse<void>> => request.post('/admin/knowledge/delete', data),

  promptList: (data: any): Promise<ApiResponse<PageResult>> => request.post('/admin/prompt/list', data),
  promptCreate: (data: any): Promise<ApiResponse<void>> => request.post('/admin/prompt/create', data),
  promptUpdate: (data: any): Promise<ApiResponse<void>> => request.post('/admin/prompt/update', data),
  promptDelete: (data: { id: number }): Promise<ApiResponse<void>> => request.post('/admin/prompt/delete', data),

  chatSessionList: (data: any): Promise<ApiResponse<PageResult>> => request.post('/admin/chat/session/list', data),
  chatMessageList: (data: any): Promise<ApiResponse<PageResult>> => request.post('/admin/chat/message/list', data),
  chatCompletion: (data: any): Promise<ApiResponse<any>> => request.post('/admin/chat/completion', data),
  chatSessionDelete: (data: { id: number }): Promise<ApiResponse<void>> => request.post('/admin/chat/session/delete', data),
};
