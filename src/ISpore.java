import java.util.List;

public interface ISpore {
    int nutrition = 0;
    List<ITekton> tekton = null;
    void affectInsect(Insect insect);
}
