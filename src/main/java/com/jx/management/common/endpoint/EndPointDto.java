package com.jx.management.common.endpoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EndPointDto<Body> {
    private final String responseCode; // must reference ResponseCode Enum class
    private final Body body;

}
