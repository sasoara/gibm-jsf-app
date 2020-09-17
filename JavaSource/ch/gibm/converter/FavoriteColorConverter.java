package ch.gibm.converter;

import ch.gibm.entity.FavoriteColor;
import ch.gibm.facade.FavoriteColorFacade;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = ch.gibm.entity.FavoriteColor.class)
public class FavoriteColorConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        FavoriteColorFacade fvtColFacade = new FavoriteColorFacade();
        int langId;

        try {
            langId = Integer.parseInt(arg2);
        } catch (NumberFormatException exception) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Type the name of a favorite color and select it (or use the dropdown)", "Type the name of a favorite color and select it (or use the dropdown)"));
        }

        return  fvtColFacade.findFavoriteColor(langId);
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {

        if (arg2 == null) {
            return "";
        }
        FavoriteColor favoCol = (FavoriteColor) arg2;
        return String.valueOf(favoCol.getId());
    }
}
