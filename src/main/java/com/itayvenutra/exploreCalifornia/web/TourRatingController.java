package com.itayvenutra.exploreCalifornia.web;

import com.itayvenutra.exploreCalifornia.domain.Tour;
import com.itayvenutra.exploreCalifornia.domain.TourRating;
import com.itayvenutra.exploreCalifornia.repo.TourRatingRepository;
import com.itayvenutra.exploreCalifornia.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository,
                                TourRepository tourRepository){
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    protected TourRatingController(){

    }

    @GetMapping
    public Page<TourRating> getRatings(@PathVariable(value = "tourId") String tourId,
                                       Pageable pageable){
        return tourRatingRepository.findByTourId(tourId, pageable);
    }

    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        return Map.of("average",tourRatingRepository.findByTourId(tourId).stream()
                .mapToInt(TourRating::getScore).average()
                .orElseThrow(() ->
                        new NoSuchElementException("Tour has no Ratings")));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") String tourId,
                                 @RequestBody @Validated TourRating tourRating) {
        verifyTour(tourId);
        tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(),
                tourRating.getScore(), tourRating.getComment()));
    }

    @PutMapping
    public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId,
                                    @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }

    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId,
                                      @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        if (tourRating.getScore() != null) {
            rating.setScore(tourRating.getScore());
        }
        if (tourRating.getComment() != null) {
            rating.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(rating);
    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") String tourId,
                       @PathVariable(value = "customerId") int customerId) {
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                        + tourId + " for customer" + customerId));
    }

    private void verifyTour(String tourId) throws NoSuchElementException {
        if (!tourRepository.existsById(tourId)) {
            throw new NoSuchElementException("Tour does not exist " + tourId);
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();

    }
}
