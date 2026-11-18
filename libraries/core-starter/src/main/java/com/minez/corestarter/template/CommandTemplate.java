package com.minez.corestarter.template;

public interface CommandTemplate<A, R> {
    R handle(A command);
}
