package application.service;

import application.entities.Category;

import javax.ejb.Local;
import java.util.List;

@Local
public interface CouchbaseCategoryService {
    void add(Category category, long id);
    Category read(long id);
    List<Category> getAllCategories();
    void remove(long id);
}
