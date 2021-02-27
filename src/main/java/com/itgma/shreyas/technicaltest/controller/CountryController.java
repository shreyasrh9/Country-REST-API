package com.itgma.shreyas.technicaltest.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itgma.shreyas.technicaltest.country.Country;
import com.itgma.shreyas.technicaltest.service.CountryService;


@RestController
public class CountryController {

	@Autowired
	private CountryService countryService;
	
	/*
	 * GET method - "Retrieve all countries from https://restcountries.eu/"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/countries")
	public List<Country> retrieveAllCountries() {
		final String uri = "https://restcountries.eu/rest/v2/all";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		return getCountryListFromJson(result);
	}

	/*
	 * GET method - "Retrieve countries based on region"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/countries/{region}")
	public List<Country> filterCountriesBasedOnRegion(@PathVariable String region) {
		final String uri = "https://restcountries.eu/rest/v2/region/" + region;
		String result = null;
		RestTemplate restTemplate = new RestTemplate();

		try {
			result = restTemplate.getForObject(uri, String.class);
		} catch (HttpClientErrorException.NotFound ex) {
			return Collections.EMPTY_LIST;
		}

		return getCountryListFromJson(result);
	}

	/*
	 * GET method - "Retrieve countries count based on region"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/countries/count")
	public Map<String, Integer> getCountriesCountBasedOnRegion() {
		final String uri = "https://restcountries.eu/rest/v2/all";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(uri, String.class);
		return getCountriesCount(result);
	}

	/*
	 * Post method - "Add favourite country to DB"
	 */

	@RequestMapping(method = RequestMethod.POST, path = "/favourite-countries")
	public ResponseEntity<Object> addFavouriteCountries(@RequestBody Country country) {
		System.out.println(country);
		Country savedCountry = countryService.save(country);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedCountry.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	/*
	 * GET method - "Retrieve favourite countries from DB"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/favourite-countries")
	public List<Country> favouriteCountries() {
		return countryService.findAll();
	}
	
	/*
	 * GET method - "Retrieve favourite country by Id from DB"
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/favourite-countries/{regionName}")
	public List<Country> getFavouriteCountryByRegionName(@PathVariable String regionName) {
		return countryService.getFavouriteCountryByRegionName(regionName);
	}

	/*
	 * Delete method - "Delete favourite country from DB"
	 */

	@RequestMapping(method = RequestMethod.DELETE, path = "/favourite-countries/{id}")
	public void deleteFavouriteCountry(@PathVariable int id) {
		countryService.deleteById(id);
	}
	
	
	private List<Country> getCountryListFromJson(String result) {
		List<Country> countriesList = new ArrayList<>();
		try {
			
			JSONArray countryJsonArray = new JSONArray(result);

			for (int i = 0; i < countryJsonArray.length(); i++) {
				Country country = new Country();
				JSONObject objects = countryJsonArray.getJSONObject(i);
				country.setCountryName(objects.getString("name"));
				country.setCountryCapital(objects.getString("capital"));
				country.setCountryRegion(objects.getString("region"));
				country.setDefaultCurrencyCode(objects.getJSONArray("currencies").getJSONObject(0).optString("code"));
				country.setDefaultCurrencyName(objects.getJSONArray("currencies").getJSONObject(0).getString("name"));
				country.setDefaultCurrencySymbol(
						objects.getJSONArray("currencies").getJSONObject(0).optString("symbol"));
				country.setDefaultLanguagesName(objects.getJSONArray("languages").getJSONObject(0).getString("name"));
				countriesList.add(country);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countriesList;
	}
	
	private Map<String, Integer> getCountriesCount(String result) {
		Map<String, Integer> countryCount = new HashMap<>();
		try {
			JSONArray countryJsonArray = new JSONArray(result);

			for (int i = 0; i < countryJsonArray.length(); i++) {
				JSONObject objects = countryJsonArray.getJSONObject(i);
				String countryRegion = objects.getString("region");

				if (countryCount.containsKey(countryRegion)) {
					Integer count = countryCount.get(countryRegion);
					countryCount.put(countryRegion, count + 1);
				} else {
					countryCount.put(countryRegion, 1);
				}
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return countryCount;
	}
	
}
