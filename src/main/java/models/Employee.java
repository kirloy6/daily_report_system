package models;
//従業員データのDTO　　テーブルデータ

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name=JpaConst.TABLE_EMP)
@NamedQueries({
    @NamedQuery(
            name=JpaConst.Q_EMP_GET_ALL,
            query = JpaConst.Q_EMP_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_EMP_COUNT,
            query = JpaConst.Q_EMP_COUNT_DEF),
    @NamedQuery(
            name = JpaConst.Q_EMP_COUNT_RESISTERED_BY_CODE,
            query = JpaConst.Q_EMP_COUNT_RESISTERED_BY_CODE_DEF),
    @NamedQuery(
            name = JpaConst.Q_EMP_GET_BY_CODE_AND_PASS,
            query = JpaConst.Q_EMP_GET_BY_CODE_AND_PASS_DEF)

})
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Employee {
    @Id
    @Column(name=JpaConst.EMP_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //社員番号
    @Column(name = JpaConst.EMP_COL_CODE, nullable = false, unique = true)//既に登録してある番号は登録できない
    private String code;

    //氏名
    @Column(name = JpaConst.EMP_COL_NAME, nullable = false)
    private String name;

    //パスワード
    @Column(name = JpaConst.EMP_COL_PASS, length = 64, nullable = false)//SHA256　ハッシュ化？
    private String password;

    //管理者権限
    @Column(name = JpaConst.EMP_COL_ADMIN_FLAG, nullable = false)
    private Integer adminFlag;

    //登録日時
    @Column(name = JpaConst.EMP_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    //更新日時
    @Column(name = JpaConst.EMP_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

    //削除の有無
    @Column(name = JpaConst.EMP_COL_DELETE_FLAG, nullable = false)
    private Integer deleteFlag;





}
