import java.util.ArrayList;

public class ParalyzingSpore extends Spore {

    public ParalyzingSpore(Tekton tekton) {
        this.nutrition = 5;
        this.tekton = tekton;
    }
    @Override
    public void affectInsect(Insect insect) {
        insect.effectedByParalyzingSpore();
    }
}
