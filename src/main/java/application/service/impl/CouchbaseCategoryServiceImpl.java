package application.service.impl;

import application.dao.CouchbaseCategoryDAO;
import application.entities.Category;
import application.helper.CategorySearchModel;
import application.service.CouchbaseCategoryService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class CouchbaseCategoryServiceImpl implements CouchbaseCategoryService {

    @Inject
    private CouchbaseCategoryDAO couchbaseCategoryDAO;

    @Override
    public void add(Category category, long id) {
        this.couchbaseCategoryDAO.add(category, id);
    }

    @Override
    public Category read(long id) {
        return this.couchbaseCategoryDAO.read(id);
    }

    @Override
    public List<Category> getAllCategories() {
        return this.couchbaseCategoryDAO.getAllCategories();
    }

    @Override
    public void remove(long id) {
        this.couchbaseCategoryDAO.remove(id);
    }

    @Override
    public List<Category> search(CategorySearchModel searchModel) {
        return this.couchbaseCategoryDAO.search(searchModel);
    }

    @Override
    public void pushList(long id, List<Category> list) {
        this.couchbaseCategoryDAO.pushList(id, list);
    }
}
