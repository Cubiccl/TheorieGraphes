package graphes.model;

import graphes.Utils;
import graphes.pcc.DijkstraV1;
import graphes.pcc.DijkstraV2;

import java.util.*;

import org.apache.commons.csv.CSVRecord;

public class Graphe
{
	public static final String DEPART = "depart", ARRIVEE = "arrivee", POIDS = "poids";
	public static final String[] CSV_HEADER =
	{ DEPART, ARRIVEE, POIDS };

	/** Crée un Graphe à partir d'un fichier csv. */
	public static Graphe creerGraphe(String path)
	{
		List<CSVRecord> records = Utils.lireFichierCSV(path);

		Graphe g = new Graphe();
		for (CSVRecord record : records)
		{
			int n1 = Integer.parseInt(record.get(DEPART));
			int n2 = Integer.parseInt(record.get(ARRIVEE));
			double poids = Double.parseDouble(record.get(POIDS));
			g.ajouterArc(n1, n2, poids);
		}

		return g;
	}

	/** Crée un graphe aléatoire.
	 * 
	 * @param noeuds - Le nombre de Noeuds de ce graphe.
	 * @param arcs - Le nombre d'Arcs de ce graphe.
	 * @param poidsMin - Le poids minimum des Arcs.
	 * @param poidsMax - Le poids maximum des Arcs. */
	public static Graphe genererGraphe(int noeuds, int arcs, double poidsMin, double poidsMax)
	{
		Graphe g = new Graphe();
		for (int i = 0; i < noeuds; ++i)
			g.ajouterNoeud(i + 1);

		if (arcs > noeuds * noeuds) arcs = noeuds * noeuds;

		Random r = new Random();
		for (int i = 0; i < arcs; ++i)
		{
			int n1, n2;
			do
			{
				n1 = r.nextInt(noeuds) + 1;
				n2 = r.nextInt(noeuds) + 1;
			} while (g.arcExiste(n1, n2));
			g.ajouterArc(n1, n2, r.nextDouble() * (poidsMax - poidsMin) + poidsMin);
		}

		return g;
	}

	/** Contient les noeuds de ce Graphe. */
	private HashMap<Integer, Noeud> noeuds;

	public Graphe()
	{
		this.noeuds = new HashMap<Integer, Noeud>();
	}

	/** Ajoute un Arc à ce Graphe.
	 * 
	 * @param id1 - ID du noeud de départ
	 * @param id2 - ID du noeud d'arrivée
	 * @param poids - Poids de l'Arc. */
	public void ajouterArc(int id1, int id2, double poids)
	{
		if (!this.noeuds.containsKey(id1)) this.ajouterNoeud(id1);
		if (!this.noeuds.containsKey(id2)) this.ajouterNoeud(id2);
		Noeud n1 = this.noeud(id1);
		Noeud n2 = this.noeud(id2);
		this.ajouterArc(n1, n2, poids);
	}

	/** Ajoute un Arc à ce Graphe.
	 * 
	 * @param n1 - Noeud de départ
	 * @param n2 - Noeud d'arrivée
	 * @param poids - Poids de l'Arc */
	public void ajouterArc(Noeud n1, Noeud n2, double poids)
	{
		if (n1 == null || n2 == null) return;
		new Arc(n1, n2, poids);
	}

	/** Ajoute un noeud à ce Graphe.
	 * 
	 * @param id - Identifiant du noeud. */
	public void ajouterNoeud(int id)
	{
		this.ajouterNoeud(new Noeud(id));
	}

	/** Ajoute un noeud à ce Graphe. */
	public void ajouterNoeud(Noeud noeud)
	{
		if (!this.noeuds.containsKey(noeud.id)) this.noeuds.put(noeud.id, noeud);
	}

	/** @param n1 - Identifiant du noeud de départ.
	 * @param n2- Identifiant du noeud d'arrivée.
	 * @return Vrai si un arc existe de n1 vers n2. */
	private boolean arcExiste(int n1, int n2)
	{
		return this.noeud(n1).arcVers(this.noeud(n2)) != null;
	}

	public Set<Arc> arcs()
	{
		Set<Arc> arcs = new HashSet<Arc>();
		for (Noeud n : this.noeuds.values())
			arcs.addAll(n.successeurs());
		return arcs;
	}

	public DijkstraV1 dijkstraV1()
	{
		return new DijkstraV1(this);
	}

	public DijkstraV2 dijkstraV2()
	{
		return new DijkstraV2(this);
	}

	/** @return Le noeud correspondant à l'identifiant donné. */
	public Noeud noeud(int id)
	{
		return this.noeuds.get(id);
	}

	public Collection<Noeud> noeuds()
	{
		return this.noeuds.values();
	}

	@Override
	public String toString()
	{
		String s = "";
		ArrayList<Arc> arcs = new ArrayList<Arc>();
		arcs.addAll(this.arcs());
		arcs.sort(Comparator.naturalOrder());

		for (Arc a : arcs)
		{
			s += a.depart + " -> " + a.arrivee + ", " + a.poids + "\n";
		}
		return s;
	}
}
