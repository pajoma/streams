package de.ifgi.streams.feeder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.vocabulary.RDF;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.Update;
import org.openrdf.query.UpdateExecutionException;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.slf4j.LoggerFactory;

import de.ifgi.streams.repository.SemanticEventInstancesRepository;

public class EventFeeder {

	public static String EIR = "http://dunedin.uni-muenster.de:8080/event-instance-repository/";
	public static String GEO = "http://www.w3.org/2003/01/geo/wgs84_pos#";
	public static String OGC = "http://www.opengis.net/ont/OGC-GeoSPARQL/1.0/";
	public static String EV = "http://example.org/event-vocabulary/";

	public static void main(String[] args) {
		EventFeeder ef = new EventFeeder();
		ef.add_events();
	}

	private void add_events() {

		SemanticEventInstancesRepository iser = new SemanticEventInstancesRepository();
		RepositoryConnection c = iser.getConnection();

		int i = 0;
		try {
			while (c.isOpen() == true) {
				i++;

				// create RDF Resources
				URI earthquake = c.getValueFactory().createURI("<" + EIR,
						"EarthQuake_" + i + ">");
				URI time = c.getValueFactory().createURI("<" + EIR,
						"Time_" + i + ">");
				URI point = c.getValueFactory().createURI("<" + EIR,
						"Point_" + i + ">");

				URI geoearthquake = c.getValueFactory().createURI("<" + GEO,
						"EarthQuake" + ">");
				URI attime = c.getValueFactory().createURI("<" + EV,
						"atTime" + ">");
				URI evtime = c.getValueFactory().createURI("<" + EV,
						"Time" + ">");
				URI atloc = c.getValueFactory().createURI("<" + EV,
						"atLocation" + ">");
				URI asISOTimeStamp = c.getValueFactory().createURI("<" + EV,
						"asISOTimeStamp" + ">");
				URI ogcpoint = c.getValueFactory().createURI("<" + OGC,
						"Point" + ">");
				URI asWKT = c.getValueFactory().createURI("<" + OGC,
						"asWKT" + ">");
				URI type = c.getValueFactory().createURI("<" + RDF.TYPE + ">");

				// create Earthquake RDF statements
				Statement s1 = c.getValueFactory().createStatement(earthquake,
						type, geoearthquake);

				Statement s2 = c.getValueFactory().createStatement(earthquake,
						attime, time);

				Statement s3 = c.getValueFactory().createStatement(earthquake,
						atloc, point);

				// point property
				Statement s4 = c.getValueFactory().createStatement(point, type,
						ogcpoint);

				Statement s5 = c.getValueFactory().createStatement(
						point,
						asWKT,
						c.getValueFactory().createLiteral(
								"POINT(38.8123 -122.8135)"));

				// time property
				Statement s6 = c.getValueFactory().createStatement(time, type,
						evtime);

				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ssZ");
				String now = sdf.format(date);

				Statement s7 = c.getValueFactory().createStatement(time,
						asISOTimeStamp, c.getValueFactory().createLiteral(now));

				// provide valid statements
				String s1sub = (s1.toString().replace("(", "").replace(")", "")
						.replace(",", ""));
				String s2sub = (s2.toString().replace("(", "").replace(")", "")
						.replace(",", ""));
				String s3sub = (s3.toString().replace("(", "").replace(")", "")
						.replace(",", ""));

				StringBuilder earthquake_s = new StringBuilder(s1sub + ". "
						+ s2sub + ". " + s3sub + ".");

				String s4sub = (s4.toString().replace("(", "").replace(")", "")
						.replace(",", ""));
				String s5sub = (s5.toString().replace("(", "").replace(")", "")
						.replace(",", ""));

				StringBuilder point_s = new StringBuilder(s4sub + ". " + s5sub
						+ ". ");

				String s6sub = (s6.toString().replace("(", "").replace(")", "")
						.replace(",", ""));
				String s7sub = (s7.toString().replace("(", "").replace(")", "")
						.replace(",", ""));

				StringBuilder time_s = new StringBuilder(s6sub + ". " + s7sub
						+ ". ");

				// Add statements with SPARQL Update Query
				String queryString = "PREFIX ns: <http://example.org/ns#> INSERT DATA { GRAPH <http://example/events> {"
						+ earthquake_s + point_s + time_s + "} }";

				try {
					Update updateQuery = c.prepareUpdate(QueryLanguage.SPARQL,
							queryString);
					updateQuery.execute();
					
					org.slf4j.Logger logger = LoggerFactory.getLogger(EventFeeder.class);
				    logger.info("Added Event statement " + i + " !");

				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedQueryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UpdateExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
