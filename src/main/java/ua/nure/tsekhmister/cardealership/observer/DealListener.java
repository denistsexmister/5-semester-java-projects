package ua.nure.tsekhmister.cardealership.observer;

import ua.nure.tsekhmister.cardealership.entity.Deal;

public interface DealListener {
    void onDealCreated(Deal deal);
}
