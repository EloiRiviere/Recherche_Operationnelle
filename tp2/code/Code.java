package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

import objects.Arc;
import objects.Noeud;

public class Code
{
  static int nbNoeuds = 0, nbArcs = 0;
  
	public static void main(final String[] args)
	{ 
		List<Noeud> listeNoeuds = new ArrayList<>();
		List<Arc> listeArcs = Code.lecture(args[0]);
    
		for(int i=0;i<nbNoeuds;i++)
		{
			listeNoeuds.add(new Noeud());
		}
	
		for(Arc arc : listeArcs)
		{
			listeNoeuds.get(arc.getEntrant()-1).getListeArcsSortants().add(arc);
			listeNoeuds.get(arc.getSortant()-1).getListeArcsEntrants().add(arc);				
		}
		for(Noeud noeud : listeNoeuds)
		{
			System.out.println(noeud);
		}
		
	}
  
  public static List<Arc> lecture(String localisation)
  {
	  List<Arc> listeArcs;
	try
    {
        
        File fichier= new File(localisation);

        BufferedReader br = new BufferedReader(new FileReader(fichier));
        String line= br.readLine();
        
        nbNoeuds = Integer.parseInt(line);
        
        line= br.readLine();
        
        nbArcs = Integer.parseInt(line);
        
        listeArcs = new ArrayList<>();
        
        int[] indiceArc= new int[nbArcs];
        int[] indiceDepart= new int[nbArcs];
        int[] indicArrivee= new int[nbArcs];
        float[] cout= new float[nbArcs];
        
        for(int i=0;i<nbArcs;i++)
        {
          String [] mot = br.readLine().split(";");
          listeArcs.add(new Arc(Integer.parseInt(mot[0]), Integer.parseInt(mot[1]), Integer.parseInt(mot[2]), Float.parseFloat(mot[3])));         
        }
        
        br.close();
        return listeArcs;
    }
    catch(Exception e)
    {
      System.err.println("erreure lecture");
    }
    
    return null;
  }
}
