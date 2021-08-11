package br.com.wipro.challenge.gestaogastos.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseError {

    private Integer code;
    private String description;
}
