package graphes.pcc;

import graphes.model.Arc;
import graphes.model.Graphe;
import graphes.model.Noeud;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public abstract class Dijkstra
{

	/** Durée du calcul. null tant que le plus court chemin n'a pas été calculé. */
	private Duration duree = null;
	/** Le Graphe concerné. */
	public final Graphe graphe;
	/** Liste des Noeuds. */
	protected HashMap<Integer, PlusCourtChemin> noeuds;

	public Dijkstra(Graphe graphe)
	{
		this.graphe = graphe;
		this.noeuds = new HashMap<Integer, PlusCourtChemin>();
	}

	public Duration duree()
	{
		return this.duree;
	}

	/** Applique les mises à jour de cout minimal après avoir marqué un Noeud.
	 * 
	 * @param marque - Le Noeud qui a été marqué. */
	protected void misesAJour(PlusCourtChemin marque)
	{
		Set<PlusCourtChemin> pcc = this.nonMarques();

		for (PlusCourtChemin plusCourtChemin : pcc)
		{
			Arc arc = marque.noeud.arcVers(plusCourtChemin.noeud);
			if (arc != null && arc.poids + marque.getCout() < plusCourtChemin.getCout())
			{
				plusCourtChemin.setPere(marque.noeud);
				plusCourtChemin.setCout(arc.poids + marque.getCout());
			}
		}
	}

	/** @return La liste des Noeuds non marqués. */
	protected abstract Set<PlusCourtChemin> nonMarques();

	/** @param depart - Identifiant du Noeud de départ.
	 * @param arrivee - Identifiant du Noeud d'arrivée.
	 * @return Le plus court chemin entre deux Noeuds. */
	public ArrayList<Arc> plusCourtChemin(int depart, int arrivee)
	{
		return this.plusCourtChemin(this.graphe.noeud(depart), this.graphe.noeud(arrivee));
	}

	/** @param depart - Noeud de départ.
	 * @param arrivee - Noeud d'arrivée.
	 * @return Le plus court chemin entre deux Noeuds. */
	public ArrayList<Arc> plusCourtChemin(Noeud depart, Noeud arrivee)
	{
		Instant debut = Instant.now();
		this.preparer(depart);

		while (!this.tousMarques())
		{
			PlusCourtChemin chemin = this.suivant();
			chemin.estMarque = true;
			this.misesAJour(chemin);
		}

		PlusCourtChemin[] chemin = this.trouverChemin(depart, arrivee);
		ArrayList<Arc> arcs = new ArrayList<Arc>();

		for (int i = 1; i < chemin.length; ++i)
		{
			arcs.add(chemin[i - 1].noeud.arcVers(chemin[i].noeud));
		}

		Instant fin = Instant.now();
		this.duree = Duration.between(debut, fin);
		return arcs;
	}

	/** Prépare le calcul. */
	protected void preparer(Noeud depart)
	{
		for (Noeud noeud : this.graphe.noeuds())
		{
			this.noeuds.put(noeud.id, new PlusCourtChemin(noeud, depart));
			Arc arc = depart.arcVers(noeud);
			if (arc != null) this.noeuds.get(noeud.id).setCout(arc.poids);
		}
		this.noeuds.get(depart.id).setCout(-1);
		this.noeuds.get(depart.id).estMarque = true;
	}

	/** @return Le prochain Noeud à marquer. */
	protected abstract PlusCourtChemin suivant();

	/** @return Vrai si tous les Noeuds sont marqués. */
	protected boolean tousMarques()
	{
		return this.nonMarques().size() == 0;
	}

	/** @return Le chemin entre les deux Noeuds, une fois que tous les pcc sont calculés. */
	protected PlusCourtChemin[] trouverChemin(Noeud depart, Noeud arrivee)
	{
		ArrayList<PlusCourtChemin> chemin = new ArrayList<PlusCourtChemin>();
		PlusCourtChemin actuel = this.noeuds.get(arrivee.id);
		while (actuel.noeud != depart)
		{
			chemin.add(actuel);
			actuel = this.noeuds.get(actuel.getPere().id);
		}
		chemin.add(this.noeuds.get(depart.id));

		PlusCourtChemin[] endroit = new PlusCourtChemin[chemin.size()];
		for (int i = 0; i < chemin.size(); ++i)
		{
			endroit[endroit.length - i - 1] = chemin.get(i);
		}

		return endroit;
	}

}
