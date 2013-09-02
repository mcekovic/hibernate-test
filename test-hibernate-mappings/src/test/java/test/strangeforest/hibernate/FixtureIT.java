package test.strangeforest.hibernate;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import com.fasterxml.jackson.databind.*;
import com.finsoft.db.*;

import static org.strangeforest.hibernate.entities.TournamentType.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class FixtureIT extends AbstractTestNGSpringContextTests {

	@Autowired private CountryRepository countries;
	@Autowired private TournamentRepository tournaments;
	@Autowired private ConnectionPoolDataSource dataSource;

	@BeforeSuite
	public void setUpSuite() {
		System.out.println("Deleting DB files...");
		ITUtil.deleteFiles(System.getProperty("user.home") + "/.hibernate-test/h2", ".*");
	}

	@AfterSuite
	public void cleanUpSuite() throws IOException {
		dataSource.dropConnections();
		System.out.println("GetCount: " + dataSource.getStatistics().get("GetCount"));
		long executions = 0;
		for (Map<String, Object> stStat : (Iterable<Map<String, Object>>)dataSource.getStatistics().get("Statements"))
			executions += (Long)stStat.get("Executions");
		System.out.println("Executions: " + executions);
		new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(System.out, dataSource.getStatistics());
	}

	@BeforeClass
	public void setUp() {
		dataSource.resetStatistics();
	}

	@Test(groups = "CountryFixture")
	public void createCountries() {
		countries.create(new Country("au", "Australia"));
		countries.create(new Country("fr", "France"));
		countries.create(new Country("mn", "Monaco"));
		countries.create(new Country("sr", "Serbia"));
		countries.create(new Country("uk", "United Kingdom"));
		countries.create(new Country("us", "USA"));
	}

	@Test(groups = "TournamentFixture", dependsOnMethods = "createCountries")
	public void createTournaments() {
		tournaments.create(new Tournament("Australian Open", GRAND_SLAM, countries.findById("au")));
		tournaments.create(new Tournament("Roland Garros", GRAND_SLAM, countries.findById("fr")));
		tournaments.create(new Tournament("Wimbledon", GRAND_SLAM, countries.findById("uk")));
		tournaments.create(new Tournament("US Open", GRAND_SLAM, countries.findById("us")));
	}
}
