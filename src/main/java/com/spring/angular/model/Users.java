package com.spring.angular.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Users")
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long Id;

    @NotNull(message = "Email can't empty")
    @Length(max = 55)
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "Email can't empty")
    @Length(max = 155)
    @Column(name = "ADDRESS")
    private String address;

    @Pattern(regexp = "^[\\\\w!#$%&'*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}",message = "E-mail non valide")
    @NotNull(message = "Email can't empty")
    @Length(max = 85)
    @Column(name = "EMAIL")
    private String email;
}
