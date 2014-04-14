import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class main {

	public static void test() {
		String query = "query/q13.sparql"; // fichier contenant la requete
		Model model = ModelFactory.createDefaultModel();
		try {
			model.read(new FileInputStream("foaf.n3"), null, "TURTLE");
			runExecQuery(query, model);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void test2() {
		String q = "query/q2.sparql"; // fichier contenant la requete
		Query query = QueryFactory.read(q);
		System.out.println("Query sent");
		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://linkedgeodata.org/sparql", query);
		ResultSet concepts = qexec.execSelect();
		ResultSetFormatter.out(concepts);
		qexec.close();
	}

	public static void runExecQuery(String qfilename, Model model) {
		Query query = QueryFactory.read(qfilename);
		QueryExecution queryExecution = QueryExecutionFactory.create(query,
				model);
		ResultSet r = queryExecution.execSelect();
		ResultSetFormatter.out(System.out, r);
		queryExecution.close();
	}

	public static void main(String[] args) {
		test2();
	}
}
