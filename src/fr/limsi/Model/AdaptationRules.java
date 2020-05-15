package fr.limsi.Model;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import fr.limsi.Model.Utils.Utils;

public class AdaptationRules {

    private UserModel user;
    private Session session;
    private StaticVariableSet<Double> adaptationVariables;

    public AdaptationRules(UserModel user, Session session){

        this.user = user;
        this.session = session;
        adaptationVariables = new StaticVariableSet<Double>();
        adaptationVariables.set("initVal", this.session.getExerciseList().get(0).getDuration());
    }


    public double defineRuleForExerciseDurationAdaptation(String expression, Exercise exercise){

        DoubleEvaluator evaluator = new DoubleEvaluator();
        StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
        variables.set("initVal", exercise.getDuration());
        variables.set("nivAP", (double)(user.getPhysicalActivityLevel()));
        return evaluator.evaluate(expression, variables);
    }

    public double adaptExerciseDuration(double initialValue){
        // assess new value
        double newValue = initialValue * assessPALevelInfluence(); // * mean(completed) -- not already done
        // set it to closest 5 minutes, to avoid weird durations like 27.5 minutes
        return Utils.setDurationToClosestUpperFiveMinutes(newValue);
    }

    public double adaptExerciseDistance(double initialValue){

        return initialValue * assessPALevelInfluence(); // * mean(completed) -- not already done
    }

    private double assessPALevelInfluence(){
        if(user.getPhysicalActivityLevel() <= 10){
            return 0.2;
        }
        else{
            return user.getPhysicalActivityLevel() / 50;
        }
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
