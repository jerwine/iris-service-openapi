package ie.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import ie.demo.model.IrisDTO;

public interface IrisService {

	/**
	 * Retrieve an Iris identified bt the ID
	 * @param id
	 * @return
	 */
	Optional<IrisDTO> getIrisById( String id );

	/**
	 * Retrieve all the Irises restricted by the pageable parameters
	 * @param pageable
	 * @return
	 */
	List<IrisDTO> getAllIris( Pageable pageable );

	/**
	 * Persist/Update an Iris or Irises
	 * @param irises
	 * @return
	 */
	List<IrisDTO> saveIris( List<IrisDTO> irises );

	/**
	 * Delete an Iris identified by the ID
	 * @param id
	 * @return
	 */
	Optional<Void> deleteIris( String id );
}