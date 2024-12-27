package com.dw.jdbcapp.repository.template;

import com.dw.jdbcapp.exception.ResourceNotFoundException;
import com.dw.jdbcapp.model.Order;
import com.dw.jdbcapp.repository.iface.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderTemplateRepository implements OrderRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    private final RowMapper<Order> orderRowMapper = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();

            order.setOrderId(rs.getString("주문번호"));
            order.setCustomerId(rs.getString("고객번호"));
            order.setEmployeeId(rs.getString("사원번호"));
            order.setOrderDate(LocalDate.parse(rs.getString("주문일")));
            order.setRequestDate(LocalDate.parse(rs.getString("요청일")));
            order.setShippingDate(LocalDate.parse(rs.getString("발송일")));

            return order;
        }
    };


    @Override
    public List<Order> getAllOrders() {
        String query = "select * from 주문";
        return jdbcTemplate.query(query, orderRowMapper);
    }

    @Override
    public Order getOrderById(String orderNumber) {
        String query = "select * from 주문 where 주문번호 = ?";
        try {
            return jdbcTemplate.queryForObject(query, orderRowMapper, orderNumber);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(
                    "주문번호가 올바르지 않습니다 : " + orderNumber
            );
        }
    }

    @Override
    public List<Order> getOrderByIdAndCustomer(int productNumber, String customerId) {
        String query = "select * from 주문 where 고객번호 = ? and" +
                " 주문번호 in (select 주문번호 from 주문세부 where 제품번호 = ?)";
        return jdbcTemplate.query(query, orderRowMapper, customerId, productNumber);
    }

    @Override
    public int saveOrder(Order order) {
        String query = "insert into 주문(주문번호,고객번호,사원번호,주문일,요청일) " +
                "values(?,?,?,?,?)";
        return jdbcTemplate.update(query,
                order.getOrderId(),
                order.getCustomerId(),
                order.getEmployeeId(),
                order.getOrderDate().toString(),
                order.getRequestDate().toString());
    }

    // 12. 20 - Q4 주문번호와 발송일을 매개변수로 해당 주문의 발송일을 수정하는 API
    @Override
    public String updateOrderWithShippingDate(String id, String date) {
        String query = "update 주문 set 발송일 = ? where 주문번호 = ?";
        jdbcTemplate.update(query, date, id);
        return "주문번호 : " + id + " 의 발송일이 " + date + "로 수정되었습니다.";
    }

    // 12. 20 - Q5 도시별로 주문금액합 결과를 내림차순 정렬하여 조회하는 API
    @Override
    public List<Map<String, Object>> getTopCitiesByTotalOrderAmount(int limit) {
        String query = "select 도시, sum(단가*주문수량) as 주문금액합 " +
                "from 주문 " +
                "inner join 고객 " +
                "on 주문.고객번호 = 고객.고객번호 " +
                "inner join 주문세부 " +
                "on 주문.주문번호 = 주문세부.주문번호 " +
                "group by 도시 " +
                "order by 주문금액합 desc " +
                "limit ?";
//        return jdbcTemplate.query(query, (rs, rowNum) -> {
//            Map<String, Double> map = new HashMap<>();
//            map.put(rs.getString("도시"),
//                    rs.getDouble("금액합"));
//            return map;
//        }, limit);
        return jdbcTemplate.queryForList(query, limit);
    }

    // 12. 21 - Q6 도시를 매개변수로 해당 도시의 년도별 주문건수를 조회하는 API
    @Override
    public List<Map<String, Object>> getOrderCountByYearForCity(String city) {
        String query = "select year(주문일) as 주문년도, " +
                "count(*) as 주문건수 " +
                "from 주문 " +
                "where 고객번호 in (select 고객번호 from 고객 where 도시 = ?) " +
                "group by 주문년도 " +
                "order by 주문년도";
        return jdbcTemplate.queryForList(query, city);
    }

    // 12. 20 - Q5 도시별로 주문금액합 결과를 내림차순 정렬하여 조회하는 API
    // 람다식 사용
    public List<Map<String, Double>> getTopCitiesByTotalOrderAmount2(int limit) {
        String query = "select 도시, sum(단가*주문수량) as 주문금액합 " +
                "from 주문 " +
                "inner join 고객 " +
                "on 주문.고객번호 = 고객.고객번호 " +
                "inner join 주문세부 " +
                "on 주문.주문번호 = 주문세부.주문번호 " +
                "group by 도시 " +
                "order by 주문금액합 desc " +
                "limit ?";
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Map<String, Double> map = new HashMap<>();
            map.put(rs.getString("도시"),
                    rs.getDouble("금액합"));
            return map;
        }, limit);
    }
}
