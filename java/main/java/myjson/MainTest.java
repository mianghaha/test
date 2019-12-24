package myjson;

public class MainTest {
    public static void main(String[] args) {
        JsonDict obj = new JsonDict();
        JsonArray arr = new JsonArray();

        arr.add(new JsonData(1));
        arr.add(new JsonData(2));

        obj.add("a", arr);
        obj.add("b", new JsonData("something"));
        obj.add("c", new JsonData(2.2));

        Object o = obj.toPlainObject();
        System.out.println(o.getClass() + ":\n" + o);

        System.out.println(obj.toJson());

        //language=JSON
        String json = "{\"a\":-1,\"b\":2.2,\"c\":{\"c1\":\"c1 content\"}," +
                "\"d\":[1,\"d2\",{\"d3\":\"d3 content\"}],\"e\":[\"oh\",\"that's\",\"good\"]}";
        try {
            JsonObj obj2 = JsonObj.makeJsonObj(json);
            System.out.println(obj2.toJson());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
