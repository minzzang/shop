package me.project.shop.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.project.shop.common.exception.BusinessException;
import me.project.shop.common.exception.BusinessMessage;
import me.project.shop.common.util.BcryptEncoderUtils;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private List<Address> address = new ArrayList<>();

    private String password;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member(String email, List<Address> address, String password) {
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public void encryptPassword() {
        password = BcryptEncoderUtils.encode(password);
    }

    public void checkPassword(String rawPassword) {
        if (!BcryptEncoderUtils.matches(rawPassword, password)) {
            throw new BusinessException(BusinessMessage.PASSWORD_MISMATCH);
        }
    }
}
