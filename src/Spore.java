import java.util.ArrayList;
import java.util.List;

abstract class Spore {
    int nutrition = 5;
    Tekton tekton;

    protected Spore(){}

    protected Spore(Tekton tekton) {
            this.tekton = tekton;
    }

    public void affectInsect(Insect insect){
        //insect.effectsAdd(this);
    }

    public void destroySpore(){
        //tünjön el a képernyőről
    }
}