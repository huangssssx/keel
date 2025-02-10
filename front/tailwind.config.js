/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: "var(--el-color-primary)",
          "light-3": "var(--el-color-primary-light-3)",
          "light-5": "var(--el-color-primary-light-5)",
          "light-7": "var(--el-color-primary-light-7)",
          "light-9": "var(--el-color-primary-light-9)",
          "dark-2": "var(--el-color-primary-dark-2)",
        },
      },
    },
  },
  plugins: [],
  corePlugins: {
    preflight: false, // 禁用 Tailwind 的基础样式重置，避免与 Element Plus 冲突
  },
};
