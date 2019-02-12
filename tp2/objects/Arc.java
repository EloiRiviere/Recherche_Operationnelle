package objects;

public class Arc
{
	int id, entrant, sortant;
	float cout;
	
	public Arc(int id, int entrant, int sortant, float cout)
	{
		this.id = id;
		this.entrant = entrant;
		this.sortant = sortant;
		this.cout = cout;
	}
	
	public int getEntrant()
	{
		return entrant;
	}
	
	public int getSortant()
	{
		return sortant;
	}
	
	public int getId()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return "\tArc " + id + "\tNoeud entrant: " + entrant + "\tNoeud sortant: " + sortant + "\tCoût: " + cout + "\n";
	}
}
