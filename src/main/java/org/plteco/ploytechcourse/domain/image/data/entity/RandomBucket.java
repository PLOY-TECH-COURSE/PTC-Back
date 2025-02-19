package org.plteco.ploytechcourse.domain.image.data.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Table(name="random_bucket")
@Entity
@Getter
public class RandomBucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
