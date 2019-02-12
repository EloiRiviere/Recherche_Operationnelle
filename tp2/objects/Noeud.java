package objects;

import java.util.List;
import java.util.ArrayList;

public class Noeud
{
	List<Arc> listArcsSortants, listArcsEntrants;
	
	public Noeud()
	{
		this.listArcsEntrants = new ArrayList<>();
		this.listArcsSortants = new ArrayList<>();
	}
	
	public List<Arc> getListeArcsSortants()
	{
		return listArcsSortants;
	}
	
	public List<Arc> getListeArcsEntrants()
	{
		return listArcsEntrants;
	}
	
	@Override
	public String toString()
	{
		String phrase = "";
		phrase = "Noeud: " + listArcsEntrants.get(0).getSortant() + "\n Liste arcs entrants:\n";
		for(Arc arc : listArcsEntrants)
		{
			phrase += arc;
		}
		phrase += "\n Liste arcs sortants:\n";
		for(Arc arc : listArcsSortants)
		{
			phrase += arc;
		}
		return phrase + "\n";
	}
	
}
