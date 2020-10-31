package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private List<Neighbour> favoritesNeighbours = new ArrayList<Neighbour>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */

    //Ajout des methodes de gestion des favorits
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public List<Neighbour> getFavoritesNeighbours() { return favoritesNeighbours; }

    @Override
    public void deleteFavoriteNeighbour(Neighbour neighbour) { favoritesNeighbours.remove(neighbour); }

    @Override
    public void addFavoriteNeighbour(Neighbour neighbour) { favoritesNeighbours.add(neighbour); }

    @Override
    public Neighbour findNeighbourById(long id){
        int i=0;
        while(i<neighbours.size() ){
            if(id == neighbours.get(i).getId()) {
                return neighbours.get(i);                  //arrete la boucle et sort de la fonction avec neighbour trouvé
            }
            i++;
        }
        return neighbours.get(0);                          //ne sera jamais utilisé car on sort tjs par la boucle
    }

    @Override
    public boolean isFavoriteNeighbour(long id) {
        int i=0;
        while(i<favoritesNeighbours.size() ){
            if(id == favoritesNeighbours.get(i).getId()) {
                return true;
            }
            i++;
        }
        return false;
    }
}
