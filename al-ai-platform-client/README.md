# al-ai-platform-client

客户端聚合目录，包含 Web 管理端与小程序端。

## 子模块

| 目录 | 说明 | 状态 |
|------|------|------|
| [al-ai-platform-client-web](./al-ai-platform-client-web) | React + Ant Design 管理后台 | 已搭建 |
| [al-ai-platform-client-mini](./al-ai-platform-client-mini) | 微信小程序 / 跨端小程序 | 待建 |

## 后端依赖

默认对接 `al-ai-platform-langchain4j-admin`：

- API Base：`http://localhost:8282/api`
- 约定：全部 `POST` + JSON Body

## 相关文档

- 仓库总览：[../README.md](../README.md)
- 服务端：[../al-ai-platform-langchain4j/README.md](../al-ai-platform-langchain4j/README.md)
