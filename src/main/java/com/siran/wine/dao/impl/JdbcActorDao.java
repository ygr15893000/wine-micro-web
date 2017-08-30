package com.siran.wine.dao.impl;

import com.siran.wine.model.Actor;
import com.siran.wine.model.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by guangrongyang on 17/6/9.
 */
public class JdbcActorDao {
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertActor;
    private SimpleJdbcInsert insertActor2;


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.insertActor = new SimpleJdbcInsert(dataSource).withTableName("t_actor");
        this.insertActor2 = new SimpleJdbcInsert(dataSource)
                .withTableName("t_actor")
                .usingGeneratedKeyColumns("id");

    }


    /**
     * select for Map
     *
     * @return
     */
    public List<Map<String, Object>> getList() {
        return this.jdbcTemplate.queryForList("select * from mytable");
    }

    /**
     * 返回一个对象
     */
    Actor actor = this.jdbcTemplate.queryForObject(
            "select first_name, last_name from t_actor where id = ?",
            new Object[]{1212L},
            new RowMapper<Actor>() {
                public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Actor actor = new Actor();
                    actor.setFirstName(rs.getString("first_name"));
                    actor.setLastName(rs.getString("last_name"));
                    return actor;
                }
            });
    /**
     * 对象 List 方式一
     *
     * @return
     */
    List<Actor> actors = this.jdbcTemplate.query(
            "select first_name, last_name from t_actor",
            new RowMapper<Actor>() {
                public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Actor actor = new Actor();
                    actor.setFirstName(rs.getString("first_name"));
                    actor.setLastName(rs.getString("last_name"));
                    return actor;
                }
            });

    /**
     * 对象 List 方式二
     *
     * @return
     */
    public List<TUser
            > findAll4() {
        final String sql = "select id, email, username from t_user";

        return jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<TUser>(TUser.class));
    }

    /**
     * select queryForObject
     *
     * @return
     */
    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count(*) from mytable", Integer.class);
    }

    public String getName() {
        return this.jdbcTemplate.queryForObject("select name from mytable", String.class);
    }


    /**
     * create table mytable
     */
    public void doExecute() {
        this.jdbcTemplate.execute("create table mytable (id integer, name varchar(100))");
    }


    /**
     * update
     *
     * @param orderId
     * @param pct
     */
    public void updateShippingCharge(long orderId, long pct) {
        // use the prepared JdbcTemplate for this update
        this.jdbcTemplate.update("update orders" +
                " set shipping_charge = shipping_charge * ? / 100" +
                " where id = ?", pct, orderId);
    }

    /**
     * update
     */
    public void update() {
        this.jdbcTemplate.update(
                "insert into t_actor (first_name, last_name) values (?, ?)",
                "Leonor", "Watling");
        this.jdbcTemplate.update(
                "update t_actor set last_name = ? where id = ?",
                "Banjo", 5276L);
        long actorId = 1;
        this.jdbcTemplate.update(
                "delete from actor where id = ?",
                Long.valueOf(actorId));
    }

    /**
     * 批量update
     *
     * @param actors
     * @return
     */
    public int[] batchUpdate(final List<Actor> actors) {
        List<Object[]> batch = new ArrayList<Object[]>();
        for (Actor actor : actors) {
            Object[] values = new Object[]{
                    actor.getFirstName(),
                    actor.getLastName(),
                    actor.getId()};
            batch.add(values);
        }
        int[] updateCounts = jdbcTemplate.batchUpdate(
                "update t_actor set first_name = ?, last_name = ? where id = ?",
                batch);
        return updateCounts;
    }

    /**
     * 批量操作 每批次限定 100 条
     *
     * @param actors
     * @return
     */

    public int[][] batchUpdate(final Collection<Actor> actors) {
        int[][] updateCounts = jdbcTemplate.batchUpdate(
                "update t_actor set first_name = ?, last_name = ? where id = ?",
                actors,
                100,
                new ParameterizedPreparedStatementSetter<Actor>() {
                    public void setValues(PreparedStatement ps, Actor argument) throws SQLException {
                        ps.setString(1, argument.getFirstName());
                        ps.setString(2, argument.getLastName());
                        ps.setLong(3, argument.getId().longValue());
                    }
                });
        return updateCounts;
    }


    /**
     * Inserting data using SimpleJdbcInsert
     *
     * @param actor
     */
    public void add(Actor actor) {
        Map<String, Object> parameters = new HashMap<String, Object>(3);
        parameters.put("id", actor.getId());
        parameters.put("first_name", actor.getFirstName());
        parameters.put("last_name", actor.getLastName());
        insertActor.execute(parameters);
    }

    /**
     * Retrieving auto-generated keys using SimpleJdbcInsert
     *
     * @param actor
     */
    public void add4ReturnKey(Actor actor) {
        Map<String, Object> parameters = new HashMap<String, Object>(2);
        parameters.put("first_name", actor.getFirstName());
        parameters.put("last_name", actor.getLastName());
        Number newId = insertActor.executeAndReturnKey(parameters);
        actor.setId(newId.longValue());
    }

    /**
     * update
     *
     * @param id
     * @param name
     */
    public void setName(int id, String name) {
        this.jdbcTemplate.update("update mytable set name = ? where id = ?", name, id);
    }














    //********************* NamedParameterJdbcTemplate
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource2(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public int countOfActorsByFirstName(String firstName) {

        String sql = "select count(*) from T_ACTOR where first_name = :first_name";

        SqlParameterSource namedParameters = new MapSqlParameterSource("first_name", firstName);

        return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public int countOfActorsByFirstName2(String firstName) {

        String sql = "select count(*) from T_ACTOR where first_name = :first_name";

        Map<String, String> namedParameters = Collections.singletonMap("first_name", firstName);

        return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    public int countOfActors(Actor exampleActor) {

        // notice how the named parameters match the properties of the above 'Actor' class
        String sql = "select count(*) from T_ACTOR where first_name = :firstName and last_name = :lastName";

        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(exampleActor);

        return this.namedParameterJdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }
}