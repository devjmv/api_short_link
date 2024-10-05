package dev.shortlink.link_status;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@Getter
@RequiredArgsConstructor
public enum Status {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    END_DATE("END_DATE");

    private final String value;
}

