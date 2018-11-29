package com.wan37.mysql.pojo.entity;

import java.util.ArrayList;
import java.util.List;

public class TBagExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TBagExample() {
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

        public Criteria andPlayerIdIsNull() {
            addCriterion("player_Id is null");
            return (Criteria) this;
        }

        public Criteria andPlayerIdIsNotNull() {
            addCriterion("player_Id is not null");
            return (Criteria) this;
        }

        public Criteria andPlayerIdEqualTo(Long value) {
            addCriterion("player_Id =", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdNotEqualTo(Long value) {
            addCriterion("player_Id <>", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdGreaterThan(Long value) {
            addCriterion("player_Id >", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdGreaterThanOrEqualTo(Long value) {
            addCriterion("player_Id >=", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdLessThan(Long value) {
            addCriterion("player_Id <", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdLessThanOrEqualTo(Long value) {
            addCriterion("player_Id <=", value, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdIn(List<Long> values) {
            addCriterion("player_Id in", values, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdNotIn(List<Long> values) {
            addCriterion("player_Id not in", values, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdBetween(Long value1, Long value2) {
            addCriterion("player_Id between", value1, value2, "playerId");
            return (Criteria) this;
        }

        public Criteria andPlayerIdNotBetween(Long value1, Long value2) {
            addCriterion("player_Id not between", value1, value2, "playerId");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andBagSizeIsNull() {
            addCriterion("bag_size is null");
            return (Criteria) this;
        }

        public Criteria andBagSizeIsNotNull() {
            addCriterion("bag_size is not null");
            return (Criteria) this;
        }

        public Criteria andBagSizeEqualTo(Integer value) {
            addCriterion("bag_size =", value, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeNotEqualTo(Integer value) {
            addCriterion("bag_size <>", value, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeGreaterThan(Integer value) {
            addCriterion("bag_size >", value, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("bag_size >=", value, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeLessThan(Integer value) {
            addCriterion("bag_size <", value, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeLessThanOrEqualTo(Integer value) {
            addCriterion("bag_size <=", value, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeIn(List<Integer> values) {
            addCriterion("bag_size in", values, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeNotIn(List<Integer> values) {
            addCriterion("bag_size not in", values, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeBetween(Integer value1, Integer value2) {
            addCriterion("bag_size between", value1, value2, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagSizeNotBetween(Integer value1, Integer value2) {
            addCriterion("bag_size not between", value1, value2, "bagSize");
            return (Criteria) this;
        }

        public Criteria andBagNameIsNull() {
            addCriterion("bag_name is null");
            return (Criteria) this;
        }

        public Criteria andBagNameIsNotNull() {
            addCriterion("bag_name is not null");
            return (Criteria) this;
        }

        public Criteria andBagNameEqualTo(String value) {
            addCriterion("bag_name =", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameNotEqualTo(String value) {
            addCriterion("bag_name <>", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameGreaterThan(String value) {
            addCriterion("bag_name >", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameGreaterThanOrEqualTo(String value) {
            addCriterion("bag_name >=", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameLessThan(String value) {
            addCriterion("bag_name <", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameLessThanOrEqualTo(String value) {
            addCriterion("bag_name <=", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameLike(String value) {
            addCriterion("bag_name like", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameNotLike(String value) {
            addCriterion("bag_name not like", value, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameIn(List<String> values) {
            addCriterion("bag_name in", values, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameNotIn(List<String> values) {
            addCriterion("bag_name not in", values, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameBetween(String value1, String value2) {
            addCriterion("bag_name between", value1, value2, "bagName");
            return (Criteria) this;
        }

        public Criteria andBagNameNotBetween(String value1, String value2) {
            addCriterion("bag_name not between", value1, value2, "bagName");
            return (Criteria) this;
        }

        public Criteria andGoodsIsNull() {
            addCriterion("goods is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIsNotNull() {
            addCriterion("goods is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsEqualTo(String value) {
            addCriterion("goods =", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotEqualTo(String value) {
            addCriterion("goods <>", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsGreaterThan(String value) {
            addCriterion("goods >", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsGreaterThanOrEqualTo(String value) {
            addCriterion("goods >=", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLessThan(String value) {
            addCriterion("goods <", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLessThanOrEqualTo(String value) {
            addCriterion("goods <=", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLike(String value) {
            addCriterion("goods like", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotLike(String value) {
            addCriterion("goods not like", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsIn(List<String> values) {
            addCriterion("goods in", values, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotIn(List<String> values) {
            addCriterion("goods not in", values, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsBetween(String value1, String value2) {
            addCriterion("goods between", value1, value2, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotBetween(String value1, String value2) {
            addCriterion("goods not between", value1, value2, "goods");
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