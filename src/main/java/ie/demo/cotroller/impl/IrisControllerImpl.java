package ie.demo.cotroller.impl;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ie.demo.service.IrisService;
import io.swagger.annotations.ApiParam;
import ie.demo.api.IrisesApi;
import ie.demo.model.IrisDTO;

@RestController
@CrossOrigin
public class IrisControllerImpl implements IrisesApi {

	final IrisService irisService;

	public IrisControllerImpl( IrisService irisService ) {
		this.irisService = irisService;
	}

	@Override
	public ResponseEntity<List<IrisDTO>> getIrises(
			@ApiParam(value = "optional page number", defaultValue = "0") @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page,
			@ApiParam(value = "optional size number", defaultValue = "10") @Valid @RequestParam(value = "size", required = false, defaultValue="10") Integer size ) {
		return new ResponseEntity<List<IrisDTO>>( irisService.getAllIris( PageRequest.of( page, size ) ), HttpStatus.OK );
	}

	@Override
	public ResponseEntity<IrisDTO> getIrisById( @ApiParam(value = "ID of iris to return",required=true) @PathVariable("id") String id ) {
		Optional<ie.demo.model.IrisDTO>iris = irisService.getIrisById( id );
		return new ResponseEntity<IrisDTO>( iris.isPresent() ? iris.get() : null, HttpStatus.OK );
	}

	@Override
	public ResponseEntity<Void> postIris( @ApiParam(value = "Created Iris" ,required=true )  @Valid @RequestBody IrisDTO irisDTO ) {
		irisService.saveIris( List.of( irisDTO ) );
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Void> deleteIrisById(
			@ApiParam(value = "ID of iris to return",required=true) @PathVariable("id") String id ) {
		irisService.deleteIris( id );
		return new ResponseEntity<>(HttpStatus.OK);
	}
}