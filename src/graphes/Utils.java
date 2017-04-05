package graphes;

import graphes.model.Arc;
import graphes.model.Graphe;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils
{

	public static List<CSVRecord> lireFichierCSV(String path)
	{
		try
		{
			FileReader fichier = new FileReader(path);
			CSVParser parser = new CSVParser(fichier, CSVFormat.DEFAULT.withHeader(Graphe.CSV_HEADER));
			List<CSVRecord> records = parser.getRecords();
			parser.close();
			return records;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CSVRecord>();
	}

	/** Affiche le chemin dans la console. */
	public static void afficherChemin(ArrayList<Arc> plusCourtChemin)
	{
		String s = plusCourtChemin.get(0).depart.id + "";
		for (Arc arc : plusCourtChemin)
			s += " -> " + arc.arrivee.id;
		System.out.println(s);
	}

}
