package ch.gibm.dao;

import ch.gibm.entity.FavoriteColor;

public class FavoriteColorDAO extends GenericDAO<FavoriteColor> {

    private static final long serialVersionUID = 1L;

    public FavoriteColorDAO() {
        super(FavoriteColor.class);
    }

    public void delete(FavoriteColor favoriteColor) {
        super.delete(favoriteColor.getId());
    }

}
