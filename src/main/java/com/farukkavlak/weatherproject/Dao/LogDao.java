package com.farukkavlak.weatherproject.Dao;

import com.farukkavlak.weatherproject.Entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDao extends JpaRepository<Log,Long> {
}
