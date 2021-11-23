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

    private final String connectionString = "localhost";
    private final String username = "gepardec-user";
    private final String password = "Amwil1988!?";
    private final String bucketName = "gepardec-bucket";
    private final String scopeName = "gepardec-scope";

    private String collection = "categories";
    private String collectionMultiple = "single-document";

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
        String query = "SELECT * FROM `" + bucketName + "`.`" + scopeName + "`.`" + collection  + "`;";
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
        String query = "SELECT * FROM `" + bucketName + "`.`" + scopeName + "`.`" + collection + "`";

        if (id > 0) {
            query = query + " USE KEYS '" + id +"' ";
        }

        if (keywords.length > 0) {
            //name
            for (int i = 0; i < keywords.length; i++) {
                if (i < (keywords.length - 1)) {
                    query = query + " WHERE `name` LIKE '" + keywords[i] +"' AND";
                } else {
                    query = query + " WHERE `name` LIKE '" + keywords[i] +"' OR";
                }
            }

            //details
            for (int i = 0; i < keywords.length; i++) {
                if (i < (keywords.length - 1)) {
                    query = query + " `details` LIKE '" + keywords[i] + "' AND ";
                } else {
                    query = query + " `details` LIKE '" +keywords[i] + "';";
                }
            }
        }

        Cluster cluster = Cluster.connect(connectionString, username, password);
        QueryResult queryResult = cluster.query(query);
        //System.out.println(queryResult.toString());
        Object[] list = queryResult.rowsAsObject().toArray();

        return CategoryJsonConverter.convertJsonObjectToCategory(list);
    }

    @Override
    public void update(Category category) {

    }

    @Override
    public void pushList(long id, List<Category> list) {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(scopeName);
        Collection collection = scope.collection(this.collectionMultiple);

        MutationResult mutationResult = collection.upsert(String.valueOf(id), list);

        cluster.disconnect();
    }
}
