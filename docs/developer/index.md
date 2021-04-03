---
layout: default
title: 插件开发
has_children: true
---

# PSH 插件开发手册

Pd2 Shell 拥有一个模块式的扩展接口，并将“模块”概念分成了两种。
其中，一种“模块”是用来扩展核心功能的[ `Plugin` (插件)](#插件)，而另一种是用来扩展插件功能的[ `Extension` (扩展)](#扩展)。

那么现在就开始吧！

## 核心依赖库

~~好吧目前确实连个依赖存储库地址都没有，所以~~
暂且从 [GitHub Releases](https://github.com/pd2-works/psh/releases){:target="_blank"} 下载一个 JAR 来用吧... （发出 咕 声）

下载之后导入即可。（对于 Java IDE 建议使用 [IntelliJ IDEA](https://www.jetbrains.com/zh-cn/idea){:target="_blank"} ，其他 IDE 都不大好用 ~~点名批评VS~~ ，文档中提到的编辑技巧之类都是针对 IDEA）

## 清单文件

PSH 使用 XML 格式的清单文件，模块加载时 PSH 会读取 JAR 根目录的 `manifest.xml` 文件并据此文件加载配置。

详见

[清单文件](manifest.md){: .btn .btn-outline }

下面是一个简单的清单示例：

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest>
	<!-- 字符串声明 -->
	<function item="string">
		<parameter name="name">Example</parameter>
		<parameter name="version_code">112</parameter>
	</function>
	<!-- 本地化路径配置 -->
	<function item="i18n">
		<parameter name="directory">@file/lang</parameter>
	</function>
	<!-- 添加插件信息 -->
	<plugin name="@string/name" 
		mainClass="com.example.ExamplePlugin" 
		versionCode="@string/version_code"/>
</manifest>
```

## 模块分类

### 插件

插件用来扩展PSH核心的功能，例如指令、外观自定义、甚至远程管理支持等强大功能。

### 扩展

扩展用于增加插件的功能，但 PSH 本身只提供了很简单的接口，即 `Extension` 类的 `getType` 和 `getObject` 方法，具体功能的 API 由扩展的目标插件提供。

可能有人会问，既然这样还要扩展有什么用呢？理由很简单， PSH 本来就负责提供统一和方便管理的接口框架，所以这个功能也只是为了统一各个插件的接口、方便一起管理，否则就与插件的功能大同小异了，没有必要单独提供。

本手册主讲插件开发，有关扩展的内容请去

[扩展功能介绍](../extension){: .btn .btn-outline }
