# simple-commands
Simple commands is a command framework for [Spigot](https://www.spigotmc.org).
It's currently under heavy development, so stay tuned.

## Why is it called "simple"?
Spigot features its own command framework by using the command design pattern. It's quite _simple_ to create own commands. 
But if you have mutliple commands with even more sub commands, there will be a lot of redundant and duplicated code.  
The intention behind the framework is to handle handle and check the arguments, so that the implementation of the command only features the actual logic.

## Test-driven development
This project is based on a test-driven development.
This means that I'll write the unit tests and interfaces before I implement it.
So checkout the development branch, if you want to know what's happening at the moment.
