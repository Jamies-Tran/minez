package com.minez.corestarter.auditor;

import java.util.Optional;

public interface AuditorProvider {
    Optional<String> auditor();
}
