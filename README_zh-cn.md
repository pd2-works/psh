# Pd2 Shell

[English](README.md) | *简体中文*

一个基于 Java &amp; Kotlin 的类 shell 实用工具。

...或许这就是一个真正的 shell? (run

## 如何使用

PSH 正处于早期开发状态，目前它并没有任何功能。

启动 PSH 的一个方法是直接执行命令，像这样：

```
$ PSH_HOME/psh [-d|--directory <work directory>] [-u|--user <login username>] [-c|--command <commvand>]

  <work directory>
  - 指定工作目录
  - 几乎所有的关于文件的操作都会将此目录视为当前目录
  - 默认值是命令运行的位置
  
  <login username>
  - 指定执行 shell 操作的用户
  - ** 仅支持 Linux，但你可以通过添加插件来使其支持其它系统
  - 如果设置值不是当前用户，你可能需要输入密码
  
  <command>
  - 指定在 PSH 初始化之后直接执行的命令
  - PSH 将会在执行此命令后自动退出
```

顺带一提，`$PSH_HOME` 是 Pd2 Shell 的程序文件夹的路径，你也可以将其添加到 `$PATH` 环境变量中。

## 关于贡献代码

我们欢迎任何非恶意的代码贡献，只要：

- **不要**将 `.gradle`, `.idea` 之类的目录提交上来。
- 请顺手写下代码注释和文档，**越多越好**

[帮助我们翻译 README 或提高 README 的质量](https://github.com/pd2-works/psh/issues/new?title=CONTRI_README&#41;)
