# simple-commands
Simple commands is a command framework in Java.
It's currently under heavy development, so stay tuned.

## Why is it called "simple"?
The intention behind the framework is to handle and check the arguments, so that the implementation of the command only features the actual logic.
This will be achieved by using annotations and `CommandArgument`s.
You are able to create custom `CommandArgument`s, that hold information about the argument type like usage, parsing and auto-completion.  

This will reduce redundant and duplicated code.

## Test-driven development
This project is based on a test-driven development.
This means that I'll write the unit tests and interfaces before I implement it.
So checkout the development branch, if you want to know what's happening at the moment.
