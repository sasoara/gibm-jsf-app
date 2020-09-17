package ch.gibm.facade;

import ch.gibm.dao.EntityManagerHelper;
import ch.gibm.dao.FavoriteColorDAO;
import ch.gibm.entity.FavoriteColor;

import java.io.Serializable;
import java.util.List;

public class FavoriteColorFacade implements Serializable {
    private static final long serialVersionUID = 1L;

    private FavoriteColorDAO favoriteColorDAO = new FavoriteColorDAO();

    public void createFavoriteColor(FavoriteColor favoriteColor) {
        EntityManagerHelper.beginTransaction();
        favoriteColorDAO.save(favoriteColor);
        EntityManagerHelper.commitAndCloseTransaction();
    }

    public void updateFavoriteColor(FavoriteColor favoriteColor) {
        EntityManagerHelper.beginTransaction();
        favoriteColorDAO.update(favoriteColor);
        //FavoriteColor persistedFvtCol = favoriteColorDAO.find(favoriteColor.getId());
        //persistedFvtCol.setName(favoriteColor.getName());
        EntityManagerHelper.commitAndCloseTransaction();
    }

    public void deleteFavoriteColor(FavoriteColor favoriteColor) {
        EntityManagerHelper.beginTransaction();
        FavoriteColor persistedFvtCol = favoriteColorDAO.findReferenceOnly(favoriteColor.getId());
        favoriteColorDAO.delete(persistedFvtCol);
        EntityManagerHelper.commitAndCloseTransaction();
    }

    public FavoriteColor findFavoriteColor(int favoriteColorId) {
        EntityManagerHelper.beginTransaction();
        FavoriteColor favoriteColor = favoriteColorDAO.find(favoriteColorId);
        EntityManagerHelper.commitAndCloseTransaction();
        return favoriteColor;
    }

    public List<FavoriteColor> listAll() {
        EntityManagerHelper.beginTransaction();
        List<FavoriteColor> result = favoriteColorDAO.findAll();
        EntityManagerHelper.commitAndCloseTransaction();
        return result;
    }
}
