---
name: al-ai-backend-conventions
description: >-
  Enforces AL AI Platform Java backend conventions for MySQL DDL, Service
  interfaces, HTTP controllers, typed Request/Response DTOs (no Map when keys
  are known), and Response VO idStr for long numeric IDs. Use when writing or
  reviewing SQL DDL, MyBatis entities, Service/ServiceImpl, RestController,
  or any code under al-ai-platform-langchain4j.
---

# AL AI Platform 后端编码规范

编写或修改 `al-ai-platform-langchain4j` 代码时必须遵守以下五条。

## 1. MySQL 建表 DDL

表定义的**前 3 个字段**必须是：

```sql
`id`          bigint auto_increment comment '主键ID',
`create_time` datetime default current_timestamp not null comment '创建时间',
`modify_time` datetime default current_timestamp not null on update current_timestamp comment '修改时间',
```

且索引**至少包含**：

```sql
primary key (`id`),
key `idx_create_time` (`create_time`),
key `idx_modify_time` (`modify_time`)
```

## 2. Service 接口

所有 Service 接口继承 `com.al.ai.platform.core.service.BaseService`。

约定：`XxxResponse extends Xxx`，`XxxRequest extends XxxResponse`。

## 3. Controller

- 仅 `@PostMapping` + `@RequestBody`
- 禁止 `@PathVariable` / `@RequestParam` / GET / PUT / DELETE
- 路径：`/api/admin/{模块}/{操作名}`

## 4. Response VO 的 idStr

超长业务数值 ID 在 VO 上提供 `idStr`；前端只用 `idStr`。

## 5. 禁止滥用 Map

字段已知时必须用 DTO，禁止 `Map` / `JSONObject` 充当契约。
