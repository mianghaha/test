package myjson;

public class JsonData extends JsonObj{
    private Object data;

    public JsonData(String value) {
        data = value;
        type = JsonType.STRING;
    }

    public JsonData(int value) {
        data = value;
        type = JsonType.INTEGER;
    }

    public JsonData(double value) {
        data = value;
        type = JsonType.DOUBLE;
    }

    public Object getData() {
        return data;
    }

    public String toJson() {
        String result = data.toString();
        if(type.equals(JsonType.STRING)) result = "\"" + result + "\"";
        return result;
    }

    @Override
    public Object toPlainObject() {
        return data;
    }
}
