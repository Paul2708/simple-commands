package de.paul2708.commands.arguments;

import de.paul2708.commands.language.MessageResource;

import java.util.List;

public class OptionalArgument<T> implements CommandArgument<T> {
    private CommandArgument<T> internal;

    OptionalArgument(CommandArgument<T> internal) {
        this.internal = internal;
    }

    @Override
    public Validation<T> validate(String argument) {
        return internal.validate(argument);
    }

    @Override
    public MessageResource usage() {
        // TODO: Append hint about optionality to usage?
        return internal.usage();
    }

    @Override
    public List<String> autoComplete(String argument) {
        return internal.autoComplete(argument);
    }

    @Override
    public CommandArgument<T> asOptional() {
        return this;
    }

    @Override
    public boolean isOptional() {
        return true;
    }
}
