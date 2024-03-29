package ie.demo.bootstrap;

import static ie.demo.bootstrap.model.IrisCSV.IRIS_CSV_COLUMNS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import ie.demo.bootstrap.mapper.IrisCSVMapper;
import ie.demo.bootstrap.model.IrisCSV;
import ie.demo.repository.IrisRepository;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class Bootstrap implements CommandLineRunner {

	@Value("classpath:iris.csv")
	Resource resourceFile;

	final IrisRepository irisRepository;
	final IrisCSVMapper irisCSVMapper;

	/*
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Transactional
	@Override
	public void run( String... args ) throws Exception {

		log.info( "Starting Bootstrap.run().." );

		irisRepository.deleteAll();

		readFile().stream()
			.forEach( irisCSV -> irisRepository.save( irisCSVMapper.toIris( irisCSV ) ) );

		log.info( "Bootstrap.run() finished.." );
	}

	/**
	 * Load in and parse the iris.csv
	 * @return
	 * @throws IOException
	 */
	List<IrisCSV> readFile() throws IOException {

		/* Set up the column mapping */
		ColumnPositionMappingStrategy<IrisCSV>irisCSVstrategy = new ColumnPositionMappingStrategy<IrisCSV>();
		irisCSVstrategy.setType( IrisCSV.class );
		irisCSVstrategy.setColumnMapping( IRIS_CSV_COLUMNS );

		/* Set up the reader of the CSV file */
		BufferedReader reader = new BufferedReader( new InputStreamReader( resourceFile.getInputStream() ) );

		/* Build up the mapping */
		CsvToBean<IrisCSV> csvToBean = new CsvToBeanBuilder<IrisCSV>( reader )
			.withSkipLines( 1 )
			.withMappingStrategy( irisCSVstrategy )
			.build();

		/* Parse the file and return as a list */
		return csvToBean.parse();
	}
}