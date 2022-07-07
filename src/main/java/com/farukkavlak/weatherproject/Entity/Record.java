package com.farukkavlak.weatherproject.Entity;

import com.farukkavlak.weatherproject.Generic.Entity.BaseEntity;
import com.farukkavlak.weatherproject.Generic.Entity.BaseFields;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_seq")
    @SequenceGenerator(name = "record_seq", sequenceName = "record_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "City")
    String city;

    @ManyToMany
    @JoinTable(
            name = "record_result_fk",
            joinColumns = @JoinColumn(name = "record_id"),
            inverseJoinColumns = @JoinColumn(name = "result_id"))
    List<Result> resultList = new ArrayList<>();

    public Record() {
        BaseFields baseFields = new BaseFields();
        baseFields.setCreateDate(LocalDateTime.now());
        this.setBaseFields(baseFields);
    }
}
