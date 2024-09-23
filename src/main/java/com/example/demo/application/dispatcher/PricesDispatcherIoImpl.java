package com.example.demo.application.dispatcher;

import com.example.demo.domain.command.CommandFailure;
import com.example.demo.exception.CommonExceptionHandler;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.vavr.control.Either.right;

@Slf4j
@Service
@AllArgsConstructor
public class PricesDispatcherIoImpl implements PricesDispatcherIo {

}
