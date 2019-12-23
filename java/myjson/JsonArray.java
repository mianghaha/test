package myjson;

import java.util.ArrayList;
import java.util.List;

public class JsonArray extends JsonObj{
    private List<JsonObj> data;

    public JsonArray() {
        data = new ArrayList<JsonObj>();
        type = JsonType.JSON_ARRAY;
    }

    public JsonArray(String json) throws IllegalJsonStringException {
        this();
        if (json.charAt(0) != '[' || json.charAt(json.length()-1) != ']')
            throw new IllegalJsonStringException("Unmatched or missing brackets");
        SearchStatus status = SearchStatus.FOR_DATA;
        int depth = 0;
        boolean ignore = false;

        int startingPoint = -1;
        boolean integer = true;
        String value;
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
                case FOR_DATA: {
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
                            data.add(new JsonData(value));
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
                            status = SearchStatus.FOR_DATA;
                            value = json.substring(startingPoint, i);
                            try {
                                if (integer) data.add(new JsonData(Integer.parseInt(value)));
                                else {
                                    data.add(new JsonData(Double.parseDouble(value)));
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
                                data.add(makeJsonObj(subJson));
                                break;
                            }
                        }
                    }
                    break;
                }
                case FOR_COMMA: {
                    switch (c) {
                        case ',': {
                            status = SearchStatus.FOR_DATA;
                            break;
                        }
                        default: throw new IllegalJsonStringException("Missing comma before the next element", i);
                    }
                    break;
                }
            }
        }

        if (! status.welcomeToEnd())
            throw new IllegalJsonStringException("Json string ended half-way");
        if(status == SearchStatus.IN_NUMBER) {
            value = json.substring(startingPoint, json.length()-1);
            try {
                if (integer) data.add(new JsonData(Integer.parseInt(value)));
                else data.add(new JsonData(Double.parseDouble(value)));
            }
            catch (NumberFormatException e) {
                throw new IllegalJsonStringException("Illegal number format - " + e.getMessage());
            }
        }
        if (depth != 0) throw new IllegalJsonStringException("Json string ended half-way");

    }

    public void add(JsonObj value) {
        data.add(value);
    }

    public void remove(int index) {
        data.remove(index);
    }

    public JsonObj get(int index) {
        return data.get(index);
    }

    public String toJson() {
        if (data.size() == 0) return "[]";
        StringBuilder result = new StringBuilder();
        for(JsonObj obj : data) {
            result.append(obj.toJson())
                    .append(',');
        }

        return "[" + result.substring(0, result.length()-1) + "]";
    }

    @Override
    public Object toPlainObject() {
        List<Object> result = new ArrayList<>();
        for (JsonObj obj : data) {
            result.add(obj.toPlainObject());
        }
        return result;
    }

    private enum SearchStatus {
        FOR_DATA,
        IN_STRING,
        IN_NUMBER,
        IN_JSON,
        FOR_COMMA;

        public boolean welcomeToEnd() {
            return (this == FOR_DATA || this == FOR_COMMA);
        }
    }
}
