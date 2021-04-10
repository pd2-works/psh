# Pd2 Shell

*English* | [简体中文](README_sc.md)

A shell-like toolbox based on Java.

...Maybe it is just a real shell? (run

[Pd2 Shell Documents (Simplified Chinese)](https://pd2-works.github.io/psh){:target="_blank"}

## How to Use

PSH is in test version, now it doesn't have any useful functions.

A plan to launch PSH is to execute a command directly, like this:

```
$PSH_HOME/psh [-d|--directory <work directory>] [-u|--user <login username>] [-c|--command <command>]

  <work directory>
  - Specify the work directory.
  - Almost all operations about file will treat this as the current directory.
  - The default value is the directory where this command is executed
  
  <login username>
  - Specify the user used to perform shell operations.
  - ** Linux only, but you can make it support other systems by adding plugins
  - If it isn't the current user, you may need a password.
  
  <command>
  - Specify the command to execute directly after PSH's initialization.
  - PSH will quit automatically after the command executing.
```

By the way, `$PSH_HOME` is Pd2 Shell's program directory path. You can also add it into the `$PATH` environment variable.

## About Contributing Code

We welcome any non-malicious code contributions, but:

- Do **NOT** commit folders like `.gradle`, `.idea` and so on.
- Please write code comments and docs by hand, **the more the better**.

[Help to translate or improve the README text](https://github.com/pd2-works/psh/issues/new?title=CONTRI_README)
