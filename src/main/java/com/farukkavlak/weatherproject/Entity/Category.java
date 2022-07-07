package com.farukkavlak.weatherproject.Entity;

import com.farukkavlak.weatherproject.Generic.Entity.BaseEntity;
import com.farukkavlak.weatherproject.Generic.Entity.BaseFields;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Category extends BaseEntity {
    @Id
    @SequenceGenerator(name = "category_seq", sequenceName = "category_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    String CO;
    String SO2;
    String O3;

    public Category() {
        BaseFields baseFields = new BaseFields();
        baseFields.setCreateDate(LocalDateTime.now());
        this.setBaseFields(baseFields);
    }
}
