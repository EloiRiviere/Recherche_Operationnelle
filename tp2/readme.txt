Executer Code.java !

Pour la partie 3, mon algorithme démarre du dépôt et va chez le premier client.
  De là si il peut aller chez le prochain client et rentrer au dépôt sans que la capacité soit dépassée il va chez le client d'après et ferais le même traitement.
  Si la capacité sera dépassée il retourne au dépôt, puis repart du dépôt vers ce client et continue sa tournée.

Il peut sans doute y avoir une meilleure solution en faisant des tests avec mon algorithme sur toutes les configurations possibles d'ordre de afficher_tableau_clients
([1,2,3,4],[1,2,4,3],[1,3,2,4],[1,3,4,2],[1,4,2,3],[1,4,3,2]...) avec les 15 clients
