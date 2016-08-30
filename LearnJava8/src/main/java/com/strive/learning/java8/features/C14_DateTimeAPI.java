package com.strive.learning.java8.features;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Provided in the java.time package, replaces older Date, Time, and Timezone classes in java.util.
 */
@SuppressWarnings("unused")
public class C14_DateTimeAPI {

	public static void main(String[] args) {
		flightExample();
	}

	private static void flightExample() {
		// Leaving fron COS to JFK at 6am, flight is 5 hours long
		ZonedDateTime departCOS = ZonedDateTime.of(
				LocalDateTime.of(2016, Month.AUGUST, 17, 6, 0, 0), ZoneId.of("America/Denver"));
		System.out.println("Depart from COS = "+departCOS);
		
		// Arrival after 5 hours in New York
		ZonedDateTime arriveJFK = departCOS.withZoneSameInstant(ZoneId.of("America/New_York")).plusHours(5);
		System.out.println("Arrive in JFK = "+arriveJFK);
		
		// Travel time from COS to LAX given arrival time
		ZonedDateTime arriveLAX = ZonedDateTime.of(
				LocalDateTime.of(2016, Month.AUGUST, 17, 13, 40, 0), ZoneId.of("America/Los_Angeles"));
		System.out.println("Arrive in LAX = "+arriveLAX);
		Duration dFlight = Duration.between(departCOS.toLocalTime(), arriveLAX.toLocalTime());
		long seconds = dFlight.getSeconds();
	    long absSeconds = Math.abs(seconds);
	    String duration = String.format("%dh %02dm %02ds", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
	    if(seconds < 0) { duration = "-"+duration; }
		System.out.println("Flight time = "+duration);
	}

	private static void formatDatesAndTimes() {
		// Standard formatter
		LocalTime wakeupTime = LocalTime.of(6, 0, 0);
		System.out.println("Wake up time: " + DateTimeFormatter.ISO_TIME.format(wakeupTime));
		
		// Custom format
		DateTimeFormatter customFormat = DateTimeFormatter.ofPattern("MMM-dd, yyyy");
		System.out.println("Custom format: "+customFormat.format(LocalDate.of(2016, Month.JANUARY, 1)));
	}

	private static void usingTimeZoneId() {
		// Default
		System.out.println("Default timezone = " + ZoneId.systemDefault());
		
		// Build a timezone
		ZoneId asiaKolkataZoneId = ZoneId.of("Asia/Kolkata");
		System.out.println("An Indian timezone = " + asiaKolkataZoneId);
		
		// To use date, time, timezone together
		ZonedDateTime dateTimeHere = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.systemDefault());
		System.out.println("Current date/time/zone = "+dateTimeHere);
		
		// Time difference
		ZoneId kampalaZoneId = ZoneId.of("Africa/Kampala");
		System.out.println("Kampala timezone = "+kampalaZoneId);
		ZonedDateTime dateTimeKampala = dateTimeHere.withZoneSameInstant(kampalaZoneId);
		System.out.println("Kampala date/time/zone = "+dateTimeKampala);
		Duration timeDifference = Duration.between(dateTimeHere.toLocalTime(), dateTimeKampala.toLocalTime());
		System.out.println("Time difference = "+timeDifference.toHours()+" hours");
		
		// Daylight savings
		boolean dstInEffect = !timeDifference.isZero();
		System.out.println("Is DST in effect? = "+dstInEffect);
		Duration kampalaDST = kampalaZoneId.getRules().getDaylightSavings(Instant.now());
		System.out.printf("Kampala zone DST is: %d hours%n", kampalaDST.toHours());
		ZonedDateTime fallDateTime = ZonedDateTime.of(2015, 11, 5, 0, 0, 0, 0, ZoneId.systemDefault());
		ZoneOffset fallZoneOffset = fallDateTime.getOffset();
		System.out.println("A date in the fall = "+fallDateTime+" {offset="+fallZoneOffset+"}");
		ZonedDateTime springDateTime = ZonedDateTime.of(2016, 4, 1, 0, 0, 0, 0, ZoneId.systemDefault());
		ZoneOffset springZoneOffset = springDateTime.getOffset();
		System.out.println("A date in the spring = "+springDateTime+" {offset="+springZoneOffset+"}");
	}

	private static void usingTemporalUnitInterface() {
		/*
		 * Has a host of date and time related constants.
		 * Implemented by java.time.temporal.ChronoUnit enumeration.
		 */
		System.out.println("ChronoUnit DateBased TimeBased Duration");
		System.out.println("---------------------------------------");
		for(ChronoUnit unit : ChronoUnit.values()) {
			System.out.printf("%10s %9b %9b %s%n",
			unit, unit.isDateBased(), unit.isTimeBased(), unit.getDuration());
		}
		
		// Use it to calculate certain values
		System.out.println("Seconds in 8 minutes = "+Duration.of(8, ChronoUnit.MINUTES).getSeconds());
		System.out.println("Seconds in 39 hours = "+Duration.of(39, ChronoUnit.HOURS).getSeconds());
	}

	private static void usingDurationClass() {
		/*
		 * Represents measures of time passage in minutes, hours, seconds.
		 */
		// Time between 2 dates
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime midnight = LocalDateTime.of(now.toLocalDate().plusDays(1), LocalTime.MIDNIGHT);
		Duration dNowToMidnight = Duration.between(now, midnight);
		System.out.println("Duration between now and midnight = "+dNowToMidnight);
		
		// Create durations
		System.out.println("450 days = "+Duration.of(450, ChronoUnit.DAYS));
		System.out.println("100 days = "+Duration.ofDays(100));
		System.out.println("100 hours = "+Duration.ofHours(100));
		System.out.println("100 minutes = "+Duration.ofMinutes(100));
		System.out.println("100 seconds = "+Duration.ofSeconds(100));
		System.out.println("100 millis = "+Duration.ofMillis(100));
		System.out.println("100 nanos = "+Duration.ofNanos(100));
		
		// Parsing
		System.out.println("Parsing 'P2DT10H30M' = "+Duration.parse("P2DT10H30M"));
	}

	private static void usingPeriodClass() {
		/*
		 * Used to measure amount of time in years, months, and days.
		 */
		LocalDate manufacturingDate = LocalDate.of(2016, Month.JANUARY, 1);
		LocalDate expiryDate = LocalDate.of(2018, Month.JULY, 18);
		Period expiry = Period.between(manufacturingDate, expiryDate);
		System.out.printf("Medicine will expire in: %d years, %d months, and %d days (%s)\n",
				expiry.getYears(), expiry.getMonths(), expiry.getDays(), expiry);
		
		// Create periods
		Period pDays = Period.ofDays(13);
		System.out.println("Period of 13 days = "+pDays);
		Period pWeeks = Period.ofWeeks(13);
		System.out.println("Period of 13 weeks = "+pWeeks);
		Period pMonths = Period.ofMonths(13);
		System.out.println("Period of 13 months = "+pMonths);
		Period pYears = Period.ofYears(13);
		System.out.println("Period of 13 years = "+pYears);
		
		// Parse
		System.out.println("Parsed period = "+Period.parse("P4Y6M15D"));
	}

	private static void usingInstantClass() {
		/*
		 * Instant represents the various timestamp values of a given instant since epoch.
		 * The times are UTC.
		 */
		Instant currTimestamp = Instant.now();
		System.out.println("Current timestamp = "+ currTimestamp);
		System.out.println("> Seconds since epoch = "+ currTimestamp.getEpochSecond());
		System.out.println("> Milliseconds since epoch = "+ currTimestamp.toEpochMilli());
	}

	private static void usingLocalDateTimeClass() {
		/*
		 * LocalDateTime represents a date and time without timezone.
		 * Is ISO-8601 with the format "YYYY-MM-DD HH:MM:SS.NNN".
		 */
		LocalDateTime currDateTime = LocalDateTime.now();
		System.out.println("Current date and time is "+currDateTime);
		System.out.println("> Date component is "+currDateTime.toLocalDate());
		System.out.println("> Time component is "+currDateTime.toLocalTime());
		
		// Specific day and time
		LocalDateTime newYears2015 = LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0, 0);
		System.out.println("New Year's Day 2015 was "+newYears2015);
		
		// Date and time math
		System.out.println("Is Jan-12, 2016 after Jun-10, 2015? "+
				LocalDateTime.of(2016, Month.JANUARY, 12, 0, 0, 0).isAfter(LocalDateTime.of(2015, Month.JUNE, 10, 0, 0, 0)));
		System.out.println("10 hours from now will be "+currDateTime.plusHours(10));
		System.out.println("150 minutes ago was "+currDateTime.minusMinutes(150));
		
		// Clock and zone
		System.out.println("UTC date/time is "+LocalDateTime.now(Clock.systemUTC()));
		System.out.println("Date/time in Japan is "+LocalDateTime.now(ZoneId.of("Asia/Tokyo")));
		
		// Parsing
		System.out.println("Parsed = "+LocalDateTime.parse("2015-11-04T13:19:10.497"));
	}

	private static void usingLocalTimeClass() {
		/*
		 * LocalTime represents a time without date or timezone.
		 * Is ISO-8601 with the format HH:MM:SS.NNN.
		 */
		LocalTime currTime = LocalTime.now();
		System.out.println("Current time is "+currTime);
		
		// Specific time
		LocalTime threeOclock = LocalTime.of(3, 0, 0, 0);
		System.out.println("3 o'clock is "+threeOclock);
		
		// Time math
		System.out.println("10 hours from now will be "+currTime.plusHours(10));
		System.out.println("150 minutes ago was "+currTime.minusMinutes(150));
		
		// Clock
		System.out.println("UTC time is "+LocalTime.now(Clock.systemUTC()));
		System.out.println("Time in Japan is "+LocalTime.now(ZoneId.of("Asia/Tokyo")));
	}

	private static void usingLocalDateClass() {
		/*
		 * LocalDate represents a date without time or timezone.
		 * Is ISO-8601 with the format YYYY-MM-DD.
		 */
		LocalDate today = LocalDate.now();
		System.out.println("Today's date is "+today);
		
		// Specific day
		LocalDate birthdate = LocalDate.of(1982, Month.SEPTEMBER, 11);
		System.out.println("My birthdate is "+birthdate);
		
		// Date math
		System.out.println("14 days from now will be "+today.plusDays(14));
		System.out.println("2 weeks ago today was "+today.minusWeeks(2));
		System.out.println("The 100th day of 2017 will be "+LocalDate.ofYearDay(2017, 100));
		System.out.println("300 days since epoch was on "+LocalDate.ofEpochDay(300));
		
		// Parsing
		String oldBirthdate = "1977-09-11";
		System.out.println("My old birthdate is "+LocalDate.parse(oldBirthdate));
	}
}
