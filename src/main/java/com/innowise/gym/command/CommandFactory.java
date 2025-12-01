package com.innowise.gym.command;

import com.innowise.gym.command.impl.DefaultCommand;
import com.innowise.gym.command.impl.LoginCommand;
import com.innowise.gym.command.impl.LogoutCommand;
import com.innowise.gym.command.impl.RegisterCommand;

public class CommandFactory {

    public static Command defineCommand(String commandStr) {
        if (commandStr == null || commandStr.isEmpty()) {
            return new DefaultCommand();
        }

        CommandName commandName;

        try {
            commandName = CommandName.valueOf(commandStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return new DefaultCommand();
        }

        switch (commandName) {
            case LOG_IN:
                return new LoginCommand();
            case REGISTER:
                return new RegisterCommand();
            case LOG_OUT:
                return new LogoutCommand();
            default:
                return new DefaultCommand();
        }
    }
}

