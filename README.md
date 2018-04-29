# Optimisation-CVRP
***Le Problème***  
Le CVRP consiste à déterminer un ensemble d’itinéraires,  
commençant et se terminant au dépôt v0 , qui couvrent un  
ensemble de clients. Chaque client a une demande  
spécifique et est visité une seule fois par véhicule. Tous les  
véhicules ont la même capacité C et transportent un seul  
type de marchandises. Aucun véhicule ne peut desservir  
plus de clients que sa capacité C ne le permet. L’objectif ici  
est de réduire au minimum la distance totale parcourue.  
Ainsi, le CVRP est réduit à cloisonner le graphe en m  
circuits simples où chaque circuit correspond à un itinéraire  
de véhicule (le nombre de véhicules utilisés est à  
déterminer, il n’est pas limité).  

**Méthodes employées :**  
*Méthodes heuristiques à base de voisinage implantés*  
- Recuit Simulé (cours: Optimisation_meta01)  
- Taboo (cours: Optimisation_meta02)  

*Méthodes heuristiques à base de population implantés*  
  
  
****Contraintes****  
Ne pas dépasser le nombre de véhicules à disposition  
Tous les clients doivent être livrés.  
Respecter la capacité des véhicules  
Cout C = 100  
  
**Initialisation**  
Nous devons génèrer des circuits simples.  
Un sommet représente un Util.Client.  
Nous partons du dêpot et choisissons un sommet au hasard. Nous créons un arrêtes et reproduisons le processus jusqu'à atteindre Cmax. 
Lorsque nous atteignons Cmax, nous fermons le circuit en créant une arrête contenant le dernier sommet et le sommet de dépot  
Nous reproduisons le processus jusqu'à avoir des circuits contenant tous les sommets.  
*alternative*  
sommet le plus proche et arrêt quand on atteind Cmax  
*Recuit Simulé (cours: Optimisation_meta01) *  
On intialise T0 (la tempèrature initiale) avec la plus grande diff de l'itinéraire de la 1ère solution avec ses voisins

##La fonction fitness
Le coût d’un itinéraire R i = v i0 , v i1 , ..., v ik+1 , où v ij ÎV et v i0 = v ik+1 = v 0 , est donné par :
cout(Ri) = Sum from j=0 to k {c_{ij+1}}
Le coût total d’une solution est la somme des coût de chaque R i .

##Algo génétique
Deux graphes G et G' composés de deux tab de Circuit.
- On choisit un sommet au hasard et on les croise sur ce sommet
PB : les doublons et certain sommets peuvent disparaîtres
- autre solutions
faire une liste des sommets dans l'ordre pour un circuit -> codage d'un graphe
- a chaque circuit, le pété à un endroit
 combiner les circuit dans un graph de sorte à avoir tous les sommets
 S'ils n'y sont pas tous: les rajouter manuellement
