# al-ai-platform-langchain

Python 原生 LangChain 侧（占位模块）。

## 定位

与 Java 侧 `al-ai-platform-langchain4j` 互补：

| 侧 | 技术 | 典型用途 |
|----|------|----------|
| Java（langchain4j） | Spring Boot + LangChain4j | 管理端、平台 API、持久化 |
| Python（本模块） | LangChain / LangGraph 等 | 实验性 Agent、评测、批处理流水线 |

## 当前状态

目录已预留，**尚未初始化**（Poetry / uv / requirements 等待定）。

## 数据库（与 Java 共用）

若后续直连 MySQL，库名同样使用前缀 `al-ai-platform-`：

- `al-ai-platform-common`：平台通用能力
- `al-ai-platform-user`：账号会话
- `al-ai-platform-biz`：业务数据预留

## 相关文档

- 仓库总览：[../README.md](../README.md)
- Java 服务端：[../al-ai-platform-langchain4j/README.md](../al-ai-platform-langchain4j/README.md)
