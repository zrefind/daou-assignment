package org.kang.assignment.domain.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RefreshToken {

    @Id
    private String key;

    @Column
    private String value;

    private RefreshToken(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static RefreshToken of(String key, String value) {
        return new RefreshToken(key, value);
    }

}
