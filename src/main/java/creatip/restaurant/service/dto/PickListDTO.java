package creatip.restaurant.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PickListDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    List<PickListData<Integer, String>> tableStatusList;
    List<PickListData<Integer, String>> catetoryStatusList;
    List<PickListData<Integer, String>> productStatusList;

    public PickListDTO() {
        this.tableStatusList = new ArrayList<PickListData<Integer, String>>();
        this.catetoryStatusList = new ArrayList<PickListData<Integer, String>>();
        this.productStatusList = new ArrayList<PickListData<Integer, String>>();
    }

    public List<PickListData<Integer, String>> getTableStatusList() {
        return tableStatusList;
    }

    public void setTableStatusList(List<PickListData<Integer, String>> tableStatusList) {
        this.tableStatusList = tableStatusList;
    }

    public void addTableStatus(Integer value, String description) {
        PickListData<Integer, String> data = new PickListData<Integer, String>();
        data.setValue(value);
        data.setDescription(description);
        this.tableStatusList.add(data);
    }

    public List<PickListData<Integer, String>> getCatetoryStatusList() {
        return catetoryStatusList;
    }

    public void setCatetoryStatusList(List<PickListData<Integer, String>> catetoryStatusList) {
        this.catetoryStatusList = catetoryStatusList;
    }

    public void addCatetoryStatus(Integer value, String description) {
        PickListData<Integer, String> data = new PickListData<Integer, String>();
        data.setValue(value);
        data.setDescription(description);
        this.catetoryStatusList.add(data);
    }

    public List<PickListData<Integer, String>> getProductStatusList() {
        return productStatusList;
    }

    public void setProductStatusList(List<PickListData<Integer, String>> productStatusList) {
        this.productStatusList = productStatusList;
    }

    public void addProductStatus(Integer value, String description) {
        PickListData<Integer, String> data = new PickListData<Integer, String>();
        data.setValue(value);
        data.setDescription(description);
        this.productStatusList.add(data);
    }

    public class PickListData<V, D> {

        private V value;
        private D description;

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public D getDescription() {
            return description;
        }

        public void setDescription(D description) {
            this.description = description;
        }
    }
}
