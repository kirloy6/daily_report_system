package models.validators;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import actions.views.ReportView;
import constants.MessageConst;

//日報インスタンスに設定されている値のバリデーションを行うクラス

public class ReportValidator {

    /**
     * 日報インスタンスの各項目についてバリデーションを行う
     * @param rv 日報インスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(ReportView rv) {
        List<String> errors = new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateTitle(rv.getTitle());
        if (!titleError.equals("")) {
            errors.add(titleError);
        }

        //内容のチェック
        String contentError = validateContent(rv.getContent());
        if (!contentError.equals("")) {
            errors.add(contentError);
        }

        String startTimeError = validateStartTime(rv.getStartTime());
        if(!startTimeError.equals("")) {
            errors.add(startTimeError);
        }
        String endTimeError = validateEndTime(rv.getEndTime());
        if(!endTimeError.equals("")) {
            errors.add(endTimeError);
        }

        String timeError = validateTime(rv.getStartTime(),rv.getEndTime());
        if(!timeError.equals("")) {
            errors.add(timeError);
        }
        String startEndTimeError = validateStartEndTime(rv.getStartTime(),rv.getEndTime());
        if(!startEndTimeError.equals("")) {
            errors.add(startEndTimeError);
        }



        return errors;
    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title タイトル
     * @return エラーメッセージ
     */
    private static String validateTitle(String title) {
        if (title == null || title.equals("")) {
            return MessageConst.E_NOTITLE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param content 内容
     * @return エラーメッセージ
     */
    private static String validateContent(String content) {
        if (content == null || content.equals("")) {
            return MessageConst.E_NOCONTENT.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    private static String validateStartTime(LocalTime startTime) {
        if(startTime == null || startTime.equals("")) {
            return MessageConst.E_NOSTARTTIME.getMessage();
        }
        return "";
    }

    private static String validateEndTime(LocalTime endTime) {
        if(endTime== null || endTime.equals("")) {
            return MessageConst.E_NOENDTIME.getMessage();
        }
        return "";
    }
    private static String validateStartEndTime(LocalTime startTime,LocalTime endTime) {
        if(startTime == null || endTime == null) {
            return MessageConst.E_NOSTARTENDTIME.getMessage();
        }
        return "";
    }





    private static String validateTime(LocalTime startTime,LocalTime endTime) {
      if(!(endTime == null || endTime.equals("") ) && !(startTime == null || startTime.equals("")))
        if(endTime.isBefore(startTime)) {
            return MessageConst.E_TIME.getMessage();
        }
        return "";
    }

}


