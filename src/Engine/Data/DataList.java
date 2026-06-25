package Engine.Data;
import java.util.ArrayList;

public class DataList {

    public record data(String file, String dataType) {};
    public static ArrayList<data> dataList = new ArrayList<>();


    public static void addToDataList(String file, String dataType) {
        dataList.add(new data(file, dataType));
    }
}
