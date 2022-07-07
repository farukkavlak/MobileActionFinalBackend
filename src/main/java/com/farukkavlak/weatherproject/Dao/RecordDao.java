package com.farukkavlak.weatherproject.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.farukkavlak.weatherproject.Entity.Record;

@Repository
public interface RecordDao extends JpaRepository<Record,Long> {
}