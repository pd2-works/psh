# Pd2 Shell

A shell-like toolbox based on Java &amp; Kotlin.

...Maybe it is just a real shell? (run

## How to Use

PSH is in early development, now it doesn't have any functions.

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
  - Specify the command to execute directly after PSH's initialization.\
  - PSH will quit automatically after the command executing.
```

By the way, `$PSH_HOME` is Pd2 Shell's program directory path. You can also add it into the environment variable `$PATH`.

## About Contributing Code

- Do **NOT** commit folders like `.gradle`/`.idea` and so on.
- Please write code comments and docs by hand, **the more the better**.

[Help to improve the README text](#)
