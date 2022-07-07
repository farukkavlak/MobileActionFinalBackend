package com.farukkavlak.weatherproject.Dao;

import com.farukkavlak.weatherproject.Entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultDao extends JpaRepository<Result,Long> {

    boolean existsByDateAndCity(long l, String city_name);

    Result getByDateAndCity(long l,String city_name);

    Result findByDateAndCity(long date,String city_name);

}