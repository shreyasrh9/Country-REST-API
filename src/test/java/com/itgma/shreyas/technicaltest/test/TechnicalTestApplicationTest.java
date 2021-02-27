package com.itgma.shreyas.technicaltest.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itgma.shreyas.technicaltest.TechnicalTestApplication;

import org.junit.Test;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TechnicalTestApplicationTest {
	@Test
	public void main() {
		TechnicalTestApplication.main(new String[] {});
	}
}
