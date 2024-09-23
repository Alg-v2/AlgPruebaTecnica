package com.example.demo.domain.command;

import io.vavr.control.Either;

public interface CommandHandler <C extends Command,E>{
    Either<CommandFailure,E> handle(C Command);
}
