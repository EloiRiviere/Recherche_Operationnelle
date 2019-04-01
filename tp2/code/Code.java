package code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import objects.Noeud;
import lireinstancevrp.LireInstanceVrp;

public class Code
{

  static Noeud[] liste_noeuds;
  static int nombre_noeuds;
  static int nombre_arcs;
  static Double[][] tableau_distance;

  static int demande[]={15,24,30,8,12,18,27,5,42,13,20,6,21,13,19};
  static int nbClient=15;
  static int capacite=100;
  static int depot = 0;
  static int clients[][] = {{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15},{15,24,30,8,12,18,27,5,42,13,20,6,21,13,19}};

  static List<List<Integer>> solution = new ArrayList<>();

  public static void main(final String[] args)
  {
    tableau_distance = tp2_partie1(args[0]);
    tableau_distance[0][0] = 0.0;
	  afficher_tableau_distances(tableau_distance);

    System.out.println("\nLecture du fichier 'E-n101-k14.vrp.txt' et création du fichier 'la_grande'");
    try
    {
      Runtime rt = Runtime.getRuntime();
      rt.exec("java lireinstancevrp.LireInstanceVrp E-n101-k14.vrp.txt la_grande");
    }
    catch(Exception e){}

    afficher_tableau_clients(clients);

    creation_solution();
    affichage_solution();
    affichage_validation2();

  }

  public static void creation_solution()
  {
    List<Integer> tournee = new ArrayList<>();
    tournee.add(depot+1);
    int total = demande[depot];
    for(int index = 1; index < nbClient; index++)
    {
      if((total + demande[index] + demande[0]) > capacite)
      {
        tournee.add(depot+1);
        total = total + demande[depot];
        solution.add(tournee);

        tournee = new ArrayList<>();
        tournee.add(depot+1);
        total = demande[depot];
      }
      tournee.add(index+1);
      total = total + demande[index];
    }
    tournee.add(depot+1);
    total = total + demande[depot];
    solution.add(tournee);
  }

  public static void affichage_solution()
  {
    System.out.println("\nSolution:\n");
    // System.out.println(solution);

    for( int i = 0; i < solution.size(); i++)
    {
      System.out.println("Tournée " + (i+1) + ":");
      System.out.print("\t");
      for(int j = 0; j < solution.get(i).size(); j++)
      {
        System.out.print("\t->\t" + solution.get(i).get(j));
      }
      System.out.print("\n");
    }
    System.out.print("\n");
  }

  public static void affichage_validation2()
  {
    if(isValideSolution2())
    {
      System.out.println("La solution est valide !");
      return;
    }
    System.out.println("La solution n'est pas valide !");
  }

  public static int cout2(List<Integer> tournee)
  {
    int total = 0;

    for(int i = 0; i<tournee.size()-1; i++)
    {
      total += clients[1][i];
    }
    return total;
  }

  public static boolean isValideTournee2(List<Integer> tournee)
  {
    if(cout2(tournee) <= capacite) return true;
  	return false;
  }

  public static boolean isValideSolution2()
  {
    // on vérifie le coût ne soit pas dépassé
    for(List<Integer> tournee : solution)
    {
      if(!isValideTournee2(tournee)) return false;
    }
    // on vérifie que chacun des clients ne soit présent qu'une fois (sans compter le dépôt)
    List<Integer> cl = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15));
    cl.remove(0);
    for(List<Integer> tournee : solution)
    {
      for(int i : tournee)
      {
        if(i != 1)
        {
          /*System.out.println("Suppression de " + i + " : " + */cl.remove(new Integer(i))/*)*/;
        }
      }
    }
    if(cl.isEmpty()) return true;
    return false;
  }

  // public static Double cout(List<Integer> tournee)
  // {
  // 	Double total = tableau_distance[depot][tournee.get(0)] + tableau_distance[tournee.get(tournee.size()-1)][depot];
  //
  // 	for(int i = 0; i<tournee.size()-1; i++)
  // 	{
  // 		total += tableau_distance[tournee.get(i)][tournee.get(i+1)];
  // 	}
  // 	return total;
  // }
  //
  // public static boolean isValideTournee(List<Integer> tournee)
  // {
  // 	if(cout(tournee) <= capacite) return true;
  // 	return false;
  // }
  //
  // public static boolean isValideSolution(List<List<Integer>> solution)
  // {
  // 	// vérifier si tous les clients sont présents et 1 seule fois
  // 	for(List<Integer> tournee : solution)
  // 	{
  // 		if(!isValideTournee(tournee)) return false;
  // 	}
  // 	return true;
  // }
  //

  public static Double[][] tp2_partie1(String fichier)
  {
    chargement(fichier);

    Double[][] tableau_distances = new Double[17][17];

    Double[] marques;

    Integer tableau_noeuds[] = {0,145,10, 24, 30, 36, 38, 50, 95, 124, 140, 167, 261, 286, 275, 311,320};

    for(int i=1;i<17;i++)
    {
      marques = Dikjstra(tableau_noeuds[i]);
      for(int j=1;j<17;j++)
      {
        tableau_distances[i][j] = (int)(marques[tableau_noeuds[j]] * 100)*1.0/100;
      }
    }
    sauvegarde("tableau.data", tableau_distances);
    return tableau_distances;
  }

  public static void chargement(String localisation)
  {
    try
    {
        BufferedReader buffer = new BufferedReader(new FileReader(new File(localisation)));

        nombre_noeuds = Integer.parseInt(buffer.readLine());
        nombre_arcs = Integer.parseInt(buffer.readLine());

        liste_noeuds= new Noeud[nombre_noeuds+1];

        for(int i=1;i<=nombre_noeuds;i++)
        {
          liste_noeuds[i]= new Noeud();
        }

        int noeud_entree, noeud_sortie;
        Double cout;

        for(int i=0;i<nombre_arcs;i++)
        {
          String [] mot =buffer.readLine().split(";");

          noeud_entree = Integer.parseInt(mot[1]);
          noeud_sortie = Integer.parseInt(mot[2]);
          cout = Double.parseDouble(mot[3]);

          liste_noeuds[noeud_entree].arcs.put(noeud_sortie, cout);
        }
        buffer.close();
    }
    catch(Exception e)
    {
      System.err.println("Erreur lecture");
    }
  }

  public static Double[] Dikjstra(int noeud_entree)
  {
    int i;

    // initialisation de tableau de valeurs d'accès (marques)
    Double[] marques= new Double[nombre_noeuds+1];
    for(i=1;i<=nombre_noeuds;i++) {marques[i] = Double.POSITIVE_INFINITY;} marques[noeud_entree] = 0.0;

    // initialisation de la liste des noeuds
    List<Integer> liste_noeuds_non_traites = new ArrayList<>();
    for(i=1;i<=nombre_noeuds;i++){liste_noeuds_non_traites.add(i);};

    //traitement dikjstra
    //tant qu'il reste des neuds non-traités
    while(!liste_noeuds_non_traites.isEmpty())
    {
      // System.out.println("\n\nIl reste des noeuds non-traités !");
      int num = 0;
      Double tmp, min = Double.POSITIVE_INFINITY;
      // je cherche le noeud qui a la marque la plus petite
      // System.out.println("Je recherche le noeud qui a la marque la plus petite:");
      for(i=1;i<marques.length;i++)
      {
        tmp = marques[i];
        if(liste_noeuds_non_traites.contains(i) && min > tmp)
        {
          min = tmp;
          num = i;
        }
      }
      // System.out.println("Noeud choisi: " + num);
      // System.out.println("Marque actuelle du noeud choisi: " + min);
      // affichage_tableau_marques(marques);
      //je récupère la liste des sortants de mon noeud
      for(Integer key : liste_noeuds[num].arcs.keySet())
      {
        // pour chacun de mes noeuds je dois verifier que sa marque est inférieure à ma marque + le cout d'accès à ce noeud, sinon je le remplace
        if(min + liste_noeuds[num].arcs.get(key) < marques[key])
        {
          // System.out.println("En passant par " + num + ", le noeud " + key + " aura une marque plus petite. il faut mettre à jour sa marque.");
          marques[key] = liste_noeuds[num].arcs.get(key) + min;
        }
        // else
        // {
          // System.out.println("Il n'est pas intéressant de changer la marque de " + key);
        // }
      }

      // System.out.println("On a fini de traiter le noeud " + num);
      liste_noeuds_non_traites.remove((Integer)num);
    }

    return marques;
  }

  public static void affichage_tableau_marques(Double marques[]){for(Double d : marques){System.out.println(d);}}

  public static void affichage_liste_noeuds_non_traites(List<Integer> liste_noeuds_non_traites){for(int i : liste_noeuds_non_traites){System.out.println(i);}}

  public static void sauvegarde(String localisation, Double[][]  tableau_distances)
  {
    Double tableau_noeuds[] = {145.0,10.0, 24.0, 30.0, 36.0, 38.0, 50.0, 95.0, 124.0, 140.0, 167.0, 261.0, 286.0, 275.0, 311.0, 320.0};
    int i = 1;
    for(Double d : tableau_noeuds)
    {
      tableau_distances[i][0] = d;
      tableau_distances[0][i] = d;
      i++;
    }

    try
    {

      FileWriter fw=new FileWriter(localisation);

      for(i=1; i<17; i++)
      {
        for(int j=1; j<16; j++)
        {
          fw.write(tableau_distances[i][j]+" ");
        }
        fw.write(""+tableau_distances[i][16]+"\n");
      }
      fw.close();
    }
    catch(Exception e){
      System.err.println("erreure ecriture");
    }
  }

  public static void afficher_tableau_clients(int clients[][])
  {
    System.out.println("\nTableau de clients:\n");
    for(int i = 0;i<2;i++)
    {
      for(int j=0;j<15;j++)
      {
        System.out.print(clients[i][j] + "\t");
      }
      System.out.print("\n");
    }
  }

  public static void afficher_tableau_distances(Double[][] tableau_distances)
  {
    System.out.println("\nTableau de distances:\n");
    int i, j;
    for(i=0;i<17;i++)
    {
      for(j=0;j<17;j++)
      {
        //System.out.println("tableau_distances[" + i + "][" + j + "]: " + tableau_distances[i][j]);
        System.out.print(tableau_distances[i][j]+"\t");
      }
      System.out.println("");
    }
  }

}
