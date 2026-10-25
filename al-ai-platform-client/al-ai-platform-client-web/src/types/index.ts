export interface ApiResponse<T = any> {
  code: number;
  message: string;
  data?: T;
  timestamp?: number;
}

export interface PageResult<T = any> {
  pageNum: number;
  pageSize: number;
  total: number;
  list: T[];
}

export interface LoginResponse {
  token: string;
  userId: number;
  username: string;
  nickname?: string;
}
