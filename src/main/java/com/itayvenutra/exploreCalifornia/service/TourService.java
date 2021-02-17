package com.itayvenutra.exploreCalifornia.service;

import com.itayvenutra.exploreCalifornia.domain.Tour;
import com.itayvenutra.exploreCalifornia.domain.TourPackage;
import com.itayvenutra.exploreCalifornia.repo.TourPackageRepository;
import com.itayvenutra.exploreCalifornia.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TourService {
    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository,
                       TourPackageRepository tourPackageRepository){
        this.tourPackageRepository = tourPackageRepository;
        this.tourRepository = tourRepository;
    }

    public Tour createTour(String title, String tourPackageName, Map<String, String> details) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName).orElseThrow(() ->
                new RuntimeException("Tour package does not exist: " + tourPackageName));
        return tourRepository.save(new Tour(title, tourPackage, details));
    }

    public long total() {
        return tourRepository.count();
    }



}
