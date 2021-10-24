package application.helper;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Scope;

import javax.ejb.Singleton;

@Singleton
public class CouchbaseUtil {
    private static String connectionString = "localhost";
    private static String username = "gepardec-user";
    private static String password = "";
    private static String bucketName = "gepardec-bucket";
    private static String scopeName = "gepardec-scope";

    private static Cluster cluster;
    private static Bucket bucket;
    private static Scope scope;


    public CouchbaseUtil() {
        cluster = Cluster.connect(connectionString, username, password);
        bucket = cluster.bucket(bucketName);
        scope = bucket.scope(scopeName);
    }

    public static Cluster getCluster() {
        return cluster;
    }

    public static Bucket getBucket() {
        return bucket;
    }

    public static Scope getScope() {
        return scope;
    }
}
