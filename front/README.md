我来帮你更新 README.md 文件，主要补充环境配置相关的信息：

```markdown:/Users/huangchuanjian/workspace/keel/front/README.md
# Keel - Vue 3 + TypeScript + Vite + Vue Router + Pinia

这是一个基于 Vue 3 技术栈的前端项目模板，集成了主流的开发工具和框架。

## 技术栈

- Vue 3
- TypeScript
- Vite
- Vue Router
- Pinia
- Element Plus
- Axios

## 环境要求

- Node.js >= 16.0.0
- npm >= 7.0.0

## 项目设置

1. 安装依赖
```bash
npm install
```

2. 开发环境运行
```bash
npm run dev
```

3. 生产环境构建
```bash
npm run build
```

## 开发配置

### 环境变量

项目使用 `.env` 文件管理环境变量：

- `VITE_API_BASE_URL`: API 请求的基础路径，默认为 `/api`

### 代理配置

开发环境下的代理配置（vite.config.ts）：

- 目标服务器：`http://127.0.0.1:8000`
- 代理路径：`/api`
- 支持请求日志记录
- 自动处理跨域问题

### TypeScript 配置

项目包含三个 TypeScript 配置文件：

- `tsconfig.json`: 基础配置文件
- `tsconfig.app.json`: 应用程序特定配置
- `tsconfig.node.json`: Node.js 环境配置

### 目录结构

```
src/
├── api/          # API 接口定义
├── assets/       # 静态资源
├── components/   # 公共组件
├── composeable/  # 组合式函数
├── router/       # 路由配置
├── stores/       # Pinia 状态管理
├── utils/        # 工具函数
└── views/        # 页面组件
```

## 特性

- 基于 Vue 3 的组合式 API
- TypeScript 支持
- 状态管理（Pinia）
- 路由管理（Vue Router）
- UI 组件库（Element Plus）
- 开发环境代理配置
- ESLint + Prettier 代码规范
```

这个更新后的 README.md 主要补充了：

1. 详细的技术栈说明
2. 环境要求
3. 完整的项目设置步骤
4. 开发配置说明，包括环境变量、代理配置等
5. TypeScript 配置说明
6. 清晰的目录结构
7. 项目特性概述

这样的 README 文件能够帮助新加入的开发者快速了解项目结构和配置要求。