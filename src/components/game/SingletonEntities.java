package components.game;

import ecs.Component;

public class SingletonEntities implements Component {
    private final int ball;
    private final int attackingTeam;
    private final int defendingTeam;
    private final int player;


    private SingletonEntities(Builder b){
        this.attackingTeam = b.attackingTeam;
        this.defendingTeam = b.defendingTeam;
        this.ball = b.ball;
        this.player = b.player;
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
    public static Builder builder(
            int ball,
            int attack,
            int defence,
            int pitch,
            int player
    ){
        return new Builder(ball, attack, defence, pitch, player);
    }

    public static class Builder{
        private int ball;
        private int attackingTeam;
        private int defendingTeam;
        private int pitch;
        private int player;


        private Builder(
                int ball,
                int attack,
                int defence,
                int pitch,
                int player
        ) {
            this.ball = ball;
            this.attackingTeam = attack;
            this.defendingTeam = defence;
            this.pitch = pitch;
            this.player = player;
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


        public Builder player(int player){
            this.player = player;
            return this;
        }


        public SingletonEntities build(){
            return new SingletonEntities(this);
        }
    }
}
