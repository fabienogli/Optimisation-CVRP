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

**Problème simplifiée:**
