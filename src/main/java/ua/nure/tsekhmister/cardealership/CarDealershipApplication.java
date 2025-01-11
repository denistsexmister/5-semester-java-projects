package ua.nure.tsekhmister.cardealership;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.nure.tsekhmister.cardealership.entity.CarOnSale;

import java.math.BigDecimal;
import java.time.Year;

@SpringBootApplication
public class CarDealershipApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarDealershipApplication.class, args);

		CarOnSale carOnSale = new CarOnSale("FDSWER12SAQZX23DE", 1L, "Honda",
				Year.of(2005), BigDecimal.valueOf(20), BigDecimal.valueOf(10000));


		carOnSale.setPrice(BigDecimal.valueOf(10));
		carOnSale.setOwnerId(10L);
		System.out.println("Car before undo: " + carOnSale);
		carOnSale.undo();
		System.out.println("Car after undo: " + carOnSale);
		carOnSale.undo();
		System.out.println("Car after second undo: " + carOnSale);

	}

}
