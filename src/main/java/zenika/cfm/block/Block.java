package zenika.cfm.block;

import java.util.Map;

public class Block {

    String id;

    String type;

    String parentid;


    public Block(String id, Map<String, Object> value) {
        this.id = id;
        type = (String) value.get("opcode");
        parentid = (String) value.get("parent");
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public String toString() {
        return type;
    }
}
