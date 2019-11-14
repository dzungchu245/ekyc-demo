package com.finos.ekyc.demo.model;

import java.util.List;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String token;
    private List<String> roleList;

    @JsonIgnore
    public String getRoles() {
        if (!CollectionUtils.isEmpty(roleList)) {
            return String.join(User.DELIMITER, roleList);
        } else {
            return "";
        }
    }
    
    public UserDto(User user) {
        super();
        this.setId(user.getId());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setRoleList(user.getRoleList());
        this.setUsername(user.getUsername());
    }

}
