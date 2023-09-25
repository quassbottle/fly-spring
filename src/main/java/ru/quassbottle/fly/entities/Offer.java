package ru.quassbottle.fly.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offers_id_seq")
    @SequenceGenerator(name = "offers_id_seq", allocationSize = 1)
    @Column(columnDefinition = "bigserial")
    private Long id;

    @Basic(optional = false)
    private String title;

    @Basic(optional = false)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedAt;

    @Basic(optional = false)
    private double longitude;

    @Basic(optional = false)
    private double latitude;

    @Basic(optional = false)
    private float price;

    @Builder.Default
    private boolean isHourly = false;
}
