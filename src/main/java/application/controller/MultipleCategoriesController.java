package application.controller;

import application.entities.Category;
import application.service.CouchbaseCategoryService;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class MultipleCategoriesController implements Serializable {

    @Inject
    private CouchbaseCategoryService couchbaseCategoryService;

    private List<Category> tempList = new ArrayList<>();
    private Category category = new Category();
    private long id;

    public void addCategoryToList() {
        Category tempCategory = category;
        tempList.add(tempCategory);
        category = new Category();
        System.out.println(tempList);
    }

    public void push() {
        this.couchbaseCategoryService.pushList(id, tempList);
    }

    public List<Category> getTempList() {
        return tempList;
    }

    public void setTempList(List<Category> tempList) {
        this.tempList = tempList;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
