package ru.quassbottle.fly.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "profiles")
public class Profile {
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profiles_id_seq")
    //@SequenceGenerator(name = "profiles_id_seq", allocationSize = 1)
    //@Column(columnDefinition = "bigserial")
    private Long id;

    @OneToOne
    @JsonBackReference
    @PrimaryKeyJoinColumn
    private Account account;

    @Builder.Default
    @Column(name = "is_worker")
    private boolean isWorker = false;

    @OneToMany
    private Set<Offer> offers;
}
