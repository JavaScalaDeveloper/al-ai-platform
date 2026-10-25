# al-ai-platform

专注于各类 AI 应用开发、测试与云原生部署的综合工作台。

## 模块一览

| 路径 | 说明 |
|------|------|
| [al-ai-platform-client](./al-ai-platform-client) | 客户端聚合目录 |
| [al-ai-platform-client-web](./al-ai-platform-client/al-ai-platform-client-web) | Web 管理端（React + Ant Design） |
| [al-ai-platform-client-mini](./al-ai-platform-client/al-ai-platform-client-mini) | 小程序端（待建） |
| [al-ai-platform-langchain4j](./al-ai-platform-langchain4j) | Java 21 服务端（Spring Boot + LangChain4j） |
| [al-ai-platform-langchain](./al-ai-platform-langchain) | Python LangChain（待建） |

## 数据库

库名前缀统一为 **`al-ai-platform-`**：

| 库名 | 用途 |
|------|------|
| `al-ai-platform-common` | 平台通用能力（模型、对话、知识库、Prompt、二进制文件） |
| `al-ai-platform-user` | 账号、登录会话 |
| `al-ai-platform-biz` | 业务数据（预留，如业务侧 AI 生成提示词等） |

一键 DDL：

```bash
mysql -h127.0.0.1 -uroot -p < \
  al-ai-platform-langchain4j/al-ai-platform-langchain4j-admin/src/main/resources/sql/00_all_in_one.sql
```

超管（yml）：`superadmin` / `SuperAdmin@8282`  
库表管理员：`admin` / `admin123`

## 快速开始

```bash
# 1. 初始化 MySQL（见上）

# 2. 启动 Java 管理端（8282）
cd al-ai-platform-langchain4j
mvn -pl al-ai-platform-langchain4j-admin -am spring-boot:run

# 3. 启动 Web（3282）
cd ../al-ai-platform-client/al-ai-platform-client-web
npm install && npm start
```

## 约定

- 后端规范：`.cursor/skills/al-ai-backend-conventions/`
- API 规范：`al-ai-platform-langchain4j/API_DESIGN_STANDARD.md`（全 POST + Body）
