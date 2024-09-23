package com.example.demo.domain.command;

import io.vavr.control.Either;

public interface CommandValidator <C extends Command>{
Either<CommandFailure,C> acceptOrReject(C command);
}
