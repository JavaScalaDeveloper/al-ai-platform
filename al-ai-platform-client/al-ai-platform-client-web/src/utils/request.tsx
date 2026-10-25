import axios, { AxiosInstance, AxiosResponse } from 'axios';
import { message } from 'antd';

export class ApiRequestError extends Error {
  readonly notified = true;
  constructor(msg: string) {
    super(msg);
    this.name = 'ApiRequestError';
  }
}

function extractErrorMessage(error: any): string {
  const fromData = error?.response?.data?.message || error?.message;
  if (fromData && typeof fromData === 'string' && fromData !== 'Network Error') {
    return fromData;
  }
  if (error?.message === 'Network Error' || error?.code === 'ERR_NETWORK') {
    return '无法连接后端服务，请检查服务是否已启动';
  }
  return '请求失败';
}

function rejectWithToast(error: any): Promise<never> {
  if (error instanceof ApiRequestError && error.notified) {
    return Promise.reject(error);
  }
  const msg = extractErrorMessage(error);
  message.error(msg);
  return Promise.reject(new ApiRequestError(msg));
}

const request: AxiosInstance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
  timeout: 120000,
});

request.interceptors.request.use((config: any) => {
  const token = localStorage.getItem('adminToken');
  const adminUser = localStorage.getItem('adminUser');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  if (adminUser) {
    config.headers['X-Current-User'] = adminUser;
  }
  return config;
}, (error) => rejectWithToast(error));

request.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;
    if (res && typeof res === 'object' && 'code' in res && res.code !== 200) {
      if ([401, 2001, 2004, 2005].includes(res.code)) {
        localStorage.removeItem('adminToken');
        window.location.href = '/login';
      }
      return rejectWithToast(new Error(res.message || '请求失败'));
    }
    return res;
  },
  (error) => {
    if (error?.response?.status === 401) {
      localStorage.removeItem('adminToken');
      window.location.href = '/login';
    }
    return rejectWithToast(error);
  }
);

export default request;
