package com.itgma.shreyas.technicaltest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.itgma.shreyas.technicaltest.controller.CountryController;
import com.itgma.shreyas.technicaltest.country.Country;
import com.itgma.shreyas.technicaltest.country.repositories.CountryRepository;
import com.itgma.shreyas.technicaltest.service.CountryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TechnicalTestApplicationTests {

	@Autowired
	private CountryService countryService;
	
	@InjectMocks
    CountryController countryController;
	
	@Autowired
	@MockBean
	private CountryRepository countryRepository;

	@Test
	public void saveCountryTest() {
		Country country = new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan");
		when(countryRepository.save(country)).thenReturn(country);
		assertEquals(country, countryService.addFavouriteCountry(country));
	}

	@Test
	public void getFavouriteCountries() {
		when(countryRepository.findAll()).thenReturn(
				Stream.of(new Country(376, "Andorra", "Andorra la Vella", "Europe", "EUR", "Euro", "€", "Catalan"))
						.collect(Collectors.toList()));
		assertEquals(1, countryService.getFavouriteCountries().size());
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
		countryService.deleteFavouriteCountryById(376);
		verify(countryRepository, times(1)).deleteById(376);
	}
	
	@Test
	public void getAllCountries() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
	     
	    final String baseUrl = "https://restcountries.eu/rest/v2/all";
	    URI uri = new URI(baseUrl);
	 
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	     
	    //Verify request succeed
	    Assert.assertEquals(200, result.getStatusCodeValue());
	    
	    
	    
	}
	
	@Test
	public void getCountriesByRegion() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "https://restcountries.eu/rest/v2/region/europe";
	    URI uri = new URI(baseUrl);
	 
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
	     
	    //Verify request succeed
	    Assert.assertEquals(200, result.getStatusCodeValue());
	}
}
