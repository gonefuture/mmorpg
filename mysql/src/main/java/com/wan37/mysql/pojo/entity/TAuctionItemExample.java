package com.wan37.mysql.pojo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TAuctionItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TAuctionItemExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andAuctionIdIsNull() {
            addCriterion("auction_id is null");
            return (Criteria) this;
        }

        public Criteria andAuctionIdIsNotNull() {
            addCriterion("auction_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuctionIdEqualTo(Integer value) {
            addCriterion("auction_id =", value, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdNotEqualTo(Integer value) {
            addCriterion("auction_id <>", value, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdGreaterThan(Integer value) {
            addCriterion("auction_id >", value, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("auction_id >=", value, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdLessThan(Integer value) {
            addCriterion("auction_id <", value, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdLessThanOrEqualTo(Integer value) {
            addCriterion("auction_id <=", value, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdIn(List<Integer> values) {
            addCriterion("auction_id in", values, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdNotIn(List<Integer> values) {
            addCriterion("auction_id not in", values, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdBetween(Integer value1, Integer value2) {
            addCriterion("auction_id between", value1, value2, "auctionId");
            return (Criteria) this;
        }

        public Criteria andAuctionIdNotBetween(Integer value1, Integer value2) {
            addCriterion("auction_id not between", value1, value2, "auctionId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdIsNull() {
            addCriterion("thing_info_id is null");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdIsNotNull() {
            addCriterion("thing_info_id is not null");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdEqualTo(Integer value) {
            addCriterion("thing_info_id =", value, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdNotEqualTo(Integer value) {
            addCriterion("thing_info_id <>", value, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdGreaterThan(Integer value) {
            addCriterion("thing_info_id >", value, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("thing_info_id >=", value, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdLessThan(Integer value) {
            addCriterion("thing_info_id <", value, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdLessThanOrEqualTo(Integer value) {
            addCriterion("thing_info_id <=", value, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdIn(List<Integer> values) {
            addCriterion("thing_info_id in", values, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdNotIn(List<Integer> values) {
            addCriterion("thing_info_id not in", values, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdBetween(Integer value1, Integer value2) {
            addCriterion("thing_info_id between", value1, value2, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andThingInfoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("thing_info_id not between", value1, value2, "thingInfoId");
            return (Criteria) this;
        }

        public Criteria andNumberIsNull() {
            addCriterion("number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(Integer value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(Integer value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(Integer value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(Integer value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(Integer value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<Integer> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<Integer> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(Integer value1, Integer value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("number not between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceIsNull() {
            addCriterion("auction_price is null");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceIsNotNull() {
            addCriterion("auction_price is not null");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceEqualTo(Integer value) {
            addCriterion("auction_price =", value, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceNotEqualTo(Integer value) {
            addCriterion("auction_price <>", value, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceGreaterThan(Integer value) {
            addCriterion("auction_price >", value, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceGreaterThanOrEqualTo(Integer value) {
            addCriterion("auction_price >=", value, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceLessThan(Integer value) {
            addCriterion("auction_price <", value, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceLessThanOrEqualTo(Integer value) {
            addCriterion("auction_price <=", value, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceIn(List<Integer> values) {
            addCriterion("auction_price in", values, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceNotIn(List<Integer> values) {
            addCriterion("auction_price not in", values, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceBetween(Integer value1, Integer value2) {
            addCriterion("auction_price between", value1, value2, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionPriceNotBetween(Integer value1, Integer value2) {
            addCriterion("auction_price not between", value1, value2, "auctionPrice");
            return (Criteria) this;
        }

        public Criteria andAuctionModeIsNull() {
            addCriterion("auction_mode is null");
            return (Criteria) this;
        }

        public Criteria andAuctionModeIsNotNull() {
            addCriterion("auction_mode is not null");
            return (Criteria) this;
        }

        public Criteria andAuctionModeEqualTo(Integer value) {
            addCriterion("auction_mode =", value, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeNotEqualTo(Integer value) {
            addCriterion("auction_mode <>", value, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeGreaterThan(Integer value) {
            addCriterion("auction_mode >", value, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("auction_mode >=", value, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeLessThan(Integer value) {
            addCriterion("auction_mode <", value, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeLessThanOrEqualTo(Integer value) {
            addCriterion("auction_mode <=", value, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeIn(List<Integer> values) {
            addCriterion("auction_mode in", values, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeNotIn(List<Integer> values) {
            addCriterion("auction_mode not in", values, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeBetween(Integer value1, Integer value2) {
            addCriterion("auction_mode between", value1, value2, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andAuctionModeNotBetween(Integer value1, Integer value2) {
            addCriterion("auction_mode not between", value1, value2, "auctionMode");
            return (Criteria) this;
        }

        public Criteria andPushTimeIsNull() {
            addCriterion("push_time is null");
            return (Criteria) this;
        }

        public Criteria andPushTimeIsNotNull() {
            addCriterion("push_time is not null");
            return (Criteria) this;
        }

        public Criteria andPushTimeEqualTo(Date value) {
            addCriterion("push_time =", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotEqualTo(Date value) {
            addCriterion("push_time <>", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThan(Date value) {
            addCriterion("push_time >", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("push_time >=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThan(Date value) {
            addCriterion("push_time <", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeLessThanOrEqualTo(Date value) {
            addCriterion("push_time <=", value, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeIn(List<Date> values) {
            addCriterion("push_time in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotIn(List<Date> values) {
            addCriterion("push_time not in", values, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeBetween(Date value1, Date value2) {
            addCriterion("push_time between", value1, value2, "pushTime");
            return (Criteria) this;
        }

        public Criteria andPushTimeNotBetween(Date value1, Date value2) {
            addCriterion("push_time not between", value1, value2, "pushTime");
            return (Criteria) this;
        }

        public Criteria andBiddersIsNull() {
            addCriterion("bidders is null");
            return (Criteria) this;
        }

        public Criteria andBiddersIsNotNull() {
            addCriterion("bidders is not null");
            return (Criteria) this;
        }

        public Criteria andBiddersEqualTo(String value) {
            addCriterion("bidders =", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersNotEqualTo(String value) {
            addCriterion("bidders <>", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersGreaterThan(String value) {
            addCriterion("bidders >", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersGreaterThanOrEqualTo(String value) {
            addCriterion("bidders >=", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersLessThan(String value) {
            addCriterion("bidders <", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersLessThanOrEqualTo(String value) {
            addCriterion("bidders <=", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersLike(String value) {
            addCriterion("bidders like", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersNotLike(String value) {
            addCriterion("bidders not like", value, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersIn(List<String> values) {
            addCriterion("bidders in", values, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersNotIn(List<String> values) {
            addCriterion("bidders not in", values, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersBetween(String value1, String value2) {
            addCriterion("bidders between", value1, value2, "bidders");
            return (Criteria) this;
        }

        public Criteria andBiddersNotBetween(String value1, String value2) {
            addCriterion("bidders not between", value1, value2, "bidders");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}