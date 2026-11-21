package org.example.ecommerce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultJs<T> {

    private T msg;

    public ResultJs(T s) {
        this.msg = s;
    }
}
