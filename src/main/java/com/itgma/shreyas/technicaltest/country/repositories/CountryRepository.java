package com.itgma.shreyas.technicaltest.country.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.itgma.shreyas.technicaltest.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

	List<Country> findByCountryRegion(String region);
	
	@Transactional
	void deleteByCountryName(String countryName);

}
