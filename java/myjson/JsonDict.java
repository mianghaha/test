package myjson;

import java.util.HashMap;
import java.util.Map;

public class JsonDict extends JsonObj{
    private Map<String, JsonObj> data;

    public JsonDict() {
        data = new HashMap<String, JsonObj>();
        type = JsonType.JSON_DICT;
    }

    public JsonDict(Map<String, String> map) {
        data = new HashMap<String, JsonObj>();
        for (String key : map.keySet()) {
            data.put(key, new JsonData(map.get(key)));
        }
        type = JsonType.JSON_DICT;
    }

    public JsonDict(String json) throws IllegalJsonStringException{
        this();
        if (json.charAt(0) != '{' || json.charAt(json.length()-1) != '}')
            throw new IllegalJsonStringException("Unmatched or missing brackets");

        SearchStatus status = SearchStatus.FOR_KEY;
        int depth = 0;
        boolean ignore = false;

        int startingPoint = -1;
        String key = null, value;
        boolean integer = true;
        for (int i = 1; i < json.length()-1; i++) {
            char c = json.charAt(i);
            if (c == ' ') continue;

            if (c == '\\') {
                ignore = true;
                continue;
            }
            if (ignore) {
                ignore = false;
                continue;
            }

            switch (status) {
                case FOR_KEY: {
                    switch (c) {
                        case '"': {
                            status = SearchStatus.IN_KEY;
                            startingPoint = i + 1;
                            break;
                        }
                        default: throw new IllegalJsonStringException("Illegal key format");
                    }
                    break;
                }
                case IN_KEY: {
                    switch (c) {
                        case '"': {
                            status = SearchStatus.FOR_COLON;
                            key = json.substring(startingPoint, i);
                            break;
                        }
                    }
                    break;
                }
                case FOR_COLON: {
                    switch (c) {
                        case ':': {
                            status = SearchStatus.FOR_VALUE;
                            break;
                        }
                        default: throw new IllegalJsonStringException("Missing colon after a key");
                    }
                    break;
                }
                case FOR_VALUE: {
                    switch (c) {
                        case '[': case '{': {
                            status = SearchStatus.IN_JSON;
                            depth ++;
                            startingPoint = i;
                            break;
                        }
                        case '"': {
                            status = SearchStatus.IN_STRING;
                            startingPoint = i + 1;
                            break;
                        }
                        default: {
                            status = SearchStatus.IN_NUMBER;
                            startingPoint = i;
                        }
                    }
                    break;
                }
                case IN_STRING: {
                    switch (c) {
                        case '"': {
                            status = SearchStatus.FOR_COMMA;
                            value = json.substring(startingPoint, i);
                            data.put(key, new JsonData(value));
                            break;
                        }
                    }
                    break;
                }
                case IN_NUMBER: {
                    switch (c) {
                        case '.': {
                            integer = false;
                            break;
                        }
                        case ',': {
                            status = SearchStatus.FOR_KEY;
                            value = json.substring(startingPoint, i);
                            try {
                                if (integer) data.put(key, new JsonData(Integer.parseInt(value)));
                                else {
                                    data.put(key, new JsonData(Double.parseDouble(value)));
                                    integer = true;
                                }
                            }
                            catch (NumberFormatException e) {
                                throw new IllegalJsonStringException("Illegal number format - " + e.getMessage());
                            }
                            break;
                        }
                    }
                    break;
                }
                case IN_JSON: {
                    switch (c) {
                        case '{': case '[': {
                            depth ++;
                            break;
                        }
                        case '}': case ']': {
                            depth --;
                            if (depth != 0) break;
                            else {
                                status = SearchStatus.FOR_COMMA;
                                String subJson = json.substring(startingPoint, i+1);
                                data.put(key, makeJsonObj(subJson));
                                break;
                            }
                        }
                    }
                    break;
                }
                case FOR_COMMA: {
                    switch (c) {
                        case ',': {
                            status = SearchStatus.FOR_KEY;
                            break;
                        }
                        default: throw new IllegalJsonStringException("Missing comma before a key-value pair");
                    }
                    break;
                }
            }
        }
        if(! status.welcomeToEnd())
            throw new IllegalJsonStringException("Json string ended half-way");
        if(status == SearchStatus.IN_NUMBER) {
            value = json.substring(startingPoint, json.length()-1);
            try {
                if (integer) data.put(key, new JsonData(Integer.parseInt(value)));
                else data.put(key, new JsonData(Double.parseDouble(value)));
            }
            catch (NumberFormatException e) {
                throw new IllegalJsonStringException("Illegal number format - " + e.getMessage());
            }
        }
        if (depth != 0) throw new IllegalJsonStringException("Json string ended half-way");
    }

    public void add(String key, JsonObj value) {
        data.put(key, value);
    }

    public void remove(String key) {
        data.remove(key);
    }

    public JsonObj get(String key) {
        return data.get(key);
    }

    public String toJson() {
        if (data.size() == 0) return "{}";
        StringBuilder result = new StringBuilder();
        for (String key : data.keySet()) {
            result.append('"')
                    .append(key)
                    .append("\":")
                    .append(data.get(key).toJson())
                    .append(',');
        }

        return "{" + result.substring(0, result.length()-1) + "}";
    }

    @Override
    public Object toPlainObject() {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, JsonObj> entry : data.entrySet()) {
            result.put(entry.getKey(), entry.getValue().toPlainObject());
        }
        return result;
    }

    private enum SearchStatus {
        FOR_KEY,
        IN_KEY,
        FOR_COLON,
        FOR_VALUE,
        IN_STRING,
        IN_NUMBER,
        IN_JSON,
        FOR_COMMA;

        public boolean welcomeToEnd(){
            return (this == FOR_KEY || this == FOR_COMMA || this == IN_NUMBER);
        }
    }
}



