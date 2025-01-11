package ua.nure.tsekhmister.cardealership.dao.mysql;

import ua.nure.tsekhmister.cardealership.dao.DealDAO;
import ua.nure.tsekhmister.cardealership.entity.*;
import ua.nure.tsekhmister.cardealership.observer.DealListener;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MySqlDealDAO implements DealDAO {
    private final List<DealListener> dealListeners = new ArrayList<>();

    public void addDealListener(DealListener dealListener) {
        dealListeners.add(dealListener);
    }

    private void notifyDealListeners(Deal deal) {
        for (DealListener listener : dealListeners) {
            listener.onDealCreated(deal);
        }
    }

    @Override
    public Deal addDeal(User seller, User buyer, CarOnSale car) throws SQLException {
        Connection conn = null;
        try {
            conn = MySqlDAOFactory.getConnection(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            if (Objects.equals(seller.getId(), buyer.getId())) {
                throw new SQLException("Seller and buyer id can't be equal");
            }
            deleteCarFromSale(conn, car);
            Deal deal = addDeal(conn, seller, buyer, car);
            addSoldCar(conn, car, deal);

            notifyDealListeners(deal);

            conn.commit();
            return deal;
        } catch (SQLException e) {
            MySqlDAOFactory.rollback(conn);
            throw new SQLException(e);
        } finally {
            MySqlDAOFactory.close(conn);
        }
    }

    @Override
    public List<Deal> getDeals() throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "select deal_id, seller_id, buyer_id, deal_date, cost from deal;")) {
            List<Deal> deals = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    deals.add(mapDeal(rs));
                }

                return deals;
            }
        }
    }

    @Override
    public List<Deal> getDealsWhereSeller(User user) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement("select deal_id, seller_id, buyer_id, deal_date, cost from deal " +
                "where seller_id = ?")) {
            ps.setLong(1, user.getId());
            List<Deal> deals = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    deals.add(mapDeal(rs));
                }

                return deals;
            }
        }
    }

    @Override
    public List<Deal> getDealsWhereBuyer(User user) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("select deal_id, seller_id, buyer_id, deal_date, cost from deal " +
                     "where buyer_id = ?")) {
            ps.setLong(1, user.getId());
            List<Deal> deals = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    deals.add(mapDeal(rs));
                }

                return deals;
            }
        }
    }

    @Override
    public List<Deal> getDealsSinceDate(LocalDateTime localDateTime) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "select deal_id, seller_id, buyer_id, deal_date, cost from deal where deal_date > ?;")) {
            ps.setString(1, localDateTime.toString());
            List<Deal> deals = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    deals.add(mapDeal(rs));
                }

                return deals;
            }
        }
    }

    @Override
    public List<Deal> getDealsWhereCostIsBiggerThan(BigDecimal price) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "select deal_id, seller_id, buyer_id, deal_date, cost from deal where cost > ?;")) {
            ps.setBigDecimal(1, price);
            List<Deal> deals = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    deals.add(mapDeal(rs));
                }

                return deals;
            }
        }
    }

    @Override
    public SoldCar getSoldCarByDeal(Deal deal) throws SQLException {
        try (Connection conn = MySqlDAOFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(
                "select vin, deal_id, brand, production_year, engine_power" +
                        " from sold_car where deal_id = ?")) {
            ps.setLong(1, deal.getDealId());
            try (ResultSet rs = ps.executeQuery()) {
                SoldCar soldCar = null;
                if (rs.next()) {
                    soldCar = mapSoldCar(rs);
                }

                return soldCar;
            }
        }
    }

    private SoldCar mapSoldCar(ResultSet rs) throws SQLException {
        SoldCar soldCar = new SoldCar();

        soldCar.setVin(rs.getString("vin"));
        soldCar.setDealId(rs.getLong("deal_id"));
        soldCar.setBrand(rs.getString("brand"));
        soldCar.setProductionYear(Year.of(rs.getDate("production_year").toLocalDate().getYear()));
        soldCar.setEnginePower(rs.getBigDecimal("engine_power"));

        return soldCar;
    }

    private Deal mapDeal(ResultSet rs) throws SQLException {
        Deal deal = new Deal();

        deal.setDealId(rs.getLong("deal_id"));
        deal.setSellerId(rs.getLong("seller_id"));
        deal.setBuyerId((rs.getLong("buyer_id")));
        deal.setDealDate(rs.getTimestamp("deal_date").toLocalDateTime());
        deal.setCost(rs.getBigDecimal("cost"));

        return deal;
    }

    private void addSoldCar(Connection conn, Car car, Deal deal) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO sold_car(vin, deal_id, brand, production_year, engine_power) VALUES (?, ?, ?, ?, ?);")) {
            ps.setString(1, car.getVin());
            ps.setLong(2, deal.getDealId());
            ps.setString(3, car.getBrand());
            ps.setString(4, car.getProductionYear().toString());
            ps.setBigDecimal(5, car.getEnginePower());
            ps.executeUpdate();
        }
    }

    private Deal addDeal(Connection conn, User seller, User buyer, CarOnSale car) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "insert into deal(seller_id, buyer_id, deal_date, cost) values (?, ?, default, ?);",
                Statement.RETURN_GENERATED_KEYS)) {
            Deal deal = new Deal();
            ps.setLong(1, seller.getId());
            ps.setLong(2, buyer.getId());
            ps.setBigDecimal(3, car.getPrice());

            deal.setSellerId(seller.getId());
            deal.setBuyerId(buyer.getId());
            deal.setCost(car.getPrice());

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    deal.setDealId(rs.getLong(1));
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement("Select deal_date from deal where deal_id = ?")) {
                ps2.setLong(1, deal.getDealId());

                try (ResultSet rs2 = ps2.executeQuery()) {
                    rs2.next();
                    deal.setDealDate(rs2.getTimestamp("deal_date").toLocalDateTime());
                }
            }
            return deal;
        }
    }

    private void deleteCarFromSale(Connection conn, CarOnSale car) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM car_on_sale WHERE vin = ?")) {
            ps.setString(1, car.getVin());

            ps.executeUpdate();
        }
    }
}
