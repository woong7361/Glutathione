package com.example.productservice.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {
    private Integer page;
    private Integer size;

    public Integer getPage() {
        return page != null ? page - 1 : 0;
    }

    public Integer getSize() {
        return size != null ? size : 5;
    }

    public Integer getOffset() {
        return page == null || size == null ? 0 : size * (page - 1);
    }
}
