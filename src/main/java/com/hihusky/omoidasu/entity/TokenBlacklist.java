package com.hihusky.omoidasu.entity;

import com.hihusky.omoidasu.enums.TokenType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;


// todo create a table to maintain the blacklisted tokens
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "token_blacklist")
public class TokenBlacklist {
    @Id
    @GeneratedValue
    private Long id;
    private String token;
    private Date expiration;
    private TokenType tokenType;
    private Date blacklistedAt;
}
