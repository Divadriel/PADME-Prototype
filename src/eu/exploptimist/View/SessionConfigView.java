package eu.exploptimist.View;

import eu.exploptimist.Model.UserModel;
import javafx.scene.Parent;

public class SessionConfigView extends Parent {

    private UserModel user;
    private TraceView traceView;

    public SessionConfigView(UserModel usr, TraceView trcView){

        user = usr;
        traceView = trcView;
    }
}
