package com.itayvenutra.exploreCalifornia.repo;

import com.itayvenutra.exploreCalifornia.domain.TourRating;
import com.itayvenutra.exploreCalifornia.domain.TourRatingPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk>  {

    List<TourRating> findByPkTourId(Integer tourId);
    Optional<TourRating> findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);

}