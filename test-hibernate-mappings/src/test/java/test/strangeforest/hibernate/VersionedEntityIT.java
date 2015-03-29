package test.strangeforest.hibernate;

import java.util.*;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class VersionedEntityIT extends AbstractTestNGSpringContextTests {

	@Autowired private VersionedEntityRepository entities;
	private long entityId;
	private Random rnd = new Random();

	private static final String ENTITY_NAME = "Entity";
	private static final String DETAIL_NAME = "Detail";
	private static final int UPDATE_WARM_COUNT = 1;
	private static final int UPDATE_COUNT      = 2;

	@Test
	public void entityIsCreated() {
		VersionedEntity entity = new VersionedEntity(ENTITY_NAME);
		entities.create(entity);
		entityId = entity.getId();

		assertThat(getEntity().getVersion()).isEqualTo(0);
	}

	@Test(dependsOnMethods = "entityIsCreated")
	public void entityIsUpdated() {
		VersionedEntity entity = getEntity();
		entity.setName(ENTITY_NAME + '!');
		entities.save(entity);

		assertThat(getEntity().getVersion()).isEqualTo(1);
	}

	@Test(dependsOnMethods = "entityIsUpdated")
	public void detailIsAdded() {
		VersionedEntity entity = getEntity();
		entity.addDetail(DETAIL_NAME);
		entities.save(entity);

		VersionedEntity savedEntity = getEntity();
		assertThat(savedEntity.getDetails()).hasSize(1);
		assertThat(savedEntity.getVersion()).isEqualTo(2);
	}

	@Test(dependsOnMethods = "detailIsAdded")
	public void warmEntityNameIsChanged() {
		for (int i = 0; i < UPDATE_WARM_COUNT; i++)
			entities.changeName(entityId, ENTITY_NAME + rnd.nextLong());
	}

	@Test(dependsOnMethods = "warmEntityNameIsChanged")
	public void warmEntityNameIsChangedOptimistic() {
		for (int i = 0; i < UPDATE_WARM_COUNT; i++)
			entities.changeNameOptimistic(entityId, ENTITY_NAME + rnd.nextLong());
	}

	@Test(dependsOnMethods = "warmEntityNameIsChangedOptimistic")
	public void warmEntityNameIsChangedPessimistic() {
		for (int i = 0; i < UPDATE_WARM_COUNT; i++)
			entities.changeNamePessimistic(entityId, ENTITY_NAME + rnd.nextLong());
	}

	@Test(dependsOnMethods = "warmEntityNameIsChangedPessimistic")
	public void entityNameIsChanged() {
		for (int i = 0; i < UPDATE_COUNT; i++)
			entities.changeName(entityId, ENTITY_NAME + rnd.nextLong());
	}

	@Test(dependsOnMethods = "entityNameIsChanged")
	public void entityNameIsChangedOptimistic() {
		for (int i = 0; i < UPDATE_COUNT; i++)
			entities.changeNameOptimistic(entityId, ENTITY_NAME + rnd.nextLong());
	}

	@Test(dependsOnMethods = "entityNameIsChangedOptimistic")
	public void entityNameIsChangedPessimistic() {
		for (int i = 0; i < UPDATE_COUNT; i++)
			entities.changeNamePessimistic(entityId, ENTITY_NAME + rnd.nextLong());
	}

	@Test(dependsOnMethods = "entityNameIsChangedPessimistic", expectedExceptions = EntityNotFoundException.class)
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
