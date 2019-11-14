package com.finos.ekyc.demo.model;

import java.beans.Transient;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ekyc_user")
public class User {

    public static final String DELIMITER = ";";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String username;
    @Column
    @JsonIgnore
    private String password;

    @Column
    private String roles;

    @Transient
    public List<String> getRoleList() {
        if (StringUtils.isNotBlank(this.roles)) {
            return Arrays.asList(this.roles.trim().split(DELIMITER));
        } else {
            return Collections.emptyList();
        }
    }
}
