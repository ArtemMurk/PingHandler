package com.murk.telegram.ping.handler.core.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;


@Data
abstract class AbstractStatus {
    private @NonNull STATUS status;
}
