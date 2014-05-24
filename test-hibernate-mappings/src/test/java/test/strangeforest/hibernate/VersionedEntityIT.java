package test.strangeforest.hibernate;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class VersionedEntityIT extends AbstractTestNGSpringContextTests {

	@Autowired private VersionedEntityRepository entities;
	private long entityId;

	private static final String ENTITY_NAME = "Entity";
	private static final String DETAIL_NAME = "Detail";

	@Test
	public void entityIsCreated() {
		VersionedEntity entity = new VersionedEntity(ENTITY_NAME);
		entities.create(entity);
		entityId = entity.getId();

		assertThat(getEntity().getVersion(), is(equalTo(0)));
	}

	@Test(dependsOnMethods = "entityIsCreated")
	public void entityIsUpdated() {
		VersionedEntity entity = getEntity();
		entity.setName(ENTITY_NAME + "2");
		entities.save(entity);

		assertThat(getEntity().getVersion(), is(equalTo(1)));
	}

	@Test(dependsOnMethods = "entityIsUpdated")
	public void detailIsAdded() {
		VersionedEntity entity = getEntity();
		entity.addDetail(DETAIL_NAME);
		entities.save(entity);

		VersionedEntity savedEntity = getEntity();
		assertThat(savedEntity.getDetails(), hasSize(1));
		assertThat(savedEntity.getVersion(), is(equalTo(2)));
	}

	@Test(dependsOnMethods = "detailIsAdded", expectedExceptions = EntityNotFoundException.class)
	public void entityIdDeleted() {
		entities.deleteById(entityId);
		getEntity();
		fail("VersionedEntity is not deleted.");
	}


	// Util

	private VersionedEntity getEntity() {
		return entities.find(entityId);
	}
}
