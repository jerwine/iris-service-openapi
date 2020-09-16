package ie.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ie.demo.api.mapper.IrisMapper;
import ie.demo.domain.Iris;
import ie.demo.repository.IrisRepository;
import ie.demo.model.IrisDTO;
import ie.demo.service.IrisService;

@Service
public class IrisServiceImpl implements IrisService {

	final IrisMapper irisMapper;
	final IrisRepository irisRepository;
	final MongoTemplate mongoTemplate;

	public IrisServiceImpl( IrisMapper irisMapper, IrisRepository irisRepository,
			MongoTemplate mongoTemplate ) {
		this.irisMapper = irisMapper;
		this.irisRepository = irisRepository;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Optional<IrisDTO> getIrisById( String id ) {
		Optional<Iris>iris = irisRepository.findById( id );
		if( iris.isPresent() ) return Optional.of( irisMapper.toIrisDTO( iris.get() ) );
		return Optional.empty();
	}

	@Override
	public List<IrisDTO> getAllIris( Pageable pageable ) {
		return irisRepository.findAll( pageable ).getContent().stream()
				.map( irisMapper :: toIrisDTO )
				.collect(Collectors.toList());
	}

	@Override
	public List<IrisDTO> saveIris(List<IrisDTO> irises) {
		return irisRepository
			.saveAll( 
				irises.stream()
					.map( irisMapper :: toIris )
					.collect( Collectors.toList() ) )
			.stream()
			.map( irisMapper :: toIrisDTO )
			.collect(Collectors.toList());
	}

	@Override
	public Optional<Void> deleteIris(String id) {
		irisRepository.deleteById( id );
		return Optional.empty();
	}
}