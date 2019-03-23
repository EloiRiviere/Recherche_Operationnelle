/* 
 * Creation by benja the 5 mars 2019.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lireinstancevrp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * LireInstanceVrp.
 *
 * @author benja
 */
public class LireInstanceVrp {
    static Coord []coords;
    static int capa;
    static int nbNoeud;
    static int [] demande;
    static double tableauDistance[][]; 
  /**
   * Main method.
   * 
   * @param args the command line arguments.
   */
  public static void main(final String[] args) {
    
    String fichierContenu="";
    try
    {
        FileWriter fw=new FileWriter(args[1]);
        File fichier= new File(args[0]);

        BufferedReader br = new BufferedReader(new FileReader(fichier));
        
        // les lignes OSEF
        fw.write(br.readLine()+"\n");
        fw.write(br.readLine()+"\n");
        fw.write(br.readLine()+"\n");
        String line= br.readLine();
        fw.write(line+"\n");
        
        String [] mot =line.split(" ");
        
        nbNoeud=Integer.parseInt(mot[2]);
       
        fw.write(br.readLine()+"\n");
        line= br.readLine();
        fw.write(line+"\n");
         capa= Integer.parseInt(mot[2]);
        
         fw.write("tableau distance\n");
       
         br.readLine();
        coords= new Coord[nbNoeud+1];
        
        for(int i=1;i<nbNoeud+1;i++){
          line= br.readLine();
          
          mot =line.split(" ");
          coords[i]=new Coord(Integer.parseInt(mot[0]), Integer.parseInt(mot[1]), Integer.parseInt(mot[2]));
          
        }
        
        tableauDistance=new double[nbNoeud+1][nbNoeud+1];
        
        fw.write(tableauDistance());
        
        
        fw.write(br.readLine()+"\n");
        
        demande=new int[nbNoeud+1];
        for(int i=0;i<nbNoeud;i++){
          line= br.readLine();
          fw.write(line+"\n");
          mot =line.split(" ");
          
          demande[Integer.parseInt(mot[0])]=Integer.parseInt(mot[1]);
          
        }
 
        br.close();
        fw.close();
    }
    catch(Exception e){
      System.err.println("erreure lecture ou ecriture");
    }
    
    
    
  }
  
  public static String tableauDistance()
  {
     String res="";
     
     for(int i=1;i<nbNoeud+1;i++){
        for(int j=1;j<nbNoeud+1;j++){
         
          tableauDistance[i][j]=Math.sqrt((coords[i].x-coords[j].x)*(coords[i].x-coords[j].x)+(coords[j].y-coords[i].y)*(coords[j].y-coords[i].y));
          
          if(j!=nbNoeud)
            res+=tableauDistance[i][j]+" ";
        }
        
        res+=tableauDistance[i][nbNoeud]+"\n";
        
      }
     
     return res;
  }

}
