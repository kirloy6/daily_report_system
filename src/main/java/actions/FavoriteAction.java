package actions;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.FavoriteView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Favorite;
import services.FavoriteService;
import services.ReportService;
import utils.DBUtil;

public class FavoriteAction extends ActionBase {

    private ReportService service;
    private FavoriteService fservice;

    @Override
    public void process() throws ServletException, IOException {
        service = new ReportService();
        fservice = new FavoriteService();
        //メソッドを実行
        invoke();
        service.close();
        fservice.close();

    }

    public void index() throws ServletException, IOException {

        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //指定されたページ数の一覧画面に表示する日報データを取得
        List<FavoriteView> favorites = fservice.getMyFavorite(ev);





        putRequestScope(AttributeConst.FAVORITES, favorites); //取得したいいねデータ

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_FAV_INDEX);
    }





    public void create() throws ServletException, IOException {

        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));
        //request.getParameter("id")と同じ　show.jspでnameが識別できなかった為、データが送られていなかった。


        FavoriteView fv = new FavoriteView();
        fv.setEmployee(ev);
        fv.setReport(rv);

        List<String> errors = fservice.create(fv);
        if (!(errors.size() > 0)) {


        //セッションに登録完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_FAVORITE.getMessage());

        //一覧画面にリダイレクト
        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW,rv.getId());//indexからshow()の時の様にid番号を指定する。
        }

    }





    public void destroy() throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        // ログインしている従業員情報
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);



        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));


        Favorite f = em.createNamedQuery(JpaConst.Q_FAV_BY_EMPLOYEE_AND_REPORT, Favorite.class)
                .setParameter("report", ReportConverter.toModel(rv)).setParameter("employee", EmployeeConverter.toModel(ev)).getSingleResult();


        em.getTransaction().begin();
        em.remove(f);
        em.getTransaction().commit();
        em.close();

        //セッションに登録完了のフラッシュメッセージを設定
        putSessionScope(AttributeConst.FLUSH, MessageConst.I_UNFAVORITE.getMessage());

        redirect(ForwardConst.ACT_REP, ForwardConst.CMD_SHOW, rv.getId());

    }


    }


