package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FavoriteConverter;
import actions.views.FavoriteView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Favorite;
import models.validators.FavoriteValidator;

public class FavoriteService extends ServiceBase {

    public List<FavoriteView> getMyFavorite(EmployeeView employee) {

        List<Favorite> favorites = em.createNamedQuery(JpaConst.Q_FAV_GET_ALL_BY_EMPLOYEE, Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getResultList();
        return FavoriteConverter.toViewList(favorites);
    }

    public List<String> create(FavoriteView fv) {
        List<String> errors = FavoriteValidator.validate(fv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            fv.setCreatedAt(ldt);
            fv.setUpdatedAt(ldt);
            createInternal(fv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    public List<String> destroy(FavoriteView fv) {

        //バリデーションを行う
        List<String> errors = FavoriteValidator.validate(fv);

        if (errors.size() == 0) {

            destroyInternal(fv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    private void createInternal(FavoriteView fv) {

        em.getTransaction().begin();
        em.persist(FavoriteConverter.toModel(fv));
        em.getTransaction().commit();

    }

    public void destroyInternal(FavoriteView fv) {

        em.getTransaction().begin();
        em.remove(FavoriteConverter.toModel(fv));
        em.getTransaction().commit();
    }

    public FavoriteView getByEmployeeAndRecord(ReportView rv, EmployeeView ev) {

       Favorite favorite = em.createNamedQuery(JpaConst.Q_FAV_BY_EMPLOYEE_AND_REPORT, Favorite.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getSingleResult();
        return FavoriteConverter.toView(favorite);
    }


}
