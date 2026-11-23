package com.minez.corestarter.template;

public interface QueryTemplate<A, Result> {
    Result handle(A query);
}
