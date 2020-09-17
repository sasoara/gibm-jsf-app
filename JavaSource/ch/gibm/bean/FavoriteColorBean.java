package ch.gibm.bean;


import ch.gibm.entity.FavoriteColor;
import ch.gibm.facade.FavoriteColorFacade;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean(name = "favoriteColorBean")
public class FavoriteColorBean extends AbstractBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private FavoriteColor favoriteColor;
    private List<FavoriteColor> favoriteColors;
    private FavoriteColorFacade favoriteColorFacade;

    public FavoriteColor getFavoriteColor() {
        if (favoriteColor == null) {
            favoriteColor = new FavoriteColor();
        }

        return favoriteColor;
    }

    public void setFavoriteColor(FavoriteColor favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public List<FavoriteColor> getAllFavoriteColors() {
        if (favoriteColors == null) {
            loadFavoriteColors();
        }

        return favoriteColors;
    }

    public FavoriteColorFacade getFavoriteColorFacade() {
        if (favoriteColorFacade == null) {
            favoriteColorFacade = new FavoriteColorFacade();
        }

        return favoriteColorFacade;
    }

    public void createFavoriteColor() {
        try {
            getFavoriteColorFacade().createFavoriteColor(favoriteColor);
            closeDialog();
            displayInfoMessageToUser("Created with success");
            loadFavoriteColors();
            resetFavoriteColor();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("A problem occurred while saving. Try again later");
            e.printStackTrace();
        }
    }

    public void updateFavoriteColor() {
        try {
            getFavoriteColorFacade().updateFavoriteColor(favoriteColor);
            closeDialog();
            displayInfoMessageToUser("Updated with success");
            loadFavoriteColors();
            resetFavoriteColor();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("A problem occurred while updating. Try again later");
            e.printStackTrace();
        }
    }

    public void deleteFavoriteColor() {
        try {
            getFavoriteColorFacade().deleteFavoriteColor(favoriteColor);
            closeDialog();
            displayInfoMessageToUser("Deleted with success");
            loadFavoriteColors();
            resetFavoriteColor();
        } catch (Exception e) {
            keepDialogOpen();
            displayErrorMessageToUser("A problem occurred while removing. Try again later");
            e.printStackTrace();
        }
    }

    private void loadFavoriteColors() {
        favoriteColors = getFavoriteColorFacade().listAll();
    }

    public void resetFavoriteColor() {
        favoriteColor = new FavoriteColor();
    }
}
