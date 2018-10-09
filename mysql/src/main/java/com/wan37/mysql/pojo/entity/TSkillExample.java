package com.wan37.mysql.pojo.entity;

import java.util.ArrayList;
import java.util.List;

public class TSkillExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TSkillExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeIsNull() {
            addCriterion("skills_type is null");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeIsNotNull() {
            addCriterion("skills_type is not null");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeEqualTo(Integer value) {
            addCriterion("skills_type =", value, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeNotEqualTo(Integer value) {
            addCriterion("skills_type <>", value, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeGreaterThan(Integer value) {
            addCriterion("skills_type >", value, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("skills_type >=", value, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeLessThan(Integer value) {
            addCriterion("skills_type <", value, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeLessThanOrEqualTo(Integer value) {
            addCriterion("skills_type <=", value, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeIn(List<Integer> values) {
            addCriterion("skills_type in", values, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeNotIn(List<Integer> values) {
            addCriterion("skills_type not in", values, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeBetween(Integer value1, Integer value2) {
            addCriterion("skills_type between", value1, value2, "skillsType");
            return (Criteria) this;
        }

        public Criteria andSkillsTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("skills_type not between", value1, value2, "skillsType");
            return (Criteria) this;
        }

        public Criteria andCdIsNull() {
            addCriterion("cd is null");
            return (Criteria) this;
        }

        public Criteria andCdIsNotNull() {
            addCriterion("cd is not null");
            return (Criteria) this;
        }

        public Criteria andCdEqualTo(Integer value) {
            addCriterion("cd =", value, "cd");
            return (Criteria) this;
        }

        public Criteria andCdNotEqualTo(Integer value) {
            addCriterion("cd <>", value, "cd");
            return (Criteria) this;
        }

        public Criteria andCdGreaterThan(Integer value) {
            addCriterion("cd >", value, "cd");
            return (Criteria) this;
        }

        public Criteria andCdGreaterThanOrEqualTo(Integer value) {
            addCriterion("cd >=", value, "cd");
            return (Criteria) this;
        }

        public Criteria andCdLessThan(Integer value) {
            addCriterion("cd <", value, "cd");
            return (Criteria) this;
        }

        public Criteria andCdLessThanOrEqualTo(Integer value) {
            addCriterion("cd <=", value, "cd");
            return (Criteria) this;
        }

        public Criteria andCdIn(List<Integer> values) {
            addCriterion("cd in", values, "cd");
            return (Criteria) this;
        }

        public Criteria andCdNotIn(List<Integer> values) {
            addCriterion("cd not in", values, "cd");
            return (Criteria) this;
        }

        public Criteria andCdBetween(Integer value1, Integer value2) {
            addCriterion("cd between", value1, value2, "cd");
            return (Criteria) this;
        }

        public Criteria andCdNotBetween(Integer value1, Integer value2) {
            addCriterion("cd not between", value1, value2, "cd");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionIsNull() {
            addCriterion("mp_consumption is null");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionIsNotNull() {
            addCriterion("mp_consumption is not null");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionEqualTo(Long value) {
            addCriterion("mp_consumption =", value, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionNotEqualTo(Long value) {
            addCriterion("mp_consumption <>", value, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionGreaterThan(Long value) {
            addCriterion("mp_consumption >", value, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionGreaterThanOrEqualTo(Long value) {
            addCriterion("mp_consumption >=", value, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionLessThan(Long value) {
            addCriterion("mp_consumption <", value, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionLessThanOrEqualTo(Long value) {
            addCriterion("mp_consumption <=", value, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionIn(List<Long> values) {
            addCriterion("mp_consumption in", values, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionNotIn(List<Long> values) {
            addCriterion("mp_consumption not in", values, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionBetween(Long value1, Long value2) {
            addCriterion("mp_consumption between", value1, value2, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andMpConsumptionNotBetween(Long value1, Long value2) {
            addCriterion("mp_consumption not between", value1, value2, "mpConsumption");
            return (Criteria) this;
        }

        public Criteria andHpLoseIsNull() {
            addCriterion("hp_lose is null");
            return (Criteria) this;
        }

        public Criteria andHpLoseIsNotNull() {
            addCriterion("hp_lose is not null");
            return (Criteria) this;
        }

        public Criteria andHpLoseEqualTo(Long value) {
            addCriterion("hp_lose =", value, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseNotEqualTo(Long value) {
            addCriterion("hp_lose <>", value, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseGreaterThan(Long value) {
            addCriterion("hp_lose >", value, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseGreaterThanOrEqualTo(Long value) {
            addCriterion("hp_lose >=", value, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseLessThan(Long value) {
            addCriterion("hp_lose <", value, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseLessThanOrEqualTo(Long value) {
            addCriterion("hp_lose <=", value, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseIn(List<Long> values) {
            addCriterion("hp_lose in", values, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseNotIn(List<Long> values) {
            addCriterion("hp_lose not in", values, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseBetween(Long value1, Long value2) {
            addCriterion("hp_lose between", value1, value2, "hpLose");
            return (Criteria) this;
        }

        public Criteria andHpLoseNotBetween(Long value1, Long value2) {
            addCriterion("hp_lose not between", value1, value2, "hpLose");
            return (Criteria) this;
        }

        public Criteria andLevelIsNull() {
            addCriterion("level is null");
            return (Criteria) this;
        }

        public Criteria andLevelIsNotNull() {
            addCriterion("level is not null");
            return (Criteria) this;
        }

        public Criteria andLevelEqualTo(Integer value) {
            addCriterion("level =", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotEqualTo(Integer value) {
            addCriterion("level <>", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThan(Integer value) {
            addCriterion("level >", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("level >=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThan(Integer value) {
            addCriterion("level <", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelLessThanOrEqualTo(Integer value) {
            addCriterion("level <=", value, "level");
            return (Criteria) this;
        }

        public Criteria andLevelIn(List<Integer> values) {
            addCriterion("level in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotIn(List<Integer> values) {
            addCriterion("level not in", values, "level");
            return (Criteria) this;
        }

        public Criteria andLevelBetween(Integer value1, Integer value2) {
            addCriterion("level between", value1, value2, "level");
            return (Criteria) this;
        }

        public Criteria andLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("level not between", value1, value2, "level");
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