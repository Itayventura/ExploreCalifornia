package com.itayvenutra.exploreCalifornia.service;

import static org.junit.jupiter.api.Assertions.*;

import com.itayvenutra.exploreCalifornia.service.TourRatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.itayvenutra.exploreCalifornia.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TourRatingServiceIntegrationTest {
    private static final int CUSTOMER_ID = 456;
    private static final int TOUR_ID = 1;
    private static final int NOT_A_TOUR_ID = 123;

    @Autowired
    private TourRatingService service;

    //Happy Path delete existing TourRating.

    List<TourRating> toList(Iterable<TourRating> tourRatings){
        List<TourRating> tourRatingList = new ArrayList<>();
        tourRatings.forEach(tourRating -> tourRatingList.add(tourRating));
        return tourRatingList;
    }
    @Test
    public void delete() {
        Iterable<TourRating> tourRatings = service.lookupAll();
        List<TourRating> tourRatingList = toList(tourRatings);
        service.delete(tourRatingList.get(0).getTour().getId(), tourRatingList.get(0).getCustomerId());
        assertEquals(tourRatingList.size()-1, toList(service.lookupAll()).size());

    }

    //UnHappy Path, Tour NOT_A_TOUR_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void deleteException() {
        service.delete(NOT_A_TOUR_ID, 1234);
    }


    //Happy Path to Create a new Tour Rating
    @Test
    public void createNew() {
        //would throw NoSuchElementException if TourRating for TOUR_ID by CUSTOMER_ID already exists
        service.createNew(TOUR_ID, CUSTOMER_ID, 2, "it was fair");

        //Verify New Tour Rating created.
        TourRating newTourRating = service.verifyTourRating(TOUR_ID, CUSTOMER_ID);
        assertThat(newTourRating.getTour().getId(), is(TOUR_ID));
        assertThat(newTourRating.getCustomerId(), is(CUSTOMER_ID));
        assertThat(newTourRating.getScore(), is(2));
        assertThat(newTourRating.getComment(), is ("it was fair"));
    }

    //UnHappy Path, Tour NOT_A_TOUR_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void createNewException() {
        service.createNew(NOT_A_TOUR_ID, CUSTOMER_ID, 2, "it was fair");
    }

    //Happy Path many customers Rate one tour
    @Test
    public void rateMany() {
        List<TourRating> tourRatingList = toList(service.lookupAll());
        int ratings = tourRatingList.size();
        service.rateMany(TOUR_ID, 5, new Integer[]{100, 101, 102});
        assertThat(toList(service.lookupAll()).size(), is(ratings + 3));
    }

    //Unhappy Path, 2nd Invocation would create duplicates in the database, DataIntegrityViolationException thrown
    @Test(expected = DataIntegrityViolationException.class)
    public void rateManyProveRollback() {
        List<TourRating> tourRatingList = toList(service.lookupAll());

        int ratings = tourRatingList.size();
        Integer customers[] = {100, 101, 102};
        service.rateMany(TOUR_ID, 3, customers);
        service.rateMany(TOUR_ID, 3, customers);
    }

    //Happy Path, Update a Tour Rating already in the database
    @Test
    public void update() {
        createNew();
        TourRating tourRating = service.update(TOUR_ID, CUSTOMER_ID, 1, "one");
        assertThat(tourRating.getTour().getId(), is(TOUR_ID));
        assertThat(tourRating.getCustomerId(), is(CUSTOMER_ID));
        assertThat(tourRating.getScore(), is(1));
        assertThat(tourRating.getComment(), is("one"));
    }

    //Unhappy path, no Tour Rating exists for tourId=1 and customer=1
    @Test(expected = NoSuchElementException.class)
    public void updateException() throws Exception {
        service.update(1, 1, 1, "one");
    }

    //Happy Path, Update a Tour Rating already in the database
    @Test
    public void updateSome() {
        createNew();
        TourRating tourRating = service.update(TOUR_ID, CUSTOMER_ID, 1, "one");
        assertThat(tourRating.getTour().getId(), is(TOUR_ID));
        assertThat(tourRating.getCustomerId(), is(CUSTOMER_ID));
        assertThat(tourRating.getScore(), is(1));
        assertThat(tourRating.getComment(), is("one"));
    }

    //Unhappy path, no Tour Rating exists for tourId=1 and customer=1
    @Test(expected = NoSuchElementException.class)
    public void updateSomeException() throws Exception {
        service.update(1, 1, 1, "one");
    }


    //UnHappy Path, Tour NOT_A_TOUR_ID does not exist
    @Test(expected = NoSuchElementException.class)
    public void getAverageScoreException() {
        service.getAverageScore(NOT_A_TOUR_ID); //That tour does not exist
    }
}