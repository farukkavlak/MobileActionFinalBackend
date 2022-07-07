package com.farukkavlak.weatherproject.Entity;

import com.farukkavlak.weatherproject.Generic.Entity.BaseEntity;
import com.farukkavlak.weatherproject.Generic.Entity.BaseFields;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Log extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_seq")
    @SequenceGenerator(name = "log_seq", sequenceName = "log_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    public String description;
    public String type;
    public Log(String description,String type) {
        BaseFields baseFields = new BaseFields();
        baseFields.setCreateDate(LocalDateTime.now());
        this.setBaseFields(baseFields);
        this.description = description;
        this.type = type;
    }
}
