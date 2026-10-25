# al-ai-platform-client-web

AL AI Platform Web 管理后台。

## 技术栈

- React 18 + TypeScript
- Ant Design 5
- React Router v6
- Axios（统一拦截器 + Token）
- CRA + `react-app-rewired`

## 菜单结构

| 一级 | 二级 | 三级 | 路径 |
|------|------|------|------|
| 平台能力 | 对话与 Agent | 对话试验场 | `/chat/playground` |
| | | 会话列表 | `/chat/sessions` |
| | 知识库 | 知识库管理 | `/knowledge/bases` |
| | | Prompt 模板 | `/knowledge/prompts` |
| | 模型中心 | 模型配置 | `/model/configs` |
| | 运维看板 | 控制台 | `/ops/dashboard` |

## 环境变量

见 `.env`：

```
PORT=3282
REACT_APP_API_BASE_URL=http://localhost:8282/api
```

## 启动

```bash
npm install
npm start
# http://localhost:3282
```

登录账号（与后端 yml 超管一致）：`superadmin` / `SuperAdmin@8282`  
（库表管理员 `admin` / `admin123` 仍可用，需先初始化 MySQL。）

## 目录结构

```
src/
├── api/admin.tsx      # 管理端 API（全 POST）
├── utils/request.tsx  # Axios + Token
├── types/
└── pages/             # Login / ProductHome / Layout / 业务页
```

## 相关模块

- 客户端聚合：[../README.md](../README.md)
- Java 服务端：[../../al-ai-platform-langchain4j/README.md](../../al-ai-platform-langchain4j/README.md)
