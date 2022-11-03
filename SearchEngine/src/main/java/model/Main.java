package model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
public class Main {



	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

		String fileName = "src/main/resources/data.xml";
		SpringApplication.run(Main.class, args);

//	    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//		Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
//		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
//		Session session = sessionFactory.openSession();
//
//		XMLParser parser = new XMLParser();
//		List<String> list = parser.parser(fileName);
//		list.forEach(GetLinks::new);
//
//		Morpholog morpholog = new Morpholog();
//		morpholog.getListPageContents();
//
//		Indexer indexer = new Indexer();
//		indexer.indexer();
//
//		SearchSystem searchSystem = new SearchSystem();
//
//
//	    session.close();


	}

}
