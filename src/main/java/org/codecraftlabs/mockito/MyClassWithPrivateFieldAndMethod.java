package org.codecraftlabs.mockito;

public class MyClassWithPrivateFieldAndMethod {
    public String field1 = "";
    public String valueSetByPrivateMethod = "";
    private String hiddenField = "initial";

    public String getValue() {
        return hiddenField;
    }

    public String getValueSetByPrivateMethod() {
        return valueSetByPrivateMethod;
    }

    public String toBeMockedByMockito() {
        return "stuff";
    }

    private void meineMethod() {
        valueSetByPrivateMethod = "lalal";
    }
}
