package ru.itmentor.spring.boot_security.demo.api.response;



import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class UserResponse {

    private String name;
    private String lastname;
    private Integer age;
}
