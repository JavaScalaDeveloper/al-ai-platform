# al-ai-platform-langchain4j

Java 21 服务端：Spring Boot 3.3 + MyBatis-Plus + LangChain4j。

## 子模块

| 模块 | 说明 |
|------|------|
| [al-ai-platform-langchain4j-core](./al-ai-platform-langchain4j-core) | 实体 / Mapper / Service / 多数据源 / LangChain4j 对话 |
| [al-ai-platform-langchain4j-admin](./al-ai-platform-langchain4j-admin) | 管理端启动（默认端口 **8282**） |

## 数据库

库名前缀：`al-ai-platform-`

| 库名 | 用途 |
|------|------|
| `al-ai-platform-common` | 平台通用能力（模型 / 对话 / 知识库 / Prompt / 二进制文件） |
| `al-ai-platform-user` | 账号、会话 |
| `al-ai-platform-biz` | 业务数据预留（如业务侧生成提示词等） |

DDL 目录：`al-ai-platform-langchain4j-admin/src/main/resources/sql/`

```bash
# 推荐一键执行
mysql -h127.0.0.1 -uroot -p < \
  al-ai-platform-langchain4j-admin/src/main/resources/sql/00_all_in_one.sql
```

超管（yml）：`superadmin` / `SuperAdmin@8282`  
库表管理员（DDL 种子）：`admin` / `admin123`

## 环境变量

| 变量 | 默认 | 说明 |
|------|------|------|
| `ALAI_DB_HOST` | `aliyun-2c2g` | MySQL 主机（线下/测试） |
| `ALAI_DB_PORT` | `3307` | 端口 |
| `ALAI_DB_USER` | （环境变量必填） | 用户名，写入本机 `~/.zshrc` |
| `ALAI_DB_PASSWORD` | （环境变量必填） | 密码，写入本机 `~/.zshrc`，勿提交仓库 |
| `ALAI_LLM_BASE_URL` | `http://127.0.0.1:11434/v1` | OpenAI 兼容 Base URL |
| `ALAI_LLM_API_KEY` | `sk-local` | API Key |
| `ALAI_LLM_MODEL` | `qwen2.5` | 默认模型 ID |

## 启动

```bash
mvn -pl al-ai-platform-langchain4j-admin -am spring-boot:run
# http://localhost:8282
```

## API 约定

见 [API_DESIGN_STANDARD.md](./API_DESIGN_STANDARD.md)：

- 禁止 RESTful 路径参数
- 统一 `POST` + `@RequestBody`
- 路径形如 `/api/admin/{模块}/{操作}`

后端编码规范：仓库根目录 `.cursor/skills/al-ai-backend-conventions/`
