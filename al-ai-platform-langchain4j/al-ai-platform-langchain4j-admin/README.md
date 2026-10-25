# al-ai-platform-langchain4j-admin

管理端 Spring Boot 启动模块（默认端口 **8282**）。

## 职责

- 启动类：`com.al.ai.platform.admin.AdminApplication`
- 薄 Controller 层：鉴权、模型、对话、知识库、Prompt
- CORS、全局异常处理
- 配置与 DDL 资源

## 配置

主配置：`src/main/resources/application.yml`

数据源（库名前缀 `al-ai-platform-`）：

| 配置键 | 库名 |
|--------|------|
| `spring.datasource.common` | `al-ai-platform-common`（平台通用能力） |
| `spring.datasource.user` | `al-ai-platform-user` |
| （后续）`spring.datasource.biz` | `al-ai-platform-biz`（业务数据） |

### 超管（yml，不依赖 DB）

```yaml
alai:
  admin:
    super:
      username: superadmin          # 可用 ALAI_SUPER_USERNAME 覆盖
      password: SuperAdmin@8282     # 可用 ALAI_SUPER_PASSWORD 覆盖
      nickname: 超级管理员
```

登录时优先校验 yml 超管，再回落到库表 `admin_account`。

## DDL

```
src/main/resources/sql/
├── 00_all_in_one.sql          # 一键建库建表
├── create_databases.sql
├── al-ai-platform-common/   # 平台表
├── al-ai-platform-user/
└── al-ai-platform-biz/      # 业务库预留
```

```bash
mysql -h127.0.0.1 -uroot -p < src/main/resources/sql/00_all_in_one.sql
```

## 启动

在父工程目录执行：

```bash
cd ..
mvn -pl al-ai-platform-langchain4j-admin -am spring-boot:run
```

或本模块：

```bash
mvn spring-boot:run
```

## 主要接口前缀

| 前缀 | 说明 |
|------|------|
| `/api/admin/login` | 登录 |
| `/api/admin/model/*` | 模型配置 |
| `/api/admin/chat/*` | 会话 / 对话补全 |
| `/api/admin/knowledge/*` | 知识库 |
| `/api/admin/prompt/*` | Prompt 模板 |

API 一律 `POST` + Body，见父模块 [API_DESIGN_STANDARD.md](../API_DESIGN_STANDARD.md)。
