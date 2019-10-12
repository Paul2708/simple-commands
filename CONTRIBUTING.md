# Contributing guidelines
> First of all if you want to contribute, I am really thankful and I appreciate it a lot!

The following topics will guide you through a _good_ contribution.

## Find an issue you want to work on
The [issues page](https://github.com/Paul2708/simple-commands/issues) gives an overview about all open ideas, bugs and other issues.
If you found an interesting one, just comment the issue and I will assign you to the issue (to avoid duplicated contributions).

If this is done, you can start to work on the issue (see **Getting started**).
Otherwise you can **Create an issue**.

### Create an issue
If you have an idea, feature request or other things you want to mention, you can open a new issue.
(Issue templates: [Report a bug](https://github.com/Paul2708/simple-commands/issues/new?assignees=&labels=bug&template=bug-report.md&title=) or [Request a feature](https://github.com/Paul2708/simple-commands/issues/new?assignees=&labels=enhancement&template=feature_request.md&title=))

## Getting started
1. Be sure you have an assigned issue you can refer to.
2. Fork the project.
3. Create a new branch based on the issue, e.g. `fix/null-argument`, `feature/uuid-argument`, `doc/extendend-example`.
4. Commit your changes to the branch. Make more simple commits instead of one commit that resolves the issue at once.
5. Push the branch to your forked project.
6. **Open a pull request**.

### Checkstyle
The only restriction is a provided checkstyle file under `src/test/resources/checkstyle.xml`.
Please make sure that `mvn checkstyle:check` won't fail.
Otherwise the pull request will be rejected.

### New command arguments
If you want to add a new argument, please do the following:
1. Create a class in `de.paul2708.commands.arguments.impl` in `arguments` that implements `CommandArgument<?>`.
2. Add relevant messages in `messages_XX.properties`.
3. Register it in `ArgumentHolder`.
4. Test it locally by creating a sample command, that can be removed afterwards.

Note: Enums are already covered. You have to register it without creating a new class.

## Open a pull request
If you are finally done, you should open a pull request.
You have to merge your `feature branch` into `development`.
Please use the pull request template to provide all relevant information.

> Thanks for reading (and contributing), Paul. :heart: