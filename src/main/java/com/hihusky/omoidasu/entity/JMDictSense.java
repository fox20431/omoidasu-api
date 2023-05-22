package com.hihusky.omoidasu.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "entry") // To avoid the circular reference
@Entity
@Table(name = "jmdict_senses")
public class JMDictSense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_stagk",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "stagk")
    private List<String> stagkList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_stagr",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "stagr")
    private List<String> stagrList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_pos",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "pos")
    private List<String> posList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_cross_references",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "cross_reference")
    private List<String> crossReferences;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_antonyms",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "antonym")
    private List<String> antonyms;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_fields",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "field")
    private List<String> fields;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_misc",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "misc")
    private List<String> miscList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_information",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Lob
    @Column(name = "information")
    private List<String> informationList;
    @ElementCollection
    @CollectionTable(
            name = "jmdict_sense_dialects",
            joinColumns = @JoinColumn(name = "sense_id"))
    @Column(name = "dialect")
    private List<String> dialects;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "entry_id")
    private JMDictEntry entry;

    @OneToMany(mappedBy = "sense", cascade = CascadeType.PERSIST)
    private List<JMDictGloss> glosses;
    @OneToMany(mappedBy = "sense", cascade = CascadeType.PERSIST)
    private List<JMDictLanguageSource> languageSources;
}
