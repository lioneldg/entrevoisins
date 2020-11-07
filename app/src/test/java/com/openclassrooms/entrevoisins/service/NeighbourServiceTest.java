package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void addAndGetFavoriteNeighbourWithSuccess(){
        Neighbour neighbour= service.getNeighbours().get(0);
        assertFalse(service.getFavoritesNeighbours().contains(neighbour));
        service.addFavoriteNeighbour(neighbour);
        assertTrue(service.getFavoritesNeighbours().contains(neighbour));
    }

    @Test
    public void deleteFavoriteNeighbourWithSuccess(){
        Neighbour neighbour= service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbour);
        service.deleteFavoriteNeighbour(neighbour);
        assertFalse(service.getFavoritesNeighbours().contains(neighbour));
    }

    @Test
    public void findNeighbourByIdWithSuccess(){
        Neighbour neighbour= service.getNeighbours().get(0);
        assertEquals(neighbour.getId(), service.findNeighbourById(neighbour.getId()).getId());
    }

    @Test
    public void isFavoriteNeighbourWithSuccess(){
        Neighbour neighbour= service.getNeighbours().get(0);
        service.addFavoriteNeighbour(neighbour);
        assertTrue(service.isFavoriteNeighbour(neighbour.getId()));
    }

    @Test
    public void createNeighbourWithSuccess(){
        List<Neighbour> neighbours = service.getNeighbours();
        Neighbour newNeighbour =  new Neighbour(9855648, "Lionel", "https://i.pravatar.cc/300?u=a042581f3e39026702d", "Saint-Pierre-du-Mont ; 5km",
                "+33 6 86 57 90 14",  "Bonjour !Je souhaiterais faire de la marche nordique. Pas initiée, je recherche une ou plusieurs personnes susceptibles de m'accompagner !J'aime les jeux de cartes tels la belote et le tarot..");
        service.createNeighbour(newNeighbour);
        assertTrue(neighbours.contains(newNeighbour));
    }
}
