package components.game;

import ecs.Component;

public class SingletonEntities implements Component {
    private final int ball;
    private final int attackingTeam;
    private final int defendingTeam;


    private SingletonEntities(Builder b){
        this.attackingTeam = b.attackingTeam;
        this.defendingTeam = b.defendingTeam;
        this.ball = b.ball;
    }


    public int getAttack() {
        return attackingTeam;
    }

    public int getDefence(){
        return defendingTeam;
    }

    public int getBall(){
        return ball;
    }

    // Entry point.
    public static Builder builder(int ball, int attack, int defence, int pitch){
        return new Builder(ball, attack, defence, pitch);
    }

    public static class Builder{
        private int ball;
        private int attackingTeam;
        private int defendingTeam;
        private int pitch;


        private Builder(int ball, int attackingTeam, int defendingTeam, int pitch) {
            this.ball = ball;
            this.attackingTeam = attackingTeam;
            this.defendingTeam = defendingTeam;
            this.pitch = pitch;
        }


        public Builder ball(int ball){
            this.ball = ball;
            return this;
        }


        public Builder attackingTeam(int attackingTeam){
            this.attackingTeam = attackingTeam;
            return this;
        }


        public Builder defendingTeam(int defendingTeam){
            this.defendingTeam = defendingTeam;
            return this;
        }

        public Builder pitch(int pitch){
            this.pitch = pitch;
            return this;
        }


        public SingletonEntities build(){
            return new SingletonEntities(this);
        }
    }
}
