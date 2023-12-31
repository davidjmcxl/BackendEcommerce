package com.ecommerce.ecommerce.model.entities;
import com.ecommerce.ecommerce.model.Dto.RegisterUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String password;
    @Column(unique = true)
    private String email ;
    private String rol ="ROLE_USER";
    @Column(unique = true)
    private BigInteger identification;
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(RegisterUserDTO registerUserDTO) {
        this.name=registerUserDTO.name();
        this.email=registerUserDTO.email();
        this.password= new BCryptPasswordEncoder().encode(registerUserDTO.password());
        this.address=registerUserDTO.address();
        this.identification=registerUserDTO.identification();
        if(registerUserDTO.rol()!=null){
            this.rol=registerUserDTO.rol();
        }

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.rol));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
