package application.controller;

import application.entities.Category;
import application.helper.CategorySearchModel;
import application.service.CouchbaseCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBTransactionRolledbackException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;


@Named
@RequestScoped
public class CouchbaseSearchController implements Serializable {
    private static final long serialVersionUID = 2L;
    private static final Logger logger = LoggerFactory.getLogger("CouchbaseLogger");

    @Inject
    private CouchbaseCategoryService couchbaseCategoryService;

    private CategorySearchModel searchModel = new CategorySearchModel();

    private String textInput;
    private String idInput;
    private List<Category> list;

    public void startSearch() {
        try {
            this.list = this.couchbaseCategoryService.search(this.searchModel);
        } catch (EJBTransactionRolledbackException e) {
            logger.error("Missing input variable led to a NullPointerException while trying to fetch data!");
        }
    }

    public String getTextInput() {
        return textInput;
    }

    public void setTextInput(String textInput) {
        this.textInput = textInput;
    }

    public String getIdInput() {
        return idInput;
    }

    public void setIdInput(String idInput) {
        this.idInput = idInput;
    }

    public CategorySearchModel getSearchModel() {
        return searchModel;
    }

    public void setSearchModel(CategorySearchModel searchModel) {
        this.searchModel = searchModel;
    }

    public CouchbaseCategoryService getCouchbaseCategoryService() {
        return couchbaseCategoryService;
    }

    public void setCouchbaseCategoryService(CouchbaseCategoryService couchbaseCategoryService) {
        this.couchbaseCategoryService = couchbaseCategoryService;
    }

    public List<Category> getList() {
        return list;
    }

    public void setList(List<Category> list) {
        this.list = list;
    }
}
