package hu.helper.bang.center.commpost.controller.respond;

import hu.helper.bang.center.commpost.dao.model.CommentDo;

import java.util.HashMap;


/**
 * @author lin
 */
public class CommentRespond extends HashMap<String, HashMap<String, CommentDo>> {
    private final String itemName = "item ";
    private int countItem = -1;

    private final String subComment = "comment ";
    private int countComment = 0;

    public CommentRespond() {
        super();

    }

    public void createNewItem() {
        countItem++;
        put(itemName + countItem, new HashMap<>());
        countComment = 0;

    }

    public void addComment(CommentDo commentDo) {
        String name = itemName + countItem;
        HashMap<String, CommentDo> hashMap = get(name);
        hashMap.put(subComment +countComment, commentDo);
        put(name, hashMap);
        countComment++;
    }

    public void addParentComment(CommentDo parentComment) {
        String name = itemName + countItem;
        HashMap<String, CommentDo> hashMap = get(name);
        hashMap.put("parent", parentComment);
        put(name, hashMap);
    }


}
