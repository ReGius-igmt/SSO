package ru.regiuss.servers.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", length = 25)
    private String username;

    @Column(name = "password", length = 256)
    @JsonIgnore
    private String password;

    @Column(name = "vk", length = 12)
    private String vk;

    @Column(name = "tg", length = 12)
    private String tg;

    @Column(name = "google", length = 12)
    private String google;

    @Column(name = "github", length = 12)
    private String github;

    @Column(name = "create_date")
    private OffsetDateTime createDate;

    @Column(name = "last_login_date")
    private OffsetDateTime lastLoginDate;
}