package dev.shortlink.link_status;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    TEST("TEST");

    private final String value;
}

