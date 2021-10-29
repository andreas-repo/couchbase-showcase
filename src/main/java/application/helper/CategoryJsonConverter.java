package application.helper;

import application.entities.Category;
import com.couchbase.client.java.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class CategoryJsonConverter {

    public static Category convertJsonToCategory(Object object) {
        Category tempCategory = new Category();
        object = object.toString().replace("categories", "category");
        JsonObject jsonObject = JsonObject.fromJson((String) object);
        String tempString = jsonObject.get("category").toString();
        jsonObject = JsonObject.fromJson(tempString);
        tempCategory.setName(jsonObject.get("name").toString());
        tempCategory.setDetails(jsonObject.get("details").toString());
        return tempCategory;
    }

    public static List<Category> convertJsonObjectToCategory(Object[] objects) {
        List<Category> categoryList = new ArrayList<>();

        for (Object object : objects) {
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
}
