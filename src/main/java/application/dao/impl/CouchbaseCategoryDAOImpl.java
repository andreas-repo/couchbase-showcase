package application.dao.impl;

import application.dao.CouchbaseCategoryDAO;
import application.entities.Category;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryResult;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CouchbaseCategoryDAOImpl implements CouchbaseCategoryDAO {

    private String connectionString = "localhost";
    private String username = "gepardec-user";
    private String password = "";
    private String bucketName = "gepardec-bucket";
    private String scopeName = "gepardec-scope";

    private String DOCUMENT_COLLECTION = "categories";

    @Override
    public void add(Category category, long id) {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(scopeName);
        Collection collection = scope.collection(DOCUMENT_COLLECTION);

        MutationResult mutationResult = collection.upsert(String.valueOf(id), category);

        cluster.disconnect();
    }

    @Override
    public Category read(long id) {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(scopeName);
        Collection collection = scope.collection(DOCUMENT_COLLECTION);

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
        String query = "select * from `gepardec-bucket`.`gepardec-scope`.`categories`;";
        Cluster cluster = Cluster.connect(connectionString, username, password);
        QueryResult queryResult = cluster.query(query);

        List<Category> categoryList = new ArrayList<>();

        Object[] list = queryResult.rowsAsObject().toArray();

        for (Object object : list) {
            Category tempCategory = new Category();
            object = object.toString().replace("categories", "category");
            JsonObject jsonObject = JsonObject.fromJson((String) object);
            String tempString = jsonObject.get("category").toString();
            jsonObject = JsonObject.fromJson(tempString);
            tempCategory.setName(jsonObject.get("name").toString());
            tempCategory.setDetails(jsonObject.get("details").toString());
            categoryList.add(tempCategory);
        }

        return categoryList;
    }

    @Override
    public void remove(long id) {
        Cluster cluster = Cluster.connect(connectionString, username, password);
        Bucket bucket = cluster.bucket(bucketName);
        Scope scope = bucket.scope(scopeName);
        Collection collection = scope.collection(DOCUMENT_COLLECTION);
        try {
            collection.remove(String.valueOf(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cluster.disconnect();
    }
}
