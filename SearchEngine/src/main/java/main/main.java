package main;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class main {



	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

		String fileName = "src/main/resources/data.xml";
		SpringApplication.run(main.class, args);

	    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
		SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
		Session session = sessionFactory.openSession();

		XMLParser parser = new XMLParser();
		List<String> list = parser.parser(fileName);
		list.forEach(GetLinks::new);

		Morpholog morpholog = new Morpholog();
		morpholog.getListPageContents();

		Indexer indexer = new Indexer();
		indexer.indexer();

		SearchSystem searchSystem = new SearchSystem();


	    session.close();


	}

}
