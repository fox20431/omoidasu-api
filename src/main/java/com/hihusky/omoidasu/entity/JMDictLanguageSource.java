package com.hihusky.omoidasu.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "sense")
@Entity
@Table(name = "jmdict_language_source_list")
public class JMDictLanguageSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "value")
    private String value;
    @Column(name = "language")
    private String language;
    @Column(name = "type")
    private String type;
    @Column(name = "wasei")
    private boolean wasei;

    // foreign key
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sense_id")
    private JMDictSense sense;
}
