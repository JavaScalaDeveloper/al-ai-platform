# AL AI Platform API 设计规范

## 重要规定

**本服务端一律禁止使用 RESTful 风格！**

1. 路径上不允许有参数（禁止 `@PathVariable`）
2. 所有入参都必须为 Body（统一 `@RequestBody`）
3. 统一使用 POST 方法（不使用 GET、PUT、DELETE）
4. 路径命名：`/api/{模块}/{操作名}`，如 `/api/admin/login`、`/api/admin/model/list`

## 正确示例

```java
@PostMapping("/detail")
public Result<XxxResponse> detail(@RequestBody IdRequest request) { }

@PostMapping("/list")
public Result<PageResult<XxxResponse>> list(@RequestBody XxxRequest request) { }

@PostMapping("/delete")
public Result<Void> delete(@RequestBody IdRequest request) { }
```

## DTO 约定

- `XxxResponse extends Xxx`（实体）
- `XxxRequest extends XxxResponse`（含 pageNum/pageSize）
- Service 接口继承 `BaseService<XxxRequest, XxxResponse>`
