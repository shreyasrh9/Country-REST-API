package com.itgma.shreyas.technicaltest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.itgma.shreyas.technicaltest.country.Country;
import com.itgma.shreyas.technicaltest.country.repositories.CountryRepository;
import com.itgma.shreyas.technicaltest.service.CountryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TechnicalTestApplicationTests {

	@Autowired
	private CountryService countryService;

	@MockBean
	private CountryRepository countryRepository;

	@Test
	public void saveCountryTest() {
		Country country = new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan");
		when(countryRepository.save(country)).thenReturn(country);
		assertEquals(country, countryService.save(country));
	}

	@Test
	public void getFavouriteCountries() {
		when(countryRepository.findAll()).thenReturn(
				Stream.of(new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan"))
						.collect(Collectors.toList()));
		assertEquals(1, countryService.findAll().size());
	}

	@Test
	public void getCountryByRegionTest() {
		String region = "Europe";
		when(countryRepository.findByCountryRegion(region)).thenReturn(
				Stream.of(new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan"))
						.collect(Collectors.toList()));
	}
	
	@Test
    public void deleteById() throws Exception {      
		countryService.deleteById(376);
		verify(countryRepository, times(1)).deleteById(376);
    }
	
}
