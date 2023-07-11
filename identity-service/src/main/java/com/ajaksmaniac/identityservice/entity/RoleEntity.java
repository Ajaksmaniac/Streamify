package com.ajaksmaniac.identityservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "roles")
@Entity
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "role_id")
    private Long id;

    @Column(unique = true)
    private String name;

    public RoleEntity(Long id) {
        this.id = id;
    }

}
