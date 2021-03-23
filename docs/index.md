---
layout: default
title: "主页"
---

# Pd2 Shell 官方文档

这里是 Pd2 Shell 的官方文档，欢迎你的到来！

**文档主要部分尚未完善**，欢迎来与我们一同编写。

[快速开始](#开始){: .btn }
[GitHub](https://github.com/pd2-works/psh){: .btn }

## 开始

### 选择你的身份

不论是开发者，还是使用者，这里都有详细指引。

[我是开发者](developer){: .btn .btn-purple }

```java
import ink.pd2.shell.api.*;
public class ExamplePlugin extends Plugin {
	public Plugin() {
		//初始化插件对象
		//调用父类方法传递插件信息
		String name = Resources.INS.getString("example.name");
		String description = Resources.INS.getString("example.description");
		super("example", 1, name, description);
	}
	public void init() throws InitializationException {
		//TODO 初始化插件功能
	}
}
```

[我是使用者](user){: .btn .btn-blue }
```shell
psh:echo "Hello, world!"
psh:help -c 1
psh:modmgr plugin view --all
sudo systemctl enable smb.service # 是的没错这里甚至有sudo
```

## 关于项目

Pd2 Shell 是Pd2群组成员 Maxel Black 发起的一个类shell工具箱开发项目。
该项目使用 Java 语言，期望提供一个有着统一扩展接口和强大自定义功能的跨平台CLI实用工具框架，使控制台工具的日常使用更加方便快捷。

~~同时由于拥有相当开放的自定义功能（几乎可以更改一切），用控制台装逼也变得特别简单！（大雾）~~

### 项目参与者

- [Maxel Black](https://github.com/maxelblack) ([maxelblack@outlook.com](mailto:maxelblack@outlook.com))
- [Cookie Sukazyo Eyre](https://github.com/Eyre-S) ([sukazyo@outlook.com](mailto:sukazyo@outlook.com))

Pd2 Shell 是开源项目，我们欢迎任何非恶意代码贡献，包括但不限于核心代码、平台支持插件，甚至这个文档和它的翻译版本等。

对项目做过贡献的人若无特殊需求均会被列入上方参与者列表，即使你可能只贡献了一个字。
我们对每个做出贡献的人表示衷心感谢！

~~如果觉得项目不错的话就去[GitHub主页](https://github.com/pd2-works/psh)给个Star呗\~~~
