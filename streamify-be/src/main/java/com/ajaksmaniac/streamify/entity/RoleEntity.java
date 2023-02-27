package com.ajaksmaniac.streamify.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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

    private String name;
}
