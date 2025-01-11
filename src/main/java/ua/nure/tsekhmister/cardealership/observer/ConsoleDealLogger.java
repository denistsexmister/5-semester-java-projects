package ua.nure.tsekhmister.cardealership.observer;

import ua.nure.tsekhmister.cardealership.entity.Deal;

public class ConsoleDealLogger implements DealListener {

    @Override
    public void onDealCreated(Deal deal) {
        StringBuilder sb = new StringBuilder("Deal created between user with id ");

        sb.append(deal.getSellerId()).append(" and user with id ");
        sb.append(deal.getBuyerId()).append(" at ").append(deal.getDealDate());
        sb.append(" for ").append(deal.getCost()).append(" cost");

        System.out.println(sb);
    }
}
