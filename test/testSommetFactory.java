import Util.Client;
import Util.SommetFactory;

import java.util.Map;

public class testSommetFactory {

    public static void main(String[] args) {
        Map<Integer, Client>  clientMap = SommetFactory.getDataFromDb("data01");
        clientMap.values().stream().forEach(client -> {
            //System.out.println(client);
        });
    }
}
