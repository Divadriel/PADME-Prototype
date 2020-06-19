package fr.limsi.Model;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.StaticVariableSet;
import fr.limsi.Model.Utils.Colors;
import fr.limsi.Model.Utils.Strings;
import fr.limsi.Model.Utils.Utils;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public AdaptationRules(){

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
        return Utils.ceilToNInteger(newValue, 5);
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

    /**
     * PSEUDO CODE FOCUS RULE
     * // colours
     * if(user.focus == "promotion"){
     *     apply promotion colours;
     * }
     * else{
     *     apply prevention colours;
     * }
     *
     *
     */

    public static JSONObject loadMotivationalMessages(UserModel user){
        if(user.getPromotion() < user.getPrevention()){
            return loadPreventionMessages();
        }
        else{
            return loadPromotionMessages();
        }
    }

    public static JSONObject loadColors(UserModel user){
        if(user.getPromotion() < user.getPrevention()){
            return loadPreventionColors();
        }
        else{
            return loadPromotionColors();
        }
    }

    private static JSONObject loadPromotionMessages(){
        JSONObject messages = new JSONObject();
        messages.put("MESS_EX_BEG", Strings.PROM_MESS_EX_BEG);
        messages.put("MESS_EX_MID1", Strings.PROM_MESS_EX_MID1);
        messages.put("MESS_EX_MID2", Strings.PROM_MESS_EX_MID2);
        messages.put("MESS_EX_END", Strings.PROM_MESS_EX_END);
        messages.put("MESS_PROFILE_CREATED", Strings.PROM_MESS_PROFILE_CREATED);
        messages.put("MESS_SESSION_END", Strings.PROM_MESS_SESSION_END);
        messages.put("ASCII_EX_BEG", Strings.PROM_ASCII_EX_BEG);
        messages.put("ASCII_EX_MID", Strings.PROM_ASCII_EX_MID);
        messages.put("ASCII_EX_END", Strings.PROM_ASCII_EX_END);
        return messages;
    }
    private static JSONObject loadPreventionMessages(){
        JSONObject messages = new JSONObject();
        messages.put("MESS_EX_BEG", Strings.PREV_MESS_EX_BEG);
        messages.put("MESS_EX_MID1", Strings.PREV_MESS_EX_MID1);
        messages.put("MESS_EX_MID2", Strings.PREV_MESS_EX_MID2);
        messages.put("MESS_EX_END", Strings.PREV_MESS_EX_END);
        messages.put("MESS_PROFILE_CREATED", Strings.PREV_MESS_PROFILE_CREATED);
        messages.put("MESS_SESSION_END", Strings.PREV_MESS_SESSION_END);
        messages.put("ASCII_EX_BEG", Strings.PREV_ASCII_EX_BEG);
        messages.put("ASCII_EX_MID", Strings.PREV_ASCII_EX_MID);
        messages.put("ASCII_EX_END", Strings.PREV_ASCII_EX_END);
        return messages;
    }

    private static JSONObject loadPromotionColors(){
        JSONObject colors = new JSONObject();
        colors.put("COL_BRIGHTER", Colors.PROM_COL_BRIGHTER);
        colors.put("COL_DARK", Colors.PROM_COL_DARK);
        colors.put("COL_DARKER", Colors.PROM_COL_DARKER);
        colors.put("COL_DARKEST", Colors.PROM_COL_DARKEST);
        colors.put("COL_MAIN", Colors.PROM_COL_MAIN);
        return colors;
    }

    private static JSONObject loadPreventionColors(){
        JSONObject colors = new JSONObject();
        colors.put("COL_BRIGHTER", Colors.PREV_COL_BRIGHTER);
        colors.put("COL_DARK", Colors.PREV_COL_DARK);
        colors.put("COL_DARKER", Colors.PREV_COL_DARKER);
        colors.put("COL_DARKEST", Colors.PREV_COL_DARKEST);
        colors.put("COL_MAIN", Colors.PREV_COL_MAIN);
        return colors;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
