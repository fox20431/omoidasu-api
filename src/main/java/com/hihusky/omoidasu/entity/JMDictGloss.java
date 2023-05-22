package com.hihusky.omoidasu.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "sense")
@Entity
@Table(name = "jmdict_glosses")
public class JMDictGloss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "language")
    private String language;

    @Lob
    @Column(name = "gloss")
    private String gloss;

    // foreign key
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sense_id")
    private JMDictSense sense;

}
