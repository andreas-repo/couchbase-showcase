package application.dao.impl;

import application.dao.CouchbaseCategoryDAO;
import application.entities.Category;
import application.helper.CategoryJsonConverter;
import application.helper.CategorySearchModel;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class CouchbaseCategoryDAOImpl implements CouchbaseCategoryDAO {

    private String connectionString = "localhost";
    private String username = "gepardec-user";
    private String password = "";
    private String bucketName = "gepardec-bucket";
    private String scopeName = "gepardec-scope";

    private String collection = "categories";

    @Override
    public void add(Category category, long id) {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(scopeName);
        Collection collection = scope.collection(this.collection);

        MutationResult mutationResult = collection.upsert(String.valueOf(id), category);

        cluster.disconnect();
    }

    @Override
    public Category read(long id) {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(scopeName);
        Collection collection = scope.collection(this.collection);

        GetResult result = null;

        try {
            result = collection.get(String.valueOf(id));
            return result.contentAs(Category.class);
        } catch (Exception ignored) {

        }

        return new Category();
    }

    @Override
    public List<Category> getAllCategories() {
        String query = "select * from `" + bucketName + "`.`" + scopeName + "`.`" + collection  + "`;";
        Cluster cluster = Cluster.connect(connectionString, username, password);
        QueryResult queryResult = cluster.query(query);
        Object[] list = queryResult.rowsAsObject().toArray();

        return CategoryJsonConverter.convertJsonObjectToCategory(list);
    }

    @Override
    public void remove(long id) {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(scopeName);
        Collection collection = scope.collection(this.collection);
        try {
            collection.remove(String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cluster.disconnect();
    }

    @Override
    public List<Category> search(CategorySearchModel searchModel) {
        String[] keywords = searchModel.getKeywords();
        long id = searchModel.getId();
        String query = "select * from `" + bucketName + "`.`" + scopeName + "`.`" + collection + "`";

        if (id > 0) {
            query = query + " USE KEYS '" + id +"' ";
        }

        if (keywords.length > 0) {
            //name
            for (int i = 0; i < keywords.length; i++) {
                if (i < (keywords.length - 1)) {
                    query = query + " where `name` like '" + keywords[i] +"' and";
                } else {
                    query = query + " where `name` like '" + keywords[i] +"' or";
                }
            }

            //details
            for (int i = 0; i < keywords.length; i++) {
                if (i < (keywords.length - 1)) {
                    query = query + " `details` like '" + keywords[i] + "' and ";
                } else {
                    query = query + " `details` like '" +keywords[i] + "';";
                }
            }
        }

        System.out.println(query);

        Cluster cluster = Cluster.connect(connectionString, username, password);
        QueryResult queryResult = cluster.query(query);
        Object[] list = queryResult.rowsAsObject().toArray();

        return CategoryJsonConverter.convertJsonObjectToCategory(list);
    }
}
