package graphes.model;

public class Arc implements Comparable<Arc>
{

	/** Les Noeuds d'extrémités. */
	public final Noeud depart, arrivee;
	/** Le poids de l'arc. */
	public final double poids;

	public Arc(Noeud depart, Noeud arrivee)
	{
		this(depart, arrivee, 1);
	}

	public Arc(Noeud depart, Noeud arrivee, double poids)
	{
		this.depart = depart;
		this.arrivee = arrivee;
		this.poids = poids;

		this.depart.ajouterSuccesseur(this);
	}

	@Override
	public int compareTo(Arc anotherArc)
	{
		if (this.depart == anotherArc.depart) return this.arrivee.id - anotherArc.arrivee.id;
		return this.depart.id - anotherArc.depart.id;
	}

}
