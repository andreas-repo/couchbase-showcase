package application.controller;

import application.entities.Category;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class MultipleCategoriesController {

    private List<Category> tempList = new ArrayList<>();
    private Category category = new Category();

    public void addCategoryToList() {
        Category tempCategory = category;
        tempList.add(tempCategory);
        category = new Category();

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
}
