package com.minez.corestarter.template;

public interface QueryTemplate<A, R> {
    R handle(A query);
}
