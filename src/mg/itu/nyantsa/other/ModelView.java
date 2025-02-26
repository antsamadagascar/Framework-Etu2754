package mg.itu.nyantsa.other ;

import mg.itu.nyantsa.exception.ValidationError;
import java.util.HashMap;

public class ModelView {
    private HashMap<String, Object> data;
    private String url;
    
    private ValidationError validationError;
    private boolean hasErrors;

    public ModelView() {
        this.data = new HashMap<>();
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void add(String name, Object value) {
        this.data.put(name, value);
    }

    public void setValidationError(ValidationError error) {
        this.validationError = error;
        this.hasErrors = true;
        // Ajouter les erreurs et les valeurs dans data pour les rendre accessibles à la vue
        this.data.put("fieldErrors", error.getFieldErrors());
        this.data.put("fieldValues", error.getFieldValues());
    }

    public boolean hasErrors() {
        return hasErrors;
    }

}