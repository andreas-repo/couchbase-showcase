package application.controller;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class CouchbaseSearchController implements Serializable {
    private static final long serialVersionUID = 2L;

    private String textInput;
    private String idInput;

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
}
