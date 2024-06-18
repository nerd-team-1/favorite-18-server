package com.nerd.favorite18.core.api._common.controller.v1.request;

import com.nerd.favorite18.core.api._common.domain.ExampleData;

public record ExampleRequestDto(String data) {
    public ExampleData toExampleData() {
        return new ExampleData(data, data);
    }
}
