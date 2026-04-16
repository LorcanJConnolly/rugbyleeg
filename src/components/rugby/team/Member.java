package components.rugby.team;

import ecs.Component;

public class Member implements Component {
    private int team;

    public Member(int member){
        this.team = member;
    }

    public int getTeam(){
        return team;
    }
}
