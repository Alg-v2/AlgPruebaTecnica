package com.example.demo.domain.command;

import lombok.Data;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import com.example.demo.exception.model.Error;

import java.util.List;

@Data
@SuperBuilder
public class CommandFailure {

    final String code;
    @Singular final List<Error> errors;
    @Singular final List<String> arguments;
}
