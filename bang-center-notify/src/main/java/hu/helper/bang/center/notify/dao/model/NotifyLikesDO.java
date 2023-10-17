package hu.helper.bang.center.notify.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点赞列表DTO
 *
 * @Author : Jiang XingKun
 * @Date : 2023/2/22
 * @Description :
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotifyLikesDO extends NotifyDO {


    /**
     *  通知数量
     */
    private Integer number;

    /**
     * 通知名字
     */
    private String names;


    public NotifyLikesDO(String title, Integer number, String names) {
        super.setTitle(title);
        this.names=names;
        this.number=number;

    }
    public void numberPlus(){
        number++;
    }
    public void namePlus(String name){
        names+=";"+name;
    }
}
