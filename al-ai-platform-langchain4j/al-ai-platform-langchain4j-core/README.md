# al-ai-platform-langchain4j-core

服务端核心能力库（无独立启动类），被 `al-ai-platform-langchain4j-admin` 依赖。

## 职责

- 统一返回体：`Result` / `ResultCode` / `PageResult`
- 异常：`BusinessException`
- CRUD 模板：`BaseService` / `BaseServiceImpl`
- 多数据源：`common`（`al-ai-platform-common`）、`user`（`al-ai-platform-user`）；`biz` 库预留业务数据
- 包域（代码）与库职责：
  - `biz.user` → user 库：管理员账号、登录会话
  - `biz.model` / `chat` / `knowledge` / `prompt` → common 库：平台通用 AI 能力

## 包结构

```
com.al.ai.platform.core
├── common/          # Result、DTO、异常、时区
├── service/         # BaseService*
├── config/          # Mybatis-Plus 分页等
├── datasource/      # biz / user 数据源
└── biz/             # 各业务域 entity / mapper / dto / service
```

## 约定

- Service 必须继承 `BaseService`
- DTO：`XxxRequest extends XxxResponse extends Xxx(Entity)`
- 出入参字段已知时禁止滥用 `Map`

详见仓库 `.cursor/skills/al-ai-backend-conventions/`。

## 依赖关系

```
al-ai-platform-langchain4j-admin
        └── al-ai-platform-langchain4j-core
```
