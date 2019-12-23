package myjson;

public class IllegalJsonStringException extends Exception {
    private String moreInformation;
    private int indexOfWrongChar;

    public IllegalJsonStringException(String reason, int index) {
        moreInformation = reason;
        indexOfWrongChar = index;
    }

    public IllegalJsonStringException(String reason) {
        this(reason, -1);
    }

    @Override
    public String getMessage() {
        if (indexOfWrongChar == -1)
            return moreInformation;
        else return moreInformation + " at index of " + indexOfWrongChar;
    }
}
