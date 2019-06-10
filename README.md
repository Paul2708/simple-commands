# simple-commands
Simple commands is a command framework for [Spigot](https://www.spigotmc.org).
It's currently under heavy development, so stay tuned.

Note: This project is inspired by [Akir](https://github.com/aikar/commands).
So checkout the other command framework, if you find this project interesting.

## Why is it called "simple"?
Spigot features its own command framework by using the command design pattern.
It's quite _simple_ to create own commands.
But if you have multiple commands with even more sub commands, there will be a lot of redundant and duplicated code.

The intention behind the framework is to handle handle and check the arguments, so that the implementation of the command only features the actual logic. This will be achieved by using annotations and interfaces for command arguments.

## Current supported features
- custom command arguments
- declaring a command by annotation and its arguments
- register commands without editing `plugin.yml`

## Installation
### Maven
1. Install [BuildTools](https://www.spigotmc.org/wiki/buildtools/) and build the actual version (current: 1.13.2).
2. Clone the repository `git clone https://github.com/Paul2708/simple-commands.git`
3. Install it into your local maven repo by `mvn clean install`
4. Add the following dependency to your project
```xml
<dependency>
    <groupId>de.paul2708</groupId>
    <artifactId>simple-commands-core</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

## Getting started
A detailed description is coming soon.
An example plugin can be found [here](https://github.com/Paul2708/simple-commands/tree/master/example).

## TODO
- adding multiple and custom languages
- adding more command arguments
- plugin injection
- more tests (!)
- optional annotation for parameter

## Contribution
If you want to contribute, just open a [pull request](https://github.com/Paul2708/simple-commands/pulls).

## Test-driven development
This project is based on a test-driven development.
This means that I'll write the unit tests and interfaces before I implement it.
So checkout the development branch, if you want to know what's happening at the moment.
