# ChatGPT 手机版（第三方网页封装示例）

这是一个 Android Studio 项目。安装后会显示“ChatGPT 手机版”桌面图标，并打开 `https://chatgpt.com/`。

## 重要说明
- 这不是 OpenAI 官方应用，也不包含 OpenAI API 密钥。
- 登录和聊天由 ChatGPT 官方网站提供；网页策略或版本变化可能导致部分功能在 WebView 中不可用。
- 如果登录失败，请使用官方 ChatGPT Android 应用或在 Chrome 中访问 ChatGPT。
- 不要在第三方应用中输入或保存不必要的敏感信息。

## 编译 APK
1. 在电脑安装 Android Studio（建议使用稳定版）。
2. 解压项目文件夹并在 Android Studio 中选择 **Open**。
3. 等待 Gradle 同步完成；如提示安装 Android SDK Platform 35，请按提示安装。
4. 选择 **Build > Build Bundle(s) / APK(s) > Build APK(s)**。
5. 构建完成后，点击提示中的 **locate**，找到 `app-debug.apk`。
6. 将 APK 传到小米手机。打开文件安装；若系统询问，允许该文件管理器“安装未知应用”。只安装你自己编译且信任的文件。

## 环境
- Android Studio
- JDK 17
- Android SDK Platform 35
- Gradle Wrapper 可由 Android Studio 首次导入时生成/配置


## 仅用手机通过云端编译（无需 Windows 电脑）
1. 在 GitHub 网站创建一个新的空仓库。
2. 将本 ZIP 解压后，把里面的项目文件上传到仓库根目录（需保留 `.github/workflows/build-apk.yml`；部分手机文件管理器默认隐藏 `.github` 文件夹）。
3. 打开仓库的 **Actions** 标签，选择 **Build Android APK**，再点 **Run workflow**（如果上传代码后自动运行，也可直接打开最近一次运行）。
4. 等待任务完成，进入成功的运行记录，在 **Artifacts** 区域下载 `ChatGPTMobile-debug-apk`。
5. 解压下载的 artifact，里面的 `app-debug.apk` 就是安装文件。

注意：GitHub 账号和仓库的具体页面可能随网站调整；请勿把密码、API 密钥或个人登录信息写进代码仓库。这个 APK 是调试签名，不是 OpenAI 官方签名应用。
