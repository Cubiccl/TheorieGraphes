package graphes.pcc;

import graphes.model.Graphe;
import graphes.model.Noeud;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class DijkstraV2 extends Dijkstra
{

	/** Les Noeuds non marqués, classés par cout. */
	private Stack<PlusCourtChemin> stack;

	public DijkstraV2(Graphe graphe)
	{
		super(graphe);
		this.stack = new Stack<PlusCourtChemin>();
	}

	@Override
	protected void misesAJour(PlusCourtChemin marque)
	{
		super.misesAJour(marque);
		this.stack.remove(marque);
		this.stack.sort(Comparator.naturalOrder());
	}

	@Override
	protected Set<PlusCourtChemin> nonMarques()
	{
		Set<PlusCourtChemin> set = new HashSet<PlusCourtChemin>();
		set.addAll(this.stack);
		return set;
	}

	@Override
	protected void preparer(Noeud depart)
	{
		super.preparer(depart);
		for (PlusCourtChemin pcc : this.noeuds.values())
			if (pcc.noeud != depart) this.stack.add(pcc);
	}

	@Override
	protected PlusCourtChemin suivant()
	{
		return this.stack.firstElement();
	}

}
