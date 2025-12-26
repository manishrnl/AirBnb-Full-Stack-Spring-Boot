package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Hotel;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.HotelMinPrice;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.HotelMinPriceRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.HotelRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.InventoryRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.strategy.PricingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PricingUpdateService {
//Schedular to update inventory and hotel min Price every hour


    private final HotelRepository hotelRepository;
    private final InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;


   // @Scheduled(cron = "40 0/14 * * * *")// Every 14 minutes and 40 sec , this method will
    // run automatically
  //  @Scheduled(cron = "1 */10 * * * *")// Every 10 minutes  this method will run
    // automatically
    public void updatePrices() {
        int page = 0;
        int batchSize = 100;

        while (true) {
            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page, batchSize));
            if (hotelPage.isEmpty()) {
                break;
            }
            hotelPage.getContent().forEach(hotel -> updateHotelPrices(hotel));

            page++;
        }
    }


    private void updateHotelPrices(Hotel hotel) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);

        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel, startDate, endDate);
        updateInventoryPrice(inventoryList);
        updateHotelMinPrice(hotel, inventoryList, startDate, endDate);


    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate startDate, LocalDate endDate) {
        //Compute min price per day for hotel
        Map<LocalDate, BigDecimal> dailyMinPrices = inventoryList.stream()
                .collect(Collectors.groupingBy(
                        Inventory::getDate,
                        Collectors.mapping(Inventory::getPrice,
                                Collectors.minBy(Comparator.naturalOrder()))
                ))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().orElse(BigDecimal.ZERO)));

        //Prepare HotelPrice Entities in Bulk
        List<HotelMinPrice> hotelMinPrices = new ArrayList<>();
        dailyMinPrices.forEach((date, price) -> {
            HotelMinPrice hotelMinPrice = hotelMinPriceRepository.findByHotelAndDate(hotel, date)
                    .orElse(new HotelMinPrice(hotel, date));
            hotelMinPrice.setPrice(price);
            hotelMinPrices.add(hotelMinPrice);
        });


        //Save All Hotel
        hotelMinPriceRepository.saveAll(hotelMinPrices);

    }

    private void updateInventoryPrice(List<Inventory> inventoryList) {
        inventoryList.forEach(inventory -> {
            BigDecimal price = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(price);
        });
        inventoryRepository.saveAll(inventoryList);
    }

}
