package com.study.cachedemo.dto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "user", schema = "cache")
@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", length = 50)
    private String userName;
    @Column(name = "phone_number", length = 13)
    private String phoneNumber;
    @Column(name = "password", length = 50)
    private String password;
    @Column(name = "address", length = 100)
    private String address;

    @Builder
    public User(Long id, String userName, String phoneNumber, String password, String address) {
        this.id = id;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
    }
}
