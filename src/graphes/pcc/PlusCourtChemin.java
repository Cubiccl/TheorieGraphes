package graphes.pcc;

import graphes.model.Noeud;

public class PlusCourtChemin implements Comparable<PlusCourtChemin>
{

	/** Cout du chemin. */
	private double cout;
	/** Vrai si le Noeud est marqué. */
	public boolean estMarque;
	/** Noeud concerné (celui dont il faut calculer le plus court chemin). */
	public final Noeud noeud;
	/** Dernier Noeud traversé par ce chemin. */
	private Noeud pere;

	public PlusCourtChemin(Noeud noeud, Noeud pere)
	{
		this.noeud = noeud;
		this.pere = pere;
		this.cout = Double.POSITIVE_INFINITY;
		this.estMarque = false;
	}

	@Override
	public int compareTo(PlusCourtChemin o)
	{
		if (this.cout == o.cout) return 0;
		if (this.cout > o.cout) return 1;
		return -1;
	}

	public double getCout()
	{
		return this.cout;
	}

	public Noeud getPere()
	{
		return this.pere;
	}

	public void setCout(double cout)
	{
		this.cout = cout;
	}

	public void setPere(Noeud pere)
	{
		this.pere = pere;
	}

}
