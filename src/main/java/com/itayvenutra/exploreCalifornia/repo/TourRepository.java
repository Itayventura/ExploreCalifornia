package com.itayvenutra.exploreCalifornia.repo;

import com.itayvenutra.exploreCalifornia.domain.Tour;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
@Tag(name = "Tour", description = "The Tour API")
public interface TourRepository extends PagingAndSortingRepository<Tour, Integer> {
    @Override
    @RestResource(exported = false)
    <S extends Tour> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends Tour> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @RestResource(exported = false)
    void deleteById(Integer integer);

    @Override
    @RestResource(exported = false)
    void delete(Tour tour);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends Tour> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAll();

    Page<Tour> findByTourPackageCode(@Param("code")String code, Pageable pageable);
}
