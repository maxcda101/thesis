package ultis;

import models.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnhQuan on 8/19/2016.
 */
public class DataNow {
    private Node node;
    private List<Data> datas;

    public DataNow(Node node, List<Data> datas) {
        this.node = node;
        this.datas = datas;
    }

    public DataNow() {
        datas=new ArrayList<Data>();
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public List<Data> getDatas() {
        return datas;
    }

    public void setDatas(List<Data> datas) {
        this.datas = datas;
    }
    public void addData(Data data){
        datas.add(data);
    }
}
