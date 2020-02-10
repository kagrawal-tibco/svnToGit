package com.tibco.cep.pattern.util.callback;

import com.tibco.cep.pattern.matcher.master.Input;
import com.tibco.cep.pattern.matcher.master.Source;
import com.tibco.cep.pattern.matcher.response.Response;

/*
* Author: Ashwin Jayaprakash Date: Sep 1, 2009 Time: 11:48:05 AM
*/
public class CallTrace {
    protected Input input;

    protected Response response;

    protected Source optionalSource;

    public CallTrace(Input input, Response response) {
        this.input = input;
        this.response = response;
    }

    public CallTrace(Source optionalSource, Input input, Response response) {
        this(input, response);

        this.optionalSource = optionalSource;
    }

    public Input getInput() {
        return input;
    }

    public Response getResponse() {
        return response;
    }

    public Source getOptionalSource() {
        return optionalSource;
    }
}
