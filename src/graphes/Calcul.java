package graphes;

import graphes.model.Graphe;

import java.time.Duration;

public class Calcul
{

	/** Nombre d'Arcs dans le graphe. */
	public final int arcs;
	/** Densité du graphe. */
	public final double densite;
	/** Durée du calcul. */
	public final Duration duree;
	/** Nombre de Noeuds dans le graphe. */
	public final int noeuds;

	public Calcul(Graphe graphe, Duration duree)
	{
		super();
		this.duree = duree;
		this.arcs = graphe.arcs().size();
		this.noeuds = graphe.noeuds().size();
		this.densite = this.arcs * 1. / this.noeuds / this.noeuds;
	}

}
