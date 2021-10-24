package application.controller;

import application.entities.Category;
import application.service.CouchbaseCategoryService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class CouchbaseController implements Serializable {
    private static final long serialVersionUUID = 1L;

    @Inject
    private CouchbaseCategoryService couchbaseCategoryService;


    private long id;
    private long removeId;
    private Category category = new Category();
    private long readId;
    private Category readCategory;
    private List<Category> list;

    @PostConstruct
    public void init() {
        this.list = this.couchbaseCategoryService.getAllCategories();
    }

    public void addCategory() {
        this.couchbaseCategoryService.add(category, id);

        category = new Category();
    }

    public void readCategory() {
        this.readCategory = this.couchbaseCategoryService.read(readId);
    }

    public void delete() {
        this.couchbaseCategoryService.remove(removeId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CouchbaseCategoryService getCouchbaseCategoryService() {
        return couchbaseCategoryService;
    }

    public void setCouchbaseCategoryService(CouchbaseCategoryService couchbaseCategoryService) {
        this.couchbaseCategoryService = couchbaseCategoryService;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getReadId() {
        return readId;
    }

    public void setReadId(long readId) {
        this.readId = readId;
    }

    public Category getReadCategory() {
        return readCategory;
    }

    public void setReadCategory(Category readCategory) {
        this.readCategory = readCategory;
    }

    public List<Category> getList() {
        return list;
    }

    public void setList(List<Category> list) {
        this.list = list;
    }

    public long getRemoveId() {
        return removeId;
    }

    public void setRemoveId(long removeId) {
        this.removeId = removeId;
    }
}
