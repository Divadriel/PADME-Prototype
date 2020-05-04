package fr.limsi.Model;

import fr.limsi.Model.Utils.Strings;

public class AdaptationRules {

    private UserModel user;

    public AdaptationRules(UserModel user){
        this.user = user;
    }


    // compute rules to have an adaptation
    public float lengthPAMultiplier(){
        if(user.getPhysicalActivityLevel() == Strings.PA_NOT_ACTIVE){
            return 0.8f;
        }
        if(user.getPhysicalActivityLevel() == Strings.PA_VERY_ACTIVE){
            return 1.2f;
        }
        return 1.0f;
    }
    public float distancePAMultiplier(){
        if(user.getPhysicalActivityLevel() == Strings.PA_NOT_ACTIVE){
            return 0.6f;
        }
        if(user.getPhysicalActivityLevel() == Strings.PA_VERY_ACTIVE){
            return 1.1f;
        }
        return 1.0f;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
