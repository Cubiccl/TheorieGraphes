package graphes.pcc;

import graphes.model.Graphe;

import java.util.HashSet;
import java.util.Set;

public class DijkstraV1 extends Dijkstra
{

	public DijkstraV1(Graphe graphe)
	{
		super(graphe);
	}

	@Override
	protected Set<PlusCourtChemin> nonMarques()
	{
		HashSet<PlusCourtChemin> pcc = new HashSet<PlusCourtChemin>();
		for (PlusCourtChemin plusCourtChemin : this.noeuds.values())
			if (!plusCourtChemin.estMarque) pcc.add(plusCourtChemin);
		return pcc;
	}

	@Override
	protected PlusCourtChemin suivant()
	{
		Set<PlusCourtChemin> chemins = this.nonMarques();
		PlusCourtChemin proche = chemins.iterator().next();

		for (PlusCourtChemin pcc : chemins)
		{
			if (proche.getCout() > pcc.getCout()) proche = pcc;
		}

		return proche;
	}
}
