package controllers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import actions.ActionBase;
import actions.UnknownAction;
import constants.ForwardConst;

/**
 * フロントコントローラ
 *
 */
@WebServlet("/")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FrontController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //パラメータに該当するActionクラスのインスタンス
        ActionBase action = getAction(request, response);//下のgetactionメソッドの値をactionに代入 多態性

        //サーブレットコンテキスト、リクエスト、レスポンスをActionインスタンスのフィールドに設定
        action.init(getServletContext(), request, response);//三つの値をセットしている
        //httpservletインターフェースメソッド

        //Actionクラスの処理を呼び出し
        action.process();
    }




    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * リクエストパラメータの値から該当するActionクラスのインスタンスを作成し、返却する
     * (例:パラメータが action=Employee の場合、actions.EmployeeActionオブジェクト)
     * @param request リクエスト
     * @param response レスポンス
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" }) //コンパイラ警告を抑制
    private ActionBase getAction(HttpServletRequest request, HttpServletResponse response) {
        Class type=null;
        ActionBase action = null;
        try {

            //リクエストからパラメータ"action"の値を取得 (例:"Employee"、"Report")
            String actionString = request.getParameter(ForwardConst.ACT.getValue());

            //該当するActionオブジェクトを作成 (例:リクエストからパラメータ action=Employee の場合、actions.EmployeeActionオブジェクト)
            type = Class.forName(String.format("actions.%sAction", actionString));//%sの部分にactionStringの値が入る

            //ActionBaseのオブジェクトにキャスト(例:actions.EmployeeActionオブジェクト→actions.ActionBaseオブジェクト)
            action = (ActionBase) (type.asSubclass(ActionBase.class)
                    .getDeclaredConstructor()
                    .newInstance());
            /*あまり気にしない。actionemployeeなどのタイプから親クラスのインスタンスを生成　多態性
             * employeeやunknown reportなどのインスタンスがactionにはいる。親クラスActionBaseから作られたもの。

             */

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException
                | IllegalArgumentException | InvocationTargetException| NoSuchMethodException e) {
            e.printStackTrace();

            //リクエストパラメータに設定されている"action"の値が不正の場合(例:action=xxxxx 等、該当するActionクラスがない場合)
            //エラー処理を行うActionオブジェクトを作成
            action = new UnknownAction();

        }
        return action; //問題なければactionを返す

    }

}