package com.wan37.mysql.pojo.entity;

import java.util.ArrayList;
import java.util.List;

public class TSceneExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TSceneExample() {
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

        public Criteria andStateIsNull() {
            addCriterion("state is null");
            return (Criteria) this;
        }

        public Criteria andStateIsNotNull() {
            addCriterion("state is not null");
            return (Criteria) this;
        }

        public Criteria andStateEqualTo(Integer value) {
            addCriterion("state =", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotEqualTo(Integer value) {
            addCriterion("state <>", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThan(Integer value) {
            addCriterion("state >", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("state >=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThan(Integer value) {
            addCriterion("state <", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateLessThanOrEqualTo(Integer value) {
            addCriterion("state <=", value, "state");
            return (Criteria) this;
        }

        public Criteria andStateIn(List<Integer> values) {
            addCriterion("state in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotIn(List<Integer> values) {
            addCriterion("state not in", values, "state");
            return (Criteria) this;
        }

        public Criteria andStateBetween(Integer value1, Integer value2) {
            addCriterion("state between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andStateNotBetween(Integer value1, Integer value2) {
            addCriterion("state not between", value1, value2, "state");
            return (Criteria) this;
        }

        public Criteria andNeighborsIsNull() {
            addCriterion("neighbors is null");
            return (Criteria) this;
        }

        public Criteria andNeighborsIsNotNull() {
            addCriterion("neighbors is not null");
            return (Criteria) this;
        }

        public Criteria andNeighborsEqualTo(String value) {
            addCriterion("neighbors =", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsNotEqualTo(String value) {
            addCriterion("neighbors <>", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsGreaterThan(String value) {
            addCriterion("neighbors >", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsGreaterThanOrEqualTo(String value) {
            addCriterion("neighbors >=", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsLessThan(String value) {
            addCriterion("neighbors <", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsLessThanOrEqualTo(String value) {
            addCriterion("neighbors <=", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsLike(String value) {
            addCriterion("neighbors like", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsNotLike(String value) {
            addCriterion("neighbors not like", value, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsIn(List<String> values) {
            addCriterion("neighbors in", values, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsNotIn(List<String> values) {
            addCriterion("neighbors not in", values, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsBetween(String value1, String value2) {
            addCriterion("neighbors between", value1, value2, "neighbors");
            return (Criteria) this;
        }

        public Criteria andNeighborsNotBetween(String value1, String value2) {
            addCriterion("neighbors not between", value1, value2, "neighbors");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsIsNull() {
            addCriterion("game_object_ids is null");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsIsNotNull() {
            addCriterion("game_object_ids is not null");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsEqualTo(String value) {
            addCriterion("game_object_ids =", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsNotEqualTo(String value) {
            addCriterion("game_object_ids <>", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsGreaterThan(String value) {
            addCriterion("game_object_ids >", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsGreaterThanOrEqualTo(String value) {
            addCriterion("game_object_ids >=", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsLessThan(String value) {
            addCriterion("game_object_ids <", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsLessThanOrEqualTo(String value) {
            addCriterion("game_object_ids <=", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsLike(String value) {
            addCriterion("game_object_ids like", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsNotLike(String value) {
            addCriterion("game_object_ids not like", value, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsIn(List<String> values) {
            addCriterion("game_object_ids in", values, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsNotIn(List<String> values) {
            addCriterion("game_object_ids not in", values, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsBetween(String value1, String value2) {
            addCriterion("game_object_ids between", value1, value2, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andGameObjectIdsNotBetween(String value1, String value2) {
            addCriterion("game_object_ids not between", value1, value2, "gameObjectIds");
            return (Criteria) this;
        }

        public Criteria andPlayersIsNull() {
            addCriterion("players is null");
            return (Criteria) this;
        }

        public Criteria andPlayersIsNotNull() {
            addCriterion("players is not null");
            return (Criteria) this;
        }

        public Criteria andPlayersEqualTo(String value) {
            addCriterion("players =", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersNotEqualTo(String value) {
            addCriterion("players <>", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersGreaterThan(String value) {
            addCriterion("players >", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersGreaterThanOrEqualTo(String value) {
            addCriterion("players >=", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersLessThan(String value) {
            addCriterion("players <", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersLessThanOrEqualTo(String value) {
            addCriterion("players <=", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersLike(String value) {
            addCriterion("players like", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersNotLike(String value) {
            addCriterion("players not like", value, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersIn(List<String> values) {
            addCriterion("players in", values, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersNotIn(List<String> values) {
            addCriterion("players not in", values, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersBetween(String value1, String value2) {
            addCriterion("players between", value1, value2, "players");
            return (Criteria) this;
        }

        public Criteria andPlayersNotBetween(String value1, String value2) {
            addCriterion("players not between", value1, value2, "players");
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