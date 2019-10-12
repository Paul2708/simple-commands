# simple-commands [![Build Status](https://travis-ci.com/Paul2708/simple-commands.svg?branch=master)](https://travis-ci.com/Paul2708/simple-commands) [![](https://jitpack.io/v/Paul2708/simple-commands.svg)](https://jitpack.io/#Paul2708/simple-commands)
Simple commands is a command framework for [Spigot](https://www.spigotmc.org).
It's currently under heavy development, so stay tuned.

Note: This project is inspired by [Aikar](https://github.com/aikar/commands).
So checkout the other command framework, if you find this project interesting.

## Why is it called "simple"?
Spigot features its own command framework by using the command design pattern.

It's quite _simple_ to create own commands.
But if you have multiple commands with even more sub commands, there will be a lot of redundant and duplicated code.

The intention behind the framework is to handle and check the arguments, so that the implementation of the command only features the actual logic. This will be achieved by using annotations and interfaces for command arguments.

## Current supported features
- basic set of command arguments (primitive data types, enums)
- custom command arguments
- declaring a command by annotation and its arguments
- register commands without editing `plugin.yml`
- dependency injection via `@Inject`
- custom messages by using language files

## Installation
### Maven
Just add the following repository and dependency to your Maven project.
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
```xml
<dependency>
    <groupId>com.github.Paul2708</groupId>
    <artifactId>simple-commands</artifactId>
    <version>0.2.3</version>
</dependency>
```

### Jar
If you don't use a build tool like Maven or Gradle, you can download the latest release [here](https://github.com/Paul2708/simple-commands/releases).

### Local build
1. Clone the repository `git clone https://github.com/Paul2708/simple-commands.git`
2. Install it into your local maven repo by `mvn clean install`
3. Add the following dependency to your project
```xml
<dependency>
    <groupId>de.paul2708</groupId>
    <artifactId>simple-commands-core</artifactId>
    <version>0.2.3</version>
</dependency>
```

## Getting started
### Sample command
The following code snippet represents a simple teleport command.

```java
@Command(name = "teleport", desc = "Teleport you to a player", permission = "example.tp")
public void test(Player sender, Player target) {
    condition(!sender.equals(target), "You cannot teleport you to yourself");

    sender.teleport(target);
    sender.sendMessage("You got teleported to " + target.getName() + ".");
}
```

An example plugin with all current features like language selection and dependency injection can be found [here](https://github.com/Paul2708/simple-commands/tree/master/example).
It's really recommended as it is a real working plugin with features implemented.

Some wiki pages will follow soon.

### Comparison: Spigot command vs. simple command
The following commands teleport a target player to a given y-height.

#### Spigot command
```java
@Override
public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
        sender.sendMessage("This command is for players only.");
        return false;
    }
    if (!sender.hasPermission("example.tp")) {
        sender.sendMessage("You do not have enough permissions.");
        return false;
    }
    if (args.length != 2) {
        sender.sendMessage("False usage. Please use 3 parameters instead of " + args.length);
        return false;
    }

    Player player = Bukkit.getPlayer(args[0]);
    if (player == null || !player.isOnline()) {
        sender.sendMessage("The player is not online.");
        return false;
    }

    int yHeight;

    try {
        yHeight = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
        sender.sendMessage("This argument is not a number.");
        return false;
    }

    if (yHeight <= 0) {
        sender.sendMessage("The height has to be positive.");
        return false;
    }

    // Finally done...
    Location location = player.getLocation().clone();
    location.setY(yHeight);
    player.teleport(location);

    sender.sendMessage("You teleported " + player.getName() + " to " + yHeight);
    return true;
}
```

#### Simple command
```java
@Command(name = "teleport", desc = "Teleport a player to y", permission = "example.tp")
public void test(Player sender, Player target, int yHeight) {
    condition(yHeight > 0, "The height has to be positive.");

    Location location = target.getLocation().clone();
    location.setY(yHeight);
    target.teleport(location);

    sender.sendMessage("You teleported " + target.getName() + " to " + yHeight);
}
```

I think, you can spot the differences and the boilerplate by spigot.

## Contribution
[Here](CONTRIBUTING.md) is a set of instructions that will guide you through your contribution.

## Contact
If you find any issues, please let me know!
Just open a [bug report](https://github.com/Paul2708/simple-commands/issues/new?template=bug-report.md) or another [issue](https://github.com/Paul2708/simple-commands/issues/new/choose).

Paul2708 - [Twitter](https://twitter.com/theplayerpaul) Discord: Paul2708#1098 [Mail](mailto:playerpaul2708@gmx.de)