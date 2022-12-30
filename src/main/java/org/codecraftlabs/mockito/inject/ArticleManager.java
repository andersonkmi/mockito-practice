package org.codecraftlabs.mockito.inject;

import java.util.Objects;

public class ArticleManager {
    private final User user;
    private final ArticleDatabase articleDatabase;

    public ArticleManager(User user, ArticleDatabase articleDatabase) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(articleDatabase);
        this.user = user;
        this.articleDatabase = articleDatabase;
    }

    public void initialize() {
        articleDatabase.addListener(new ArticleListener());
        articleDatabase.setUser(user);
    }
}
