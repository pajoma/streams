package de.ifgi.streams.repository;

import org.openrdf.query.resultio.TupleQueryResultFormat;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
import org.openrdf.rio.RDFFormat;

public class SemanticEventInstancesRepository {

	private static HTTPRepository sesameRepository;
	private RepositoryConnection connection;
	private static String SERVERURL = "http://dunedin.uni-muenster.de:8080/openrdf-sesame/";
	private static String REPOSITORYID = "Iser";

	public Repository getSesameRepository() {
		return sesameRepository;
	}

	public RepositoryConnection getConnection() {
		return connection;
	}

	public SemanticEventInstancesRepository() {

		SemanticEventInstancesRepository.sesameRepository = new HTTPRepository(
				SERVERURL, REPOSITORYID);
		try {
			sesameRepository.initialize();
			sesameRepository
					.setPreferredTupleQueryResultFormat(TupleQueryResultFormat.SPARQL);
			sesameRepository.setPreferredRDFFormat(RDFFormat.RDFXML);
			this.connection = sesameRepository.getConnection();
			this.connection.setAutoCommit(false);

		} catch (RepositoryException e) {
			System.out.println(e.getMessage());
		}

	}

}
