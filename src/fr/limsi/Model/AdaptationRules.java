package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;

public class AdaptationRules {

    private UserModel user;

    public AdaptationRules(UserModel user){
        this.user = user;
    }


    public double defineRuleForExerciseDurationAdaptation(){
        return 0;
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
