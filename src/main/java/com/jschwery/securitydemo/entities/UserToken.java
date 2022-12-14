package com.jschwery.securitydemo.entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class UserToken {

    @Id
    @SequenceGenerator(name = "token_sequence",
    sequenceName = "token_sequence",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "token_sequence")
    private long userTokenID;

    @Column(name = "User_Token", nullable = false)
    private String token;

    @Column(name = "Expire_Time")
    private Timestamp tokenExpiration;

    public UserToken(String tokenToAdd, User user){
        this.token = tokenToAdd;
        this.user = user;
    }

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false
    )
    private User user;


}
