package com.minez.corestarter.template;

public interface CommandTemplate<A, Result> {
    Result handle(A command);
}
