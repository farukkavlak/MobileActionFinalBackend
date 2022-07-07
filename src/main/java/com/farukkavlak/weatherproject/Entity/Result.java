package com.farukkavlak.weatherproject.Entity;

import com.farukkavlak.weatherproject.Generic.Entity.BaseEntity;
import com.farukkavlak.weatherproject.Generic.Entity.BaseFields;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Result extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
    @SequenceGenerator(name = "result_seq", sequenceName = "result_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    Long date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "result_category_fk", referencedColumnName = "id")
    Category categories;

    @ManyToMany(mappedBy = "resultList")
    List<Record> records = new ArrayList<>();

    String city ;

    public Result() {
        BaseFields baseFields = new BaseFields();
        baseFields.setCreateDate(LocalDateTime.now());
        this.setBaseFields(baseFields);
    }
}
