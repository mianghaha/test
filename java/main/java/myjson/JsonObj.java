package myjson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class JsonObj implements Jsonable {
    JsonType type;
    private Object data;

    public JsonType getType() {
        return type;
    }

    public static JsonObj makeJsonObj(String json) throws IllegalJsonStringException {
        if(json.length() < 2) throw new IllegalJsonStringException("too short to be a json");

        JsonObj obj;
        if(json.charAt(0) == '{') {
            obj = new JsonDict(json);
        }
        else if(json.charAt(0) == '[') {
            obj = new JsonArray(json);
        }
        else throw new IllegalJsonStringException("not a json dict or json array");

        return obj;
    }

    public boolean isData() {
        return type.isData();
    }
}

enum JsonType {
    JSON_ARRAY,
    JSON_DICT,
    STRING,
    INTEGER,
    DOUBLE;

    public boolean isData() {
        return !(this == JSON_ARRAY || this == JSON_DICT);
    }
}
