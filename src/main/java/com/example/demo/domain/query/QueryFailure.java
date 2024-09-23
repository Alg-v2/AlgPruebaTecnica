package com.example.demo.domain.query;

import com.example.demo.domain.command.CommandFailure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class QueryFailure extends CommandFailure {
}
