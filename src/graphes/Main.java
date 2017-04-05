package graphes;

import graphes.model.Graphe;
import graphes.pcc.Dijkstra;

public class Main
{
	/** Store les calculs effectués. */
	private static Calcul[] calculs;

	public static Window window;

	public static void calcul(int noeuds, int arcs, int graphes)
	{/* Graphe g = Graphe.creerGraphe("resources/grapheTest.csv"); window.addText(g); DijkstraV1 d = g.dijkstraV1(); afficherChemin(d.plusCourtChemin(4, 1)); window.addText("Calculé en " + d.duree().toMillis() + " millisecondes."); window.addText();
	 * 
	 * Graphe g1 = Graphe.genererGraphe(4, 9, 4, 12); window.addText(g1); */

		window.addText("\n\nNoeuds: " + noeuds + ", Arcs: " + arcs + ", Graphes: " + graphes);

		calculs = new Calcul[graphes];

		window.addText("Calcul V1...");
		for (int i = 0; i < graphes; ++i)
		{
			// window.addText("Graphe " + i);
			Graphe g = Graphe.genererGraphe(noeuds, arcs, 0, 1);
			Dijkstra d = g.dijkstraV1();
			d.plusCourtChemin(1, 2);
			calculs[i] = new Calcul(g, d.duree());
		}

		window.addText("Nombre d'arcs: " + calculs[0].arcs + ", Densité: " + calculs[0].densite);
		double moyen = 0, ecart = 0;
		for (Calcul calcul : calculs)
		{
			moyen += calcul.duree.toMillis();
			ecart += calcul.duree.toMillis() * calcul.duree.toMillis();
		}

		moyen /= calculs.length;
		ecart /= calculs.length;
		ecart -= moyen * moyen;

		window.addText("Temps moyen: " + moyen + " millisecondes, Ecart-type: " + ecart);

		window.addText("\nCalcul V2...");
		for (int i = 0; i < graphes; ++i)
		{
			Graphe g = Graphe.genererGraphe(noeuds, arcs, 0, 1);
			Dijkstra d = g.dijkstraV2();
			d.plusCourtChemin(1, 2);
			calculs[i] = new Calcul(g, d.duree());
		}

		moyen = 0;
		ecart = 0;
		for (Calcul calcul : calculs)
		{
			moyen += calcul.duree.toMillis();
			ecart += calcul.duree.toMillis() * calcul.duree.toMillis();
		}

		moyen /= calculs.length;
		ecart /= calculs.length;
		ecart -= moyen * moyen;

		window.addText("Temps moyen: " + moyen + " millisecondes, Ecart-type: " + ecart);
	}

	public static void main(String[] args)
	{
		window = new Window();
		window.setVisible(true);
	}

}
