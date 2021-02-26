package com.itgma.shreyas.technicaltest.country.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itgma.shreyas.technicaltest.country.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer>{

}
