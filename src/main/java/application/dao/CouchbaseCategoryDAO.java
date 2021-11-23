package application.dao;

import application.entities.Category;
import application.helper.CategorySearchModel;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CouchbaseCategoryDAO {
    void add(Category category, long id);
    Category read(long id);
    List<Category> getAllCategories();
    void remove(long id);
    List<Category> search(CategorySearchModel searchModel);
    void update(Category category);
    void pushList(long id, List<Category> list);
}
