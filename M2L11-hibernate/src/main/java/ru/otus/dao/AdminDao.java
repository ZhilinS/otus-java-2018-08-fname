/*
 * Copyright (C) 2018, SEMRUSH CY LTD or it's affiliates
 */
package ru.otus.dao;

import ru.otus.HibernateExecutor;
import ru.otus.SessionConfig;
import ru.otus.dataset.AdminDataSet;
import ru.otus.dataset.HiberUserDataSet;

public final class AdminDao {

    private final HibernateExecutor<AdminDataSet> executor;

    public AdminDao() {
        this(new HibernateExecutor<>(new SessionConfig()));
    }

    public AdminDao(final HibernateExecutor<AdminDataSet> executor) {
        this.executor = executor;
    }

    public void save(final AdminDataSet admin) {
        this.executor.save(admin);
    }

    public AdminDataSet get(final Long id) {
        return this.executor.load(AdminDataSet.class, id);
    }

    public AdminDataSet byUser(final HiberUserDataSet user) {
        return this.executor.byUser(AdminDataSet.class, user.getId());
    }
}
