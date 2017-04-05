package graphes.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Noeud
{

	/** Identifiant du Noeud. */
	public final int id;
	/** Successeurs du Noeud. */
	private LinkedList<Arc> successeurs;

	public Noeud(int id)
	{
		this.id = id;
		this.successeurs = new LinkedList<Arc>();
	}

	public void ajouterSuccesseur(Arc successeur)
	{
		for (Arc arc : this.successeurs)
			if (arc.arrivee == successeur.arrivee) return;

		this.successeurs.add(successeur);
	}

	/** @return L'arc qui joint ce Noeud au noeud demandé. Renvoie null si le noeud n'est pas un successeur. */
	public Arc arcVers(Noeud noeud)
	{
		for (Arc arc : this.successeurs)
			if (arc.arrivee == noeud) return arc;
		return null;
	}

	/** @return La liste des successeurs de ce noeud. */
	public Set<Arc> successeurs()
	{
		Set<Arc> arcs = new HashSet<Arc>();
		arcs.addAll(this.successeurs);
		return arcs;
	}

	@Override
	public String toString()
	{
		return Integer.toString(this.id);
	}

}
