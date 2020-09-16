package ie.demo.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ie.demo.domain.Iris;

public interface IrisRepository extends MongoRepository<Iris, String> {

	/**
	 * Retrieve all the Irises restricted by the pageable parameter
	 * @param pageable
	 * @return
	 */
	@Query("{ id: { $exists: true } }")
	List<Iris> retrieveAllPageable( final Pageable pageable );

	/**
	 * Retrieve all the Irises identified with the species restricted by the pageable parameter
	 * @param species
	 * @param pageable
	 * @return
	 */
	@Query("{ id: { $exists: true } }")
	List<Iris> retrieveBySpeciesPageable( final String species, final Pageable pageable );
}